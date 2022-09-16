package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.CollectableEntities.Anduril;
import dungeonmania.CollectableEntities.Armour;
import dungeonmania.CollectableEntities.CollectableEntities;
import dungeonmania.BuildableEntities.Bow;
import dungeonmania.BuildableEntities.BuildableEntities;
import dungeonmania.BuildableEntities.MidnightArmour;
import dungeonmania.BuildableEntities.Sceptre;
import dungeonmania.BuildableEntities.Shield;
import dungeonmania.CollectableEntities.Key;
import dungeonmania.CollectableEntities.Sword;
import dungeonmania.CollectableEntities.TheOneRing;
import dungeonmania.response.models.ItemResponse;

public class Inventory {
    private List<CollectableEntities> items = new ArrayList<>();
    private List<BuildableEntities> buildables = new ArrayList<>();
    private List<Key> keys = new ArrayList<>();
    private List<CollectableEntities> itemToUse = new ArrayList<>();
    private List<TheOneRing> rings = new ArrayList<>();
    private Dungeon dungeon;
    private List<Sword> swords = new ArrayList<>();
    private List<Armour> armours = new ArrayList<>();
    private List<Shield> shields = new ArrayList<>();
    private List<Bow> bows = new ArrayList<>();
    private List<Anduril> andurils = new ArrayList<>();
    private Random random;

    public Inventory(Dungeon dungeon) {
        this.dungeon = dungeon;
        random = dungeon.getRandom();
    }

    public List<CollectableEntities> getItems() {
        return items;
    }

    public CollectableEntities findItemById(String id) {
        if (items.stream().filter(n -> (n.getId().equals(id))).collect(Collectors.toList()).size() > 0) {
            return items.stream().filter(n -> (n.getId().equals(id))).collect(Collectors.toList()).get(0);
        } else if (rings.stream().filter(n -> (n.getId().equals(id))).collect(Collectors.toList()).size() > 0) {
            return rings.stream().filter(n -> (n.getId().equals(id))).collect(Collectors.toList()).get(0);
        } else {
            return null;
        }
    }

    public CollectableEntities findItemToUseById(String id) {
        List<CollectableEntities> ls = itemToUse.stream().filter(n -> (n.getId().equals(id)))
                .collect(Collectors.toList());
        if (ls.size() == 0) {
            return null;
        } else {
            return ls.get(0);
        }
    }

    public boolean isHaveKey() {
        if (keys.size() > 0) {
            return true;
        }
        return false;
    }

    public void setItems(List<CollectableEntities> items) {
        this.items = items;
    }

    /**
     * add an item to item list in inventory
     * 
     * @param item the item to be added
     */
    public void addItem(CollectableEntities item) {
        items.add(item);
    }

    /**
     * remove an item from item list in inventory
     * 
     * @param item the item to be removed
     */
    public void removeItem(CollectableEntities item) {
        this.items.remove(item);
    }

    /**
     * add an item to item to use list in inventory
     * 
     * @param item the item to be added
     */
    public void addItemToUse(CollectableEntities item) {
        this.itemToUse.add(item);
    }

    /**
     * remove an item from item to use list in inventory
     * 
     * @param item the item to be removed
     */
    public void removeItemToUse(CollectableEntities item) {
        this.itemToUse.remove(item);
    }

    public List<BuildableEntities> getBuildables() {
        return buildables;
    }

    /**
     * remove an item from buildable list in inventory
     * 
     * @param item the item to be removed
     */
    public void removeBuildables(BuildableEntities entity) {
        this.buildables.remove(entity);
    }

    public void setBuildables(List<BuildableEntities> buildables) {
        this.buildables = buildables;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    /**
     * get a list of item response of the items in the inventory
     * 
     * @return a list of item response
     */
    public List<ItemResponse> getItemResponse() {
        List<ItemResponse> itemResponses = new ArrayList<>();
        for (CollectableEntities item : items) {
            itemResponses.add(new ItemResponse(item.getId(), item.getType()));
        }
        for (BuildableEntities buildable : buildables) {
            itemResponses.add(new ItemResponse(buildable.getId(), buildable.getType()));
        }
        for (TheOneRing ring : rings) {
            itemResponses.add(new ItemResponse(ring.getId(), ring.getType()));
        }
        return itemResponses;
    }

    /**
     * get a list of strings contains the names of buildable items with existing
     * items in the inventory
     * 
     * @return a list of string contains the names of buildable items
     */
    public List<String> getBuildableList() {
        List<String> currentBuildable = new ArrayList<>();
        if (getWoodNum() >= 1 && getArrowNum() >= 3) {
            if (!currentBuildable.contains("bow")) {
                currentBuildable.add("bow");
            }
        }

        if (getWoodNum() >= 2 && (getTreasureNum() >= 1 || getKeyNum() >= 1)) {
            if (!currentBuildable.contains("shield")) {
                currentBuildable.add("shield");
            }
        }

        if ((getWoodNum() >= 1 || getArrowNum() >= 2) && (getKeyNum() >= 1 || getTreasureNum() >= 1)
                && this.isHasSunstone()) {
            if (!currentBuildable.contains("sceptre")) {
                currentBuildable.add("sceptre");
            }
        }

        if (getArmourNum() >= 1 && this.isHasSunstone() && !this.getDungeon().hasZombie()) {
            if (!currentBuildable.contains("midnight_armour")) {
                currentBuildable.add("midnight_armour");
            }
        }
        return currentBuildable;
    }

    /**
     * build a bow, delete the materials and add a new bow item
     */
    public void buildBow() {
        if (this.getBuildableList().contains("bow")) {
            Bow bow = new Bow(this);
            buildables.add(bow);
            bows.add(bow);
            bow.build();
        }
    }

    /**
     * build a shield, delete the materials and add a new shield item
     */
    public void buildShield() {
        if (this.getBuildableList().contains("shield")) {
            Shield shield = new Shield(this);
            buildables.add(shield);
            shields.add(shield);
            shield.build();
        }
    }

    public void buildSceptre() {
        if (this.getBuildableList().contains("sceptre")) {
            Sceptre sceptre = new Sceptre(this);
            buildables.add(sceptre);
            sceptre.build();
        }
    }

    public void buildMidnightArmour() {
        if (this.getBuildableList().contains("midnight_armour")) {
            MidnightArmour midnightArmour = new MidnightArmour(this);
            buildables.add(midnightArmour);
            midnightArmour.build();
        }
    }

    /**
     * get the number of woods in the inventory
     */
    public int getWoodNum() {
        int woodNum = 0;
        for (CollectableEntities item : items) {
            if (item.getType().equals("wood")) {
                woodNum++;
            }
        }
        return woodNum;
    }

    /**
     * get the number of arrows in the inventory
     */
    public int getArrowNum() {
        int arrowNum = 0;
        for (CollectableEntities item : items) {
            if (item.getType().equals("arrows")) {
                arrowNum++;
            }
        }
        return arrowNum;
    }

    /**
     * get the number of treasures in the inventory
     */
    public int getTreasureNum() {
        int treasureNum = 0;
        for (CollectableEntities item : items) {
            if (item.getType().equals("treasure")) {
                treasureNum++;
            }
        }
        return treasureNum;
    }

    /**
     * get the number of keys in the inventory
     */
    public int getKeyNum() {
        int keyNum = 0;
        for (CollectableEntities item : items) {
            if (item.getType().contains("Key")) {
                keyNum++;
            }
        }
        return keyNum;
    }

    /**
     * check if the inventory has a sword inside
     * 
     * @return the resulf of if the inventory has a sword inside
     */
    public boolean isHaveSword() {
        if (swords.size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * use the sword (reduce durability)
     */
    public void useSword() {
        swords.get(0).reduceEndurance(this);
    }

    /**
     * check if the inventory has a armour inside
     * 
     * @return the resulf of if the inventory has a armour inside
     */
    public boolean isHaveArmour() {
        if (armours.size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * use the armour (reduce durability)
     */
    public void useArmour() {
        armours.get(0).reduceEndurance(this);
    }

    /**
     * check if the inventory has a bow inside
     * 
     * @return the resulf of if the inventory has a bow inside
     */
    public boolean isHaveBow() {
        if (bows.size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * use the bow (reduce durability)
     */
    public void useBow() {
        bows.get(0).reduceEndurance(this);
    }

    /**
     * check if the inventory has a shield inside
     * 
     * @return the resulf of if the inventory has a shield inside
     */
    public boolean isHaveShield() {
        if (shields.size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * use the shield (reduce durability)
     */
    public void useShield() {
        shields.get(0).reduceEndurance(this);
    }

    /**
     * check if the inventory has a anduril inside
     * 
     * @return the resulf of if the inventory has a sword inside
     */
    public boolean isHaveAnduril() {
        if (andurils.size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * use the anduril (reduce durability)
     */
    public void useAnduril() {
        andurils.get(0).reduceEndurance(this);
    }

    public List<Key> getKeys() {
        return keys;
    }

    /**
     * delete a coin in the inventory
     */
    public void deleteCoin() {
        if (this.getTreasureNum() > 0) {
            for (CollectableEntities item : items) {
                if (item.getType().equals("treasure")) {
                    items.remove(item);
                    break;
                }
            }
        }
    }

    /**
     * add the one ring into the inventory
     */
    public void addRing(TheOneRing ring) {
        this.rings.add(ring);
    }

    /**
     * add the one ring into the inventory
     */
    public void addNewRing() {
        TheOneRing ring = new TheOneRing(0, 0, dungeon);
        this.rings.add(ring);
    }

    /**
     * remove the one ring from the inventory
     */
    public void removeRing(TheOneRing ring) {
        this.rings.remove(ring);
    }

    /**
     * check if there is a the one ring in the inventory
     * 
     * @return the result of if there is a the one ring in the inventory
     */
    public boolean checkOneRing() {
        if (rings.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * use the shield (reduce durability)
     */
    public void useOneRing() {
        removeRing(rings.get(0));
    }

    /**
     * add a sword into the inventory
     */
    public void addSword(Sword sword) {
        this.swords.add(sword);
    }

    /**
     * add an armour into the inventory
     */
    public void addArmour(Armour armour) {
        this.armours.add(armour);
    }

    /**
     * remove a sword from the inventory
     */
    public void removeSword(Sword sword) {
        this.swords.remove(sword);
    }

    /**
     * remove a armour from the inventory
     */
    public void removeArmour(Armour armour) {
        this.armours.remove(armour);
    }

    /**
     * remove a bow from the inventory
     */
    public void removeBow(Bow bow) {
        this.bows.remove(bow);
    }

    /**
     * remove ashield from the inventory
     */
    public void removeShield(Shield shield) {
        this.shields.remove(shield);
    }

    /**
     * remove the sunstone
     */
    public void removeSunstone() {
        List<CollectableEntities> copyItems = new ArrayList<>();
        copyItems.addAll(items);
        boolean removed = false;
        for (CollectableEntities item : copyItems) {
            if (item.getType().equals("sunstone") && !removed) {
                this.getItems().remove(item);
                removed = true;
            }
        }
    }

    /**
     * remove a key from inventory
     * 
     * @param key the key to be removed
     */
    public void removeKey(Key key) {
        this.getKeys().remove(key);
        this.getItems().remove(key);
    }

    public boolean isHasSunstone() {
        for (CollectableEntities item : items) {
            if (item.getType().equals("sunstone")) {
                return true;
            }
        }
        return false;
    }

    /**
     * get the number of armour in the inventory
     * 
     * @return the number of armours in the inventory
     */
    public int getArmourNum() {
        return armours.size();
    }

    /**
     * add an anduril into the inventory
     */
    public void addAnduril(Anduril anduril) {
        this.andurils.add(anduril);
    }

    /**
     * add a new anduril into the inventory
     */
    public void addNewAnduril() {
        Anduril anduril = new Anduril(0, 0, dungeon);
        this.andurils.add(anduril);
    }

    /**
     * remove the given anduril from the inventory
     */
    public void removeAnduril(Anduril anduril) {
        this.andurils.remove(anduril);
    }

    public boolean isHaveMidnightArmour() {
        return buildables.stream().anyMatch(n -> n.getType().equals("midnight_armour"));
    }

    /**
     * check if there's a sceptre in the inventory
     * 
     * @return if there's a sceptre in the inventory
     */
    public boolean isHaveSceptre() {
        return buildables.stream().anyMatch(n -> n.getType().equals("sceptre"));
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

}
