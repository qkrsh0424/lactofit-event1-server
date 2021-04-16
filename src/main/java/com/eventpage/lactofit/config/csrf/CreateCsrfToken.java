package com.eventpage.lactofit.config.csrf;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;

public class CreateCsrfToken {

    public void responseCsrfToken(HttpServletRequest request, HttpServletResponse response, String csrfJwtSecret){
        CookieCsrfTokenRepository csrfTokenRepo = new CookieCsrfTokenRepository();
        CsrfToken newCsrfToken = csrfTokenRepo.generateToken(request);
        csrfTokenRepo.setCookiePath("/");
        csrfTokenRepo.setCookieName("psrf_token");
        csrfTokenRepo.setCookieHttpOnly(false);
        csrfTokenRepo.setCookieDomain("localhost");
        // csrfTokenRepo.setSecure(true);
        csrfTokenRepo.saveToken(newCsrfToken, request, response);

        String csrfJwtToken = getCsrfJwt(newCsrfToken, csrfJwtSecret);

        ResponseCookie resCookieCsrfToken = ResponseCookie.from("xt_jwt", csrfJwtToken)
                .path("/")
                .httpOnly(true)
                .sameSite("Strict")
                // .secure(true)
                .maxAge(CsrfExtInterface.CSRF_COOKIE_EXPIRE_TIME)
                .build();
                
        response.addHeader(HttpHeaders.SET_COOKIE, resCookieCsrfToken.toString());
    }

    private String getCsrfJwt(CsrfToken csrfToken, String csrfJwtSecret){
        Map<String, Object> jwtHeader = new HashMap<>();
        jwtHeader.put("alg", "HMAC512");
        jwtHeader.put("typ", "JWT");

        String jwtToken = JWT.create()
                .withSubject("JWT XSRF")   // sub 서브젝트
                .withHeader(jwtHeader)
                .withExpiresAt(new Date(System.currentTimeMillis() + (CsrfExtInterface.CSRF_TOKEN_EXPIRE_TIME)))  // exp 만료시간
                .withIssuedAt(new Date(System.currentTimeMillis())) // iat 발급시간
                .withClaim("xti", csrfToken.getToken())    // xti xsrf token id
                .sign(Algorithm.HMAC512(csrfJwtSecret));
        return jwtToken;
    }
}
