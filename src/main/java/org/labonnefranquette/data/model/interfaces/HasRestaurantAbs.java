package org.labonnefranquette.data.model.interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import org.labonnefranquette.data.model.Restaurant;

@MappedSuperclass
public abstract class HasRestaurantAbs implements HasRestaurant {

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    @Override
    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    @Override
    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
