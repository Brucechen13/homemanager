package com.cc.springdemo.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ShiroConfig {

    @Bean
    public Realm realm() {
        return new UserRealm();
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }
    /**
     * 注册安全管理,必须设置 SecurityManager
     *
     * @param oAuth2Realm    认证
     * @param sessionManager 缓存
     * @return
     */
//    @Bean
//    public SecurityManager securityManager(UserRealm oAuth2Realm, SessionManager sessionManager) {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        // 可以添加多个认证，执行顺序是有影响的
//        securityManager.setRealm(oAuth2Realm);
//        securityManager.setSessionManager(sessionManager);
//        return securityManager;
//    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();
        // 由于demo1展示统一使用注解做访问控制，所以这里配置所有请求路径都可以匿名访问
        chain.addPathDefinition("/**", "anon"); // all paths are managed via annotations

        //访问控制
        chain.addPathDefinition("/user/login", "anon");//可以匿名访问
        chain.addPathDefinition("/page/401", "anon");//可以匿名访问
        chain.addPathDefinition("/page/403", "anon");//可以匿名访问
        chain.addPathDefinition("/t4/hello", "anon");//可以匿名访问

        chain.addPathDefinition("/index", "authc");//需要登录
        chain.addPathDefinition("/t4/user", "user");//已登录或“记住我”的用户可以访问
        chain.addPathDefinition("/t4/mvnBuild", "authc,perms[mvn:install]");//需要mvn:build权限
        chain.addPathDefinition("/t4/gradleBuild", "authc,perms[gradle:build]");//需要gradle:build权限
        chain.addPathDefinition("/t4/js", "authc,roles[js]");//需要js角色
        chain.addPathDefinition("/t4/python", "authc,roles[python]");//需要python角色

        // shiro 提供的登出过滤器，访问指定的请求，就会执行登录，默认跳转路径是"/"，或者是"shiro.loginUrl"配置的内容
        // 由于application-shiro.yml中配置了 shiro:loginUrl: /page/401，返回会返回对应的json内容
        // 可以结合/user/login和/t1/js接口来测试这个/t4/logout接口是否有效
        chain.addPathDefinition("/t4/logout", "anon,logout");


        return chain;
    }

//    @Bean
//    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
//        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
//        shiroFilter.setSecurityManager(securityManager);
//        //自定义一个oauth2拦截器，不设置就是使用默认的拦截器
//        /*Map<String, Filter> filters = new HashMap<>();
//        filters.put("oauth2", new OAuth2Filter());
//        shiroFilter.setFilters(filters);*/
//        //拦截器
//        //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->
//        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//        Map<String, String> filterMap = new LinkedHashMap<>();
//        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
//        filterMap.put("/user/logout", "logout");
//        // 验证码
//        filterMap.put("/user/captcha.jpg", "anon");
//        // 设置系统模块下访问需要权限
//        filterMap.put("/user/login", "anon");
//        // 自定义的拦截
//        //filterMap.put("/sys/**", "oauth2");
//        filterMap.put("/user/**", "authc");
//        // 登陆的 url
//        shiroFilter.setLoginUrl("/user/login");
//        // 登陆成功跳转的 url
//        shiroFilter.setSuccessUrl("/");
//        // 未授权的 url
//        // shiroFilter.setUnauthorizedUrl("/login.html");
//        //未授权界面;
//        shiroFilter.setUnauthorizedUrl("/403");
//        shiroFilter.setFilterChainDefinitionMap(filterMap);
//        return shiroFilter;
//    }


    /**
     * Shiro生命周期处理器
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    /**
     * 开启Shiro的注解，
     * (如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,
     * 并在必要时进行安全逻辑验证 * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }
    /**
     * 开启 shiro aop注解支持.
     *
     * @param securityManager
     * @return
     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
//        advisor.setSecurityManager(securityManager);
//        return advisor;
//    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();//散列算法:MD2、MD5、SHA-1、SHA-256、SHA-384、SHA-512等。
        hashedCredentialsMatcher.setHashAlgorithmName("SHA-256");
        hashedCredentialsMatcher.setHashIterations(1);//散列的次数，默认1次， 设置两次相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }
    /**
     * 注册身份验证
     * @param hashedCredentialsMatcher 凭证匹配器
     * @return
     */
    @Bean
    public UserRealm oAuth2Realm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        UserRealm oAuth2Realm = new UserRealm();
        oAuth2Realm.setCredentialsMatcher(hashedCredentialsMatcher);
        return oAuth2Realm;
    }
}
