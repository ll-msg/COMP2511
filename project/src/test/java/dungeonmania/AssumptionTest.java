package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import dungeonmania.ActiveStrategy.OrStrategy;
import dungeonmania.CollectableEntities.Armour;
import dungeonmania.CollectableEntities.InvincibilityPotion;
import dungeonmania.CollectableEntities.InvisibilityPotion;
import dungeonmania.CollectableEntities.Key;
import dungeonmania.CollectableEntities.Sword;
import dungeonmania.CollectableEntities.Treasure;
import dungeonmania.CollectableEntities.Wood;
import dungeonmania.StaticEntities.BombUsed;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Door;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.movingentity.Mercenary;
import dungeonmania.movingentity.NonPlayer;
import dungeonmania.movingentity.Player;
import dungeonmania.movingentity.Spider;
import dungeonmania.movingentity.ZombieToast;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;

public class AssumptionTest {
    @Test
    public void testInvisiblePlayerCantInteractWithEntity() {
        // set up the player
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // ceate a boulder
        Boulder boulder = new Boulder(2, 1, dungeon);
        boulder.add();
        // apply invisible potion
        InvisibilityPotion potion = new InvisibilityPotion(1, 1, dungeon);
        player.applyItem(potion);
        player.playerMove(Direction.RIGHT);
        // boulder should not be pushed player
        assertTrue(boulder.getX() == 2);
        assertTrue(boulder.getY() == 1);
        // player should move to new position
        assertTrue(player.getX() == 2);
        assertTrue(player.getY() == 1);

    }

    @Test
    public void testInvisiblePlayerCantOpenDoor() {
        // set up the player
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // create a key in the position of player
        Key key = new Key(1, 1, dungeon, 1);
        key.add();
        // create a lock door next to player
        Door door = new Door(2, 1, dungeon, 1);
        door.add();
        // apply invisible potion
        InvisibilityPotion potion = new InvisibilityPotion(1, 1, dungeon);
        player.applyItem(potion);
        // pick up the key
        // move to the position of the door
        player.playerMove(Direction.RIGHT);
        // check whether the door is open
        assertFalse(door.isOpen());
    }

    @Test
    public void testDuplicateApplyPotion() {
        // set up the player
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // create mercenary
        Mercenary mercenary = new Mercenary(4, 1, dungeon);
        mercenary.add();
        // apply invisible potion
        InvisibilityPotion potion = new InvisibilityPotion(1, 1, dungeon);
        player.applyItem(potion);
        assertEquals(10, player.getRemainingTime());
        // simulate one tick
        player.playerMove(Direction.NONE);
        mercenary.move();
        // mercenary should not move to player
        assertEquals(mercenary.getX(), 4);
        assertEquals(9, player.getRemainingTime());
        // duplicate dink invisible potion
        InvisibilityPotion potion2 = new InvisibilityPotion(1, 1, dungeon);
        player.applyItem(potion2);
        // remaining time should be resetted to 10
        assertEquals(10, player.getRemainingTime());
        player.playerMove(Direction.NONE);
        mercenary.move();
        // mercenary should not move to player
        assertEquals(mercenary.getX(), 4);
        // drink invincibility potion
        InvincibilityPotion potion3 = new InvincibilityPotion(1, 1, dungeon);
        player.applyItem(potion3);
        player.playerMove(Direction.NONE);
        mercenary.move();
        // mercenary should now run away (MOVE UP)
        assertEquals(mercenary.getX(), 4);
        assertEquals(mercenary.getY(), 0);
    }

    @Test
    public void testNonPlayerMoveThroughOpenDoor() {
        // set up the player
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // create a key in the position of player
        Key key = new Key(1, 1, dungeon, 1);
        key.add();
        // create a lock door next to player
        Door door = new Door(2, 1, dungeon, 1);
        door.add();
        dungeon.tickPlayerAfterMoving();
        assertEquals(1, dungeon.getInventory().getKeyNum());
        // move to the door
        player.playerMove(Direction.RIGHT);
        // let player open the door
        dungeon.tickPlayerAfterMoving();
        assertTrue(door.isOpen());
        // player position is (3 , 1)
        player.playerMove(Direction.RIGHT);
        // create a mercenary to the left of the door, the merceanry should move to the
        // door to chase player
        Mercenary mercenary = new Mercenary(1, 1, dungeon);
        mercenary.add();
        mercenary.move();
        // mercenary should be able to move through the open door
        assertEquals(2, mercenary.getX());
    }

    @Test
    public void testNonplayerPushBuild() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Boulder boulder = new Boulder(1, 1, dungeon);
        boulder.add();
        // create a zombie with seed 0 -> 60 48 29 47 15 53 91
        ZombieToast zombie = new ZombieToast(1, 2, dungeon, 0);
        // should move up but block by the boulder
        zombie.move();
        assertEquals(1, zombie.getX());
        assertEquals(2, zombie.getY());
    }

    @Test
    public void testPushBoulderToNonPlayer() {
        // set up the player
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        Boulder boulder1 = new Boulder(2, 1, dungeon);
        boulder1.add();
        Boulder boulder2 = new Boulder(1, 2, dungeon);
        boulder2.add();
        // create a zmobie to the righr of the boulder1
        NonPlayer zombie = new ZombieToast(3, 1, dungeon);
        zombie.add();
        // create a mercenary in a cell below boulder2
        NonPlayer mercenary = new Mercenary(1, 3, dungeon);
        mercenary.add();
        // player should not be able to push boulder1 to the right
        player.playerMove(Direction.RIGHT);
        assertEquals(2, boulder1.getX());
        // player should not be able to push boulder2 to the bottom
        player.playerMove(Direction.DOWN);
        assertEquals(2, boulder2.getY());
    }

    @Test
    public void testPotionRemainingTime() {
        // set up the player
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        Mercenary mercenary = new Mercenary(2, 1, dungeon);
        assertEquals(80, mercenary.getHealth());
        mercenary.add();
        // add wall to block mercenary movement
        Wall wall1 = new Wall(2, 0, dungeon);
        wall1.add();
        Wall wall2 = new Wall(3, 1, dungeon);
        wall2.add();
        Wall wall3 = new Wall(2, 2, dungeon);
        wall3.add();
        // apply invisible potion
        InvisibilityPotion potion = new InvisibilityPotion(1, 1, dungeon);
        player.applyItem(potion);
        // mercenary should not move in next 10 ticks
        for (int i = 0; i < 10; i++) {
            player.playerMove(Direction.NONE);
            mercenary.move();
            assertEquals(2, mercenary.getX());
            assertEquals(1, mercenary.getY());
        }
        // the power of potion disappear at 11th tick
        // mercenary move to player
        player.playerMove(Direction.NONE);
        mercenary.move();
        assertEquals(1, mercenary.getX());
        assertEquals(1, mercenary.getY());
        // create a new mercenary at 2,1 and remove first mercenary
        mercenary.remove();
        Mercenary mercenary2 = new Mercenary(2, 1, dungeon);
        mercenary2.add();
        // player apply invinciblity potion
        InvincibilityPotion potion2 = new InvincibilityPotion(1, 1, dungeon);
        player.applyItem(potion2);
        // mercenary2 should run away but blocked by walls in next 10 ticks
        assertEquals(10, player.getRemainingTime());
        for (int i = 0; i < 10; i++) {
            player.playerMove(Direction.NONE);
            mercenary2.move();
            assertEquals(2, mercenary2.getX());
            assertEquals(1, mercenary2.getY());
        }
        // the power of potion disappear at 11th tick
        // mercenary move to player
        player.playerMove(Direction.NONE);
        mercenary2.move();
        assertEquals(1, mercenary2.getX());
        assertEquals(1, mercenary2.getY());
    }

    @Test
    public void playerHealthInHardGameMode() {
        // set up the player
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        // set gamemode
        dungeon.setGameMode("hard");
        Player player = new Player(1, 1, dungeon);
        player.initialisePlayerPosition(1, 1);
        player.add();
        dungeon.setPlayer(player);
        // check player health
        assertEquals(80, player.getHealth());
    }

    @Test
    public void testArmour() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        Armour armour = new Armour(1, 1, dungeon);
        // add armour for player
        dungeon.getInventory().addArmour(armour);
        Spider spider = new Spider(2, 2, dungeon, true);
        spider.add();

        player.playerMove(Direction.RIGHT);
        spider.move();
        dungeon.tickPlayerAfterMoving();
        // armour defence = 0.7
        // player receive damage = (20 / 10) * 0.7 = 1.4
        // player health = 100 - 1.4 = 98.6
        assertTrue(Double.compare(98.6, player.getHealth()) == 0);

    }

    @Test
    public void testShield() {
        // set up player
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        Wood wood1 = new Wood(1, 1, dungeon);
        wood1.add();
        dungeon.tickPlayerAfterMoving();
        Wood wood2 = new Wood(1, 1, dungeon);
        wood2.add();
        dungeon.tickPlayerAfterMoving();
        Treasure treasure = new Treasure(1, 1, dungeon);
        treasure.add();
        dungeon.tickPlayerAfterMoving();
        // build shield
        dungeon.getInventory().buildShield();
        assertTrue(dungeon.getInventory().isHaveShield());
        // create spider
        Spider spider = new Spider(2, 2, dungeon, true);
        spider.add();
        player.playerMove(Direction.RIGHT);
        spider.move();
        dungeon.tickPlayerAfterMoving();
        // shield defence = 0.8
        // player receive damage = (20 / 10) * 0.7 = 1.4
        // player health = 100 - 1.6 = 98.4
        assertTrue(Double.compare(98.4, player.getHealth()) == 0);
    }

    @Test
    public void enemyOnlyAttackPlayerTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        dungeon.setGameMode("standard");
        Player player = new Player(0, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(0, 1);

        Mercenary mercenary = new Mercenary(1, 2, dungeon);
        mercenary.add();
        mercenary.setAlly(true);
        Spider spider = new Spider(2, 1, dungeon, true);
        spider.add();
        double allyHealth = mercenary.getHealth();

        player.playerMove(Direction.RIGHT);
        dungeon.enemiesMove();
        dungeon.tickPlayerAfterMoving();
        assertEquals(allyHealth, mercenary.getHealth());
    }

    @Test
    public void enemyPlayerDieTogetherTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        dungeon.setGameMode("standard");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        Sword sword = new Sword(1, 1, dungeon);
        sword.add();
        dungeon.tickPlayerAfterMoving();

        assertTrue(dungeon.getInventory().isHaveSword());

        player.receiveDamage(99.9);

        Spider spider = new Spider(2, 2, dungeon, true);
        spider.add();
        spider.receiveDamage(18);

        player.playerMove(Direction.RIGHT);
        dungeon.enemiesMove();
        dungeon.tickPlayerAfterMoving();
        assertEquals(0, dungeon.getEnemies().size());
        assertEquals(0, dungeon.getEntities().size());
    }

    @Test
    public void boulderCannotBePushedOntoBombTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        dungeon.setGameMode("standard");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);

        Boulder boulder = new Boulder(2, 1, dungeon);
        boulder.add();

        BombUsed bomb = new BombUsed(3, 1, dungeon, new OrStrategy());
        bomb.add();

        player.playerMove(Direction.RIGHT);
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    public void exchangePositionWithoutBattleTest() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("onlyPlayerSpider", "standard"));
        DungeonResponse res = controller.tick(null, Direction.DOWN);
        List<EntityResponse> entities = res.getEntities();
        assertTrue(entities.stream().filter(n -> n.getType().equals("spider")).collect(Collectors.toList()).size() > 0);
    }

    @Test
    public void invincibleStateNoReductionOfDurabilityTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        dungeon.setGameMode("standard");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        Sword sword = new Sword(1, 1, dungeon);
        sword.add();
        dungeon.tickPlayerAfterMoving();

        for (int i = 0; i < 9; i++) {
            sword.reduceEndurance(dungeon.getInventory());
        }
        
        InvincibilityPotion potion = new InvincibilityPotion(1, 1, dungeon);
        potion.add();
        dungeon.tickPlayerAfterMoving();

        player.applyItem(potion);

        Spider spider = new Spider(1, 2, dungeon, true);
        spider.add();

        player.playerMove(Direction.DOWN);
        dungeon.tickPlayerAfterMoving();

        assertTrue(dungeon.getInventory().isHaveSword());
    }

    @Test
    public void allyAttackWithoutRangeLimitTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        dungeon.setGameMode("standard");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);

        Treasure treasure = new Treasure(1, 1, dungeon);
        treasure.add();
        dungeon.tickPlayerAfterMoving();
        
        Wall wall1 = new Wall(1, 2, dungeon);
        Wall wall2 = new Wall(0, 3, dungeon);
        Wall wall3 = new Wall(2, 3, dungeon);
        Wall wall4 = new Wall(1, 4, dungeon);
        wall1.add();
        wall2.add();
        wall3.add();
        wall4.add();

        Mercenary mercenary = new Mercenary(1, 3, dungeon);
        mercenary.add();

        player.interact(mercenary);

        assertTrue(player.AlliesDamage() > 0);
    }

    @Test
    public void maxSpiderTest() {
        DungeonManiaController controller = new DungeonManiaController();
        assertDoesNotThrow(() -> controller.newGame("onlyPlayerSpider", "standard"));
        controller.tick(null, Direction.UP);

        for (int i = 0; i < 30; i++) {
            controller.tick(null, Direction.NONE);
        }

        DungeonResponse res = controller.tick(null, Direction.NONE);
        List<EntityResponse> entities = res.getEntities();
        assertTrue(entities.stream().filter(n -> n.getType().equals("spider")).collect(Collectors.toList()).size() == 5);
    }


}
