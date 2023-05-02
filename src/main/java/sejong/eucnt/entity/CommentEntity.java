package sejong.eucnt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sejong.eucnt.dto.BoardFormDto;
import sejong.eucnt.dto.CommentFormDto;
import sejong.eucnt.global.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board;
    private String content;
    public static CommentEntity commentEntity(CommentFormDto commentFormDto, BoardEntity board) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentFormDto.getContent());
        commentEntity.setBoard(board);
        commentEntity.setComment_id(commentFormDto.getComment_id());
        return commentEntity;
    }
}
