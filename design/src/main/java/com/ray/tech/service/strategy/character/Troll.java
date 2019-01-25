package com.ray.tech.service.strategy.character;

import com.ray.tech.service.strategy.Character;
import com.ray.tech.service.strategy.weapon.Knife;

public class Troll extends Character {
    @Override
    public void fight() {
        super.weapon.useWeapon();
    }

    public Troll() {
        super(new Knife());
    }
}
