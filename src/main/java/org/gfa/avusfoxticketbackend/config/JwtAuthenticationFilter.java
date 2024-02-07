package org.gfa.avusfoxticketbackend.config;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.gfa.avusfoxticketbackend.config.services.JwtService;
import org.gfa.avusfoxticketbackend.config.services.RefreshTokenService;
import org.gfa.avusfoxticketbackend.models.User;
import org.gfa.avusfoxticketbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  private final RefreshTokenService refreshTokenService;

  private final UserService userService;

  @Autowired
  public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService, RefreshTokenService refreshTokenService, UserService userService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
    this.refreshTokenService = refreshTokenService;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain)
          throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String jwtToken;
    String userEmail = null;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    jwtToken = jwtService.extractBearerToken(authHeader);
    try {
      userEmail = jwtService.extractUsername(jwtToken);
    } catch (ExpiredJwtException ignored) {
    }
    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
      if (jwtService.isTokenValid(jwtToken, userDetails)) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
      } else {
        User user = userService.extractUserFromToken(jwtToken).get();
        if (refreshTokenService.findByUser(user).isPresent()) {
          String refreshToken = user.getRefreshToken().getToken();
          FlashMap flashMap = new FlashMap();
          flashMap.put("token", refreshToken);
          FlashMapManager flashMapManager = RequestContextUtils.getFlashMapManager(request);
          flashMapManager.saveOutputFlashMap(flashMap, request, response);
          response.sendRedirect("/api/refresh-token");
        }
      }
    }
  }
}

