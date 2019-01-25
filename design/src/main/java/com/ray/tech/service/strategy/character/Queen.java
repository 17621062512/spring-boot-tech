package com.ray.tech.service.strategy.character;

import com.ray.tech.service.strategy.Character;
import com.ray.tech.service.strategy.weapon.BowArrow;

public class Queen extends Character {
    @Override
    public void fight() {
        super.weapon.useWeapon();
    }

    public Queen() {
        super(new BowArrow());
    }
}
