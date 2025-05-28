package org.labonnefranquette.data.utils;

import org.jetbrains.annotations.NotNull;
import org.labonnefranquette.data.model.Order;
import org.labonnefranquette.data.model.Payment;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class OrderTools {

    private static short orderNumberCount = 0;
    private static AtomicLong lastUse = new AtomicLong(System.currentTimeMillis());


    private static synchronized void init() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUse.get() >= 3 * 60 * 60 * 1000) { // 3 heures
            orderNumberCount = 0;
        }
    }
    private static synchronized short increment() {
        init();
        if (orderNumberCount >= 400) {
            orderNumberCount = 0;
        }
        orderNumberCount++;
        lastUse.set(System.currentTimeMillis());
        return orderNumberCount;
    }

    public Boolean checkPrice(@NotNull Order order) {

        int articlesPrice = 0;
        if (order.getArticles() != null) {
            articlesPrice = order.getArticles().stream()
                    .mapToInt(article -> article.getQuantity() * article.getTotalPrice())
                    .sum();
        }

        int menusPrice = 0;
        if (order.getMenus() != null) {
            menusPrice = order.getMenus().stream()
                    .mapToInt(menu -> menu.getQuantity() * menu.getTotalPrice())
                    .sum();
        }


        int correctPrice = articlesPrice + menusPrice;

        return correctPrice == order.getTotalPrice();
    }
    public short setOrderNumber() {
        return increment();
    }

    public String setOrderPaymentType(Collection<Payment> payments) {

        if (payments.isEmpty()) {
            return "AUCUN";
        }
        Set<String> paymentsType = payments.stream()
                .map(Payment::getType)
                .collect(Collectors.toSet());
        if (paymentsType.size() == 1) {
            return paymentsType.iterator().next();
        }
        return "MIXED";
    }
}
