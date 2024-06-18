package demo.booking.config.filters;

import demo.booking.common.Constants;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final UserDetailsService userDetailsService;
    private final String secret;

    public AuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, String secret) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = this.getAuthentication(request);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(Constants.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(token) && StringUtils.hasText(secret)) {
            try {
                token = token.replace(Constants.TOKEN_PREFIX, Constants.EMPTY);
                Jws<Claims> parseToken = Jwts.parser().setSigningKey(secret.getBytes()).build().parseClaimsJws(token);
                String username = parseToken.getBody().getSubject();
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails != null) {
                    return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                }
            } catch (ExpiredJwtException e) {
                log.warn("Request to parse expired JWT : {} failed : {}", token, e.getMessage());
            } catch (UnsupportedJwtException e) {
                log.warn("Request to parse unsupported JWT : {} failed : {}", token, e.getMessage());
            } catch (MalformedJwtException e) {
                log.warn("Request to parse invalid JWT : {} failed : {}", token, e.getMessage());
            } catch (SignatureException e) {
                log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, e.getMessage());
            } catch (IllegalArgumentException e) {
                log.warn("Request to parse empty or null JWT : {} failed : {}", token, e.getMessage());
            }
        }
        return null;
    }
}
