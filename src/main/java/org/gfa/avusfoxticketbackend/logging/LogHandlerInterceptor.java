package org.gfa.avusfoxticketbackend.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Component
public class LogHandlerInterceptor implements HandlerInterceptor {

  Logger logger = LoggerFactory.getLogger(LogHandlerInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    logger.info("Type:{} Endpoint:{} Parameters:{} Status:{}", request.getMethod(), request.getRequestURI(), request.getQueryString(), response.getStatus());
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    logger.info("Type:{} Endpoint:{} Parameters:{} Status:{}", request.getMethod(), request.getRequestURI(), request.getQueryString(), response.getStatus());
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    if(response.getStatus() >= 400) {
      logger.error("Type:{} Endpoint:{} Parameters:{} Status:{}", request.getMethod(), request.getRequestURI(), request.getQueryString(), response.getStatus());
    } else {
      logger.info("Type:{} Endpoint:{} Parameters:{} Status:{}", request.getMethod(), request.getRequestURI(), request.getQueryString(), response.getStatus());
    }
  }
}
