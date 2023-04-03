package sejong.eucnt.vo.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestUpdatePassword {
    private String userName;
    private String password;
    private String newPassword;
    private String secondNewPassword;
}
