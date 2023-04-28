package sejong.eucnt.service;

import org.springframework.stereotype.Service;
import sejong.eucnt.dto.BoardFormDto;
import sejong.eucnt.vo.request.RequestCreateBoard;
import sejong.eucnt.vo.request.RequestReadBoard;
import sejong.eucnt.vo.request.RequestUpdateBoard;

import java.util.List;

@Service
public interface BoardService {
    BoardFormDto createBoard(RequestCreateBoard requestCreateBoard);
    BoardFormDto readBoard(Long id);
    List<BoardFormDto> getBoardList();
    BoardFormDto updateBoard(Long id, RequestUpdateBoard requestUpdateBoard);
    void deleteBoard(Long id);
}
