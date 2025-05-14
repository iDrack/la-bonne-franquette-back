package org.labonnefranquette.data.model.interfaces;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.labonnefranquette.data.model.enums.TauxTVA;

@MappedSuperclass
@Data
public abstract class RestaurantItemAbs extends HasRestaurantAbs implements RestaurantItem, HasPrice {
    @Column(name = "taux_tva")
    @NotNull(message = "Ce champs ne peut pas être vide.")
    @Enumerated(EnumType.STRING)
    private TauxTVA tauxTVA;


    @Column(name = "prix_ht", nullable = false, length = 10)
    @NotNull(message = "Ce champs ne peut pas être vide")
    @Min(value = 0, message = "Ce champs ne peut pas être négatif")
    private int prixHT;


    public float getTauxTVAFloat() {
        if (this.getRestaurant() != null && getRestaurant().getIsTVAEnable()) {
            return tauxTVA.getFloat();
        }
        return TauxTVA.AUCUN.getFloat();
    }

    @Override
    public TauxTVA getTauxTVA() {
        return this.tauxTVA;
    }

    @Override
    public void setTauxTVA(TauxTVA tauxTVA) {
        this.tauxTVA = tauxTVA;
    }

    public int getTotalPrice() {
        return (int) ((this.prixHT) * this.getTauxTVAFloat());
    }

    public void setTotalPrice(int prixHT) {
        this.prixHT = prixHT;
    }

}
