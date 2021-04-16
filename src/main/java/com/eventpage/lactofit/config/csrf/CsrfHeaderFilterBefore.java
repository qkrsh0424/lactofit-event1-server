package com.eventpage.lactofit.config.csrf;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * CsrfHeaderFilterBefore과 csrf 필터를 통과하게되면, csrf 토큰들을 재갱신 시켜준다음 계속해서 필터를 태운다.
 */
@Slf4j
public class CsrfHeaderFilterBefore extends OncePerRequestFilter {

    private String csrfJwtSecret;

    public CsrfHeaderFilterBefore(String csrfJwtSecret) {
        this.csrfJwtSecret = csrfJwtSecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // log.info("==============csrf filter before here================");

        // Get 요청이 들어오면 그냥 통과시킨다.
        if (request.getMethod().equals("GET") || request.getMethod().equals("get")) {
            // System.out.println("CsrfHeaderFilterBefore Get Method fulfill.");
            filterChain.doFilter(request, response);
            return;
        }
        CreateCsrfToken createCsrfToken = new CreateCsrfToken();
        try {
            String csrfHeaderTokenValue = request.getHeader("x-xsrf-token");
            Cookie csrfJwtTokenCookie = WebUtils.getCookie(request, "xt_jwt");

            if (validateJwtToken(csrfJwtTokenCookie)) {
                String csrfJwtTokenValue = JWT.require(Algorithm.HMAC512(csrfJwtSecret)).build()
                        .verify(csrfJwtTokenCookie.getValue()).getClaim("xti").asString();
                if (csrfHeaderTokenValue.equals(csrfJwtTokenValue)) {
                    filterChain.doFilter(request, response);
                } else {
                    createCsrfToken.responseCsrfToken(request, response, csrfJwtSecret);
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    return;
                }
            } else {
                createCsrfToken.responseCsrfToken(request, response, csrfJwtSecret);
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }
        } catch (NullPointerException e) {
            createCsrfToken.responseCsrfToken(request, response, csrfJwtSecret);
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }

    }

    private boolean validateJwtToken(Cookie csrfJwtTokenCookie) {
        try {
            JWT.require(Algorithm.HMAC512(csrfJwtSecret)).build().verify(csrfJwtTokenCookie.getValue());
            return true;
        } catch (NullPointerException e) {
            // System.out.println("Token Cookie null");
            log.error("JWT Token Cookie null");
            return false;
        } catch (AlgorithmMismatchException e) {
            // System.out.println("AlgorithmMismatch exception");
            log.error("JWT AlgorithmMismatch exception");
            return false;
        } catch (TokenExpiredException e) {
            // System.out.println("Token Expired exception");
            log.error("JWT Token Expired exception");
            return false;
        } catch (InvalidClaimException e) {
            // System.out.println("Invalid Claim Exception");
            log.error("JWT Invalid Claim Exception");
            return false;
        } catch (JWTVerificationException e) {
            // System.out.println("Jwt verification exception");
            log.error("JWT verification exception");
            return false;
        }
    }
}
