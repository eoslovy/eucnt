package sejong.eucnt.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sejong.eucnt.dto.UserFormDto;
import sejong.eucnt.enumeration.Gender;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
@ToString
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "userName")
    private String userName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String password;
    private String secondPassword;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardEntity> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
    public static UserEntity createUser(UserFormDto userFormDto, BCryptPasswordEncoder passwordEncoder){
        UserEntity userEntity = new UserEntity();
        String password = passwordEncoder.encode(userFormDto.getPassword());

        userEntity.setUserName(userFormDto.getUserName());
        userEntity.setGender(userFormDto.getGender());
        userEntity.setPassword(password);
        userEntity.setSecondPassword(password);

        return userEntity;
    }
}
