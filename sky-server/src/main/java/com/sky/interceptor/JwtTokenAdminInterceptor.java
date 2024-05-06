package com.sky.interceptor;

import com.sky.constant.JwtClaimsConstant;
import com.sky.context.BaseContext;
import com.sky.properties.JwtProperties;
import com.sky.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor for jwt token verification
 */
@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * Verify jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Current thread id: " + Thread.currentThread().getId());

        //Determine whether the currently intercepted method is the Controller's method or other resources
        if (!(handler instanceof HandlerMethod)) {
            //The currently intercepted method is not a dynamic method, so let it go directly.
            return true;
        }

        //1. Get the token from the request header
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        //2. Verification token
        try {
            log.info("JWT verification:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
            log.info("Current employee id:", empId);
            BaseContext.setCurrentId(empId);
            //3. Pass, release
            return true;
        } catch (Exception ex) {
            //4. If it fails, respond with 401 status code
            response.setStatus(401);
            return false;
        }
    }
}
