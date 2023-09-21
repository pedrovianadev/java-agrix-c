package com.betrybe.agrix.controller;

import com.betrybe.agrix.controller.dto.CropResponseDto;
import com.betrybe.agrix.controller.dto.CropsDto;
import com.betrybe.agrix.controller.dto.FarmDto;
import com.betrybe.agrix.exceptions.FarmNotFoundException;
import com.betrybe.agrix.model.entities.Crop;
import com.betrybe.agrix.model.entities.Farm;
import com.betrybe.agrix.service.CropService;
import com.betrybe.agrix.service.FarmService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Creates the FarmController class, a RestController that will
 * contain all the functions of the /farms endpoint.
 */
@RestController
@RequestMapping("/farms")
public class FarmController {

  private FarmService farmService;
  private CropService cropService;

  /**
   * Constructor of the FarmController class.
   *
   * @param farmService instance of the service layer received
   *        by dependency injection.
   * @for cropService instance of the crops service layer received
   *        by dependency injection.
   */
  @Autowired
  public FarmController(FarmService farmService, CropService cropService) {
    this.farmService = farmService;
    this.cropService = cropService;
  }

  /**
   * Creates the POST /farms endpoint, where you can create and save
   *        in the database a new farm.
   *
   * @param farmDto Receives a new farm in the Dto model
   * @return Returns a ResponseEntity with the information
   *        of the new farm created
   */
  @PostMapping
  public ResponseEntity<Farm> createFarm(@RequestBody FarmDto farmDto) {
    Farm farmToSave = farmDto.toFarm();
    Farm createdFarm = this.farmService.createFarm(farmToSave);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdFarm);
  }

  /**
   * Creates the GET /farms route that returns all registered farms.
   *
   * @return returns a list of all registered farms
   */
  @GetMapping
  @Secured({"ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER"})
  public ResponseEntity<List<Farm>> getAllFarms() {
    List<Farm> allFarms = this.farmService.getFarms();
    return ResponseEntity.status(HttpStatus.OK).body(allFarms);
  }

  /**
   * Creates the GET route /farms/id that returns the farm searched by id.
   *
   * @return returns a farm from the database.
   */
  @GetMapping("/{farmId}")
  public ResponseEntity getFarmById(@PathVariable Long farmId) throws FarmNotFoundException {
    try {
      Optional<Farm> farmToFound = this.farmService.getFarmById(farmId);

      if (farmToFound.isEmpty()) {
        throw new FarmNotFoundException();
      }

      Farm farmFound = farmToFound.get();
      return ResponseEntity.status(HttpStatus.OK).body(farmFound);
    } catch (FarmNotFoundException farmNotFoundException) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(farmNotFoundException.getMessage());
    }

  }

  /**
   * POST route /farmid/crops that saves a crop on a specified farm.
   *
   * @param farmId farm id
   * @param cropsDto Dto layer for creating crops
   * @return returns a CropResponseDto with only the Crop information
   *        omitting farm information.
   */
  @PostMapping("/{farmId}/crops")
  public ResponseEntity createCropByFarmId(@PathVariable Long farmId, @RequestBody
      CropsDto cropsDto) {
    try {
      Optional<Farm> farmToSave = this.farmService.getFarmById(farmId);

      if (farmToSave.isEmpty()) {
        throw new FarmNotFoundException();
      }

      Farm farmFound = farmToSave.get();
      Crop cropToSave = cropsDto.toCrop(farmFound);
      Crop cropSaved = this.cropService.saveCropByFarmId(cropToSave);

      CropResponseDto cropResponseDto = new CropResponseDto(
          cropSaved.getId(), cropSaved.getName(),
          cropSaved.getPlantedArea(), cropSaved.getFarm().getId(),
          cropSaved.getPlantedDate(), cropSaved.getHarverstDate());

      return ResponseEntity.status(HttpStatus.CREATED).body(cropResponseDto);

    } catch (FarmNotFoundException farmNotFoundException) {

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(farmNotFoundException.getMessage());

    }
  }

  /**
   * Method that maps the /farmId/crops route and returns all the crops
   *        that a farm has.
   *
   * @param farmId id of the farm fetched by parameter.
   * @return returns a list with all the crops or a farm exception
   *        not found
   */
  @GetMapping("/{farmId}/crops")
  public ResponseEntity getAllCropsFromFarm(@PathVariable Long farmId) {
    try {

      List<Crop> allCrops = this.farmService.getAllCropsFromFarm(farmId);
      List<CropResponseDto> allCropsResponse = allCrops.stream().map(
          crop -> new CropResponseDto(crop.getId(), crop.getName(),
          crop.getPlantedArea(), crop.getFarm().getId(),
              crop.getPlantedDate(), crop.getHarverstDate())).collect(Collectors.toList());

      return ResponseEntity.status(HttpStatus.OK).body(allCropsResponse);

    } catch (FarmNotFoundException farmNotFoundException) {

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(farmNotFoundException.getMessage());

    }
  }
}
