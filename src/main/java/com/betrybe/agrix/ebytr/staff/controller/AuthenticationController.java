package com.betrybe.agrix.ebytr.staff.controller;

import com.betrybe.agrix.ebytr.staff.controller.dto.LoginPerson;
import com.betrybe.agrix.ebytr.staff.controller.dto.TokenDto;
import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.ebytr.staff.exception.IncorrectPasswordException;
import com.betrybe.agrix.ebytr.staff.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Auth route control layer.
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  private AuthenticationManager authenticationManager;

  private TokenService tokenService;

  /**
   * Auth route control layer constructor.
   *
   * @param authenticationManager authenticationmanager received by dependency injection
   * @param tokenService instantiation of the token service layer received by dependency injection
   */
  @Autowired
  public AuthenticationController(AuthenticationManager authenticationManager,
      TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  /**
   * POST route /login, used to log in an already created user.
   *
   * @param loginPerson receives the Dto of loginPerson with the login information
   * @return returns a generated token
   */
  @PostMapping("/login")
  public ResponseEntity authenticatePerson(@RequestBody LoginPerson loginPerson) {

    UsernamePasswordAuthenticationToken usernamePassword =
        new UsernamePasswordAuthenticationToken(loginPerson.username(), loginPerson.password());

    Authentication auth = this.authenticationManager.authenticate(usernamePassword);

    Person person = (Person) auth.getPrincipal();
    String token = this.tokenService.generateToken(person);
    TokenDto tokenDto = new TokenDto(token);

    return ResponseEntity.status(HttpStatus.OK).body(tokenDto);

  }

}
