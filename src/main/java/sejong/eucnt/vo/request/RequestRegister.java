package sejong.eucnt.vo.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import sejong.eucnt.enumeration.Gender;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
public class RequestRegister {
    private String userName;
    private String password;
    private String SecondPassword;
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
