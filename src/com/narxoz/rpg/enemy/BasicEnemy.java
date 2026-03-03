package com.narxoz.rpg.enemy;

public class BasicEnemy implements Enemy {
    private final String title;
    private final int damage;
    private int health;

    public BasicEnemy(String title, int damage, int health) {
        this.title = title;
        this.damage = damage;
        this.health = health;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void applyDamage(int amount) {
        // TODO: enforce min 0
        int dmg = Math.max(0, amount);
        health = Math.max(0, amount-dmg);
    }

    @Override
    public boolean isDefeated() {
        return health <= 0;
    }

    public int getHealth() {
        return health;
    }
}
