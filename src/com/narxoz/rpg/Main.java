package com.narxoz.rpg;

import com.narxoz.rpg.adapter.EnemyCombatantAdapter;
import com.narxoz.rpg.adapter.HeroCombatantAdapter;
import com.narxoz.rpg.battle.BattleEngine;
import com.narxoz.rpg.battle.Combatant;
import com.narxoz.rpg.battle.EncounterResult;
import com.narxoz.rpg.enemy.Goblin;
import com.narxoz.rpg.hero.Mage;
import com.narxoz.rpg.hero.Warrior;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== HW3 Battle Engine Demo (Singleton + Adapter) ===\n");

        // ===== Create heroes =====
        Warrior warrior = new Warrior("Arman");
        Mage mage = new Mage("Asylbek");

        // ===== Create enemies =====
        Goblin goblin1 = new Goblin();
        Goblin goblin2 = new Goblin();
        Goblin goblin3 = new Goblin();

        // ===== Wrap with adapters =====
        List<Combatant> teamA = new ArrayList<>();
        teamA.add(new HeroCombatantAdapter(warrior));
        teamA.add(new HeroCombatantAdapter(mage));

        List<Combatant> teamB = new ArrayList<>();
        teamB.add(new EnemyCombatantAdapter(goblin1));
        teamB.add(new EnemyCombatantAdapter(goblin2));
        teamB.add(new EnemyCombatantAdapter(goblin3));

        // ===== Demonstrate Singleton =====
        BattleEngine engine1 = BattleEngine.getInstance();
        BattleEngine engine2 = BattleEngine.getInstance();

        System.out.println("Same BattleEngine instance? " + (engine1 == engine2));
        System.out.println();

        engine1.setRandomSeed(42L);

        // ===== Run battle =====
        EncounterResult result = engine1.runEncounter(teamA, teamB);

        // ===== Print summary =====
        System.out.println("Winner: " + result.getWinner());
        System.out.println("Rounds: " + result.getRounds());
        System.out.println("\n--- Battle Log ---");

        for (String line : result.getBattleLog()) {
            System.out.println(line);
        }

        System.out.println("\n=== Demo Complete ===");
    }
}
