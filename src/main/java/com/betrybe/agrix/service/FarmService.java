package com.betrybe.agrix.service;

import com.betrybe.agrix.exceptions.FarmNotFoundException;
import com.betrybe.agrix.model.entities.Crop;
import com.betrybe.agrix.model.entities.Farm;
import com.betrybe.agrix.model.repositories.FarmRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service layer for everything that interacts with the farm entity.
 */
@Service
public class FarmService {

  private FarmRepository farmRepository;

  /**
   * Constructor function of the FarmService class.
   *
   * @param farmRepository repository received from spring
   *        by dependency injection
   */
  @Autowired
  public FarmService(FarmRepository farmRepository) {
    this.farmRepository = farmRepository;
  }

  public List<Farm> getFarms() {
    List<Farm> allFarms = this.farmRepository.findAll();
    return allFarms;
  }

  public Farm createFarm(Farm newFarm) {
    Farm createdFarm = this.farmRepository.save(newFarm);
    return createdFarm;
  }

  public Optional<Farm> getFarmById(Long id) {
    Optional<Farm> farmFound = this.farmRepository.findById(id);
    return farmFound;
  }

  /**
   * Method that returns all the crops on a farm.
   *
   * @param id id of the searched farm
   * @return Returns a list of all the plantations
   * @throws FarmNotFoundException if the farm is not found
   *        an exception is raised.
   */
  public List<Crop> getAllCropsFromFarm(Long id) throws FarmNotFoundException {
    Optional<Farm> farmToFound = this.farmRepository.findById(id);

    if (farmToFound.isEmpty()) {
      throw new FarmNotFoundException();
    }

    Farm farmFound = farmToFound.get();
    return farmFound.getCrops();
  }

}