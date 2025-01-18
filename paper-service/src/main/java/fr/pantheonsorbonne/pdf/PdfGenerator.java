package fr.pantheonsorbonne.pdf;

import fr.pantheonsorbonne.dto.PaperDTOs.CompletePaperDTO;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PdfGenerator {

        public Byte[] generatePdf(CompletePaperDTO completePaperDTO) {
            // Generate PDF

            return new Byte[0];
        }
}
