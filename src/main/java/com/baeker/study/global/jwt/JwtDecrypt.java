package com.baeker.study.global.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class JwtDecrypt {

    @Value("${custom.jwt.secret-key}")
    private String secretKeyPlain;
    private SecretKey cachedSecretKey;
    private Long memberId;


    public SecretKey getSecretKey() {
        if (cachedSecretKey == null)
            cachedSecretKey = _getSecretKey();

        return cachedSecretKey;
    }

    private SecretKey _getSecretKey() {
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }

    public Map<String, Object> getClaims(String token) {
        String body = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("body", String.class);

        return toMap(body);
    }

    private Map<String, Object> toMap(String jsonStr) {
        try {
            return new ObjectMapper().readValue(jsonStr, LinkedHashMap.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public Long getMemberId(String token) {
        if (this.memberId == null) {
            Map<String, Object> claims = getClaims(token);
            this.memberId = (long) claims.get("id");
        }

        return this.memberId;
    }
}
