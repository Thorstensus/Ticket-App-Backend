package org.gfa.avusfoxticketbackend.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LogHandlerInterceptor implements HandlerInterceptor {

  public static Object object;
  Logger logger = LoggerFactory.getLogger(LogHandlerInterceptor.class);

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    if (response.getStatus() >= 400) {
      logger.error(
          "Type:{} Endpoint:{} Parameters:{} Status:{}",
          request.getMethod(),
          request.getRequestURI(),
          object,
          response.getStatus());
    } else {
      logger.info(
          "Type:{} Endpoint:{} Parameters:{} Status:{}",
          request.getMethod(),
          request.getRequestURI(),
          object,
          response.getStatus());
    }
  }
}
