package com.betrybe.agrix.model.repositories;

import com.betrybe.agrix.model.entities.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Creates the Farm repository.
 */
public interface FarmRepository extends JpaRepository<Farm, Long> {
}