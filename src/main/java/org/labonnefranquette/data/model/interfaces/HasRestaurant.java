package org.labonnefranquette.data.model.interfaces;

import org.labonnefranquette.data.model.Restaurant;

public interface HasRestaurant {
    public Restaurant getRestaurant();

    public void setRestaurant(Restaurant restaurant);
}
