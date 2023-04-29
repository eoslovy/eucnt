package sejong.eucnt.service;

import org.springframework.stereotype.Service;
import sejong.eucnt.dto.BoardFormDto;
import sejong.eucnt.entity.BoardEntity;
import sejong.eucnt.enumeration.CountryName;
import sejong.eucnt.vo.request.RequestCreateBoard;
import sejong.eucnt.vo.request.RequestUpdateBoard;

import java.util.List;

@Service
public interface BoardService {
    BoardFormDto createBoard(RequestCreateBoard requestCreateBoard, CountryName countryName);
    BoardFormDto readBoard(Long id);
    List<BoardFormDto> getBoardList(CountryName countryName);
    BoardFormDto updateBoard(Long id, RequestUpdateBoard requestUpdateBoard);
    void deleteBoard(Long id);
}
