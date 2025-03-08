package org.labonnefranquette.data.model.interfaces;

import org.labonnefranquette.data.model.enums.TauxTVA;

public interface RestaurantItem extends HasRestaurant {

    public float getTauxTVAFloat();

    public TauxTVA getTauxTVA();

    public void setTauxTVA(TauxTVA tauxTVA);

    public void setPrixHT(int prixHT);
}
