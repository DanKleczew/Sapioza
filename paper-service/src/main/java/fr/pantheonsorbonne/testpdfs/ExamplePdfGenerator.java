package fr.pantheonsorbonne.testpdfs;

import fr.pantheonsorbonne.dto.PaperDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.apache.camel.CamelContext;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Path("/test")
public class ExamplePdfGenerator {

    @Inject
    CamelContext camelContext;

    @GET
    @Produces("application/pdf")
    public Response test() {

//        byte[] pdfBytes = this.generatePdf(new SubmittedPaperDTO(
//                new PaperDTO(
//                        "TestArticle",
//                        18L,
//                        ResearchField.ART,
//                        "RandomJournal",
//                        new Date(),
//                        "ak;bj;ep",
//                        "This is an abstract. The best abstract",
//                        "https://lolololo",
//                        197,
//                        null
//                ),
//                "This is the body of the pdf, \r I don't know what to write. " +
//                        " This project is awful. Well not " +
//                        "really but it is getting a bit long"
//
        try {
            byte[] pdfbytes = this.generatePdf();
            return Response.ok(pdfbytes).header("Content-Disposition", "inline; filename=\"generated.pdf\"").build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

public byte[] generatePdf() {
    try (PDDocument document = new PDDocument()) {
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            // Titre principal
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("A Review of Cryptographic Electronic Voting");
            contentStream.endText();

            // Auteur(s)
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 730);
            contentStream.showText("Yun-Xing Kho, Swee-Huay Heng, Ji-Jian Chin");
            contentStream.endText();

            // Affiliations
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 710);
            contentStream.showText("1. Faculty of Information Science and Technology, Multimedia University, Malaysia");
            contentStream.endText();
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 690);
            contentStream.showText("2. Faculty of Computing and Informatics, Multimedia University, Malaysia");
            contentStream.endText();

            // Résumé
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 660);
            contentStream.showText("Abstract:");
            contentStream.endText();

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 640);
            contentStream.showText("A vast number of e-voting schemes various random bullshit until I get to the end" +
                            "of the line, and maybe some pineapple on top of the mountain");
            contentStream.endText();

            // Mots-clés
            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 600);
            contentStream.showText("Keywords:");
            contentStream.endText();

            contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 580);
            contentStream.showText("blind signature-based e-voting, blockchain-based e-voting, cryptography...");
            contentStream.endText();
        }

        // Sauvegarde en mémoire
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.save(outputStream);
        return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }
    }
}

