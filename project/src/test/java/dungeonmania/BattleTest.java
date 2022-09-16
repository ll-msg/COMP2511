package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Armour;
import dungeonmania.CollectableEntities.Arrows;
import dungeonmania.CollectableEntities.InvincibilityPotion;
import dungeonmania.CollectableEntities.InvisibilityPotion;
import dungeonmania.CollectableEntities.Sword;
import dungeonmania.CollectableEntities.Treasure;
import dungeonmania.CollectableEntities.Wood;
import dungeonmania.movingentity.Mercenary;
import dungeonmania.movingentity.Player;
import dungeonmania.movingentity.Spider;
import dungeonmania.util.Direction;

public class BattleTest {
    @Test
    public void basicBattleTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        Spider spider = new Spider(2, 2, dungeon, true);
        spider.add();

        player.playerMove(Direction.RIGHT);
        dungeon.enemiesMove();
        assertEquals(2, spider.getX());
        assertEquals(1, spider.getY());
        dungeon.tickPlayerAfterMoving();
        assertEquals(0, dungeon.getEnemies().size());
    }

    @Test
    public void basicPlayerDieTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        player.receiveDamage(99);
        dungeon.setPlayer(player);
        Spider spider = new Spider(2, 2, dungeon, true);
        spider.add();

        player.playerMove(Direction.RIGHT);
        dungeon.enemiesMove();
        dungeon.tickPlayerAfterMoving();
        assertEquals(1, dungeon.getEnemies().size());
        assertEquals(1, dungeon.getEntities().size());
    }

    @Test
    public void invincibleBattleTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        dungeon.setGameMode("standard");
        Player player = new Player(0, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(0, 1);
        player.applyItem(new InvincibilityPotion(0, 1, dungeon));
        player.receiveDamage(99);

        Mercenary mercenary1 = new Mercenary(1, 1, dungeon);
        mercenary1.add();

        player.playerMove(Direction.RIGHT);

        dungeon.tickPlayerAfterMoving();
        assertEquals(0, dungeon.getEnemies().size());

        Mercenary mercenary2 = new Mercenary(1, 2, dungeon);
        mercenary2.add();
        mercenary2.setAlly(true);

        player.playerMove(Direction.DOWN);

        dungeon.tickPlayerAfterMoving();
        assertEquals(1, dungeon.getEnemies().size());
    }

    @Test
    public void peacefulBattleTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        dungeon.setGameMode("peaceful");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        Spider spider = new Spider(2, 2, dungeon, true);
        spider.add();

        player.playerMove(Direction.RIGHT);
        dungeon.enemiesMove();
        assertEquals(2, spider.getX());
        assertEquals(1, spider.getY());
        dungeon.tickPlayerAfterMoving();
        assertEquals(1, dungeon.getEnemies().size());
    }

    @Test
    public void invisiblePotionBattleTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        dungeon.setGameMode("standard");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        InvisibilityPotion potion = new InvisibilityPotion(1, 1, dungeon);
        potion.add();
        dungeon.tickPlayerAfterMoving();

        dungeon.useItem(potion.getId());

        Spider spider = new Spider(2, 2, dungeon, true);
        spider.add();

        player.playerMove(Direction.RIGHT);
        dungeon.enemiesMove();
        assertEquals(2, spider.getX());
        assertEquals(1, spider.getY());
        dungeon.tickPlayerAfterMoving();
        assertEquals(1, dungeon.getEnemies().size());
    }

    @Test
    public void invinciblePotionBattleTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        dungeon.setGameMode("standard");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        InvincibilityPotion potion = new InvincibilityPotion(1, 1, dungeon);
        potion.add();
        dungeon.tickPlayerAfterMoving();

        dungeon.useItem(potion.getId());

        Spider spider = new Spider(2, 2, dungeon, true);
        spider.add();

        player.playerMove(Direction.RIGHT);
        dungeon.enemiesMove();
        assertNotEquals(2, spider.getX());
        assertNotEquals(1, spider.getY());
        dungeon.tickPlayerAfterMoving();
        assertEquals(1, dungeon.getEnemies().size());
    }

    @Test
    public void normalBattleWithAllyTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        dungeon.setGameMode("standard");
        Player player = new Player(0, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);

        Mercenary mercenary = new Mercenary(1, 2, dungeon);
        mercenary.add();
        mercenary.setAlly(true);

        player.playerMove(Direction.DOWN);

        dungeon.tickPlayerAfterMoving();
        assertEquals(1, dungeon.getEnemies().size());
    }

    @Test
    public void detectBattleTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        dungeon.setGameMode("standard");
        Player player = new Player(0, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        Mercenary mercenary = new Mercenary(1, 1, dungeon);
        mercenary.add();
        Mercenary mercenary1 = new Mercenary(1, 2, dungeon);
        mercenary1.add();
        Spider spider = new Spider(2, 1, dungeon, true);
        spider.add();

        player.playerMove(Direction.RIGHT);
        dungeon.tickPlayerAfterMoving();
        assertEquals(2, dungeon.getEnemies().size());

        player.playerMove(Direction.RIGHT);
        dungeon.enemiesMove();
        dungeon.tickPlayerAfterMoving();
        assertEquals(1, dungeon.getEnemies().size());
    }

    @Test
    public void battleWithWeaponTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        dungeon.setGameMode("standard");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        Sword sword = new Sword(1, 1, dungeon);
        sword.add();
        dungeon.tickPlayerAfterMoving();

        Armour armour = new Armour(1, 1, dungeon);
        armour.add();
        dungeon.tickPlayerAfterMoving();

        Wood wood1 = new Wood(1, 1, dungeon);
        wood1.add();
        dungeon.tickPlayerAfterMoving();

        Wood wood2 = new Wood(1, 1, dungeon);
        wood2.add();
        dungeon.tickPlayerAfterMoving();

        Wood wood3 = new Wood(1, 1, dungeon);
        wood3.add();
        dungeon.tickPlayerAfterMoving();

        Arrows arrows1 = new Arrows(1, 1, dungeon);
        arrows1.add();
        dungeon.tickPlayerAfterMoving();

        Arrows arrows2 = new Arrows(1, 1, dungeon);
        arrows2.add();
        dungeon.tickPlayerAfterMoving();

        Arrows arrows3 = new Arrows(1, 1, dungeon);
        arrows3.add();
        dungeon.tickPlayerAfterMoving();

        Treasure treasure = new Treasure(1, 1, dungeon);
        treasure.add();
        dungeon.tickPlayerAfterMoving();

        dungeon.getInventory().buildBow();
        dungeon.getInventory().buildShield();

        Mercenary mercenary = new Mercenary(1, 2, dungeon);
        mercenary.add();

        player.playerMove(Direction.DOWN);

        dungeon.tickPlayerAfterMoving();
        assertEquals(0, dungeon.getEnemies().size());
    }

    @Test
    public void playerTheOneRingTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        player.receiveDamage(99);
        dungeon.setPlayer(player);
        dungeon.getInventory().addNewRing();
        Spider spider = new Spider(2, 2, dungeon, true);
        spider.add();

        player.playerMove(Direction.RIGHT);
        dungeon.enemiesMove();
        dungeon.tickPlayerAfterMoving();
        assertEquals(0, dungeon.getEnemies().size());
        assertEquals(1, dungeon.getEntities().size());
    }

    @Test
    public void durabilityTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        dungeon.setGameMode("standard");
        Player player = new Player(0, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(0, 1);
        Sword sword = new Sword(1, 1, dungeon);
        sword.add();
        player.playerMove(Direction.RIGHT);
        dungeon.tickPlayerAfterMoving();

        Armour armour = new Armour(1, 1, dungeon);
        armour.add();
        dungeon.tickPlayerAfterMoving();

        assertTrue(dungeon.getInventory().isHaveArmour());

        Wood wood1 = new Wood(1, 1, dungeon);
        wood1.add();
        dungeon.tickPlayerAfterMoving();

        Wood wood2 = new Wood(1, 1, dungeon);
        wood2.add();
        dungeon.tickPlayerAfterMoving();

        Wood wood3 = new Wood(1, 1, dungeon);
        wood3.add();
        dungeon.tickPlayerAfterMoving();

        Arrows arrows1 = new Arrows(1, 1, dungeon);
        arrows1.add();
        dungeon.tickPlayerAfterMoving();

        Arrows arrows2 = new Arrows(1, 1, dungeon);
        arrows2.add();
        dungeon.tickPlayerAfterMoving();

        Arrows arrows3 = new Arrows(1, 1, dungeon);
        arrows3.add();
        dungeon.tickPlayerAfterMoving();

        Treasure treasure = new Treasure(1, 1, dungeon);
        treasure.add();
        dungeon.tickPlayerAfterMoving();

        dungeon.getInventory().buildBow();
        dungeon.getInventory().buildShield();

        Spider spider = new Spider(1, 1, dungeon, true);
        spider.add();

        Spider spider1 = new Spider(1, 1, dungeon, true);
        spider1.add();

        Spider spider2 = new Spider(1, 1, dungeon, true);
        spider2.add();

        Spider spider3 = new Spider(1, 1, dungeon, true);
        spider3.add();

        Spider spider4 = new Spider(1, 1, dungeon, true);
        spider4.add();

        Spider spider5 = new Spider(1, 1, dungeon, true);
        spider5.add();

        Spider spider6 = new Spider(1, 1, dungeon, true);
        spider6.add();

        Spider spider7 = new Spider(1, 1, dungeon, true);
        spider7.add();

        Spider spider8 = new Spider(1, 1, dungeon, true);
        spider8.add();

        Spider spider9 = new Spider(1, 1, dungeon, true);
        spider9.add();

        player.playerMove(Direction.RIGHT);
        player.playerMove(Direction.LEFT);
        dungeon.tickPlayerAfterMoving();

        assertFalse(dungeon.getInventory().isHaveArmour());
        assertFalse(dungeon.getInventory().isHaveShield());
        assertFalse(dungeon.getInventory().isHaveBow());

        assertEquals(0, dungeon.getEnemies().size());
    }

    @Test
    public void swordAndArmourDurabilityTest() {
        // set up
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        dungeon.setGameMode("standard");
        Player player = new Player(0, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(0, 1);
        Inventory inventory = dungeon.getInventory();
        // set the durabilitty of swoard and armour to 1
        Sword sword = new Sword(1, 1, dungeon);
        sword.add();
        sword.setDurability(1);
        Armour armour = new Armour(1, 1, dungeon);
        armour.add();
        armour.setDurability(1);
        // pick up the soward and armour
        player.playerMove(Direction.RIGHT);
        dungeon.tickPlayerAfterMoving();
        Spider spider = new Spider(2, 1, dungeon, true);
        spider.add();
        assertTrue(inventory.isHaveSword());
        assertTrue(inventory.isHaveArmour());
        // fight with the spider
        player.playerMove(Direction.RIGHT);
        player.playerTick();
        assertFalse(inventory.isHaveSword());
        assertFalse(inventory.isHaveArmour());

    }
}
