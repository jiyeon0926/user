package user.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import user.constants.GlobalConstants;
import user.exception.UnauthorizedException;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws UnauthorizedException {
        HttpSession session = request.getSession(false); // request 객체 내에 session이 없으면 null을 반환

        if (session == null) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        if (session.getAttribute(GlobalConstants.USER_AUTH) == null) {
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        // 인증 성공
        return true;
    }
}
