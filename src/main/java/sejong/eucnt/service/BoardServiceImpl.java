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
}
