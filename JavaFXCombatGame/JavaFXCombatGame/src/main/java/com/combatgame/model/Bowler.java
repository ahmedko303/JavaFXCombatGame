package com.combatgame.model;

import javafx.scene.paint.Color;

public class Bowler extends Character {
    public Bowler() {
        super(200, WeaponRegistry.getWeapon(3));
        this.visual.setFill(Color.DARKGREEN);
    }

    @Override
    public String getType() {
        return "Bowler";
    }
}
