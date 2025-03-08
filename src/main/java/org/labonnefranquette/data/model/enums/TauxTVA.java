package org.labonnefranquette.data.model.enums;

public enum TauxTVA {
    AUCUN, INTERMEDIAIRE, NORMAL;

    public float getFloat() {
        switch (this) {
            case AUCUN -> {
                return 1;
            }
            case NORMAL -> {
                return 1.20f;
            }
            case INTERMEDIAIRE -> {
                return 1.10f;
            }
            default -> {
                return 1;
            }
        }
    }

    public TauxTVA fromString(String tauxTVA) {
        switch (tauxTVA) {
            case "AUCUN" -> {
                return AUCUN;
            }
            case "NORMAL" -> {
                return NORMAL;
            }
            case "INTERMEDIAIRE" -> {
                return INTERMEDIAIRE;
            }
            default -> {
                return AUCUN;
            }
        }
    }

}
