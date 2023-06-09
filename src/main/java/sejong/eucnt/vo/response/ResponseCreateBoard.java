package sejong.eucnt.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import sejong.eucnt.entity.UserEntity;
import sejong.eucnt.enumeration.CountryName;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCreateBoard {
    private Long id;
    private String title;
//    @Enumerated(EnumType.STRING)
//    private CountryName countryName;
    private String content;
}
