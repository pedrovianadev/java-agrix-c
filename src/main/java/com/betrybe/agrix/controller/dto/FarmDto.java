package com.betrybe.agrix.controller.dto;

import com.betrybe.agrix.model.entities.Farm;

/**
 * Dto layer for creating farms, adopting the good practices of using
 *        of dto.
 *
 * @param id id of the new farm
 * @param name name of the new farm
 * @param size size of the new farm
 */
public record FarmDto(Long id, String name, Double size) {

  /**
   * Function that converts FarmDto to a Farm, instantiating.
   *
   * @return returns an instantiated Farm
   */
  public Farm toFarm() {
    return new Farm(id, name, size);
  }
}
