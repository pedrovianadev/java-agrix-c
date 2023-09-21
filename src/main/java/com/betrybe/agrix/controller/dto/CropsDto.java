package com.betrybe.agrix.controller.dto;

import com.betrybe.agrix.model.entities.Crop;
import com.betrybe.agrix.model.entities.Farm;
import java.time.LocalDate;

/**
 * Dto para criacao de Crops.
 *
 * @param name nome da Crop criada
 * @param plantedArea area plantada da Crop criada
 */
public record CropsDto(String name, Double plantedArea,
                       String plantedDate, String harvestDate) {

  /**
   * Método responsavel por converter a Dto da crop para uma farm.
   *
   * @param farm Fazenda recebida por parametro para registro
   * @return retorna uma crop criada.
   */
  public Crop toCrop(Farm farm) {
    LocalDate plantedDateParsed = LocalDate.parse(plantedDate);
    LocalDate harvestDateParsed = LocalDate.parse(harvestDate);
    return new Crop(name, plantedArea, farm, plantedDateParsed, harvestDateParsed);
  }
}