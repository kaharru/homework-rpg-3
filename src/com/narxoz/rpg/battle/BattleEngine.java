package com.narxoz.rpg.battle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class BattleEngine {
    private static BattleEngine instance;
    private Random random = new Random(1L);

    private BattleEngine() {
    }

    public static BattleEngine getInstance() {
        if (instance == null) {
            instance = new BattleEngine();
        }
        return instance;
    }

    public BattleEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public void reset() {
        // TODO: reset any battle state if you add it
        this.random = new java.util.Random(1L);
    }

    public EncounterResult runEncounter(List<Combatant> teamA, List<Combatant> teamB) {
        if (teamA == null || teamB == null){
            throw new IllegalArgumentException("teams must not be null");
        }
        if (teamA.isEmpty()|| teamB.isEmpty()){
            throw new IllegalArgumentException("Teams must not be empty");
        }
        // TODO: validate inputs and run round-based battle
        // TODO: use random if you add critical hits or target selection
        // Work on copies so caller lists aren’t unexpectedly modified
        List<Combatant> a = new ArrayList<>(teamA);
        List<Combatant> b = new ArrayList<>(teamB);

        EncounterResult result = new EncounterResult();
        result.addLog("=== Encounter Start ===");
        result.addLog("Team A size=" + a.size() + ", Team B size=" + b.size());

        int rounds = 0;

        // End when one team has 0 living combatants
        while (hasLiving(a) && hasLiving(b)) {
            rounds++;
            result.addLog("\n-- Round " + rounds + " --");

            // Team A attacks Team B in order
            performAttacksInOrder(a, b, "A", result);

            // Team B attacks Team A in order (only if still alive)
            if (hasLiving(b)) {
                performAttacksInOrder(b, a, "B", result);
            }
        }

        result.setRounds(rounds);

        if (hasLiving(a) && !hasLiving(b)) result.setWinner("TEAM_A");
        else if (!hasLiving(a) && hasLiving(b)) result.setWinner("TEAM_B");
        else result.setWinner("DRAW");

        result.addLog("\n=== Encounter End ===");
        result.addLog("Winner: " + result.getWinner());
        result.addLog("Rounds: " + result.getRounds());

        return result;
    }

    // Team attacks other team in order; remove dead immediately (required)
    private void performAttacksInOrder(List<Combatant> attackers,
                                       List<Combatant> defenders,
                                       String teamName,
                                       EncounterResult result) {

        for (Combatant attacker : attackers) {
            if (attacker == null || !attacker.isAlive()) continue;
            if (!hasLiving(defenders)) return;

            Combatant target = pickRandomLiving(defenders);
            if (target == null) return;

            int dmg = Math.max(1, attacker.getAttackPower());
            target.takeDamage(dmg);

            result.addLog("Team " + teamName + ": " + attacker.getName()
                    + " hits " + target.getName()
                    + " for " + dmg);

            // remove dead combatants from the fight (required)
            if (!target.isAlive()) {
                result.addLog(target.getName() + " is defeated!");
                defenders.remove(target);
            }
        }
    }

    private boolean hasLiving(List<Combatant> team) {
        for (Combatant c : team) {
            if (c != null && c.isAlive()) return true;
        }
        return false;
    }

    private Combatant pickRandomLiving(List<Combatant> team) {
        List<Combatant> alive = new ArrayList<>();
        for (Combatant c : team) {
            if (c != null && c.isAlive()) alive.add(c);
        }
        if (alive.isEmpty()) return null;
        return alive.get(random.nextInt(alive.size()));
    }
    }
