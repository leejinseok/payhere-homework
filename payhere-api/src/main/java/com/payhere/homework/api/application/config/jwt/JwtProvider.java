package com.payhere.homework.api.application.config.jwt;


import com.payhere.homework.core.db.domain.owner.ShopOwner;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtProvider {

    private final Key key;

    private static final Long ACCESS_TOKEN_EXPIRE = (long) 1000 * 60 * 30; // 30분
    private static final Long REFRESH_TOKEN_EXPIRE = (long) 1000 * 60 * 60 * 24 * 15; // 15일

    public JwtProvider(final String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(final ShopOwner shopOwner) {
        Map<String, Object> claims = getClaimsFrom(shopOwner);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(shopOwner.getId()))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 만료 테스트 (test only)
    public String createExpiredToken(final ShopOwner member) {
        Map<String, Object> claims = getClaimsFrom(member);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(member.getId()))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() - ACCESS_TOKEN_EXPIRE))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(final ShopOwner shopOwner) {
        Map<String, Object> claims = getClaimsFrom(shopOwner);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(shopOwner.getId()))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private Map<String, Object> getClaimsFrom(final ShopOwner shopOwner) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", shopOwner.getId());
        return claims;
    }

    public boolean checkTokenExpired(Date expiration) {
        return expiration.getTime() <= new Date().getTime();
    }

    public Jws<Claims> parse(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }


}
