package sejong.eucnt.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentFormDto {
    private Long comment_id;
    private Long board_id;
    private String content;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}
