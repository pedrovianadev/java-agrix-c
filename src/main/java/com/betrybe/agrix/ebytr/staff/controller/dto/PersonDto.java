package com.betrybe.agrix.ebytr.staff.controller.dto;

import com.betrybe.agrix.ebytr.staff.entity.Person;

/**
 * Cadamada Dto de Person, used to receive information from new people.
 *
 * @param username username of the new person
 * @param password password of the new person
 * @param role new person's role
 */
public record PersonDto(String username, String password, String role) {

  /**
   * Transforms Dto into a new instance of Person.
   *
   * @return returns a new instance of Person
   */
  public Person toPerson() {
    return new Person(username, password, role);
  }
}
