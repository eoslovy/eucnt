package sejong.eucnt.vo.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestLogin {
    private String userName;
    private String password;

}
