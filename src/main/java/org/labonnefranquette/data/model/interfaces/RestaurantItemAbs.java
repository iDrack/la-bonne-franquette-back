package org.labonnefranquette.data.model.interfaces;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.labonnefranquette.data.model.enums.VATRate;

@MappedSuperclass
@Data
public abstract class RestaurantItemAbs extends HasRestaurantAbs implements RestaurantItem, HasPrice {
    @Column(name = "vat_rate")
    @NotNull(message = "Ce champs ne peut pas être vide.")
    @Enumerated(EnumType.STRING)
    private VATRate VATRate;


    @Column(name = "price", nullable = false, length = 10)
    @NotNull(message = "Ce champs ne peut pas être vide")
    @Min(value = 0, message = "Ce champs ne peut pas être négatif")
    private int price;


    public float getVATRateFloat() {
        if (this.getRestaurant() != null && getRestaurant().getIsTVAEnable()) {
            return VATRate.getFloat();
        }
        return VATRate.AUCUN.getFloat();
    }

    public VATRate getVATRate() {
        return this.VATRate;
    }

    public void setVATRate(VATRate VATRate) {
        this.VATRate = VATRate;
    }

    public int getTotalPrice() {
        return (int) ((this.price) * this.getVATRateFloat());
    }

    public void setTotalPrice(int totalPrice) {
        this.price = totalPrice;
    }

}
