package com.cc.homeserver.config;

import com.cc.homeserver.handle.WebSocketHandler;
import com.cc.homeserver.interceptor.WebSocketHandshakeInterceptor;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //1.注册WebSocket
        String websocket_url = "/websocket/socketServer.do";                        //设置websocket的地址
        registry.addHandler(webSocketHandler(), websocket_url).                          //注册Handler
                addInterceptors(new WebSocketHandshakeInterceptor());                   //注册Interceptor

        //2.注册SockJS，提供SockJS支持(主要是兼容ie8)
        String sockjs_url = "/sockjs/socketServer.do";                              //设置sockjs的地址
        registry.addHandler(webSocketHandler(), sockjs_url).                            //注册Handler
                addInterceptors(new WebSocketHandshakeInterceptor()).                   //注册Interceptor
                withSockJS();                                                           //支持sockjs协议
    }

    @Bean
    public TextWebSocketHandler webSocketHandler() {
        return new WebSocketHandler();
    }
}