package fr.pantheonsorbonne.pdf;

import fr.pantheonsorbonne.dto.PaperDTOs.CompletePaperDTO;
import fr.pantheonsorbonne.dto.PaperDTOs.PaperDTO;
import fr.pantheonsorbonne.global.UserInfoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;

@ApplicationScoped
public class PdfGenerator {

    public Byte[] generatePdf(CompletePaperDTO completePaperDTO) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Configuration générale
                float margin = 50; // Marge de la page
                float yStart = page.getMediaBox().getHeight() - margin; // Point de départ vertical
                float width = page.getMediaBox().getWidth() - 2 * margin; // Largeur de la zone de texte
                float leading = 14f; // Espacement entre les lignes

                // Récupération des informations
                PaperDTO paper = completePaperDTO.paperDTO();
                UserInfoDTO user = completePaperDTO.userInfoDTO();
                String body = completePaperDTO.body();

                // Titre de l'article
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
                yStart = writeLine(contentStream, "Article Title: " + paper.title(), width, yStart, leading);

                // Auteur
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                yStart = writeLine(contentStream, "Author: " + user.firstName() + " " + user.lastName(), width, yStart, leading);

                // Publication
                yStart = writeLine(contentStream, "Published in: " + paper.publishedIn(), width, yStart, leading);
                yStart = writeLine(contentStream, "Date: " + paper.publicationDate(), width, yStart, leading);

                // Résumé
                yStart -= leading * 1.5;
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                yStart = writeLine(contentStream, "Abstract:", width, yStart, leading);

                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                yStart = writeParagraph(contentStream, paper.abstract_(), width, yStart, leading, document);

                // Mots-clés
                yStart -= leading * 1.5;
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                yStart = writeLine(contentStream, "Keywords:", width, yStart, leading);

                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                yStart = writeParagraph(contentStream, paper.keywords(), width, yStart, leading, document);

                // Corps de l'article
                yStart -= leading * 1.5;
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                yStart = writeLine(contentStream, "Body:", width, yStart, leading);

                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                yStart = writeParagraph(contentStream, body, width, yStart, leading, document);
            }

            // Sauvegarde en mémoire
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            byte[] pdf = outputStream.toByteArray();

            // Conversion de byte[] en Byte[]
            Byte[] result = new Byte[pdf.length];
            for (int i = 0; i < pdf.length; i++) {
                result[i] = pdf[i]; // Boxing de byte en Byte
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }
    }

    private float writeLine(PDPageContentStream contentStream, String text, float width, float y, float leading) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(50, y); // Marge de 50
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
        contentStream.showText(text);
        contentStream.endText();
        return y - leading; // Retourner la nouvelle position de `y`
    }

    private float writeParagraph(PDPageContentStream contentStream, String text, float width, float y, float leading, PDDocument document) throws IOException {
        float startX = 50; // Position horizontale
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if ((line + " " + word).length() > (width / 7)) { // Largeur maximale par ligne
                // Écrire la ligne actuelle
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                contentStream.showText(line.toString().trim());
                contentStream.endText();

                // Préparer une nouvelle ligne
                line = new StringBuilder();
                y -= leading; // Décalage pour la nouvelle ligne

                // Vérifier si la page est pleine
                if (y < 50) {
                    contentStream.close(); // Ferme le flux de contenu actuel

                    // Crée une nouvelle page
                    PDPage newPage = new PDPage();
                    document.addPage(newPage);
                    contentStream = new PDPageContentStream(document, newPage);

                    // Réinitialise la position verticale
                    y = newPage.getMediaBox().getHeight() - 50;
                }
            }
            line.append(word).append(" ");
        }

        // Écrire la dernière ligne
        if (line.length() > 0) {
            contentStream.beginText();
            contentStream.newLineAtOffset(startX, y);
            contentStream.showText(line.toString().trim());
            contentStream.endText();
            y -= leading;
        }

        contentStream.close();

        return y;
    }


//    public Byte[] generatePdf(CompletePaperDTO completePaperDTO) {
//        // Generate PDF
//
//        return new Byte[0];
//    }

}
