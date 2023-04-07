package sejong.eucnt.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sejong.eucnt.dto.BoardFormDto;
import sejong.eucnt.dto.UserFormDto;
import sejong.eucnt.entity.BoardEntity;
import sejong.eucnt.enumeration.CountryName;
import sejong.eucnt.service.BoardService;
import sejong.eucnt.vo.request.RequestCreateBoard;
import sejong.eucnt.vo.request.RequestReadBoard;
import sejong.eucnt.vo.request.RequestUpdateBoard;
import sejong.eucnt.vo.response.ResponseCreateBoard;
import sejong.eucnt.vo.response.ResponseReadBoard;
import sejong.eucnt.vo.response.ResponseRegister;
import sejong.eucnt.vo.response.ResponseUpdateBoard;

@RestController
@Slf4j
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/boards/{country_name}/create")
    public ResponseEntity<ResponseCreateBoard> createBoard(@PathVariable("country_name")CountryName countryName, @RequestBody RequestCreateBoard requestCreateBoard) {
        BoardFormDto boardFormDto = boardService.createBoard(requestCreateBoard);
        ResponseCreateBoard responseCreateBoard = new ModelMapper().map(boardFormDto, ResponseCreateBoard.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseCreateBoard);
    }

    @GetMapping("/boards/{country_name}/read/{id}")
    public ResponseEntity<ResponseReadBoard> readBoard(@PathVariable("country_name") CountryName countryName, @PathVariable("id") Long id){
        BoardFormDto boardFormDto = boardService.readBoard(id);
        ResponseReadBoard responseReadBoard = new ModelMapper().map(boardFormDto, ResponseReadBoard.class);
        responseReadBoard.setStatus("success");
        return ResponseEntity.ok(responseReadBoard);
    }

    @PutMapping("/boards/{country_name}/update/{id}")
    public ResponseEntity<ResponseUpdateBoard> updateBoard(@PathVariable("country_name") CountryName countryName,
                                                           @PathVariable Long id, @RequestBody RequestUpdateBoard requestUpdateBoard) {
        BoardFormDto boardFormDto = boardService.updateBoard(id, requestUpdateBoard);
        ResponseUpdateBoard responseUpdateBoard = new ModelMapper().map(boardFormDto, ResponseUpdateBoard.class);
        responseUpdateBoard.setStatus("success");
        return ResponseEntity.ok(responseUpdateBoard);
    }

    @DeleteMapping("/boards/{country_name}/delete/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable("country_name") CountryName countryName, @PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.ok("User deleted successfully");
//        return ResponseEntity.noContent().build();
    }
}
