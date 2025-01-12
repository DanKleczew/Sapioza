package fr.pantheonsorbonne.exception;

public class ReviewNotFoundException extends Exception{

    public ReviewNotFoundException(Long paperId, Long reviewerId){
        super("Review for paper " + paperId + "and reviewer " + reviewerId + " not found");
    }
}
