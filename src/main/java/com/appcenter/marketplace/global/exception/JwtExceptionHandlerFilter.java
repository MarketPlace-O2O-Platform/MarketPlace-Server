package com.appcenter.marketplace.global.exception;

import com.appcenter.marketplace.global.common.CommonResponse;
import com.appcenter.marketplace.global.common.StatusCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    // JwtAuthenticationFilter 오류
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch(CustomException e) {
            setErrorResponse(response, e.getStatusCode());
        }
    }

    private void setErrorResponse(HttpServletResponse response, StatusCode statusCode) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(statusCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        CommonResponse<Object> commonResponse = CommonResponse.from(statusCode.getMessage());

        response.getWriter().write(objectMapper.writeValueAsString(commonResponse));

    }
}
