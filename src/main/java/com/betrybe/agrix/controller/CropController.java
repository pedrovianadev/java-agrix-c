package com.betrybe.agrix.controller;

import com.betrybe.agrix.controller.dto.CropResponseDto;
import com.betrybe.agrix.exceptions.CropNotFoundException;
import com.betrybe.agrix.exceptions.FertilizerNotFoundException;
import com.betrybe.agrix.model.entities.Crop;
import com.betrybe.agrix.model.entities.Fertilizer;
import com.betrybe.agrix.service.CropService;
import com.betrybe.agrix.service.FertilizerService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller of all methods in the /crops route.
 */
@RestController
@RequestMapping("/crops")
public class CropController {
  private CropService cropService;
  private FertilizerService fertilizerService;

  /**
   * CropController controller constructor.
   *
   * @param cropService receives the service layer by
   *        dependency injection.
   */
  @Autowired
  public CropController(CropService cropService, FertilizerService fertilizerService) {
    this.cropService = cropService;
    this.fertilizerService = fertilizerService;
  }

  /**
   * Map the GET /crops route with the function of returning a list of
   * all the plantations in the database.
   *
   * @return returns a CropResponseDto list of all the crops.
   */
  @GetMapping
  @Secured({"ROLE_ADMIN", "ROLE_MANAGER"})
  public ResponseEntity<List<CropResponseDto>> getAllCrops() {

    List<Crop> allCrops = this.cropService.getAllCrops();

    List<CropResponseDto> cropsResponse = allCrops.stream()
        .map(crop -> new CropResponseDto(crop.getId(), crop.getName(),
        crop.getPlantedArea(), crop.getFarm().getId(), crop.getPlantedDate(),
            crop.getHarverstDate()))
        .toList();

    return ResponseEntity.status(HttpStatus.OK).body(cropsResponse);
  }

  /**
   * Maps the GET /crops/id route to return the information of a crop
   * specifies.
   *
   * @param id id of the searched crop passed by Path
   * @return returns a ResponseEntity with the specified crop or an error
   *        if it is not found.
   */
  @GetMapping("/{id}")
  public ResponseEntity getCropById(@PathVariable Long id) {
    try {

      Crop cropFound = this.cropService.getCropById(id);

      CropResponseDto cropResponse = new CropResponseDto(cropFound.getId(),
          cropFound.getName(), cropFound.getPlantedArea(), cropFound.getFarm().getId(),
          cropFound.getPlantedDate(), cropFound.getHarverstDate());

      return ResponseEntity.status(HttpStatus.OK).body(cropResponse);

    } catch (CropNotFoundException cropNotFoundException) {

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cropNotFoundException.getMessage());

    }
  }

  /**
   * Route that allows you to search for crops based on the harvest date.
   *
   * @param start search start date
   * @param end search end date
   * @return returns a list of crops that meet the requirement
   */
  @GetMapping("/search")
  public ResponseEntity<List<CropResponseDto>> searchCropByDate(
      @RequestParam LocalDate start,
      @RequestParam LocalDate end) {

    List<Crop> allCrops = this.cropService.searchCropByDate(start, end);

    List<CropResponseDto> allCropsConverted = allCrops.stream()
        .map(crop -> new CropResponseDto(crop.getId(), crop.getName(),
            crop.getPlantedArea(), crop.getFarm().getId(), crop.getPlantedDate(),
            crop.getHarverstDate()))
        .toList();

    return ResponseEntity.status(HttpStatus.OK).body(allCropsConverted);

  }

  /**
   * Method that associates planting with a fertilizer.
   *
   * @param cropId Id of the crop to be associated
   * @param fertilizerId id of the fertilizer to be associated
   * @return returns if the operation was successful
   */
  @PostMapping("/{cropId}/fertilizers/{fertilizerId}")
  public ResponseEntity createFertilizerByCropId(@PathVariable Long cropId,
      @PathVariable Long fertilizerId) {
    try {

      Crop cropAdded = this.cropService.setFertilizer(cropId, fertilizerId);
      System.out.println(cropAdded);

      return ResponseEntity.status(HttpStatus.CREATED).body(
          "Fertilizante e plantação associados com sucesso!"
      );
    } catch (FertilizerNotFoundException fertilizerNotFoundException) {

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
          fertilizerNotFoundException.getMessage()
      );

    } catch (CropNotFoundException cropNotFoundException) {

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
          cropNotFoundException.getMessage()
      );

    }

  }

  /**
   * Method that returns all the fertilizers in a plantation.
   *
   * @param cropId id of the searched crop
   * @return returns all the fertilizers of the searched crop
   */
  @GetMapping("/{cropId}/fertilizers")
  public ResponseEntity getFertilizersFromCropId(@PathVariable Long cropId) {
    try {

      Crop cropFound = this.cropService.getCropById(cropId);
      List<Fertilizer> fertilizers = cropFound.getFertilizers();

      return ResponseEntity.status(HttpStatus.OK).body(fertilizers);

    } catch (CropNotFoundException cropNotFoundException) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
          cropNotFoundException.getMessage());
    }
  }
}