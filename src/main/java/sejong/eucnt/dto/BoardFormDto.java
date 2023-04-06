package sejong.eucnt.dto;

import lombok.Data;
import sejong.eucnt.entity.UserEntity;
import sejong.eucnt.enumeration.CountryName;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class BoardFormDto {
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private CountryName countryName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
