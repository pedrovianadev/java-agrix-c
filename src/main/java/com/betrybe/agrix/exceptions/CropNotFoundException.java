package com.betrybe.agrix.exceptions;

/**
 * Customized exception that will be thrown if the plantation
 * is not found.
 */
public class CropNotFoundException extends Exception {

  /**
   * Custom exception constructor calling the super method.
   */
  public CropNotFoundException() {
    super("Plantação não encontrada!");
  }
}