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
    private Long comments_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity board;
    private String comments;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    public static CommentEntity commentEntity(CommentFormDto commentFormDto, BoardEntity board, UserEntity user) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setComments(commentFormDto.getComments());
        commentEntity.setBoard(board);
        commentEntity.setUser(user);
        commentEntity.setComments_id(commentFormDto.getComments_id());
        return commentEntity;
    }
}
