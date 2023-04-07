package sejong.eucnt.vo.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RequestUpdateBoard {
    private String title;
    private String content;
    private LocalDateTime updated_at;
}
