package sejong.eucnt.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import sejong.eucnt.enumeration.Gender;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseRegister {
    private String userName;
}
