package com.douzone.pingpong.config.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

import java.util.Collections;


@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/pub");         // send (prefix) / 발행 : 클라이언트
        config.enableSimpleBroker("/sub");      // room 구독 : 대화방
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")
                .withSockJS();
    }
}






//@Configuration
//@EnableWebSocketMessageBroker // Stomp V2
//public class WebSockConfig implements WebSocketMessageBrokerConfigurer {
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/sub");  // 메시지 구독하는 요청의 prefix
//        config.setApplicationDestinationPrefixes("/pub");       // 메시지를 발행하는 요청의 prefix
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws-stomp").withSockJS();
//        /**
//         * SockJs란? => 낮은 브라우저에서도 webSocket을 사용할 수 있게끔 하는 라이브러리
//         * sockJs 클라이언트가 Websocket 핸드셰이크를 하기 위해 연결할 endpoint를 지정
//         * 클라이언트가 연결되고 http://localhost:8080/pingpong-stomp 으로 웹소켓 통신이 가능한지 확인한 후,
//         * 응답이 websocket:true 이면 웹소켓 연결된다.
//         */
//    }
//    /**
//     * endpoint : /ws/pingpong/chat
//     * 도메인이 다른서버에서도 접속가능하게끔 CORS : setAllowedorigins(*) 세팅
//     *
//     * # CORS :  한 출처에서 실행 중인 웹 애플리케이션이 다른 출처의 선택한 자원에 접근할 수 있는 권한을
//     * 부여하도록 브라우저에 알려주는 체제
//     * 웹 애플리케이션은 리소스가 자신의 출처(도메인, 프로토콜, 포트)와 다를 때
//     * 교차 출처 HTTP 요청을 실행합
//     */
//}