package com.betrybe.agrix.controller.dto;

import com.betrybe.agrix.model.entities.Farm;

/**
 * Camada Dto para criacao de farms, adotando as boas praticas do uso
 * de dto.
 *
 * @param id id da nova farm
 * @param name nome da nova farm
 * @param size tamanho da nova farm
 */
public record FarmDto(Long id, String name, Double size) {

  /**
   * Funcao que converte a FarmDto para uma Farm, instanciando.
   *
   * @return retorna uma Farm instanciada
   */
  public Farm toFarm() {
    return new Farm(id, name, size);
  }
}