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
import sejong.eucnt.enumeration.CountryName;
import sejong.eucnt.repository.BoardRepository;
import sejong.eucnt.repository.UserRepository;
import sejong.eucnt.vo.request.RequestCreateBoard;
import sejong.eucnt.vo.request.RequestUpdateBoard;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public BoardFormDto createBoard(RequestCreateBoard requestCreateBoard, CountryName countryName) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        BoardFormDto boardFormDto = mapper.map(requestCreateBoard, BoardFormDto.class);
        boardFormDto.setCountryName(countryName);
        Long user_id = requestCreateBoard.getUser_id();

        if (user_id == null) {
            throw new IllegalArgumentException("사용자 ID가 null입니다.");
        }
        UserEntity userEntity = userRepository.findById(user_id)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        BoardEntity boardEntity = BoardEntity.boardEntity(boardFormDto, userRepository.findById(user_id).get());
        boardRepository.save(boardEntity);

        return mapper.map(boardEntity, BoardFormDto.class);
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
    public List<BoardFormDto> getBoardList(CountryName countryName) {
        List<BoardEntity> all = boardRepository.findAll();

        List<BoardEntity> boardEntities = new ArrayList<>();

        for(BoardEntity x : all){
            if(x.getCountryName() == countryName)
                boardEntities.add(x);
        }
        if (boardEntities.isEmpty()) {
            throw new EntityNotFoundException("해당 국가에 대한 게시물을 찾을 수 없습니다.");
        }

        List<BoardFormDto> boardFormDto = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        for(BoardEntity b : boardEntities){
            BoardFormDto dto = mapper.map(b, BoardFormDto.class);
            boardFormDto.add(dto);
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
