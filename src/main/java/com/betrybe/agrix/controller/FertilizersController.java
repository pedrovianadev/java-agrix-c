package com.betrybe.agrix.controller;

import com.betrybe.agrix.controller.dto.FertilizerDto;
import com.betrybe.agrix.controller.dto.FertilizerResponseDto;
import com.betrybe.agrix.exceptions.FertilizerNotFoundException;
import com.betrybe.agrix.model.entities.Fertilizer;
import com.betrybe.agrix.service.FertilizerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Creates the control layer for the /fertilizers routes.
 */
@RestController
@RequestMapping("/fertilizers")
public class FertilizersController {

  private FertilizerService fertilizerService;

  /**
   * Builder of the /fertilizers route control layer.
   *
   * @param fertilizerService receives an instance of the
   *        service layer by dependency injection
   */
  @Autowired
  public FertilizersController(FertilizerService fertilizerService) {
    this.fertilizerService = fertilizerService;
  }

  /**
   * Method that maps the POST /fertilizers route to the creation of a new
   * fertilizer.
   *
   * @param newFertilizerInfo information about the fertilizer to be created
   * @return returns the new fertilizer created
   */
  @PostMapping
  public ResponseEntity<FertilizerResponseDto>
      createFertilizer(@RequestBody FertilizerDto newFertilizerInfo) {
    Fertilizer newFertilizer = newFertilizerInfo.toFertilizer();

    Fertilizer createdFertilizer = this.fertilizerService.createFertilizer(newFertilizer);
    FertilizerResponseDto fertilizerResponse = new FertilizerResponseDto(
          createdFertilizer.getId(), createdFertilizer.getName(), createdFertilizer.getBrand(),
          createdFertilizer.getComposition()
      );

    return ResponseEntity.status(HttpStatus.CREATED).body(fertilizerResponse);
  }

  /**
   * Method that returns all the fertilizers mapped on the route
   * GET /fertilizers.
   *
   * @return returns all fertilizers
   */
  @GetMapping
  public ResponseEntity<List<Fertilizer>> getAllFertilizers() {
    List<Fertilizer> allFertilizers = this.fertilizerService.getAllFertilizers();
    return ResponseEntity.status(HttpStatus.OK).body(allFertilizers);
  }

  /**
   * Method that returns the fertilizer fetched by the id, mapped
   * in the GET /fertilizers/id route.
   *
   * @param fertilizerId id of the searched fertilizer
   * @return returns the searched fertilizer
   */
  @GetMapping("/{fertilizerId}")
  public ResponseEntity getFertilizerById(@PathVariable Long fertilizerId) {
    try {

      Fertilizer fertilizerFound = this.fertilizerService.getFertilizerById(fertilizerId);
      return ResponseEntity.status(HttpStatus.OK).body(fertilizerFound);

    } catch (FertilizerNotFoundException fertilizerNotFoundException) {

      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(fertilizerNotFoundException.getMessage());

    }
  }
}