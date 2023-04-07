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
import sejong.eucnt.vo.response.ResponseCreateBoard;
import sejong.eucnt.vo.response.ResponseReadBoard;
import sejong.eucnt.vo.response.ResponseRegister;

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

}
