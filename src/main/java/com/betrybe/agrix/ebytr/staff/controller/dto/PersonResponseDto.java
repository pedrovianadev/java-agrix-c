package com.betrybe.agrix.ebytr.staff.controller.dto;

/**
 * Dto layer for the response of the POST /persons creation route, which hides the created password.
 *
 * @param id id of the person created
 * @param username username of the person created
 * @param role role of the person created
 */
public record PersonResponseDto(Long id, String username, String role) {
}
