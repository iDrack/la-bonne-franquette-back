package org.labonnefranquette.data.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.labonnefranquette.data.model.Paiement;

import java.io.IOException;

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

    public void toPDF(Paiement paiement, String dest) throws IOException {
        // Créer un document PDF
        PDDocument document = new PDDocument();

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
        contentStream.newLineAtOffset(25, 700);
        contentStream.showText("Facture :");
        contentStream.newLine();
        contentStream.showText("Numéro de commande : " + paiement.getCommande().getNumero());
        contentStream.newLine();
        contentStream.showText("Identifiant de commande : " + paiement.getCommande().getId());
        contentStream.newLine();
        contentStream.showText("Date : " + paiement.getDate());
        contentStream.newLine();
        contentStream.showText("Type de réglement : " + paiement.getType());
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
        document.save(dest);

        // Fermer le document
        document.close();
    }
}