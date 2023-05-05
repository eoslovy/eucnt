package sejong.eucnt.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentFormDto {
    private Long comments_id;
    private String comments;
    private String userName;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}
