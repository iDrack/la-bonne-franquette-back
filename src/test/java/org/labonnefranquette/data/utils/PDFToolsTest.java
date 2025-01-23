package org.labonnefranquette.data.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PDFToolsTest {

    private PDFTools pdfTools;

    @BeforeEach
    public void setup() {
        pdfTools = PDFTools.getInstance();
    }

    @Test
    void toPDF_createsPDFWithArticlesFormattedCorrectly() throws IOException {
        Paiement paiement = mock(Paiement.class);
        Commande commande = mock(Commande.class);
        Article article = mock(Article.class);

        when(paiement.getCommande()).thenReturn(commande);
        when(commande.getNumero()).thenReturn((short) 12345);
        when(commande.getId()).thenReturn(1L);
        when(paiement.getDate()).thenReturn(new Date());
        when(paiement.getType()).thenReturn(PaiementTypeCommande.CB);
        when(paiement.getPrixHT()).thenReturn(10000);
        when(paiement.getPrixTTC()).thenReturn(11000);
        when(commande.getPrixHT()).thenReturn(10000);
        when(commande.getArticles()).thenReturn(List.of(article));
        when(article.getNom()).thenReturn("Article 1");
        when(article.getPrixHT()).thenReturn(1000);


        pdfTools.toPDF(paiement, "test.pdf");

        PDDocument document = PDDocument.load(new File("tmp/pdf/test.pdf"));
        assertTrue(document.getNumberOfPages() > 0);
        document.close();

        Files.delete(Paths.get("tmp/pdf/test.pdf"));
    }

    @Test
    void toPDF_handlesNullArticles() throws IOException {
        Paiement paiement = mock(Paiement.class);
        Commande commande = mock(Commande.class);

        when(paiement.getCommande()).thenReturn(commande);
        when(commande.getNumero()).thenReturn((short) 12345);
        when(commande.getId()).thenReturn(1L);
        when(paiement.getDate()).thenReturn(new Date());
        when(paiement.getType()).thenReturn(PaiementTypeCommande.CB);
        when(paiement.getPrixHT()).thenReturn(10000);
        when(paiement.getPrixTTC()).thenReturn(11000);
        when(commande.getPrixHT()).thenReturn(10000);
        when(commande.getArticles()).thenReturn(null);

        pdfTools.toPDF(paiement, "test.pdf");

        PDDocument document = PDDocument.load(new File("tmp/pdf/test.pdf"));
        assertTrue(document.getNumberOfPages() > 0);
        document.close();

        Files.delete(Paths.get("tmp/pdf/test.pdf"));
    }

    @Test
    void toPDF_handlesEmptyArticlesList() throws IOException {
        Paiement paiement = mock(Paiement.class);
        Commande commande = mock(Commande.class);

        when(paiement.getCommande()).thenReturn(commande);
        when(commande.getNumero()).thenReturn((short) 12345);
        when(commande.getId()).thenReturn(1L);
        when(paiement.getDate()).thenReturn(new Date());
        when(paiement.getType()).thenReturn(PaiementTypeCommande.CB);
        when(paiement.getPrixHT()).thenReturn(10000);
        when(paiement.getPrixTTC()).thenReturn(11000);
        when(commande.getPrixHT()).thenReturn(10000);
        when(commande.getArticles()).thenReturn(Collections.emptyList());

        pdfTools.toPDF(paiement, "test.pdf");

        PDDocument document = PDDocument.load(new File("tmp/pdf/test.pdf"));
        assertTrue(document.getNumberOfPages() > 0);
        document.close();

        Files.delete(Paths.get("tmp/pdf/test.pdf"));
    }

    @Test
    void generation() {
        Commande commande = new Commande();
        commande.setNumero((short) 12345);
        commande.setId(1L);
        commande.setPrixHT(10000);

        // Créer un objet Paiement
        Paiement paiement = new Paiement();
        paiement.setDate(new Date());
        paiement.setType(PaiementTypeCommande.CB);
        paiement.setTicketEnvoye(true);
        paiement.setPrixHT(10000);
        paiement.setPrixTTC(11000);
        paiement.setCommande(commande);

        // Générer le PDF
        PDFTools pdfTools = PDFTools.getInstance();
        try {
            pdfTools.toPDF(paiement, "output.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}