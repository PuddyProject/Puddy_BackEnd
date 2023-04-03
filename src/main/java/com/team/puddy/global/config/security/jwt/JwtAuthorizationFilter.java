package com.team.puddy.global.config.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.team.puddy.global.config.auth.AuthConstants;
import com.team.puddy.global.config.auth.JwtUserDetails;
import com.team.puddy.global.error.exception.BusinessException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class    JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtVerifier jwtVerifier;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AuthConstants.HEADER_STRING.getValue());
        log.info("Header Authorization : {}", authorizationHeader);

        if (authorizationHeader == null || !authorizationHeader.startsWith(AuthConstants.TOKEN_PREFIX.getValue())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace(AuthConstants.TOKEN_PREFIX.getValue(), "");
        try {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            JwtUserDetails jwtUserDetails = new JwtUserDetails(decodedJWT);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(jwtUserDetails, null, jwtUserDetails.getAuthorities());
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            setJwtException(request, JwtException.WRONG_TOKEN);
        } catch (ExpiredJwtException | BusinessException e) {
            setJwtException(request, JwtException.EXPIRED_TOKEN);
        } catch (Exception e) {
            log.error("JwtFilter - doFilterInternal() : {}", e.getMessage());
            setJwtException(request, JwtException.UNKNOWN_ERROR);
        }
        filterChain.doFilter(request, response);
    }

    private void setJwtException(HttpServletRequest request, JwtException exception) {
        request.setAttribute("exception", exception.name());
    }

}