package org.labonnefranquette.data.utils;

import org.labonnefranquette.data.model.Commande;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class PriceTools {
    public Boolean isCorrectPrice(Commande commande) {

        int articlesPrice = commande.getArticles().stream()
            .mapToInt(article -> article.getQuantite() * article.getPrixHT())
            .sum();

        int menusPrice = commande.getMenus().stream()
            .mapToInt(menu -> menu.getQuantite() * menu.getPrixHT())
            .sum();

        int correctPrice = articlesPrice + menusPrice;

        return correctPrice == commande.getPrixHT();
    }
}
