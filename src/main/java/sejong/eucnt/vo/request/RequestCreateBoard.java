package sejong.eucnt.vo.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import sejong.eucnt.entity.UserEntity;
import sejong.eucnt.enumeration.CountryName;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RequestCreateBoard {
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private CountryName countryName;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private UserEntity user;
    private String content;
}
