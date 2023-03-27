package sejong.eucnt.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import sejong.eucnt.dto.UserFormDto;
import sejong.eucnt.vo.request.RequestLogin;
import sejong.eucnt.vo.request.RequestUser;

public interface UserService extends UserDetailsService {
    UserFormDto checkValidation(RequestLogin requestLogin);

    UserFormDto createUser(RequestUser requestUser);

    UserFormDto getUserDetailsByEmail(String userName);

    UserFormDto updateUser(Long id, RequestUser userFormDto);
}
