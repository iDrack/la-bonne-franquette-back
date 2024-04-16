package org.example.labonnefranquette.services;

import org.example.labonnefranquette.model.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuService {

    public List<Menu> getAllMenu();

    public Optional<Menu> getMenuById(long id);
}
