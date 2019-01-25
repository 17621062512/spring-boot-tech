package com.ray.tech.service.strategy.character;

import com.ray.tech.service.strategy.Character;
import com.ray.tech.service.strategy.weapon.Axe;

public class Knight extends Character {
    @Override
    public void fight() {
        super.weapon.useWeapon();
    }

    public Knight() {
        super(new Axe());
    }
}
