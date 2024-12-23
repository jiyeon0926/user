package user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import user.dto.LoginRequestDto;
import user.dto.UserRequestDto;
import user.entity.User;
import user.repository.UserRepository;
import user.util.PasswordEncoder;
import user.util.SlackNotifier;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SlackNotifier slackNotifier;

    @Transactional
    public void signup(UserRequestDto userRequestDto) {
        String encodedPassword = PasswordEncoder.encode(userRequestDto.getPassword());
        userRequestDto.updatePassword(encodedPassword);

        userRepository.save(userRequestDto.toEntity());
    }

    public User login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());

        if (user == null || !PasswordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 이메일 혹은 잘못된 비밀번호 입니다.");
        }

        String message = user.getEmail() + " 사용자가 로그인을 했습니다!";
        slackNotifier.sendNotification(message);

        return user;
    }
}
