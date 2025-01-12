package fr.pantheonsorbonne.exception;

public class ReviewNotCreatedException extends Exception{

    public ReviewNotCreatedException() {
        super("Error : Review not created");
    }
}
