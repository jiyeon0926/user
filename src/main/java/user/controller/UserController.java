package user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.constants.GlobalConstants;
import user.dto.LoginRequestDto;
import user.dto.UserRequestDto;
import user.entity.User;
import user.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping
    public ResponseEntity<String> signup(@Valid @RequestBody UserRequestDto userRequestDto) {
        userService.signup(userRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto loginRequestDto,
                                        HttpServletRequest request) {
        User user = userService.login(loginRequestDto);

        HttpSession session = request.getSession();
        session.setAttribute(GlobalConstants.USER_AUTH, user.getId());

        return ResponseEntity.ok("로그인 되었습니다.");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate(); // 세션 무효화
        }

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}
