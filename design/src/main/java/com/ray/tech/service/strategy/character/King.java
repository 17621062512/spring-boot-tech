package com.ray.tech.service.strategy.character;

import com.ray.tech.service.strategy.Character;
import com.ray.tech.service.strategy.weapon.Sword;

public class King extends Character {
    @Override
    public void fight() {
        super.weapon.useWeapon();
    }

    public King() {
        super(new Sword());
    }
}
