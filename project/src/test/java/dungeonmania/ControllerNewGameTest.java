package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ControllerNewGameTest {
    @Test
    public void testNewSuccess() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() ->{controller.newGame("maze", "standard");});
    }


    @Test
    public void testNewWithWrongName() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () ->{controller.newGame("wda", "standard");});
    }

    @Test
    public void testNewWithWrongMode() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () ->{controller.newGame("maze", "NotExist");});
    }
}
