package fr.pantheonsorbonne.testpdfs;

import fr.pantheonsorbonne.dto.PaperDTOs.CompletePaperDTO;
import fr.pantheonsorbonne.dto.PaperDTOs.SubmittedPaperDTO;
import fr.pantheonsorbonne.pdf.PdfGenerator;
import fr.pantheonsorbonne.service.PdfGenerationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/pdf")
public class PdfResourceExample {

    @Inject
    PdfGenerator pdfGenerator;

    @GET
    @Path("/generate")
    public Response getPdf() {
        // Generate PDF
        CompletePaperDTO completePaperDTO = new CompletePaperDTO(null, null, null);
        Byte[] pdf = this.pdfGenerator.generatePdf(completePaperDTO);
        return Response.ok(pdf).header("Content-Disposition", "inline; filename=\"generated.pdf\"").build();
    }
}
