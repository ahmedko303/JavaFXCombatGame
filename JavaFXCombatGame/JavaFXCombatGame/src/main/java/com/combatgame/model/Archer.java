package com.combatgame.model;

import javafx.scene.paint.Color;

public class Archer extends Character {
    public Archer() {
        super(100, WeaponRegistry.getWeapon(1));
        this.visual.setFill(Color.GREEN);
    }

    @Override
    public String getType() {
        return "Archer";
    }
}
