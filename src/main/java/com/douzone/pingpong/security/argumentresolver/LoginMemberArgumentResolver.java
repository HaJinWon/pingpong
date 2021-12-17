package com.douzone.pingpong.security.argumentresolver;

import com.douzone.pingpong.domain.member.Member;
import com.douzone.pingpong.security.SessionConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter 실행");
        // @Login 어노테이션이 있는지 체크
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);

        // 반환타입이 Member 인지 체크
        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

        // 둘다 참이면 true 반환 -> 아래 resolveArgument실행
        // false 면 실행하지않음
        return hasLoginAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolveArgument 실향");
        // request 가져오기
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        //session가져오기
        HttpSession session = request.getSession(false);

        //session이 없으면 null 을 반환
        if (session == null) {
            return null;
        }

        //session이 있으면, SessionConstants.LOGIN_MEMBER 이 벨류를 가지는 오브젝트 반환
        return session.getAttribute(SessionConstants.LOGIN_MEMBER);
    }
}
