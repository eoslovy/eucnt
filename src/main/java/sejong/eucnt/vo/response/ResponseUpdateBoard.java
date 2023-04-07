package sejong.eucnt.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUpdateBoard {
    private String status;
    public void setStatus(String status) {
        this.status = status;
    }
}
