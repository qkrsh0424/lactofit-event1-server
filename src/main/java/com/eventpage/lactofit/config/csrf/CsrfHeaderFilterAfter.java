package com.eventpage.lactofit.config.csrf;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * CsrfHeaderFilterBefore과 csrf 필터를 통과하게되면, csrf 토큰들을 재갱신 시켜준다음 계속해서 필터를 태운다.
 */
@Slf4j
public class CsrfHeaderFilterAfter extends OncePerRequestFilter {
    private String csrfJwtSecret;
    
    public CsrfHeaderFilterAfter(String csrfJwtSecret){
        this.csrfJwtSecret = csrfJwtSecret;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        // log.info("==============csrf filter after here================");
        CreateCsrfToken createCsrfToken = new CreateCsrfToken();
        createCsrfToken.responseCsrfToken(request, response, csrfJwtSecret);
        filterChain.doFilter(request, response);
    }
}
