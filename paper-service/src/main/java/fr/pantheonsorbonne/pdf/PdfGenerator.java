package fr.pantheonsorbonne.pdf;

import fr.pantheonsorbonne.dto.CompletePaperDTO;
import fr.pantheonsorbonne.dto.SubmittedPaperDTO;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PdfGenerator {

        public byte[] generatePdf(CompletePaperDTO completePaperDTO) {
            // Generate PDF

            return new byte[0];
        }
}
