package com.combatgame.model;

import javafx.scene.Node;

public class Projectile {
    private double x, y;
    private double speed;
    private int damage;
    private Character owner;
    private int direction;
    private Node visual;
    private boolean active;

    public Projectile(double x, double y, double speed, int damage, int direction, Character owner, Node visual) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.damage = damage;
        this.direction = direction;
        this.owner = owner;
        this.visual = visual;
        this.active = true;

        this.visual.setLayoutX(x);
        this.visual.setLayoutY(y);
    }

    public Character getOwner() {
        return owner;
    }

    public void update() {
        x += speed * direction;
        visual.setLayoutX(x);
    }

    public Node getVisual() {
        return visual;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        visual.setVisible(active);
    }

    public double getX() {
        return x;
    }

    public int getDamage() {
        return damage;
    }
}
