package user.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class User extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 5, nullable = false)
    private String firstname; // 이름

    @Column(length = 3, nullable = false)
    private String lastname; // 성

    @Column(length = 10, unique = true, nullable = false)
    private String nickname; // 별명

    private boolean isDeleted = false; // true이면 탈퇴, false이면 탈퇴하지 않음

    public User() {}

    public User(String email, String password, String firstname, String lastname, String nickname) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
    }
}
