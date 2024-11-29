package org.labonnefranquette.data.security.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtBlacklistService {

    static private final Set<String> blacklist = new HashSet<>();

    static public void addToBlacklist(String token) {
        blacklist.add(token);
    }

    static public boolean isBlacklisted(String token) {
        return blacklist.contains(token);
    }

    static public void clearBlacklist() {
        blacklist.clear();
    }
}