package com.combatgame.model;

import javafx.scene.paint.Color;

public class Rogue extends Character {
    public Rogue() {
        super(90, WeaponRegistry.getWeapon(0));
        this.visual.setFill(Color.DARKRED);
    }

    @Override
    public String getType() {
        return "Rogue";
    }
}
