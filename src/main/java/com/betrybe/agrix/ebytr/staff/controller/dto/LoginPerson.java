package com.betrybe.agrix.ebytr.staff.controller.dto;

/**
 * Dto layer to receive a person's login information.
 *
 * @param username username to be logged in
 * @param password password of the user to be logged in
 */
public record LoginPerson(String username, String password) {
}
