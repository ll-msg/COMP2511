package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dungeonmania.BuildableEntities.Sceptre;
import dungeonmania.CollectableEntities.InvincibilityPotion;
import dungeonmania.CollectableEntities.ItemUsedStrategy;
import dungeonmania.CollectableEntities.Sunstone;
import dungeonmania.CollectableEntities.TheOneRing;
import dungeonmania.CollectableEntities.Treasure;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingentity.Assassin;
import dungeonmania.movingentity.Mercenary;
import dungeonmania.movingentity.Player;
import dungeonmania.util.Direction;

public class BribeTest {
    @Test
    public void unitBribeAssassinFailTest() {
        // set up
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // create a assassin next to player
        Assassin assassin = new Assassin(1, 2, dungeon);
        assassin.add();
        // crate a treasure at the position of the player
        Treasure gold1 = new Treasure(1, 1, dungeon);
        gold1.add();
        // pick the treasure
        dungeon.tickPlayerAfterMoving();
        Inventory inventory = dungeon.getInventory();
        assertEquals(1, inventory.getTreasureNum());
        // bribe the assassin without enough item
        assertThrows(InvalidActionException.class, () -> assassin.interact(player));
    }

    @Test
    public void unitBribeAssassinSuccessTest1() {
        // set up
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // create a assassin next to player
        Assassin assassin = new Assassin(1, 2, dungeon);
        assassin.add();
        // crate a treasure at the position of the player
        Treasure gold1 = new Treasure(1, 1, dungeon);
        gold1.add();
        // pick the treasure
        dungeon.tickPlayerAfterMoving();
        // create a oneRing at the position of the player
        TheOneRing oneRing = new TheOneRing(1, 1, dungeon);
        oneRing.add();
        // pick up the treasure and oneRing
        dungeon.tickPlayerAfterMoving();
        Inventory inventory = dungeon.getInventory();
        // check num of gold
        assertEquals(1, inventory.getTreasureNum());
        // check the one ring
        assertTrue(inventory.checkOneRing());
        // bribe the assassin with enough item
        assertDoesNotThrow(() -> assassin.interact(player));
        assertEquals(1, player.getNumOfAllies());
        // glod should be used
        assertEquals(0, inventory.getTreasureNum());
    }

    @Test
    public void unitBribeAssassinSuccessSunStoneTest() {
        // set up
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // create a assassin next to player
        Assassin assassin = new Assassin(1, 2, dungeon);
        assassin.add();
        // crate a sunstone at the position of the player
        Sunstone sunStone = new Sunstone(1, 1, dungeon);
        sunStone.add();
        // pick the treasure
        dungeon.tickPlayerAfterMoving();
        // create a oneRing at the position of the player
        TheOneRing oneRing = new TheOneRing(1, 1, dungeon);
        oneRing.add();
        // pick up the treasure and oneRing
        dungeon.tickPlayerAfterMoving();
        Inventory inventory = dungeon.getInventory();
        // check sunstone before bribing
        assertTrue(inventory.isHasSunstone());
        // check the one ring
        assertTrue(inventory.checkOneRing());
        // bribe the assassin with enough item
        assertDoesNotThrow(() -> assassin.interact(player));
        assertEquals(1, player.getNumOfAllies());
        // sun stone should still be keeped after bribing
        assertTrue(inventory.isHasSunstone());

    }

    @Test
    public void unitBribeAssassinSuccessSunStoneAndGoldTest() {
        // set up
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // create a assassin next to player
        Assassin assassin = new Assassin(1, 2, dungeon);
        assassin.add();
        // crate a sunstone at the position of the player
        Sunstone sunStone = new Sunstone(1, 1, dungeon);
        sunStone.add();
        // pick the treasure
        dungeon.tickPlayerAfterMoving();
        // create a oneRing at the position of the player
        TheOneRing oneRing = new TheOneRing(1, 1, dungeon);
        oneRing.add();
        // crate a treasure at the position of the player
        Treasure gold1 = new Treasure(1, 1, dungeon);
        gold1.add();
        // pick up the treasure and oneRing
        dungeon.tickPlayerAfterMoving();
        Inventory inventory = dungeon.getInventory();
        // check num of gold before bribing
        assertEquals(1, inventory.getTreasureNum());
        // check sunstone
        assertTrue(inventory.isHasSunstone());
        // check the one ring
        assertTrue(inventory.checkOneRing());
        // bribe the assassin with enough item
        assertDoesNotThrow(() -> assassin.interact(player));
        assertEquals(1, player.getNumOfAllies());
        // check num of gold after bribing, gold should not be used
        assertEquals(1, inventory.getTreasureNum());
        // sun stone should still be keeped after bribing
        assertTrue(inventory.isHasSunstone());
        ItemUsedStrategy InvincibilityPotion = new InvincibilityPotion(1, 1, dungeon);
        player.applyItem(InvincibilityPotion);
        dungeon.movePlayerWithDirection(Direction.UP);
        dungeon.enemiesMove();
        dungeon.tickPlayerAfterMoving();
        // bribed mercenary cant be interactabled again
        assertFalse(assassin.isInteractable());

        player.checkAlliesControlTime();
        assertEquals(1, player.getX());
        assertEquals(0, player.getY());
        assertEquals(1, assassin.getX());
        assertEquals(1, assassin.getY());
    }

    @Test
    public void unitBribeWithSceptreAssassinTest() {
        // set up
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // create a assassin next to player
        Assassin assassin = new Assassin(1, 2, dungeon, 0);
        assassin.add();
        assertTrue(assassin.getArmour() == null);
        Inventory inventory = dungeon.getInventory();
        Sceptre sceptre = new Sceptre(inventory);
        inventory.getBuildables().add(sceptre);
        assertTrue(inventory.isHaveSceptre());
        // bribe the assassin by using sceptre
        assertDoesNotThrow(() -> assassin.interact(player));
        assertEquals(1, player.getNumOfAllies());
        // assassin should only be bribed by 10 tick
        for (int i = 0; i < 10; i++) {
            dungeon.movePlayerWithDirection(Direction.UP);
            dungeon.enemiesMove();
            dungeon.tickPlayerAfterMoving();
            player.checkAlliesControlTime();
        }
        assertFalse(assassin.isAlly());
        assertTrue(assassin.isInteractable());
        // assassin should battle with the player at 11th tick
        dungeon.movePlayerWithDirection(Direction.NONE);
        dungeon.enemiesMove();
        assertEquals(player.getX(), assassin.getX());
        assertEquals(player.getY(), assassin.getY());
        dungeon.tickPlayerAfterMoving();
        assertFalse(assassin.isAlly());
        assertEquals(0, dungeon.getEnemies().size());
    }

    @Test
    public void unitBribeWithSceptreAssassinTwiceTest() {
        // set up
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // create a assassin next to player
        Assassin assassin = new Assassin(1, 2, dungeon, 0);
        assassin.add();
        assertTrue(assassin.getArmour() == null);
        Inventory inventory = dungeon.getInventory();
        Sceptre sceptre = new Sceptre(inventory);
        inventory.getBuildables().add(sceptre);
        assertTrue(inventory.isHaveSceptre());
        // bribe the assassin by using sceptre
        assertDoesNotThrow(() -> assassin.interact(player));
        assertEquals(1, player.getNumOfAllies());
        // assassin should only be bribed by 10 tick
        for (int i = 0; i < 10; i++) {
            dungeon.movePlayerWithDirection(Direction.UP);
            dungeon.enemiesMove();
            dungeon.tickPlayerAfterMoving();
            player.checkAlliesControlTime();
        }
        // controling time is over
        assertEquals(0, assassin.getControlTime());
        // use sceptre to bribe the assassin again
        assertDoesNotThrow(() -> assassin.interact(player));
        assertEquals(10, assassin.getControlTime());
    }

    @Test
    public void bribeWithSunStoneWithoutTreasure() {
        // set up
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // create a assassin next to player
        Assassin assassin = new Assassin(1, 2, dungeon);
        assassin.add();
        // create a oneRing at the position of the player
        TheOneRing oneRing = new TheOneRing(1, 1, dungeon);
        oneRing.add();
        // pick up the treasure and oneRing
        dungeon.tickPlayerAfterMoving();
        Inventory inventory = dungeon.getInventory();
        // player does not have any gold
        assertEquals(0, inventory.getTreasureNum());
        // player does not have Sunstone
        assertFalse(inventory.isHasSunstone());
        // check the one ring
        assertTrue(inventory.checkOneRing());
        // bribe the assassin without enough item
        assertThrows(InvalidActionException.class, () -> assassin.interact(player));
    }

    @Test
    public void unitBribeWithSceptreMercenaryTest() {
        // set up
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // create a assassin next to player
        Mercenary mercenary = new Mercenary(1, 2, dungeon, 0);
        mercenary.add();
        assertTrue(mercenary.getArmour() == null);
        Inventory inventory = dungeon.getInventory();
        Sceptre sceptre = new Sceptre(inventory);
        inventory.getBuildables().add(sceptre);
        assertTrue(inventory.isHaveSceptre());
        // player doesnot have ally before bribing
        assertEquals(0, player.getNumOfAllies());
        // bribe the assassin by using sceptre
        assertDoesNotThrow(() -> mercenary.interact(player));
        assertEquals(1, player.getNumOfAllies());
        // assassin should only be bribed by 10 tick

        for (int i = 0; i < 10; i++) {
            dungeon.movePlayerWithDirection(Direction.UP);
            dungeon.enemiesMove();
            dungeon.tickPlayerAfterMoving();
            // bribed mercenary cant be interactabled again
            assertFalse(mercenary.isInteractable());
            player.checkAlliesControlTime();

        }
        assertFalse(mercenary.isAlly());
        assertTrue(mercenary.isInteractable());
        // assassin should battle with the player at 11th tick
        dungeon.movePlayerWithDirection(Direction.NONE);
        dungeon.enemiesMove();
        assertEquals(player.getX(), mercenary.getX());
        assertEquals(player.getY(), mercenary.getY());
        dungeon.tickPlayerAfterMoving();
        assertFalse(mercenary.isAlly());
        assertEquals(0, dungeon.getEnemies().size());
    }

    @Test
    public void unitBribeAssassinfailDistanceTest() {
        // set up
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // create a assassin next to player
        Assassin assassin = new Assassin(1, 4, dungeon);
        assassin.add();
        // crate a treasure at the position of the player
        Treasure gold1 = new Treasure(1, 1, dungeon);
        gold1.add();
        // pick the treasure
        dungeon.tickPlayerAfterMoving();
        // create a oneRing at the position of the player
        TheOneRing oneRing = new TheOneRing(1, 1, dungeon);
        oneRing.add();
        // pick up the treasure and oneRing
        dungeon.tickPlayerAfterMoving();
        Inventory inventory = dungeon.getInventory();
        // check num of gold
        assertEquals(1, inventory.getTreasureNum());
        // check the one ring
        assertTrue(inventory.checkOneRing());
        // bribe the assassin with enough item, but assassin is 3 cells far away
        assertThrows(InvalidActionException.class, () -> assassin.interact(player));
    }

    @Test
    public void unitBribeMercenarySuccessSunStoneTest() {
        // set up
        Dungeon dungeon = new Dungeon("1", "dungeon.json");
        Player player = new Player(1, 1, dungeon);
        player.add();
        dungeon.setPlayer(player);
        // create a assassin next to player
        Mercenary mercenary = new Mercenary(1, 2, dungeon);
        mercenary.add();
        // crate a sunstone at the position of the player
        Sunstone sunStone = new Sunstone(1, 1, dungeon);
        sunStone.add();
        dungeon.tickPlayerAfterMoving();
        // pick up the treasure and oneRing
        dungeon.tickPlayerAfterMoving();
        Inventory inventory = dungeon.getInventory();
        // check sunstone before bribing
        assertTrue(inventory.isHasSunstone());
        // bribe the assassin with enough item
        assertDoesNotThrow(() -> mercenary.interact(player));
        assertEquals(1, player.getNumOfAllies());
        // sun stone should still be keeped after bribing
        assertTrue(inventory.isHasSunstone());
    }

}
