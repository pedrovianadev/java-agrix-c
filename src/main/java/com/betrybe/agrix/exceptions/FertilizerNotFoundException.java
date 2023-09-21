package com.betrybe.agrix.exceptions;

/**
 * Customized exception in case fertilizer is not found.
 */
public class FertilizerNotFoundException extends Exception {

  /**
   * Custom exception constructor.
   */
  public FertilizerNotFoundException() {
    super("Fertilizante não encontrado!");
  }
}