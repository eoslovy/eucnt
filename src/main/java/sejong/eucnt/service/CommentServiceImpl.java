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
import sejong.eucnt.enumeration.CountryName;
import sejong.eucnt.repository.BoardRepository;
import sejong.eucnt.repository.CommentRepository;
import sejong.eucnt.vo.request.RequestCreateComment;
import sejong.eucnt.vo.request.RequestUpdateBoard;
import sejong.eucnt.vo.request.RequestUpdateComment;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, BoardRepository boardRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public CommentFormDto createComment(RequestCreateComment requestCreateComment, CountryName countryName) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CommentFormDto commentFormDto = mapper.map(requestCreateComment, CommentFormDto.class);

        Optional<BoardEntity> board = boardRepository.findById(requestCreateComment.getBoard_id());

        CommentEntity commentEntity = CommentEntity.commentEntity(commentFormDto, board.get());
        commentRepository.save(commentEntity);

        commentFormDto.setComment_id(commentEntity.getComment_id());

        return commentFormDto;
    }

    @Override
    public CommentFormDto readComment(Long id) {
        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다"));

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CommentFormDto commentFormDto = mapper.map(commentEntity, CommentFormDto.class);
        commentFormDto.setComment_id(commentEntity.getComment_id());
        commentFormDto.setBoard_id(commentEntity.getBoard().getId());

        return commentFormDto;
    }

    @Override
    public CommentFormDto updateComment(Long id, RequestUpdateComment requestUpdateComment) {
        CommentEntity commentEntity = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다"));

        commentEntity.setContent(requestUpdateComment.getContent());
        commentRepository.save(commentEntity);

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CommentFormDto commentFormDto = mapper.map(commentEntity, CommentFormDto.class);
        commentFormDto.setComment_id(commentEntity.getComment_id());
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
