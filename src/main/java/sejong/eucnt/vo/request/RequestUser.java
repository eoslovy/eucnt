package sejong.eucnt.vo.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import sejong.eucnt.enumeration.Gender;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
public class RequestUser {
    private Long id;
    private String email;
    private String userName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String password;
    private String secondPassword;
}
