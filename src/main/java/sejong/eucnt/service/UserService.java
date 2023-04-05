package sejong.eucnt.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import sejong.eucnt.dto.UserFormDto;
import sejong.eucnt.vo.request.RequestLogin;
import sejong.eucnt.vo.request.RequestRegister;
import sejong.eucnt.vo.request.RequestUpdatePassword;
import sejong.eucnt.vo.request.RequestUpdateUsername;

@Service
public interface UserService extends UserDetailsService {
    UserFormDto checkValidation(RequestLogin requestLogin);

    UserFormDto createUser(RequestRegister requestRegister);

    UserFormDto getUserDetailsByUserName(String userName);

    UserFormDto updateUsername(Long id, RequestUpdateUsername userFormDto);

    UserFormDto updatePassword(Long id, RequestUpdatePassword userFormDto);

    void deleteUser(Long id);
}
