package mft.controller.exceptions;

public class PersonNotFoundException extends Exception {
    public PersonNotFoundException() {
        super("Person Not Found");
    }
}
