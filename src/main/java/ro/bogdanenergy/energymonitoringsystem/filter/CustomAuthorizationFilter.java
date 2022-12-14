package ro.bogdanenergy.energymonitoringsystem.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ro.bogdanenergy.energymonitoringsystem.UriMapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals(UriMapper.LOGIN) || request.getServletPath().equals(UriMapper.REFRESH_TOKEN) || request.getServletPath().equals(UriMapper.USER_BASE + UriMapper.CREATE_USER) || request.getServletPath().equals(UriMapper.DEVICE_BASE + UriMapper.GET_RANDOM_ID) || request.getServletPath().contains(UriMapper.WEBSOCKET)) {
            filterChain.doFilter(request, response);
        } else {
            Cookie[] cookies = request.getCookies();
            if(cookies.length > 0) {
                 try {
                     String token = cookies[0].getValue();
                     Algorithm algorithm = Algorithm.HMAC256("Secret123!@#".getBytes());
                     JWTVerifier verifier = JWT.require(algorithm).build();
                     DecodedJWT decodedJWT = verifier.verify(token);
                     String username = decodedJWT.getSubject();
                     String[] roles = decodedJWT.getClaim("role").asArray(String.class);
                     Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                     stream(roles).forEach(role -> {
                         authorities.add(new SimpleGrantedAuthority(role));
                     });
                     UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                     SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                     filterChain.doFilter(request, response);
                 }catch (Exception e) {
                    log.error("Error logging in: {}", e.getMessage());
                    response.setHeader("error", e.getMessage());
                    response.setStatus(HttpStatus.FORBIDDEN.value());
//                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                     Map<String, String> error = new HashMap<>();
                     error.put("error_message", e.getMessage());
                     response.setContentType(APPLICATION_JSON_VALUE);
                     new ObjectMapper().writeValue(response.getOutputStream(), error);
                 }
            }
            else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
