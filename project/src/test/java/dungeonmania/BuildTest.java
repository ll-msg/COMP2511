package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Key;
import dungeonmania.CollectableEntities.Treasure;
import dungeonmania.CollectableEntities.Wood;
import dungeonmania.movingentity.Player;
import dungeonmania.util.Direction;

public class BuildTest {
    @Test
    public void buildShield() {
        Dungeon dungeon = new Dungeon("1", "name");
        Wood Wood0 = new Wood(1, 2,dungeon);
        Wood Wood1 = new Wood(1, 3,dungeon);
        Treasure treasure = new Treasure(1, 4, dungeon);
        dungeon.getPlayer().initialisePlayerPosition(1, 1);
        Wood0.add();
        Wood1.add();
        treasure.add();
        Player player = dungeon.getPlayer();
        player.playerMove(Direction.DOWN);
        player.playerTick();
        player.playerMove(Direction.DOWN);
        player.playerTick();
        player.playerMove(Direction.DOWN);
        player.playerTick();
        player.playerMove(Direction.DOWN);
        player.playerTick();
        dungeon.getInventory().buildShield();
        assertTrue(dungeon.getInventory().getBuildables().size() > 0);
    }

    @Test
    public void buildShieldWithKey() {
        Dungeon dungeon = new Dungeon("1", "name");
        Wood Wood0 = new Wood(1, 2,dungeon);
        Wood Wood1 = new Wood(1, 3,dungeon);
        Key key = new Key(1, 4, dungeon, 1);
        dungeon.getPlayer().initialisePlayerPosition(1, 1);
        Wood0.add();
        Wood1.add();
        key.add();
        Player player = dungeon.getPlayer();
        player.playerMove(Direction.DOWN);
        player.playerTick();
        player.playerMove(Direction.DOWN);
        player.playerTick();
        player.playerMove(Direction.DOWN);
        player.playerTick();
        player.playerMove(Direction.DOWN);
        player.playerTick();
        dungeon.getInventory().buildShield();
        assertTrue(dungeon.getInventory().getBuildables().size() > 0);
    }
}
