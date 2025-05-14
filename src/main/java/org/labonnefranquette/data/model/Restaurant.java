package org.labonnefranquette.data.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Data
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private String name;

    @Column(name = "version_carte", nullable = false)
    private int versionCarte;

    @Column(name = "tva_enable", nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide.")
    private Boolean isTVAEnable;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> employees;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Addon> addons;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> menuItems;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentType> paymentTypes;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubCategory> subCategories;

    public Restaurant() {
        this.versionCarte = 1;
        this.isTVAEnable = false;
        this.employees = new ArrayList<>();
        this.ingredients = new ArrayList<>();
        this.products = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.addons = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.menuItems = new ArrayList<>();
        this.menus = new ArrayList<>();
        this.paymentTypes = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.subCategories = new ArrayList<>();
    }

    public void updateVersionCarte() {
        this.versionCarte++;
    }
}
