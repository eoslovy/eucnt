package sejong.eucnt.vo.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RequestUpdateBoard {
    private Long id;
    private String title;
    private String content;
}
