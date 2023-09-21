package com.betrybe.agrix.controller.dto;

import java.time.LocalDate;

/**
 * Cria a dto de resposta para a criacao de crops.
 *
 * @param id id da crop criada
 * @param name nome da crop criada
 * @param plantedArea area plantada da crop criada
 * @param farmId id da farm em que a crop foi criada
 * @param plantedDate data em que a crop foi plantada
 * @param harvestDate data em que a crop foi colhida
 */
public record CropResponseDto(Long id, String name, Double plantedArea, Long farmId,
                              LocalDate plantedDate, LocalDate harvestDate) {
}