package com.betrybe.agrix.ebytr.staff.exception;

/**
 * Customized exception created to inform when the password is incorrect.
 */
public class IncorrectPasswordException extends Exception {

  public IncorrectPasswordException() {
    super("Senha Incorreta");
  }
}
