package sejong.eucnt.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import sejong.eucnt.dto.BoardFormDto;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseReadBoard {
    private List<BoardFormDto> boardList;
    private int page;
    private int totalPages;
    private String status;
    public List<BoardFormDto> getBoardList() {
        return boardList;
    }

    public void setBoardList(List<BoardFormDto> boardList) {
        this.boardList = boardList;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
