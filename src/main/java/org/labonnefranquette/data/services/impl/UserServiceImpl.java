package org.labonnefranquette.data.services.impl;

import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.model.enums.Roles;
import org.labonnefranquette.data.repository.UserRepository;
import org.labonnefranquette.data.services.RestaurantService;
import org.labonnefranquette.data.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RestaurantService restaurantService;

    @Override
    public User createUser(UserCreateDto userCreateDto) throws IllegalArgumentException {
        Restaurant restaurant = restaurantService.findAllById(userCreateDto.getRestaurantId()).orElseThrow(() -> new RuntimeException("Id restaurant : " + userCreateDto.getRestaurantId() + ", restaurant introuvable."));
        if (!this.dataIsConformed(userCreateDto)) {
            throw new IllegalArgumentException("Impossible de créer ce nouvel utilisateur: Informations de connexions incorrectes.");
        }
        if (Boolean.TRUE.equals(this.userRepository.existsByUsername(userCreateDto.getUsername()))) {
            throw new IllegalArgumentException("Impossible de créer ce nouvel utilisateur: L'utilisateur '" + userCreateDto.getUsername() + "' éxiste déjà.");
        }

        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        User user = new User();
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        if (userCreateDto.getRole() == null || userCreateDto.getRole().isEmpty()) {
            user.setRoles(Roles.ROLE_USER);
        } else {
            switch (userCreateDto.getRole()) {
                case "ROLE_ADMIN":
                    user.setRoles(Roles.ROLE_ADMIN);
                case "ROLE_USER":
                    user.setRoles(Roles.ROLE_USER);
                case "ROLE_MANAGER":
                    user.setRoles(Roles.ROLE_MANAGER);
                default:
                    user.setRoles(Roles.ROLE_USER);
            }
        }
        user.setRestaurant(restaurant);

        this.userRepository.save(user);
        return user;

    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Modifie l'utilisateur pour qu'il devienne Admin
     *
     * @param user L'utilisateur à modifier
     * @return L'utilisateur modifié
     */
    @Override
    public User setUserAdmin(User user) {
        user.resetRoles();
        user.setRoles(Roles.ROLE_ADMIN);
        userRepository.save(user);
        return user;
    }

    Boolean dataIsConformed(UserCreateDto user) {
        if (user == null) {
            return false;
        }
        if (user.getUsername() == null) {
            return false;
        }
        if (user.getPassword() != null && !this.isValidPassword(user.getPassword())) {
            return false;
        }
        return true;
    }

    boolean isValidUsername(String username) {
        User user = this.userRepository.findByUsername(username);
        return user == null;
    }

    boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$");
    }
}





