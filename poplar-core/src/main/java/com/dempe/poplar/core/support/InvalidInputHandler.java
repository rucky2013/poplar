package com.dempe.poplar.core.support;

/**
 * Handler for Bad Request (400).
 *
 * @author Rodrigo Turini
 */
public interface InvalidInputHandler {

    void deny(InvalidInputException e);
}