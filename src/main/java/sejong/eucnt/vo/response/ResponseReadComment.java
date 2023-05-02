package sejong.eucnt.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseReadComment {
    private Long comment_id;
    private Long user_id;
    private Long board_id;
    private String content;
}
