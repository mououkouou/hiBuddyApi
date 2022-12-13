package com.example.hibuddy.api.configuration.jwt;


import com.example.hibuddy.api.common.RedisService;
import com.example.hibuddy.api.exception.MessageKey;
import com.example.hibuddy.api.exception.RefreshTokenExpiredException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;
    @Value("${spring.jwt.blacklist.access-token}")
    private String blacklistPrefix;

    private final long ACCESS_TOKEN_VALID_TIME = Duration.ofDays(1).toMillis();
    private final long REFRESH_TOKEN_VALID_TIME = Duration.ofDays(90).toMillis();

    private final CustomUserDetailService customUserDetailService;
    private final RedisService redisService;

    public String createAccessToken(String email) {
        return createToken(email, ACCESS_TOKEN_VALID_TIME);
    }

    public String createRefreshToken(String email) {
        String refreshToken = createToken(email, REFRESH_TOKEN_VALID_TIME);
        redisService.setValues(email, refreshToken, Duration.ofMillis(REFRESH_TOKEN_VALID_TIME));
        return refreshToken;
    }

    private String createToken(String email, Long tokenValidTime) {
        Claims claims = Jwts.claims().setSubject(email);
        Date date = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + tokenValidTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication validateToken(HttpServletRequest request, String token) {
        String exception = "exception";

        try {
            String expiredToken = redisService.getValues(blacklistPrefix + token);
            if (expiredToken != null) {
                throw new ExpiredJwtException(null, null, "토큰이 만료되었습니다.");
            }
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return getAuthentication(token);
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException e) {
            request.setAttribute(exception, "토큰의 형식을 확인하세요");
        } catch (ExpiredJwtException e) {
            request.setAttribute(exception, "토큰이 만료되었습니다.");
        } catch (IllegalArgumentException e) {
            request.setAttribute(exception, "JWT compact of handler are invalid");
        }
        return null;
    }

    private Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public void checkRefreshToken(String email, String refreshToken) {
        String getRefresh = redisService.getValues(email);
        if (!refreshToken.equals(getRefresh)) {
            throw new RefreshTokenExpiredException(MessageKey.EXPIRED_REFRESH_TOKEN);
        }
    }

    public void logout(String email, String token) {
        long expiredAccessTokenTime = getExpiration(token).getTime() - new Date().getTime();
        redisService.setValues(blacklistPrefix + token, email, Duration.ofMillis(expiredAccessTokenTime));
        redisService.deleteValues(email);
    }

    public Date getExpiration(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    }
}
