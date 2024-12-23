package user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import user.entity.User;

@Getter
public class UserRequestDto {

    @Email(message = "이메일 형식이 아닙니다.")
    @Size(max = 30)
    private String email;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "비밀번호는 최소 8자 이상, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."
    )
    private String password;

    @NotBlank(message = "firstname은 필수 입력 항목 입니다.")
    @Size(max = 5)
    private String firstname;

    @NotBlank(message = "lastname은 필수 입력 항목 입니다.")
    @Size(max = 3)
    private String lastname;

    @NotBlank(message = "nickname은 필수 입력 항목 입니다.")
    @Size(max = 10)
    private String nickname;

    public UserRequestDto(String email, String password, String firstname, String lastname, String nickname) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
    }

    public User toEntity() {
        return new User(
                this.email,
                this.password,
                this.firstname,
                this.lastname,
                this.nickname
        );
    }

    public void updatePassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }
}
