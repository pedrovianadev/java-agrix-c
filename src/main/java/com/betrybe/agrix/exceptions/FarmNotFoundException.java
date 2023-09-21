package com.betrybe.agrix.exceptions;

/**
 * Creates a custom exception for cases in which the farm
 * is not found.
 */
public class FarmNotFoundException extends Exception {

  public FarmNotFoundException() {
    super("Fazenda não encontrada!");
  }
}