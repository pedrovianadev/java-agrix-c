package com.betrybe.agrix.controller.dto;

import com.betrybe.agrix.model.entities.Crop;
import com.betrybe.agrix.model.entities.Farm;
import java.time.LocalDate;

/**
 * Dto for creating Crops.
 *
 * @param name name of the Crop created
 * @param plantedArea planted area of the created Crop
 */
public record CropsDto(String name, Double plantedArea,
                       String plantedDate, String harvestDate) {

  /**
   * Method responsible for converting the Dto of the crop to a farm.
   *
   * @param farm Farm received as a parameter for registration
   * @return returns a created crop.
   */
  public Crop toCrop(Farm farm) {
    LocalDate plantedDateParsed = LocalDate.parse(plantedDate);
    LocalDate harvestDateParsed = LocalDate.parse(harvestDate);
    return new Crop(name, plantedArea, farm, plantedDateParsed, harvestDateParsed);
  }
}
