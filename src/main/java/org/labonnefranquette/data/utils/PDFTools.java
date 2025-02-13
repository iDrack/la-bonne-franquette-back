package org.labonnefranquette.data.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.labonnefranquette.data.model.Paiement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class PDFTools {

    private static PDFTools instance;

    private PDFTools() {
    }

    public static PDFTools getInstance() {
        if (instance == null) {
            instance = new PDFTools();
        }
        return instance;
    }

    public Path toPDF(Paiement paiement, String filename, boolean seeDetails) throws IOException {
        Path pathTmp = Paths.get("tmp");
        if (!Files.exists(pathTmp)) {
            Files.createDirectories(pathTmp);
        }
        Path pathPdf = Paths.get("tmp/pdf");
        if (!Files.exists(pathPdf)) {
            Files.createDirectories(pathPdf);
        }

        // Créer un document PDF
        PDDocument document = new PDDocument();

        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm 'le' dd/MM/yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        String formattedDate = formatter.format(paiement.getDate());

        // Ajouter une page au document
        PDPage page = new PDPage();
        document.addPage(page);

        // Créer un flux de contenu pour écrire dans la page
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Définir la police et la taille de la police
        contentStream.setFont(PDType1Font.HELVETICA, 12);

        // Peut être modifier
        contentStream.beginText();
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(100, 700);
        contentStream.showText("Numéro de commande : " + paiement.getCommande().getNumero());
        contentStream.newLine();
        contentStream.showText("Identifiant de commande : " + paiement.getCommande().getId());
        contentStream.newLine();
        contentStream.showText("Date : " + formattedDate);
        contentStream.newLine();
        contentStream.showText("Type de réglement : " + paiement.getType().getName());
        contentStream.newLine();

        if (paiement.getArticles() != null && seeDetails) {
            paiement.getArticles().forEach(article -> {
                try {
                    String articleName = article.getNom();
                    String articlePrice = String.format("%.2f€", (article.getPrixHT() * 1.1) / 100);
                    contentStream.showText("    " + articleName + " : " + articlePrice);
                    contentStream.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        contentStream.newLine();
        contentStream.showText("Prix HT : " + paiement.getPrixHT() / 100 + "€");
        contentStream.newLine();
        contentStream.showText("Prix TTC : " + paiement.getPrixTTC() / 100 + "€");
        contentStream.newLine();
        contentStream.showText("Prix Total de la commande : " + (paiement.getCommande().getPrixHT() * 1.1) / 100 + "€");
        contentStream.newLine();
        contentStream.showText("Taux de TVA : 10%");
        contentStream.endText();

        // Fermer le flux de contenu
        contentStream.close();

        // Sauvegarder le document PDF
        document.save("tmp/pdf/" + filename);

        // Fermer le document
        document.close();
        return Paths.get("tmp/pdf/" + filename);
    }
}