package sejong.eucnt.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sejong.eucnt.dto.UserFormDto;
import sejong.eucnt.service.UserService;
import sejong.eucnt.vo.request.RequestLogin;
import sejong.eucnt.vo.request.RequestRegister;
import sejong.eucnt.vo.response.ResponseLogin;
import sejong.eucnt.vo.response.ResponseRegister;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLogin> login(@RequestBody RequestLogin requestLogin){
        UserFormDto userFormDto = userService.checkValidation(requestLogin);

        ResponseLogin responseLogin = new ModelMapper().map(userFormDto, ResponseLogin.class);

        return ResponseEntity.status(HttpStatus.OK).body(responseLogin);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseRegister> createUser(@RequestBody RequestRegister requestRegister){
        UserFormDto userFormDto = userService.createUser(requestRegister);

        ResponseRegister responseRegister = new ModelMapper().map(userFormDto, ResponseRegister.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseRegister);
    }

    @PutMapping("/update/{user_id}")
    public ResponseEntity<ResponseRegister> updateUser(@PathVariable("user_id") Long Id, @RequestBody RequestRegister requestRegister) {
        UserFormDto userFormDto = userService.updateUser(Id, requestRegister);
        ResponseRegister responseRegister = new ModelMapper().map(userFormDto, ResponseRegister.class);
        return ResponseEntity.ok(responseRegister);
    }
}
