package com.betrybe.agrix.controller.dto;

import com.betrybe.agrix.model.entities.Fertilizer;

/**
 * Creates the input Dto of a fertilizer.
 *
 * @param id id of the new fertilizer
 * @param name name of the new fertilizer
 * @param brand of the new fertilizer
 * @param composition composition of the new fertilizer
 */
public record FertilizerDto(Long id, String name, String brand,
                            String composition) {

  /**
   * Converts fertilizer from Dto to fertilizer.
   *
   * @return returns a new fertilizer
   */
  public Fertilizer toFertilizer() {
    return new Fertilizer(id, name, brand, composition);
  }
}