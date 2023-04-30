package sejong.eucnt.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sejong.eucnt.dto.BoardFormDto;
import sejong.eucnt.entity.BoardEntity;
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
        BoardFormDto boardFormDto = boardService.createBoard(requestCreateBoard, countryName);
        ResponseCreateBoard responseCreateBoard = new ModelMapper().map(boardFormDto, ResponseCreateBoard.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseCreateBoard);
    }

    @GetMapping("/boards/{country_name}/read/{board_id}")
    public ResponseEntity<BoardFormDto> readBoard(@PathVariable("country_name") CountryName countryName,
                                                      @PathVariable("board_id") Long id){
        BoardFormDto boardFormDto = boardService.readBoard(id);
        return ResponseEntity.ok(boardFormDto);
    }

    @GetMapping("/boards/{country_name}/read")
    public ResponseEntity<List<BoardFormDto>> getBoardList(@PathVariable("country_name") CountryName countryName) {
        return ResponseEntity.ok(boardService.getBoardList(countryName));
    }

    @PutMapping("/boards/{country_name}/update/{board_id}")
    public ResponseEntity<BoardFormDto> updateBoard(@PathVariable("country_name") CountryName countryName,
                                                           @PathVariable ("board_id") Long id, @RequestBody RequestUpdateBoard requestUpdateBoard) {
        BoardFormDto boardFormDto = boardService.updateBoard(id, requestUpdateBoard);
        return ResponseEntity.ok(boardFormDto);
    }

    @DeleteMapping("/boards/{country_name}/delete/{board_id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("country_name") CountryName countryName, @PathVariable ("board_id") Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok("deleted successfully");
    }
}
