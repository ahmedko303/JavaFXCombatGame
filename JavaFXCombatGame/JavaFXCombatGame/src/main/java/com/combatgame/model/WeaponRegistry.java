package com.combatgame.model;

import java.util.ArrayList;
import java.util.List;

public class WeaponRegistry {
    private static final List<Weapon> WEAPONS = new ArrayList<>();
    
    static {
        WEAPONS.add(new Weapon("Dagger", 15, 7.0, 400));
        WEAPONS.add(new Weapon("Arrow", 10, 8.0, 300));
        WEAPONS.add(new Weapon("Fireball", 30, 7.0, 1000));
        WEAPONS.add(new Weapon("Boulder", 40, 3.0, 1500));
        WEAPONS.add(new Weapon("Sword", 20, 6.0, 600));
    }
    
    public static List<Weapon> getAllWeapons() {
        return new ArrayList<>(WEAPONS);
    }
    
    public static Weapon getWeapon(int index) {
        if (index >= 0 && index < WEAPONS.size()) {
            Weapon original = WEAPONS.get(index);
            return new Weapon(
                original.getName(),
                original.getDamage(),
                original.getProjectileSpeed(),
                original.getCooldownMs()
            );
        }
        return getWeapon(0);
    }
    
    public static int getWeaponCount() {
        return WEAPONS.size();
    }
}
