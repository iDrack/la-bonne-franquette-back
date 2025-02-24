package org.labonnefranquette.data.model.interfaces;

import org.labonnefranquette.data.model.Restaurant;

public interface HasRestaurant {
    Restaurant getRestaurant();

    void setRestaurant(Restaurant restaurant);
}
