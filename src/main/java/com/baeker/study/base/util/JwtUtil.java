package com.baeker.study.base.util;

import com.baeker.study.base.error.exception.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Map;

import static com.baeker.study.base.error.ErrorResponse.JWT_INVALID_EXCEPTION;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private SecretKey cachedSecretKey;

    @Value("${custom.jwt.secret-key}")
    private String secretKeyPlain;

    private SecretKey _getSecretKey() {
        // 키를 Base64 인코딩함
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
        // 인코딩된 키를 이용하여 SecretKey 객체 생성
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }

    public SecretKey getSecretKey() {
        if (cachedSecretKey == null) cachedSecretKey = _getSecretKey();

        return cachedSecretKey;
    }

    public Map<String, Object> getClaims(String token) {
        String body = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("body", String.class);
        return Ut.toMap(body);
    }

    public String getClaimValue(String token, String key){
        try {
            Map<String, Object> claims = getClaims(token);
            return claims.get(key).toString();
        } catch (Exception ignored) {}
        throw new JwtException(JWT_INVALID_EXCEPTION.getErrorMsg());
    }

}