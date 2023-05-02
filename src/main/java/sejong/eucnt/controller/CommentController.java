package sejong.eucnt.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sejong.eucnt.dto.BoardFormDto;
import sejong.eucnt.dto.CommentFormDto;
import sejong.eucnt.enumeration.CountryName;
import sejong.eucnt.service.CommentService;
import sejong.eucnt.vo.request.RequestCreateComment;
import sejong.eucnt.vo.request.RequestUpdateBoard;
import sejong.eucnt.vo.request.RequestUpdateComment;
import sejong.eucnt.vo.response.ResponseCreateComment;
import sejong.eucnt.vo.response.ResponseReadComment;
import sejong.eucnt.vo.response.ResponseUpdateComment;

@RestController
@Slf4j
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{country_name}/comments")
    public ResponseEntity<ResponseCreateComment> createComment(@RequestBody RequestCreateComment requestCreateComment,
                                                               @PathVariable("country_name") CountryName countryName) {
        CommentFormDto commentFormDto = commentService.createComment(requestCreateComment, countryName);
        ResponseCreateComment responseCreateComment = new ModelMapper().map(commentFormDto, ResponseCreateComment.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseCreateComment);
    }

    @GetMapping("/{country_name}/comments/{comment_id}")
    public ResponseEntity<ResponseReadComment> readComment(@PathVariable("country_name") CountryName countryName,
                                                           @PathVariable("comment_id") Long id){
        CommentFormDto commentFormDto = commentService.readComment(id);
        ResponseReadComment responseReadComment = new ModelMapper().map(commentFormDto, ResponseReadComment.class);
        return ResponseEntity.ok(responseReadComment);
    }

    @PutMapping("/{country_name}/comments/{comment_id}")
    public ResponseEntity<ResponseUpdateComment> updateBoard(@PathVariable("country_name") CountryName countryName,
                                                             @PathVariable ("comment_id") Long id, @RequestBody RequestUpdateComment requestUpdateComment) {
        CommentFormDto commentFormDto = commentService.updateComment(id, requestUpdateComment);
        ResponseUpdateComment responseUpdateComment = new ModelMapper().map(commentFormDto, ResponseUpdateComment.class);
        return ResponseEntity.ok(responseUpdateComment);
    }

    @DeleteMapping("{country_name}/comments/{comment_id}")
    public ResponseEntity<?> deleteComment(@PathVariable("country_name") CountryName countryName, @PathVariable ("comment_id") Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("deleted successfully");
    }

}
