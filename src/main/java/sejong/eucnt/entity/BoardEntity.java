package sejong.eucnt.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sejong.eucnt.dto.BoardFormDto;
import sejong.eucnt.enumeration.CountryName;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@Setter
@ToString
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private CountryName countryName;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public static BoardEntity boardEntity(BoardFormDto boardFormDto) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setTitle(boardFormDto.getTitle());
        boardEntity.setCountryName(boardFormDto.getCountryName());
        boardEntity.setContent(boardFormDto.getContent());
        boardEntity.setCreated_at(boardFormDto.getCreated_at());
        boardEntity.setUpdated_at(boardFormDto.getUpdated_at());

        return boardEntity;
    }
}
