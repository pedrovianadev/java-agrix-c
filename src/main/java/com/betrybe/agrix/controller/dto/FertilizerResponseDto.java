package com.betrybe.agrix.controller.dto;

/**
 * Creates the Dto response layer for the /fertilizers routes.
 *
 * @param id id of the fertilizer
 * @param name fertilizer name
 * @param brand fertilizer brand
 * @param composition fertilizer composition
 */
public record FertilizerResponseDto(Long id, String name, String brand,
                                    String composition) {
}