package fr.pantheonsorbonne.dto.PaperDTOs;

public record QueriedPDF(
        String title,
        Byte[] pdf) {
    // PDF with title
}
