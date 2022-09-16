package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.CollectableEntities.Anduril;
import dungeonmania.CollectableEntities.Armour;
import dungeonmania.CollectableEntities.InvincibilityPotion;
import dungeonmania.CollectableEntities.ItemUsedStrategy;
import dungeonmania.CollectableEntities.Sunstone;
import dungeonmania.CollectableEntities.Sword;
import dungeonmania.CollectableEntities.Treasure;
import dungeonmania.movingentity.Hydra;
import dungeonmania.movingentity.Mercenary;
import dungeonmania.movingentity.Player;
import dungeonmania.movingentity.Spider;
import dungeonmania.util.Direction;

public class Milestone3BattleTest {
    @Test
    public void hydraHeadRespawnFailTest() {
        // use random seed 2 - > 8, 72, 40 ...
        Dungeon dungeon = new Dungeon("1", "dungeon.json", 2);
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        // create a hydra with health = 19
        Hydra hydra = new Hydra(1, 1, dungeon);
        hydra.add();
        // set the health of hydra to 19
        hydra.setHealth(19);
        assertEquals(1, dungeon.getEnemies().size());
        // player damage is 100/5 = 20
        // hydra should be killed since damage : 20 > 19 && head spawn rate : 8 < 49
        dungeon.tickPlayerAfterMoving();
        assertEquals(0, dungeon.getEnemies().size());
    }

    @Test
    public void hydraHeadRespawnSuccessTest() {
        // use random seed 1 - > 85, 88, 47 ...
        Dungeon dungeon = new Dungeon("1", "dungeon.json", 1);
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        Hydra hydra = new Hydra(1, 1, dungeon);
        hydra.add();
        // set the health of player to 5
        player.setHealth(5);
        assertEquals(1, dungeon.getEnemies().size());
        // initial player health : 5 ; damage: 5 * 1 / 5 = 1
        // initial hydra health : 60 ; damage: 60 * 1 / 10 = 6
        dungeon.tickPlayerAfterMoving();
        // hydra receive damage = 1, respawn rate : 85 >= 49
        // therefore the player should be killed and the health of hydra = 61
        assertEquals(61, hydra.getHealth());

    }

    @Test
    public void DamageOfNormalSwordTest() {
        // use random seed 2 - > 8, 72, 40 ... hydra can't respawn head at first fight
        Dungeon dungeon = new Dungeon("1", "dungeon.json", 2);
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        // create a hydra with health = 30
        Hydra hydra = new Hydra(2, 1, dungeon);
        hydra.setHealth(30);
        hydra.add();
        // create the sword and let the player pick it up
        Sword sword = new Sword(1, 1, dungeon);
        sword.add();
        dungeon.tickPlayerAfterMoving();
        assertTrue(dungeon.getInventory().isHaveSword());
        // the damage of sword = 10 & the initial damage of player = 20
        // total damage of the player = 30
        // move to hydra
        player.playerMove(Direction.RIGHT);
        assertEquals(1, dungeon.getEnemies().size());
        assertEquals(player.getPosition(), hydra.getPosition());
        dungeon.tickPlayerAfterMoving();
        // the hydra should be defeated since respawn rate = 8 < 49
        // health of hydra = 30 = damage received
        assertEquals(0, dungeon.getEnemies().size());
        assertEquals(97, player.getHealth());
        assertEquals(0, hydra.getHealth());

    }

    @Test
    public void DamageOfAndurilTest() {
        // use random seed 1 - > 85, 88, 47 ... -> the hydra should respawn head at 1st
        // fight
        Dungeon dungeon = new Dungeon("1", "dungeon.json", 1);
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        // create a hydra with health = 50
        Hydra hydra = new Hydra(2, 1, dungeon);
        hydra.setHealth(50);
        hydra.add();
        // create the anduril at player position and let the player pick it up
        Anduril anduril = new Anduril(1, 1, dungeon);
        anduril.add();
        dungeon.tickPlayerAfterMoving();
        assertTrue(dungeon.getInventory().isHaveAnduril());
        // move player to hydra
        player.playerMove(Direction.RIGHT);
        assertEquals(1, dungeon.getEnemies().size());
        // fight
        dungeon.tickPlayerAfterMoving();
        // player damage : 20 + 30 damage to boss = 50
        assertEquals(0, hydra.getHealth());
        assertEquals(0, dungeon.getEnemies().size());
    }

    @Test
    public void useAndurilTestAgainstNormalEnemy() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        // set the health of player to 5
        player.setHealth(5);
        // create the anduril at player position and let the player pick it up
        Anduril anduril = new Anduril(1, 1, dungeon);
        anduril.add();
        dungeon.tickPlayerAfterMoving();
        assertTrue(dungeon.getInventory().isHaveAnduril());
        // create an enemy but not boss
        Mercenary mercenary = new Mercenary(2, 1, dungeon, 1);
        mercenary.add();
        player.playerMove(Direction.RIGHT);
        // fight
        dungeon.tickPlayerAfterMoving();
        // player damage should be = 5/5 + 10 = 11
        // mercenary health = 80 - 11 = 69
        assertEquals(69, mercenary.getHealth());

    }

    @Test
    public void fightHydraWithSuccessAllyDamage() {
        // use random seed 2 - > 8, 72, 40 ... hydra can't respawn head at first fight
        Dungeon dungeon = new Dungeon("1", "dungeon.json", 2);
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        // create the anduril at player position and let the player pick it up
        Anduril anduril = new Anduril(1, 1, dungeon);
        anduril.add();
        // create gold at player position
        Treasure gold = new Treasure(1, 1, dungeon);
        gold.add();
        // pick up gold and anduril
        dungeon.tickPlayerAfterMoving();
        assertTrue(dungeon.getInventory().isHaveAnduril());
        assertEquals(1, dungeon.getInventory().getTreasureNum());
        // create a mercenary
        Mercenary ally = new Mercenary(1, 2, dungeon);
        ally.add();
        ally.interact(player);
        assertEquals(1, player.getNumOfAllies());
        // create a hydra, and set its initialise health to 66
        Hydra hydra = new Hydra(2, 1, dungeon);
        hydra.add();
        hydra.setHealth(66);
        player.playerMove(Direction.RIGHT);
        // fight
        dungeon.tickPlayerAfterMoving();
        // player damage = 30 + 20 = 50
        // ally damage = 80 * 1 / 5 = 16
        // total damage from player side = 50 + 16 = 66
        // spawn rate = 8 < 49
        assertEquals(0, hydra.getHealth());

    }

    @Test
    public void fightHydraWithFailAllyDamage() {
        // use random seed 1 - > 85, 88, 47 ... -> the hydra respawn head at 1st fight
        Dungeon dungeon = new Dungeon("1", "dungeon.json", 1);
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        // set the health of player to 1, so the fight only has 1 round
        player.setHealth(5);
        // create the anduril at player position and let the player pick it up
        Anduril anduril = new Anduril(1, 1, dungeon);
        anduril.add();
        // create gold at player position
        Treasure gold = new Treasure(1, 1, dungeon);
        gold.add();
        // pick up gold and anduril
        dungeon.tickPlayerAfterMoving();
        assertTrue(dungeon.getInventory().isHaveAnduril());
        assertEquals(1, dungeon.getInventory().getTreasureNum());
        // create a mercenary
        Mercenary ally = new Mercenary(1, 2, dungeon);
        ally.add();
        ally.interact(player);
        assertEquals(1, player.getNumOfAllies());
        // create a hydra, and set its initialise health to 100
        Hydra hydra = new Hydra(2, 1, dungeon);
        hydra.add();
        hydra.setHealth(100);
        player.playerMove(Direction.RIGHT);

        // fight
        dungeon.tickPlayerAfterMoving();
        // player damage = 30 + 1 = 31
        // ally damage = 80 * 1 / 5 = 16
        // spawn rate = 85 > 49
        // therefore the total health of hydra = 100 - 31 + 16 = 85
        assertEquals(85, hydra.getHealth());

    }

    @Test
    public void getOneRingFromBattleTest() {
        // use random seed 2 - > 8, 72, 40 ...
        // should get oneRing at the first fight
        Dungeon dungeon = new Dungeon("1", "dungeon.json", 2);
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        Spider spider = new Spider(1, 1, dungeon, true);
        spider.add();
        // fight
        dungeon.tickPlayerAfterMoving();
        assertEquals(0, dungeon.getEnemies().size());
        assertTrue(dungeon.getInventory().checkOneRing());
    }

    @Test
    public void getAndurilFromBattleTest() {
        // use random seed 1 - > [85, 88], [47, 13], [54, 4(get anduril)]
        // should get oneRing at the first fight
        Dungeon dungeon = new Dungeon("1", "dungeon.json", 1);
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        Spider spider = new Spider(1, 1, dungeon, true);
        spider.add();
        // fight
        dungeon.tickPlayerAfterMoving();
        assertEquals(0, dungeon.getEnemies().size());
        assertFalse(dungeon.getInventory().isHaveAnduril());
        Spider spider2 = new Spider(1, 1, dungeon, true);
        spider2.add();
        // fight
        dungeon.tickPlayerAfterMoving();
        assertEquals(0, dungeon.getEnemies().size());
        assertFalse(dungeon.getInventory().isHaveAnduril());
        Spider spider3 = new Spider(1, 1, dungeon, true);
        spider3.add();
        // fight
        dungeon.tickPlayerAfterMoving();
        assertEquals(0, dungeon.getEnemies().size());
        assertTrue(dungeon.getInventory().isHaveAnduril());
    }

    @Test
    public void getOneRingFromBattleWithInvincibilityPotionTest() {
        // use random seed 2 - > 8, 72, 40 ...
        // should get oneRing at the first fight
        Dungeon dungeon = new Dungeon("1", "dungeon.json", 2);
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        Spider spider = new Spider(1, 1, dungeon, true);
        spider.add();
        ItemUsedStrategy invincibilityPotion = new InvincibilityPotion(1, 1, dungeon);
        player.applyItem(invincibilityPotion);
        // fight
        dungeon.tickPlayerAfterMoving();
        assertEquals(0, dungeon.getEnemies().size());
        assertTrue(dungeon.getInventory().checkOneRing());
        assertEquals(100, player.getHealth());
    }

    @Test
    public void getAndurilFromBattleWithInvincibilityPotionTest() {
        // use random seed 1 - > [85, 88], [47, 13], [54, 4(get anduril)]
        // should get oneRing at the first fight
        Dungeon dungeon = new Dungeon("1", "dungeon.json", 1);
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        ItemUsedStrategy invincibilityPotion = new InvincibilityPotion(1, 1, dungeon);
        player.applyItem(invincibilityPotion);
        Spider spider = new Spider(1, 1, dungeon, true);
        spider.add();
        // fight
        dungeon.tickPlayerAfterMoving();
        assertEquals(0, dungeon.getEnemies().size());
        assertFalse(dungeon.getInventory().isHaveAnduril());
        assertEquals(100, player.getHealth());
        Spider spider2 = new Spider(1, 1, dungeon, true);
        spider2.add();
        // fight
        dungeon.tickPlayerAfterMoving();
        assertEquals(0, dungeon.getEnemies().size());
        assertFalse(dungeon.getInventory().isHaveAnduril());
        assertEquals(100, player.getHealth());
        Spider spider3 = new Spider(1, 1, dungeon, true);
        spider3.add();
        // fight
        dungeon.tickPlayerAfterMoving();
        assertEquals(0, dungeon.getEnemies().size());
        assertTrue(dungeon.getInventory().isHaveAnduril());
        assertEquals(100, player.getHealth());
    }

    @Test
    public void damageAndDefenceOfMidNightArmourTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        // create a mercenary with health = 30
        Mercenary mercenary = new Mercenary(2, 1, dungeon, 1);
        mercenary.setHealth(30);
        mercenary.add();
        // create the sunstone, armour and let the player pick it up
        Sunstone sunStone = new Sunstone(1, 1, dungeon);
        sunStone.add();
        dungeon.tickPlayerAfterMoving();
        Armour armour = new Armour(1, 1, dungeon);
        armour.add();
        dungeon.tickPlayerAfterMoving();
        dungeon.getInventory().buildMidnightArmour();
        assertTrue(dungeon.getInventory().isHaveMidnightArmour());
        // the damage of sword = 10 & the initial damage of player = 20
        // total damage of the player = 30
        // move to hydra
        player.playerMove(Direction.RIGHT);
        assertEquals(1, dungeon.getEnemies().size());
        assertEquals(player.getPosition(), mercenary.getPosition());
        dungeon.tickPlayerAfterMoving();
        // health of mercenary = 30 = 20(player damage) + 10 (midnightArmour damage)
        // player received damage = (30 * 1 / 10) * 0.8 = 2.4
        assertEquals(0, dungeon.getEnemies().size());
        assertEquals(0, mercenary.getHealth());
        assertEquals(97.6, player.getHealth());
        // since the mercenary dont wear armour with seed 1
        assertFalse(dungeon.getInventory().isHaveArmour());

    }

    @Test
    public void getArmourFromBattleTest() {
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        player.initialisePlayerPosition(1, 1);
        // create a mercenary with health = 30
        // merecenary wear armour with seed 2
        Mercenary mercenary = new Mercenary(2, 1, dungeon, 2);
        mercenary.setHealth(14);
        mercenary.add();
        player.playerMove(Direction.RIGHT);
        assertEquals(1, dungeon.getEnemies().size());
        assertEquals(player.getPosition(), mercenary.getPosition());
        dungeon.tickPlayerAfterMoving();
        // mecenary receive damage = 20 * 0.7 (armour) = 14 = mercenary health before
        // battle
        // player receive damage = 14 / 10 = 1.4
        assertEquals(0, dungeon.getEnemies().size());
        assertEquals(0, mercenary.getHealth());
        assertEquals(98.6, player.getHealth());
        // the player wins armour from mercenary
        assertTrue(dungeon.getInventory().isHaveArmour());
    }
}
