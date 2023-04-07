package sejong.eucnt.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sejong.eucnt.dto.BoardFormDto;
import sejong.eucnt.entity.BoardEntity;
import sejong.eucnt.entity.UserEntity;
import sejong.eucnt.repository.BoardRepository;
import sejong.eucnt.repository.UserRepository;
import sejong.eucnt.vo.request.RequestCreateBoard;
import sejong.eucnt.vo.request.RequestReadBoard;
import sejong.eucnt.vo.request.RequestUpdateBoard;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

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
        UserEntity userEntity = userRepository.findById(requestCreateBoard.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        // BoardEntity 생성
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setTitle(requestCreateBoard.getTitle());
        boardEntity.setCountryName(requestCreateBoard.getCountryName());
        boardEntity.setContent(requestCreateBoard.getContent());
        boardEntity.setUser(userEntity);
        boardEntity.setCreated_at(LocalDateTime.now());
        boardEntity.setUpdated_at(LocalDateTime.now());

        // BoardEntity 저장
        boardRepository.save(boardEntity);
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        BoardFormDto boardFormDto = mapper.map(requestCreateBoard, BoardFormDto.class);

        return boardFormDto;
    }

    @Override
    public BoardFormDto readBoard(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Board not found"));

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        BoardFormDto boardFormDto = mapper.map(boardEntity, BoardFormDto.class);

        return boardFormDto;
    }

    @Override
    public BoardFormDto updateBoard(Long id, RequestUpdateBoard requestUpdateBoard) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Board not found"));

        // updateBoard() 메서드에 필요한 필드들을 RequestUpdateBoard로부터 가져와서 BoardEntity에 반영합니다.
        boardEntity.setTitle(requestUpdateBoard.getTitle());
        boardEntity.setContent(requestUpdateBoard.getContent());
        boardEntity.setUpdated_at(LocalDateTime.now());

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
        boardRepository.deleteById(id);
    }
}