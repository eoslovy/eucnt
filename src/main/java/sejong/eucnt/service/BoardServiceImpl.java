package sejong.eucnt.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sejong.eucnt.dto.BoardFormDto;
import sejong.eucnt.entity.BoardEntity;
import sejong.eucnt.repository.BoardRepository;
import sejong.eucnt.repository.UserRepository;
import sejong.eucnt.vo.request.RequestCreateBoard;
import sejong.eucnt.vo.request.RequestUpdateBoard;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityExistsException;

@Service
@Transactional
@Slf4j
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BoardFormDto createBoard(RequestCreateBoard requestCreateBoard) {
        Long boardId = requestCreateBoard.getId();
        if (boardId != null && boardRepository.existsById(boardId)) {
            throw new EntityExistsException("게시물 아이디 " + boardId + "에 해당하는 게시물이 이미 존재합니다");
        }
//        UserEntity userEntity = userRepository.findById(requestCreateBoard.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다"));
        // BoardEntity 생성
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setTitle(requestCreateBoard.getTitle());
        boardEntity.setCountryName(requestCreateBoard.getCountryName());
        boardEntity.setContent(requestCreateBoard.getContent());
//        boardEntity.setUser(userEntity);

        // BoardEntity 저장
        boardRepository.save(boardEntity);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        BoardFormDto boardFormDto = mapper.map(requestCreateBoard, BoardFormDto.class);

        return boardFormDto;
    }

    @Override
    public BoardFormDto readBoard(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다"));

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        BoardFormDto boardFormDto = mapper.map(boardEntity, BoardFormDto.class);

        return boardFormDto;
    }

    @Override
    public List<BoardFormDto> getBoardList() {
        List<BoardEntity> boardEntities = boardRepository.findAll();

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        List<BoardFormDto> boardFormDto = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntities) {
            boardFormDto.add(mapper.map(boardEntity, BoardFormDto.class));
        }

        return boardFormDto;
    }

    @Override
    public BoardFormDto updateBoard(Long id, RequestUpdateBoard requestUpdateBoard) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다"));

        // updateBoard() 메서드에 필요한 필드들을 RequestUpdateBoard로부터 가져와서 BoardEntity에 반영합니다.
        boardEntity.setTitle(requestUpdateBoard.getTitle());
        boardEntity.setContent(requestUpdateBoard.getContent());

        // BoardEntity 저장
        boardRepository.save(boardEntity);

        // 수정된 BoardFormDto를 반환합니다.
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        BoardFormDto boardFormDto = mapper.map(boardEntity, BoardFormDto.class);
        return boardFormDto;
    }

    @Override
    public void deleteBoard(Long id) {
        if(!boardRepository.existsById(id)) {
            throw new EntityNotFoundException("게시물을 찾을 수 없습니다");
        }
        boardRepository.deleteById(id);
    }
}
