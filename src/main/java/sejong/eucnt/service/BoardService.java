package sejong.eucnt.service;

import org.springframework.stereotype.Service;
import sejong.eucnt.dto.BoardFormDto;
import sejong.eucnt.vo.request.RequestCreateBoard;
import sejong.eucnt.vo.request.RequestReadBoard;

@Service
public interface BoardService {
    BoardFormDto createBoard(RequestCreateBoard requestCreateBoard);
    BoardFormDto readBoard(Long id);
}
