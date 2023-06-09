package sejong.eucnt.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sejong.eucnt.dto.BoardFormDto;
import sejong.eucnt.dto.UserFormDto;
import sejong.eucnt.entity.BoardEntity;
import sejong.eucnt.service.JwtTokenService;
import sejong.eucnt.service.UserService;
import sejong.eucnt.vo.request.*;
import sejong.eucnt.vo.response.ResponseLogin;
import sejong.eucnt.vo.response.ResponseRegister;
import sejong.eucnt.vo.response.ResponseUpdatePassword;
import sejong.eucnt.vo.response.ResponseUser;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    private final UserService userService;
    private JwtTokenService jwtTokenService;

    @Autowired
    public UserController(UserService userService, JwtTokenService jwtTokenService) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLogin> login(@RequestBody RequestLogin requestLogin){
        UserFormDto userFormDto = userService.checkValidation(requestLogin);
        String JwtToken = jwtTokenService.generateToken(userFormDto.getUserName());


        ResponseLogin responseLogin = new ModelMapper().map(userFormDto, ResponseLogin.class);
        responseLogin.setToken(JwtToken);

        return ResponseEntity.status(HttpStatus.OK).body(responseLogin);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseRegister> createUser(@RequestBody RequestRegister requestRegister){
        UserFormDto userFormDto = userService.createUser(requestRegister);

        ResponseRegister responseRegister = new ModelMapper().map(userFormDto, ResponseRegister.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseRegister);
    }

    @PutMapping("/update-username/{user_id}")
    public ResponseEntity<ResponseRegister> updateUsername(@PathVariable("user_id") Long Id, @RequestBody RequestUpdateUsername requestUpdateUsername) {
        UserFormDto userFormDto = userService.updateUsername(Id, requestUpdateUsername);
        ResponseRegister responseRegister = new ModelMapper().map(userFormDto, ResponseRegister.class);
        return ResponseEntity.ok(responseRegister);
    }
    @PutMapping("/update-password/{user_id}")
    public ResponseEntity<ResponseUpdatePassword> updateUserPassword(@PathVariable("user_id") Long Id, @RequestBody RequestUpdatePassword requestUpdatePassword) {
        UserFormDto userFormDto = userService.updatePassword(Id, requestUpdatePassword);
        ResponseUpdatePassword responseUpdatePassword  = new ModelMapper().map(userFormDto, ResponseUpdatePassword.class);
        responseUpdatePassword.setStatus("success");

        return ResponseEntity.ok(responseUpdatePassword);
    }

    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<String> deleteUser(@PathVariable("user_id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/get-user/{user_id}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("user_id") Long id){
        UserFormDto userFormDto = userService.getUser(id);
        ResponseUser responseUser = new ModelMapper().map(userFormDto, ResponseUser.class);

        return ResponseEntity.ok(responseUser);
    }
}
