package fr.pantheonsorbonne.exception;

public class ReviewAlreadyExistsException extends Exception{

    public ReviewAlreadyExistsException(Long paperId, Long reviewerId){
        super("A review for paper " + paperId + " and reviewer " + reviewerId + " already exists");
    }
}
