package sejong.eucnt.service;

import org.springframework.stereotype.Service;
import sejong.eucnt.dto.BoardFormDto;
import sejong.eucnt.vo.request.RequestCreateBoard;

@Service
public interface BoardService {
    BoardFormDto createBoard(RequestCreateBoard requestCreateBoard);
}
