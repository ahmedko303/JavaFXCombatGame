package com.combatgame.model;

import javafx.scene.paint.Color;

public class Wizard extends Character {
    public Wizard() {
        super(80, WeaponRegistry.getWeapon(2));
        this.visual.setFill(Color.PURPLE);
    }

    @Override
    public String getType() {
        return "Wizard";
    }
}
