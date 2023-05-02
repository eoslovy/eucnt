package sejong.eucnt.service;

import org.springframework.stereotype.Service;
import sejong.eucnt.dto.CommentFormDto;
import sejong.eucnt.enumeration.CountryName;
import sejong.eucnt.vo.request.RequestCreateComment;
import sejong.eucnt.vo.request.RequestUpdateBoard;
import sejong.eucnt.vo.request.RequestUpdateComment;

@Service
public interface CommentService {
    CommentFormDto createComment(RequestCreateComment requestCreateComment, CountryName countryName);
    CommentFormDto readComment(Long id);
    CommentFormDto updateComment(Long id, RequestUpdateComment requestUpdateComment);
    void deleteComment(Long id);
}
