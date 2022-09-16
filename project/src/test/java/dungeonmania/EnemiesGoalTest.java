package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dungeonmania.Goals.EnemiesGoal;
import dungeonmania.movingentity.ZombieToast;

public class EnemiesGoalTest {
    @Test
    public void EnemiesTest() {
        Dungeon dungeon = new Dungeon("1", "name");
        EnemiesGoal goal = new EnemiesGoal(dungeon);
        ZombieToast zombieToast = new ZombieToast(1, 1, dungeon);
        zombieToast.add();
        goal.update();
        assertEquals(false, goal.isAchieved());
        zombieToast.remove();
        goal.update();
        assertEquals(true, goal.isAchieved());
        assertEquals("", goal.finalGoal());
    }
}
