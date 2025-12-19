package com.combatgame.model;

import javafx.scene.shape.Rectangle;

public abstract class Character {
    protected double x, y;
    protected double width = 40;
    protected double height = 40;
    protected int health;
    protected int maxHealth;
    protected Weapon weapon;
    protected Rectangle visual;

    protected String name;

    public Character(int health, Weapon weapon) {
        this.health = health;
        this.maxHealth = health;
        this.weapon = weapon;
        this.visual = new Rectangle(width, height);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name != null && !name.isEmpty() ? name : getType();
    }

    protected double vy = 0;
    protected boolean onGround = false;
    private static final double GRAVITY = 0.5;
    private static final double JUMP_STRENGTH = -12.0;

    public void applyGravity() {
        if (!onGround) {
            vy += GRAVITY;
        }
        y += vy;
        updateVisual();
    }

    public void jump() {
        if (onGround) {
            vy = JUMP_STRENGTH;
            onGround = false;
        }
    }

    public void checkGroundCollision(double groundLevel) {
        if (y + height >= groundLevel) {
            y = groundLevel - height;
            vy = 0;
            onGround = true;
        } else {
            onGround = false;
        }
        updateVisual();
    }

    public boolean isOnGround() {
        return onGround;
    }

    public abstract String getType();

    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        updateVisual();
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        updateVisual();
    }

    protected void updateVisual() {
        visual.setX(x);
        visual.setY(y);
    }

    public void takeDamage(int amount) {
        this.health -= amount;
        if (this.health < 0)
            this.health = 0;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    protected double rotation = 0;

    public void setRotation(double angle) {
        this.rotation = angle;
    }

    public double getRotation() {
        return rotation;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public Rectangle getVisual() {
        return visual;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void copyStateTo(Character newChar) {
        newChar.health = this.health;
        newChar.x = this.x;
        newChar.y = this.y;
        newChar.rotation = this.rotation;
        newChar.weapon = this.weapon;
        newChar.vy = this.vy;
        newChar.onGround = this.onGround;
        newChar.updateVisual();
    }
}
