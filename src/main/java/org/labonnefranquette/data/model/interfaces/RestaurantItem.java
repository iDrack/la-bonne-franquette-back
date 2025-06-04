package org.labonnefranquette.data.model.interfaces;

import org.labonnefranquette.data.model.enums.VATRate;

public interface RestaurantItem extends HasRestaurant {

    public float getVATRateFloat();

    public VATRate getVATRate();

    public void setVATRate(VATRate VATRate);

    public void setPrice(int price);
}
