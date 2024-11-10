package org.labonnefranquette.data.services;

import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.dto.impl.UserLoginDto;

import java.util.Map;

public interface AuthService {

    /**
     * Authentifie un utilisateur avec son nom d'utilisateur et son mot de passe.
     * Génère et retourne un token JWT si l'utilisateur est authentifié.
     *
     * @param userLoginDto Les données de connexion
     * @return Le token JWT généré
     */
    Map<String, String> authenticate(UserLoginDto userLoginDto);

    /**
     * Rafraîchit un token JWT.
     *
     * @param refreshToken Le token à rafraîchir
     * @return Le nouveau token JWT généré
     */
    String refresh(String refreshToken);

    /**
     * Inscris un utilisateur.
     * @param userCreateDto
     * @return Le nouveau token JWT généré
     */
    public Map<String, String> signup(UserCreateDto userCreateDto);

    /**
     * Déconnecte un utilisateur.
     * @param accessToken
     * @param refreshToken
     * return void
     */
    public void logout(String accessToken, String refreshToken);

    /**
     * Vérifie si l'utilisateur est déjà connecté.
     * @return boolean
     */
    public boolean isConnected();
}