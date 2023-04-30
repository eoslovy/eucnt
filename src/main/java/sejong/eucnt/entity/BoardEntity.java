package sejong.eucnt.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sejong.eucnt.dto.BoardFormDto;
import sejong.eucnt.enumeration.CountryName;
import sejong.eucnt.global.BaseEntity;
import sejong.eucnt.global.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@Setter
@ToString
public class BoardEntity extends BaseTimeEntity {
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
    public static BoardEntity boardEntity(BoardFormDto boardFormDto, UserEntity user) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setTitle(boardFormDto.getTitle());
        boardEntity.setCountryName(boardFormDto.getCountryName());
        boardEntity.setContent(boardFormDto.getContent());
        boardEntity.setUser(user);

        return boardEntity;
    }
}
