package com.betrybe.agrix.ebytr.staff.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.betrybe.agrix.ebytr.staff.entity.Person;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Token generation and authentication service.
 */
@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  /**
   * Function that generates a token from a Person.
   *
   * @param person person passed in as a parameter
   * @return returns a generated token
   */
  public String generateToken(Person person) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.create()
        .withIssuer("agrix")
        .withExpiresAt(expiresAt())
        .withSubject(person.getUsername())
        .sign(algorithm);
  }

  /**
   * Function that generates a token expiration date.
   *
   * @return returns an instant at which the token expires
   */
  public Instant expiresAt() {
    LocalDateTime localDateTime = LocalDateTime.now();
    return localDateTime.plusHours(48).toInstant(ZoneOffset.of("-03:00"));
  }

  /**
   * Function that validates the token, checking if it is valid and returning its subject.
   *
   * @param token receives the token to be checked
   * @return returns the subject encrypted in the token
   */
  public String validateToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.require(algorithm)
        .withIssuer("agrix")
        .build()
        .verify(token)
        .getSubject();
  }

}
