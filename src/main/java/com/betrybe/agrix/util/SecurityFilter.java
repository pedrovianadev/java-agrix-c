package com.betrybe.agrix.util;

import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.ebytr.staff.service.PersonService;
import com.betrybe.agrix.ebytr.staff.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Configura os filtros de seguranca para a utilizacao do token.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

  private TokenService tokenService;
  private PersonService personService;

  /**
   * Initializes the security filters for using the token.
   *
   * @param tokenService receives an instance of the token service layer
   * @param personService receives an instance of the person service layer
   */
  @Autowired
  public SecurityFilter(TokenService tokenService, PersonService personService) {
    this.tokenService = tokenService;
    this.personService = personService;
  }

  /**
   * Sets the filter for using tokens.
   *
   * @param request intercepts the request
   * @param response intercepts the response
   * @param filterChain receives the filter string
   * @throws ServletException in case of servlet error
   * @throws IOException in case of I/O error
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String token = recoveryToken(request);

    if (token != null) {
      String username = this.tokenService.validateToken(token);

      Person personFound = this.personService.getPersonByUsername(username);

      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
          new UsernamePasswordAuthenticationToken(personFound, null, personFound.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
    filterChain.doFilter(request, response);
  }

  /**
   * Function created to extract the token from the request header.
   *
   * @param httpServletRequest request received by parameter
   * @return returns the token
   */
  public String recoveryToken(HttpServletRequest httpServletRequest) {
    String authHeader = httpServletRequest.getHeader("Authorization");

    if (authHeader == null) {
      return null;
    }
    return authHeader.replace("Bearer ", "");
  }
}
