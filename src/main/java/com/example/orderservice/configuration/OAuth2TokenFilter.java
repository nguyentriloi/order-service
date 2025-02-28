package com.example.orderservice.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class OAuth2TokenFilter extends OncePerRequestFilter {

  // Replace with your actual JWT validation logic
  private boolean isValidToken(String token) {
    return !StringUtils.isBlank(token);
  }

  private String extractToken(HttpServletRequest request) {
    // Extract the token from the Authorization header
    String header = request.getHeader("Authorization");
    if (header != null && header.startsWith("Bearer ")) {
      return header.substring(7);  // Get token without "Bearer "
    }
    return null;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {
    // Extract token from request header
    String token = extractToken(request);

    if (isValidToken(token)) {

      final var authenticationToken = generateAuthentication(request);

      // Set the authentication in SecurityContext
      SecurityContext context = SecurityContextHolder.createEmptyContext();
      context.setAuthentication(authenticationToken);
      SecurityContextHolder.setContext(context);
    }

    // Continue filter chain
    chain.doFilter(request, response);
  }

  private static UsernamePasswordAuthenticationToken generateAuthentication(
      HttpServletRequest request) {

    val authorities = List.of(
        new SimpleGrantedAuthority("ROLE_STAFF"),
        new SimpleGrantedAuthority("ROLE_CUSTOMER")
    );

    val authenticationToken = new UsernamePasswordAuthenticationToken(
        "authenticatedUser",
        null,  // No credentials, since the token is already validated
        authorities
    );

    authenticationToken.setDetails(new WebAuthenticationDetails(request));
    return authenticationToken;
  }
}
