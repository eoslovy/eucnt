package sejong.eucnt.vo.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestCreateComment {
    private Long user_id;
    private String comments;
}
