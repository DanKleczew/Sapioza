package fr.pantheonsorbonne.exception;

public class OpinionNotFoundException extends Exception{
    public OpinionNotFoundException(Long paperId, Long userId){
        super("Opinion not found for paper id " + paperId + " and opinion id " + userId);
    }
    public OpinionNotFoundException(Long paperId){
        super("0 opinion found for paper id " + paperId);
    }
}
