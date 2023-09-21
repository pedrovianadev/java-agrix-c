package com.betrybe.agrix.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;

/**
 * Creates the Crop entity.
 */
@Entity
@Table(name = "crop")
public class Crop {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "farm_id")
  private Farm farm;

  private String name;

  @Column(name = "planted_Area")
  private Double plantedArea;

  @Column(name = "planting_date")
  private LocalDate plantedDate;

  @Column(name = "harvest_date")
  private LocalDate harverstDate;

  @ManyToMany
  @JoinTable(
      name = "crop_fertilizer",
      joinColumns = @JoinColumn(name = "fertilizer_id"),
      inverseJoinColumns = @JoinColumn(name = "crop_id")
  )
  private List<Fertilizer> fertilizers;

  public Crop() {}

  /**
   * Crop entity constructor.
   *
   * @param name name of the created Crop
   * @param plantedArea planted area of the crop created
   * @param farm farm to which the crop will be related
   */
  public Crop(String name, Double plantedArea, Farm farm,
      LocalDate plantedDate, LocalDate harverstDate) {
    this.name = name;
    this.plantedArea = plantedArea;
    this.farm = farm;
    this.plantedDate = plantedDate;
    this.harverstDate = harverstDate;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPlantedArea() {
    return plantedArea;
  }

  public void setPlantedArea(Double plantedArea) {
    this.plantedArea = plantedArea;
  }

  public Farm getFarm() {
    return farm;
  }

  public void setFarm(Farm farm) {
    this.farm = farm;
  }

  public LocalDate getPlantedDate() {
    return plantedDate;
  }

  public void setPlantedDate(LocalDate plantedDate) {
    this.plantedDate = plantedDate;
  }

  public LocalDate getHarverstDate() {
    return harverstDate;
  }

  public void setHarverstDate(LocalDate harverstDate) {
    this.harverstDate = harverstDate;
  }

  public List<Fertilizer> getFertilizers() {
    return fertilizers;
  }

  public void setFertilizers(Fertilizer fertilizers) {
    this.fertilizers.add(fertilizers);
  }
}