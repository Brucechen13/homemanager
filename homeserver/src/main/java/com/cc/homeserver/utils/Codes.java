package com.cc.homeserver.utils;

public interface Codes {

    /** 未登录 */
    int UNAUTHEN = 4401;

    /** 未授权，拒绝访问 */
    int UNAUTHZ = 4403;

    /** shiro相关的错误 */
    int SHIRO_ERR = 4444;

    /** 服务端异常 */
    int SERVER_ERR = 5500;

    /** 数据库异常 */
    int SQL_ERR = 6666;

}