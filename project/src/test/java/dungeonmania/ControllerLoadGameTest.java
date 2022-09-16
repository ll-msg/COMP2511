package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ControllerLoadGameTest {

    @Test
    public void testLoadSuccess() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> {
            controller.newGame("maze", "standard");
        });
        controller.saveGame("dungeon0");
        assertDoesNotThrow(() -> {
            controller.loadGame("dungeon0");
        });
    }

    @Test
    public void testLoadNotExist() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> {
            controller.newGame("maze", "standard");
        });
        controller.saveGame("dungeon0");
        assertThrows(IllegalArgumentException.class, () -> {
            controller.loadGame("dunge");
        });
    }

    @Test
    public void loadSwampTest() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> {
            controller.newGame("swampLoadTest", "standard");
        });
    }

    @Test
    public void loadHydraTest() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> {
            controller.newGame("hydraLoadTest", "standard");
        });
    }

    @Test
    public void loadOneRingTest() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> {
            controller.newGame("oneRingLoadTest", "standard");
        });
    }

    @Test
    public void loadAssassinTest() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> {
            controller.newGame("assassinLoadTest", "standard");
        });
    }

}
