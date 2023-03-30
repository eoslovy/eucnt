//package sejong.eucnt.security;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.jsonwebtoken.Jwts;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.core.env.Environment;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import sejong.eucnt.dto.UserFormDto;
//import sejong.eucnt.service.UserService;
//import sejong.eucnt.vo.request.RequestLogin;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Objects;
//
//@Slf4j
//public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//    private UserService userService;
//    private Environment env;
//    public AuthenticationFilter(AuthenticationManager authenticationManager,
//                                UserService userService,
//                                Environment env) {
//        super.setAuthenticationManager(authenticationManager);
//        this.userService = userService;
//        this.env = env;
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request,
//                                                HttpServletResponse response) throws AuthenticationException {
//        try {
//            RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
//
//            return getAuthenticationManager().authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            creds.getUserName(),
//                            creds.getPassword(),
//                            new ArrayList<>()
//                    )
//            );
//
//        } catch (IOException e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request,
//                                            HttpServletResponse response,
//                                            FilterChain chain,
//                                            Authentication authResult) throws IOException, ServletException {
//        String userName = ((User)authResult.getPrincipal()).getUsername();
//        UserFormDto userDetails = userService.getUserDetailsByUserName(userName);
//
//        String token = Jwts.builder()
//                .setSubject(userDetails.getUserName())
//                .setExpiration(new Date(System.currentTimeMillis() +
//                        Long.parseLong(Objects.requireNonNull(env.getProperty("token.expiration_time")))))
//                .compact();
//
//        response.addHeader("token", token);
//        response.addHeader("userName", userDetails.getUserName());
//    }
//}