package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Treasure;
import dungeonmania.Goals.TreasureGoal;

public class TreasureGoalTest {
    @Test
    public void TreasureTest() {
        Dungeon dungeon = new Dungeon("1", "name");
        Treasure treasure = new Treasure(1, 1, dungeon);
        TreasureGoal goal = new TreasureGoal(dungeon);
        treasure.add();
        assertEquals(false, goal.isAchieved());
        treasure.react();
        goal.update();
        dungeon.treasureRemove(treasure);
        assertEquals(true, goal.isAchieved());
    }
}
