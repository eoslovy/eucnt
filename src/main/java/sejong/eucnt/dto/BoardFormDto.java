package sejong.eucnt.dto;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
    private String content;
    private String userName;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}
