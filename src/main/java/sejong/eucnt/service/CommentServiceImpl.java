package sejong.eucnt.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sejong.eucnt.dto.BoardFormDto;
import sejong.eucnt.dto.CommentFormDto;
import sejong.eucnt.entity.BoardEntity;
import sejong.eucnt.entity.CommentEntity;
import sejong.eucnt.entity.UserEntity;
import sejong.eucnt.enumeration.CountryName;
import sejong.eucnt.repository.BoardRepository;
import sejong.eucnt.repository.CommentRepository;
import sejong.eucnt.repository.UserRepository;
import sejong.eucnt.vo.request.RequestCreateComment;
import sejong.eucnt.vo.request.RequestUpdateBoard;
import sejong.eucnt.vo.request.RequestUpdateComment;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, BoardRepository boardRepository,UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentFormDto createComment(RequestCreateComment requestCreateComment, CountryName countryName, Long boardId) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CommentFormDto commentFormDto = mapper.map(requestCreateComment, CommentFormDto.class);

        UserEntity user = userRepository.findById(requestCreateComment.getUser_id())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다"));
        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시물을 찾을 수 없습니다"));

        CommentEntity commentEntity = CommentEntity.commentEntity(commentFormDto, board, user);
        commentRepository.save(commentEntity);

        commentFormDto.setComments_id(commentEntity.getComments_id());

        return commentFormDto;
    }
    @Override
    public List<CommentFormDto> readCommentsByBoardId(Long boardId) {
        List<CommentEntity> commentEntities = commentRepository.findByBoardId(boardId);

        if (commentEntities.isEmpty()) {
            throw new EntityNotFoundException("해당 게시물에 대한 댓글을 찾을 수 없습니다.");
        }

        List<CommentFormDto> commentFormDtos = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        for (CommentEntity commentEntity : commentEntities) {
            UserEntity user = commentEntity.getUser();
            CommentFormDto commentFormDto = mapper.map(commentEntity, CommentFormDto.class);
            commentFormDto.setUserName(user.getUserName());
            commentFormDtos.add(commentFormDto);
        }

        return commentFormDtos;
    }

    @Override
    public CommentFormDto updateComment(Long id, RequestUpdateComment requestUpdateComment) {
        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다"));

        commentEntity.setComments(requestUpdateComment.getComments());
        commentRepository.save(commentEntity);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CommentFormDto commentFormDto = mapper.map(commentEntity, CommentFormDto.class);
        return commentFormDto;
    }

    @Override
    public void deleteComment(Long id) {
        if(!commentRepository.existsById(id)) {
            throw new EntityNotFoundException("댓글을 찾을 수 없습니다");
        }
        commentRepository.deleteById(id);
    }
}
