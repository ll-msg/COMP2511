package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.ActiveStrategy.OrStrategy;
import dungeonmania.Goals.BouldersGoal;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Switch;
import dungeonmania.util.Direction;

public class BouldersGoalTest {
    @Test
    public void BouldersTest() {
        Dungeon dungeon = new Dungeon("1", "name");
        BouldersGoal goal = new BouldersGoal(dungeon);
        Boulder boulder0 = new Boulder(1, 1, dungeon);
        Switch switch0 = new Switch(1, 2, dungeon, new OrStrategy());
        boulder0.add();
        switch0.add();
        assertEquals(true, boulder0.canBePushed(Direction.DOWN));
        assertEquals(false, goal.isAchieved());
        boulder0.push(Direction.DOWN);
        goal.update();
        assertEquals(true, switch0.isSwitched());
        assertEquals(true, goal.isAchieved());
    }
}
