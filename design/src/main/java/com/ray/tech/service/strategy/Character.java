package com.ray.tech.service.strategy;

public abstract class Character {
    protected Weapon weapon;

    abstract public void fight();

    public Character(Weapon weapon) {
        this.weapon = weapon;
    }
}
