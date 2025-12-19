package com.combatgame.model;

public class Weapon {
    private String name;
    private int damage;
    private double projectileSpeed;
    private long cooldownMs;
    private long lastShotTime;

    public Weapon(String name, int damage, double projectileSpeed, long cooldownMs) {
        this.name = name;
        this.damage = damage;
        this.projectileSpeed = projectileSpeed;
        this.cooldownMs = cooldownMs;
        this.lastShotTime = 0;
    }

    public boolean canShoot(long currentTime) {
        if (currentTime - lastShotTime >= cooldownMs) {
            lastShotTime = currentTime;
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public double getProjectileSpeed() {
        return projectileSpeed;
    }

    public long getCooldownMs() {
        return cooldownMs;
    }

    public void resetCooldown() {
        lastShotTime = 0;
    }
}
