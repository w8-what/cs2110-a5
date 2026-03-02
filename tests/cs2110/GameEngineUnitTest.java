package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

public class GameEngineUnitTest {

    private static class TestPlayer extends Player {
        TestPlayer(String name, GameEngine engine) {
            super(name, engine);
        }

        @Override
        public boolean chooseAction() {
            return false;
        }
    }

    private static void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getIntField(Object target, String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.getInt(target);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void diceRollRespectsInclusiveBounds() {
        GameEngine engine = new GameEngine(new Scanner(""), false);

        for (int i = 0; i < 500; i++) {
            int roll = engine.diceRoll(3, 3);
            assertEquals(3, roll, "A fixed-range dice roll should always return that single value.");
        }

        for (int i = 0; i < 1000; i++) {
            int roll = engine.diceRoll(-2, 2);
            assertTrue(roll >= -2 && roll <= 2, "diceRoll should include both lower and upper bounds.");
        }
    }

    @Test
    void availableWeaponsExcludesEquippedWeapons() {
        GameEngine engine = new GameEngine(new Scanner(""), false);
        Weapon w1 = new Weapon("w1", engine);
        Weapon w2 = new Weapon("w2", engine);
        Weapon w3 = new Weapon("w3", engine);

        w2.equip();
        setField(engine, "weapons", new Weapon[]{w1, w2, w3});

        Weapon[] available = engine.availableWeapons();
        assertArrayEquals(new Weapon[]{w1, w3}, available,
                "Only unequipped weapons should appear in availableWeapons().");

        w1.equip();
        w3.equip();
        Weapon[] noneAvailable = engine.availableWeapons();
        assertEquals(0, noneAvailable.length,
                "availableWeapons() should return an empty array when all weapons are equipped.");
    }

    @Test
    void processMonsterDeathMovesDeadMonsterOutOfLivingPrefix() {
        GameEngine engine = new GameEngine(new Scanner(""), false);
        Monster m1 = new Monster("m1", engine);
        Monster m2 = new Monster("m2", engine);
        Monster m3 = new Monster("m3", engine);

        setField(engine, "monsters", new Monster[]{m1, m2, m3});
        setField(engine, "numLivingMonsters", 3);

        engine.processMonsterDeath(m2);

        assertEquals(2, getIntField(engine, "numLivingMonsters"));

        Monster[] monsters = (Monster[]) getField(engine, "monsters");
        assertSame(m1, monsters[0]);
        assertSame(m3, monsters[1],
                "After deletion, the living suffix element should be swapped into the removed slot.");
        assertSame(m2, monsters[2],
                "Removed monster should end up at the first dead index.");

        engine.processMonsterDeath(m2);
        assertEquals(2, getIntField(engine, "numLivingMonsters"),
                "Removing an already-dead monster should not change living count.");
    }

    @Test
    void processPlayerDeathMovesDeadPlayerOutOfLivingPrefix() {
        GameEngine engine = new GameEngine(new Scanner(""), false);
        Player p1 = new TestPlayer("p1", engine);
        Player p2 = new TestPlayer("p2", engine);

        setField(engine, "players", new Player[]{p1, p2});
        setField(engine, "numLivingPlayers", 2);

        engine.processPlayerDeath(p1);

        assertEquals(1, getIntField(engine, "numLivingPlayers"));
        Player[] players = (Player[]) getField(engine, "players");
        assertSame(p2, players[0]);
        assertSame(p1, players[1]);
    }

    private static Object getField(Object target, String fieldName) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(target);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
