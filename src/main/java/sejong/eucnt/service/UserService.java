package sejong.eucnt.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import sejong.eucnt.dto.UserFormDto;
import sejong.eucnt.vo.request.RequestLogin;
import sejong.eucnt.vo.request.RequestRegister;

public interface UserService extends UserDetailsService {
    UserFormDto checkValidation(RequestLogin requestLogin);

    UserFormDto createUser(RequestRegister requestRegister);

    UserFormDto getUserDetailsByUserName(String userName);

    UserFormDto updateUser(Long id, RequestRegister userFormDto);
}
