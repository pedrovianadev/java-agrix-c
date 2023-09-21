package com.betrybe.agrix.service;

import com.betrybe.agrix.exceptions.FertilizerNotFoundException;
import com.betrybe.agrix.model.entities.Fertilizer;
import com.betrybe.agrix.model.repositories.FertilizerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Creates the service layer for the /Fertilizer routes.
 */
@Service
public class FertilizerService {

  private FertilizerRepository fertilizerRepository;

  @Autowired
  public FertilizerService(FertilizerRepository fertilizerRepository) {
    this.fertilizerRepository = fertilizerRepository;
  }

  /**
   * Creates a new fertilizer.
   *
   * @param fertilizerInfo information of the fertilizer to be created
   * @return returns the information of the fertilizer created
   */
  public Fertilizer createFertilizer(Fertilizer fertilizerInfo) {

    Fertilizer createdFertilizer = this.fertilizerRepository.save(fertilizerInfo);
    return createdFertilizer;
  }

  /**
   * Method that returns all registered fertilizers.
   *
   * @return returns a list of all fertilizers.
   */
  public List<Fertilizer> getAllFertilizers() {
    return this.fertilizerRepository.findAll();
  }

  /**
   * Method that returns the fertilizer based on the id.
   *
   * @param id id of the searched fertilizer
   * @return returns the searched fertilizer
   * @throws FertilizerNotFoundException returns an error if
   *        the fertilizer is not found
   */
  public Fertilizer getFertilizerById(Long id) throws FertilizerNotFoundException {
    Optional<Fertilizer> optionalFertilizer = this.fertilizerRepository.findById(id);

    if (optionalFertilizer.isEmpty()) {
      throw new FertilizerNotFoundException();
    }

    return optionalFertilizer.get();
  }
}