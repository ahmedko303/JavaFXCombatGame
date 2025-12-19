package com.combatgame.model;

public class CharacterFactory {
    
    public static Character createCharacter(String type) {
        switch (type) {
            case "Wizard":
                return new Wizard();
            case "Archer":
                return new Archer();
            case "Rogue":
                return new Rogue();
            case "Bowler":
                return new Bowler();
            default:
                return new Wizard();
        }
    }
    
    public static String[] getAllCharacterTypes() {
        return new String[]{"Wizard", "Archer", "Rogue", "Bowler"};
    }
    
    public static int getCharacterIndex(String type) {
        String[] types = getAllCharacterTypes();
        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(type)) {
                return i;
            }
        }
        return 0;
    }
}
