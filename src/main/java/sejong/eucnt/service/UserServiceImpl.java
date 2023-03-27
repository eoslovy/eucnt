package sejong.eucnt.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sejong.eucnt.dto.UserFormDto;
import sejong.eucnt.entity.UserEntity;
import sejong.eucnt.repository.UserRepository;
import sejong.eucnt.vo.request.RequestLogin;
import sejong.eucnt.vo.request.RequestUser;

import java.util.ArrayList;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserFormDto checkValidation(RequestLogin requestLogin) {
        UserEntity byEmail = userRepository.findByEmail(requestLogin.getEmail());

        if(byEmail == null)
            throw new IllegalStateException("존재하지 않는 회원입니다.");

        if(!passwordEncoder.matches(requestLogin.getPassword(), byEmail.getPassword()))
            throw new IllegalStateException("비밀번호가 틀렸습니다.");

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return mapper.map(requestLogin, UserFormDto.class);
    }

    @Override
    public UserFormDto createUser(RequestUser requestUser) {
        UserEntity byEmail = userRepository.findByEmail(requestUser.getEmail());

        if(byEmail != null)
            throw new IllegalStateException("이미 존재하는 회원입니다.");

        if(!requestUser.getPassword().equals(requestUser.getSecondPassword()))
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");

        UserEntity byUserName = userRepository.findByUserName(requestUser.getUserName());

        if(byUserName != null)
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserFormDto userFormDto = mapper.map(requestUser, UserFormDto.class);

        userRepository.save(UserEntity.createUser(userFormDto, passwordEncoder));

        return userFormDto;
    }

    @Override
    public UserFormDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null)
            throw new UsernameNotFoundException(email);

        return new ModelMapper().map(userEntity, UserFormDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null)
            throw new UsernameNotFoundException(email);

        return new User(userEntity.getEmail(), userEntity.getPassword(),
                true , true, true, true,
                new ArrayList<>());
    }

    @Override
    public UserFormDto updateUser(Long id, RequestUser requestUser) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // UserFormDto에서 새로운 유저네임을 받아와서 user 객체의 username을 업데이트합니다.
        userEntity.setUserName(requestUser.getUserName());

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserFormDto userFormDto = mapper.map(requestUser, UserFormDto.class);

        userRepository.save(userEntity); // 변경된 회원 정보를 저장합니다.
        return userFormDto;
    }
}
