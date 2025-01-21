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
/*
    public byte[] generatePdf(CompletePaperDTO completePaperDTO) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            try {
                float margin = 50;
                float yStart = page.getMediaBox().getHeight() - margin;
                float width = page.getMediaBox().getWidth() - 2 * margin;
                float leading = 14f;

                PaperDTO paper = completePaperDTO.paperDTO();
                UserInfoDTO user = completePaperDTO.userInfoDTO();
                String body = completePaperDTO.body();

                // Titre de l'article
                contentStream = writeText(contentStream, paper.title(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA_BOLD, 18);

                // Auteur
                contentStream = writeText(contentStream, "Author: " + user.firstName() + " " + user.lastName(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA, 12);

                // Publication
                contentStream = writeText(contentStream, "Published in: " + paper.publishedIn(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA, 12);
                contentStream = writeText(contentStream, "Date: " + paper.publicationDate().toString(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA, 12);

                // Résumé
                yStart -= leading * 1.5;
                contentStream = writeText(contentStream, "Abstract:", width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA_BOLD, 14);
                contentStream = writeText(contentStream, paper.abstract_(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA, 12);

                // Mots-clés
                yStart -= leading * 1.5;
                contentStream = writeText(contentStream, "Keywords:", width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA_BOLD, 14);
                contentStream = writeText(contentStream, paper.keywords(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA, 12);

                // Corps de l'article
                yStart -= leading * 1.5;
                contentStream = writeText(contentStream, "Body:", width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA_BOLD, 14);
                contentStream = writeText(contentStream, body, width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA, 12);

            } finally {
                contentStream.close();
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }
    }

    private PDPageContentStream writeText(PDPageContentStream contentStream, String text, float width, float y, float leading, PDDocument document, Standard14Fonts.FontName fontName, int fontSize) throws IOException {
        float startX = 50; // Position horizontale
        PDType1Font font = new PDType1Font(fontName);
        contentStream.setFont(font, fontSize);

        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            String testLine = line.length() == 0 ? word : line + " " + word;
            float textWidth = font.getStringWidth(testLine) / 1000 * fontSize;

            if (textWidth > width) {
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText(line.toString().trim());
                contentStream.endText();

                line = new StringBuilder(word);
                y -= leading;

                if (y < 50) {
                    // Ferme le flux actuel avant de créer une nouvelle page
                    contentStream.close();

                    PDPage newPage = new PDPage();
                    document.addPage(newPage);
                    contentStream = new PDPageContentStream(document, newPage);
                    y = newPage.getMediaBox().getHeight() - 50;

                    contentStream.setFont(font, fontSize);
                }
            } else {
                line.append(line.length() == 0 ? word : " " + word);
            }
        }

        if (line.length() > 0) {
            contentStream.beginText();
            contentStream.newLineAtOffset(startX, y);
            contentStream.showText(line.toString().trim());
            contentStream.endText();
            y -= leading;
        }

        return contentStream; // Retourne le flux (peut être un nouveau flux si une nouvelle page est ajoutée)
    }
*/

    public byte[] generatePdf(CompletePaperDTO completePaperDTO) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // Initialisation des paramètres de mise en page
            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float width = page.getMediaBox().getWidth() - 2 * margin;
            float leading = 14f;

            PaperDTO paper = completePaperDTO.paperDTO();
            UserInfoDTO user = completePaperDTO.userInfoDTO();
            String body = completePaperDTO.body();

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            try {
                // Titre de l'article
                yStart = writeText(contentStream, paper.title(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA_BOLD_OBLIQUE, 16);
                yStart -= leading * 1.5;

                // Auteur
                yStart = writeText(contentStream, "Author: " + user.firstName() + " " + user.lastName(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA, 12);

                // Publication
                yStart = writeText(contentStream, "Published in: " + paper.publishedIn(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA, 12);
                yStart = writeText(contentStream, "Date: " + paper.publicationDate().toString(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA, 12);

                // Résumé
                yStart -= leading * 1.5;
                yStart = writeText(contentStream, "Abstract", width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA_BOLD, 12);
                yStart -= leading * 0.45;
                yStart = writeText(contentStream, paper.abstract_(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA, 12);

                // Mots-clés
                yStart -= leading * 1.5;
                yStart = writeText(contentStream, "Keywords", width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA_BOLD, 12);
                yStart -= leading * 0.45;
                yStart = writeText(contentStream, paper.keywords(), width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA_OBLIQUE, 12);

                // Corps de l'article
                yStart -= leading * 1.5;
                //yStart = writeText(contentStream, "Body:", width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA_BOLD_OBLIQUE, 14);
                yStart = writeText(contentStream, body, width, yStart, leading, document, Standard14Fonts.FontName.HELVETICA, 12);
            } finally {
                contentStream.close();
            }

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
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText(line.toString().trim());
                contentStream.endText();

                line = new StringBuilder(word);
                y -= leading * 1.2;
/*
                // Si on dépasse la marge basse, ajoute une nouvelle page
                if (y < 50) {
                    contentStream.close();

                    PDPage newPage = new PDPage();
                    document.addPage(newPage);
                    contentStream = new PDPageContentStream(document, newPage);
                    y = newPage.getMediaBox().getHeight() - 50;

                    contentStream.setFont(font, fontSize);
                }
 */
            } else {
                line.append(line.length() == 0 ? word : " " + word);
            }
        }

        if (line.length() > 0) {
            contentStream.beginText();
            contentStream.newLineAtOffset(startX, y);
            contentStream.showText(line.toString().trim());
            contentStream.endText();
            y -= leading;
        }

        return y; // Renvoie la nouvelle position verticale
    }


}

