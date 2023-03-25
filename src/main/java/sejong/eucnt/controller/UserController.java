package sejong.eucnt.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sejong.eucnt.dto.UserFormDto;
import sejong.eucnt.entity.UserEntity;
import sejong.eucnt.service.UserService;
import sejong.eucnt.vo.request.RequestLogin;
import sejong.eucnt.vo.request.RequestUser;
import sejong.eucnt.vo.response.ResponseLogin;
import sejong.eucnt.vo.response.ResponseUser;

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
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser requestUser){
        UserFormDto userFormDto = userService.createUser(requestUser);

        ResponseUser responseUser = new ModelMapper().map(userFormDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }
}
