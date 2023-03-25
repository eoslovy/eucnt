package sejong.eucnt.dto;

import lombok.Data;
import sejong.eucnt.enumeration.Gender;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class UserFormDto {
    private Long id;
    private String email;
    private String userName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String password;
    private String secondPassword;
}
