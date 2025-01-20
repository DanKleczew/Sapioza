package fr.pantheonsorbonne.dto.PaperDTOs;

public record QueriedPDF(
        String title,
        byte[] pdf) {
    // PDF with title
}
