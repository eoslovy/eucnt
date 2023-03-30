package sejong.eucnt.service;

public interface JwtTokenService {
    String generateToken(String username);
    boolean validateToken(String jwtToken);
    String getUsernameFromToken(String jwtToken);
}
