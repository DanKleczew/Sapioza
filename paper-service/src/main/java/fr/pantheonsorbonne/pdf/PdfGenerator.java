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

    public byte[] generatePdf(CompletePaperDTO completePaperDTO) {
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
                //contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 18);
                yStart = writeText(contentStream, paper.title(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA_BOLD, 18);

                // Auteur
                //contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                yStart = writeText(contentStream, "Author: " + user.firstName() + " " + user.lastName(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA, 12);

                // Publication
                yStart = writeText(contentStream, "Published in: " + paper.publishedIn(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA, 12);
                yStart = writeText(contentStream, "Date: " + paper.publicationDate().toString(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA, 12);

                // Résumé
                yStart -= leading * 1.5;
                //contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                yStart = writeText(contentStream, "Abstract:", width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA_BOLD, 14);

                //contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                yStart = writeText(contentStream, paper.abstract_(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA, 12);

                // Mots-clés
                yStart -= leading * 1.5;
                //contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                yStart = writeText(contentStream, "Keywords:", width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA_BOLD, 14);

                //contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                yStart = writeText(contentStream, paper.keywords(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA, 12);

                // Corps de l'article
                yStart -= leading * 1.5;
                //contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 14);
                yStart = writeText(contentStream, "Body:", width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA_BOLD, 14);

                //contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                yStart = writeText(contentStream, body, width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA, 12);
                contentStream.close();
            }

            // Sauvegarde en mémoire
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }
    }

    private float writeText(PDPageContentStream contentStream, String text, float width, float y, float leading, PDDocument document, Standard14Fonts.FontName fontName, int fontSize) throws IOException {
        float startX = 50; // Position horizontale
        PDType1Font font = new PDType1Font(fontName);
        contentStream.setFont(font, fontSize);

        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            String testLine = line.length() == 0 ? word : line + " " + word;
            float textWidth = font.getStringWidth(testLine) / 1000 * fontSize;

            if (textWidth > width) {
                // Écrire la ligne actuelle
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText(line.toString().trim());
                contentStream.endText();

                // Préparer une nouvelle ligne
                line = new StringBuilder(word);
                y -= leading;

                // Vérifier si la page est pleine
                if (y < 50) {
                    contentStream.close(); // Ferme le flux de contenu actuel

                    // Crée une nouvelle page
                    PDPage newPage = new PDPage();
                    document.addPage(newPage);
                    contentStream = new PDPageContentStream(document, newPage);

                    // Réinitialise la position verticale
                    y = newPage.getMediaBox().getHeight() - 50;

                    // Réinitialiser les propriétés de la police
                    contentStream.setFont(font, fontSize);
                }
            } else {
                line.append(line.length() == 0 ? word : " " + word);
            }
        }

        // Écrire la dernière ligne
        if (line.length() > 0) {
            contentStream.beginText();
            contentStream.newLineAtOffset(startX, y);
            contentStream.showText(line.toString().trim());
            contentStream.endText();
            y -= leading;
        }

        return y;
    }

}
