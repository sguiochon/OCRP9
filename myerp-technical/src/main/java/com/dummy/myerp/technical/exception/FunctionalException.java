package com.dummy.myerp.technical.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Classe des Exceptions Fonctionnelles
 */
public class FunctionalException extends Exception {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur.
     *
     * @param pMessage -
     */
    public FunctionalException(String pMessage) {
        super(pMessage);
    }

    /**
     * Constructeur.
     *
     * @param pCause -
     */
    public FunctionalException(Throwable pCause) {
        super(pCause);
    }

    /**
     * Constructeur.
     *
     * @param pMessage -
     * @param pCause -
     */
    public FunctionalException(String pMessage, Throwable pCause) {
        super(pMessage + buildMessageIfCausedByConstraintViolationException(pCause), pCause);
    }

    public static String buildMessageIfCausedByConstraintViolationException(Throwable throwable){
        StringBuilder message = new StringBuilder("(");
        if (throwable instanceof ConstraintViolationException){
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) throwable;
            message.append(constraintViolationException.getMessage());
            for (ConstraintViolation<?> o : constraintViolationException.getConstraintViolations()){
                message.append("[").append(o.getMessage()).append("]");
            }
        }
        message.append(")");
        return message.toString();
    }
}
