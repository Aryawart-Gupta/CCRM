package edu.ccrm.exception;

// This is a CHECKED exception because the caller should handle it.
public class MaxCreditLimitExceededException extends Exception {
    public MaxCreditLimitExceededException(String message) {
        super(message);
    }
}