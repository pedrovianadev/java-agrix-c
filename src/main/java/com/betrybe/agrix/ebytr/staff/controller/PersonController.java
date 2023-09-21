package com.betrybe.agrix.ebytr.staff.controller;

import com.betrybe.agrix.ebytr.staff.controller.dto.LoginPerson;
import com.betrybe.agrix.ebytr.staff.controller.dto.PersonDto;
import com.betrybe.agrix.ebytr.staff.controller.dto.PersonResponseDto;
import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.ebytr.staff.exception.IncorrectPasswordException;
import com.betrybe.agrix.ebytr.staff.exception.PersonNotFoundException;
import com.betrybe.agrix.ebytr.staff.service.PersonService;
import com.betrybe.agrix.ebytr.staff.service.TokenService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Creates the control layer for the /person routes.
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

  private PersonService personService;
  private AuthenticationManager authenticationManager;

  private TokenService tokenService;

  /**
   * Persons control constructor.
   *
   * @param personService service layer received by dependency injection
   * @param authenticationManager authenticationManager received by dependency injection
   * @param tokenService token service layer received by dependency injection
   */
  @Autowired
  public PersonController(PersonService personService,
      AuthenticationManager authenticationManager,
      TokenService tokenService) {

    this.personService = personService;
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;

  }

  /**
   * POST /persons route used to register a new user.
   *
   * @param personDto Dto of the person to be created received by parameter
   * @return returns a new person created
   */
  @PostMapping
  public ResponseEntity<PersonResponseDto> createPerson(@RequestBody PersonDto personDto) {

    Person personToCreate = personDto.toPerson();

    Person createdPerson = this.personService.create(personToCreate);

    PersonResponseDto createdPersonResponse = new PersonResponseDto(createdPerson.getId(),
        createdPerson.getUsername(), personDto.role());

    return ResponseEntity.status(HttpStatus.CREATED).body(createdPersonResponse);
  }

}
