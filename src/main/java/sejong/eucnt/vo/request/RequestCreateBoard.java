package sejong.eucnt.vo.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestCreateBoard {
    private Long user_id;
    private String title;
    private String content;
}
