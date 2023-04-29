package sejong.eucnt.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sejong.eucnt.dto.BoardFormDto;
import sejong.eucnt.enumeration.CountryName;
import sejong.eucnt.service.BoardService;
import sejong.eucnt.vo.request.RequestCreateBoard;
import sejong.eucnt.vo.request.RequestUpdateBoard;
import sejong.eucnt.vo.response.*;

import java.util.List;

@RestController
@Slf4j
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/boards/{country_name}/create")
    public ResponseEntity<ResponseCreateBoard> createBoard(@PathVariable("country_name")CountryName countryName,
                                                           @RequestBody RequestCreateBoard requestCreateBoard) {
        BoardFormDto boardFormDto = boardService.createBoard(requestCreateBoard);
        ResponseCreateBoard responseCreateBoard = new ModelMapper().map(boardFormDto, ResponseCreateBoard.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseCreateBoard);
    }

//    @GetMapping("/boards/{country_name}/read")
//    public ResponseEntity<ResponseReadBoard> readBoard(@PathVariable("country_name") CountryName countryName){
//        BoardFormDto boardFormDto = boardService.readBoard(id);
//        ResponseReadBoard responseReadBoard = new ModelMapper().map(boardFormDto, ResponseReadBoard.class);
//        return ResponseEntity.ok(responseReadBoard);
//    }

    @GetMapping("/boards/{country_name}/read/{board_id}")
    public ResponseEntity<ResponseReadPost> readBoard(@PathVariable("country_name") CountryName countryName, @PathVariable("board_id") Long id){
        BoardFormDto boardFormDto = boardService.readBoard(id);
        ResponseReadPost responseReadPost = new ModelMapper().map(boardFormDto, ResponseReadPost.class);
        return ResponseEntity.ok(responseReadPost);
    }

    @GetMapping("/boards/{country_name}/read")
    public ResponseEntity<ResponseReadBoard> getBoardList(@PathVariable("country_name") CountryName countryName) {
        List<BoardFormDto> boardFormDto = boardService.getBoardList();
        ResponseReadBoard responseBoardList = new ResponseReadBoard();
        responseBoardList.setBoardList(boardFormDto);
        return ResponseEntity.ok(responseBoardList);
    }

    @PutMapping("/boards/{country_name}/update/{board_id}")
    public ResponseEntity<ResponseUpdateBoard> updateBoard(@PathVariable("country_name") CountryName countryName,
                                                           @PathVariable ("board_id") Long id, @RequestBody RequestUpdateBoard requestUpdateBoard) {
        BoardFormDto boardFormDto = boardService.updateBoard(id, requestUpdateBoard);
        ResponseUpdateBoard responseUpdateBoard = new ModelMapper().map(boardFormDto, ResponseUpdateBoard.class);
        return ResponseEntity.ok(responseUpdateBoard);
    }

    @DeleteMapping("/boards/{country_name}/delete/{board_id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("country_name") CountryName countryName, @PathVariable ("board_id") Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok("deleted successfully");
    }
}
