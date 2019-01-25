package com.ray.tech.service.strategy.weapon;

import com.ray.tech.service.strategy.Weapon;

public class Axe implements Weapon {
    @Override
    public void useWeapon() {
        System.out.println("axe");
    }
}
