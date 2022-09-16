package dungeonmania;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.CollectableEntities.CollectableEntities;
import dungeonmania.CollectableEntities.ItemUsedStrategy;
import dungeonmania.CollectableEntities.Treasure;
import dungeonmania.Goals.Goal;
import dungeonmania.StaticEntities.BombUsed;
import dungeonmania.StaticEntities.LightBulb;
import dungeonmania.StaticEntities.Portal;
import dungeonmania.StaticEntities.StaticEntities;
import dungeonmania.StaticEntities.Switch;
import dungeonmania.StaticEntities.SwitchDoor;
import dungeonmania.StaticEntities.Wire;
import dungeonmania.StaticEntities.ZombieToastSpawner;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingentity.Assassin;
import dungeonmania.movingentity.Hydra;
import dungeonmania.movingentity.Mercenary;
import dungeonmania.movingentity.NonPlayer;
import dungeonmania.movingentity.Player;
import dungeonmania.movingentity.Spider;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Dungeon {
    private List<Entity> entities = new ArrayList<Entity>();
    private List<CollectableEntities> collectableEntities = new ArrayList<CollectableEntities>();
    private List<StaticEntities> staticEntities = new ArrayList<StaticEntities>();
    private List<NonPlayer> enemies = new ArrayList<NonPlayer>();
    private List<Portal> portals = new ArrayList<Portal>();
    private List<Switch> switches = new ArrayList<Switch>();
    private List<Treasure> treasures = new ArrayList<Treasure>();
    private List<ZombieToastSpawner> spawners = new ArrayList<ZombieToastSpawner>();
    private String id;
    private String dungeonName;
    private Inventory inventory;
    private int currentTick = 1;
    private Position mercenaryPosition = new Position(0, 0);
    private int mercenaryTick = 25;
    private int defaultMercenaryTick = -1;
    private int spiderTick = 5;
    private int zombieTick = 20;
    private int zombieHardTick = 15;
    private int currentSpider = 0;
    private int spiderMax = 5;
    private int hydraTick = 50;
    private Goal goal;
    private Player player;
    private String gameMode = "standard";
    private List<Wire> wires = new ArrayList<Wire>();
    private List<BombUsed> bombs = new ArrayList<BombUsed>();
    private List<SwitchDoor> doors = new ArrayList<SwitchDoor>();
    private List<LightBulb> bulbs = new ArrayList<LightBulb>();
    private Random random = new Random();

    public Dungeon(String id, String dungeonName) {
        this.id = id;
        this.dungeonName = dungeonName;
        this.inventory = new Inventory(this);
        this.player = new Player(1, 1, this);
    }

    public Dungeon(String id, String dungeonName, int seed) {
        this(id, dungeonName);
        random = new Random(seed);
        inventory.setRandom(random);
    }

    public void setMercenarySpawnerPosition(int x, int y) {
        this.mercenaryPosition = new Position(x, y);
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String mode) {
        this.gameMode = mode;
    }

    /**
     * get a list of entities with given x and y
     * 
     * @param x location x
     * @param y location y
     * @return a list of entities with given x and y
     */
    public List<Entity> getEntitiesWithPosition(int x, int y) {
        return entities.stream().filter(entity -> (entity.getX() == x && entity.getY() == y))
                .collect(Collectors.toList());
    }

    /**
     * get a list of static entities with given x and y
     * 
     * @param x location x
     * @param y location y
     * @return a list of static entities with given x and y
     */
    public List<StaticEntities> getStaticEntitiesWithPosition(int x, int y) {
        return staticEntities.stream().filter(entity -> (entity.getX() == x && entity.getY() == y))
                .collect(Collectors.toList());
    }

    /**
     * get a list of collectable entities with given x and y
     * 
     * @param x location x
     * @param y location y
     * @return a list of collectable entities with given x and y
     */
    public List<CollectableEntities> getCollectableEntitiesWithPosition(int x, int y) {
        return collectableEntities.stream().filter(entity -> (entity.getX() == x && entity.getY() == y))
                .collect(Collectors.toList());
    }

    /**
     * get a list of enemies with given x and y
     * 
     * @param x location x
     * @param y location y
     * @return a list of enemies with given x and y
     */
    public List<NonPlayer> getEnemiesWithPosition(int x, int y) {
        return enemies.stream().filter(entity -> (entity.getX() == x && entity.getY() == y))
                .collect(Collectors.toList());
    }

    /**
     * get a switch with given x and y
     * 
     * @param x location x
     * @param y location y
     * @return a switch with given x and y
     */
    public Switch getSwitchByPosition(int x, int y) {
        for (Switch switch1 : switches) {
            if (switch1.getX() == x && switch1.getY() == y) {
                return switch1;
            }
        }

        return null;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<StaticEntities> getStaticEntities() {
        return staticEntities;
    }

    public void setStaticEntities(List<StaticEntities> staticEntities) {
        this.staticEntities = staticEntities;
    }

    public List<CollectableEntities> getCollectableEntities() {
        return collectableEntities;
    }

    public void setCollectableEntities(List<CollectableEntities> collectableEntities) {
        this.collectableEntities = collectableEntities;
    }

    /**
     * get a list of response of current entities in the dungeon
     * 
     * @return a list of response of current entities in the dungeon
     */
    public List<EntityResponse> getEntitiesResponse() {
        return entities.stream()
                .map(n -> new EntityResponse(n.getId(), n.getType(), n.getPosition(), n.isInteractable()))
                .collect(Collectors.toList());
    }

    /**
     * increase current spider number by 1
     */
    public void spiderAdd() {
        this.currentSpider = currentSpider + 1;
    }

    /**
     * decrease current spider number by 1
     */
    public void spiderRemove() {
        this.currentSpider = currentSpider - 1;
    }

    public List<NonPlayer> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<NonPlayer> enemies) {
        this.enemies = enemies;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDungeonName() {
        return dungeonName;
    }

    public void setDungeonName(String dungeonName) {
        this.dungeonName = dungeonName;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Goal getGoals() {
        return goal;
    }

    public void setGoals(Goal goal) {
        this.goal = goal;
    }

    /**
     * get the string format of the goal in the dungeon
     * 
     * @return the string format of the goal in the dungeon
     */
    public String getGoalString() {
        return goal.finalGoal();
    }

    /**
     * add an entity to the entities' list
     * 
     * @param entity the entity to be added
     */
    public void entitiesAdd(Entity entity) {
        this.entities.add(entity);
    }

    /**
     * add an enemy to the enemies' list
     * 
     * @param enemy the enemy to be added
     */
    public void enemiesAdd(NonPlayer enemy) {
        this.enemies.add(enemy);
        this.entities.add(enemy);
    }

    /**
     * add a protal to the portals' list
     * 
     * @param protal the protal to be added
     */
    public void portalAdd(Portal portal) {
        this.portals.add(portal);
    }

    /**
     * add a switch to the switchs' list
     * 
     * @param switch the switch to be added
     */
    public void switchAdd(Switch switche) {
        this.switches.add(switche);
    }

    /**
     * add a treasure to the treasures' list
     * 
     * @param treasure the treasure to be added
     */
    public void treasureAdd(Treasure treasure) {
        this.treasures.add(treasure);
    }

    /**
     * add a zombie toast spawner to the zombie toast spawners' list
     * 
     * @param pawner the zombie toast spawnerto be added
     */
    public void zombieToastSpawnersAdd(ZombieToastSpawner spawner) {
        this.spawners.add(spawner);
    }

    /**
     * remove an entity from entities' list
     * 
     * @param entity the entity to be removed
     */
    public void entitiesRemove(Entity entity) {
        this.entities.remove(entity);
    }

    /**
     * remove an portal from portals' list
     * 
     * @param portal the portal to be removed
     */
    public void portalRemove(Portal portal) {
        this.portals.remove(portal);
    }

    /**
     * remove an switch from switches' list
     * 
     * @param switch the switch to be removed
     */
    public void switchRemove(Switch switche) {
        this.switches.remove(switche);
    }

    /**
     * remove an treasure from treasures' list
     * 
     * @param treasure the treasure to be removed
     */
    public void treasureRemove(Treasure treasure) {
        this.treasures.remove(treasure);
    }

    /**
     * remove a spawner from spawners' list
     * 
     * @param spawner the spawner to be removed
     */
    public void zombieToastSpawnersRemove(ZombieToastSpawner spawner) {
        this.spawners.remove(spawner);
    }

    /**
     * add a collectable entity into collectable entities' list
     * 
     * @param collectableEntity the collectableEntity to be added
     */
    public void collectableEntitiesAdd(CollectableEntities collectableEntity) {
        this.collectableEntities.add(collectableEntity);
        this.entities.add(collectableEntity);
    }

    /**
     * add a static entity into static entities' list
     * 
     * @param staticEntity the staticEntity to be added
     */
    public void staticEntitiesAdd(StaticEntities staticEntity) {
        this.staticEntities.add(staticEntity);
        this.entities.add(staticEntity);
    }

    public List<Portal> getPortals() {
        return portals;
    }

    public void setPortals(List<Portal> portals) {
        this.portals = portals;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * find an entity by given id
     * 
     * @param id given id
     * @return the entity found
     */
    public Entity findEntityById(String id) {
        for (Entity entity : entities) {
            if (entity.getId().equals(id)) {
                return entity;
            }
        }
        return null;
    }

    /**
     * remove the static entity from static entities' list
     * 
     * @param staticEntity the static entity to be removed
     */
    public void staticEntitiesRemove(StaticEntities staticEntity) {
        this.staticEntities.remove(staticEntity);
        this.entities.remove(staticEntity);
    }

    /**
     * remove the collectable entity from collectable entities' list
     * 
     * @param collectableEntity the collectable entity to be removed
     */
    public void collectableEntitesRemove(CollectableEntities collectableEntity) {
        this.collectableEntities.remove(collectableEntity);
        this.entities.remove(collectableEntity);
    }

    /**
     * remove the enemy from static enemies' list
     * 
     * @param enemy the enemy to be removed
     */
    public void enemiesRemove(NonPlayer enemy) {
        this.enemies.remove(enemy);
        this.entities.remove(enemy);
    }

    public List<Switch> getSwitches() {
        return switches;
    }

    public void setSwitches(List<Switch> switches) {
        this.switches = switches;
    }

    public List<Treasure> getTreasures() {
        return treasures;
    }

    public void setTreasures(List<Treasure> treasures) {
        this.treasures = treasures;
    }

    /**
     * spawn enemieies according to their requirment
     */
    public void spawnEnemies() {
        if (defaultMercenaryTick == -1 && enemies.size() != 0) {
            defaultMercenaryTick = currentTick - 1;
        }

        if (currentTick == 0) {
            return;
        }

        if (currentSpider < spiderMax && currentTick % spiderTick == 0) {
            Spider spider = new Spider(0, 0, this, false);
            spider.add();
        }

        spawners.stream().forEach(spawner -> {
            int zombieModeTick = zombieTick;
            if (getGameMode().equals("hard")) {
                zombieModeTick = zombieHardTick;
            }
            if (spawner != null && currentTick % zombieModeTick == 0) {
                spawner.spawnZombie();
            }
        });

        if ((currentTick - defaultMercenaryTick) % mercenaryTick == 0 && enemies.size() != 0 && currentTick - defaultMercenaryTick != 0) {
            if (random.nextInt(100) < 30) {
                Assassin assassin = new Assassin(mercenaryPosition.getX(), mercenaryPosition.getY(), this);
                assassin.add();
            } else {
                Mercenary mercenary = new Mercenary(mercenaryPosition.getX(), mercenaryPosition.getY(), this);
                mercenary.add();
            }
        }

        if (getGameMode().equals("hard") && currentTick % hydraTick == 0) {
            Hydra hydra = new Hydra(mercenaryPosition.getX(), mercenaryPosition.getY(), this);
            hydra.add();
        }
    }

    /**
     * use a item with given id
     * 
     * @param item the givn id
     */
    public void useItem(String item) {
        if (item == null || item.equals("")) {
            return;
        }

        CollectableEntities itemUsed = inventory.findItemById(item);
        if (itemUsed == null) {
            throw new InvalidActionException("Item is not in the inventory.");
        }

        itemUsed = inventory.findItemToUseById(item);
        if (itemUsed == null) {
            throw new IllegalArgumentException("Item is not a valid used item.");
        }

        player.applyItem((ItemUsedStrategy) itemUsed);
        inventory.removeItemToUse(itemUsed);
        inventory.removeItem(itemUsed);
    }

    /**
     * move player to a given direction
     * 
     * @param direction the given direction for player to move
     */
    public void movePlayerWithDirection(Direction direction) {
        player.playerMove(direction);
    }

    /**
     * move all the enemies in the dungeon
     */
    public void enemiesMove() {
        enemies.stream().forEach(enemy -> enemy.move());
    }

    /**
     * tick the player after movement
     */
    public void tickPlayerAfterMoving() {
        player.playerTick();
    }

    /**
     * update the goal of the dungeon
     */
    public void updateGoal() {
        goal.update();
        this.currentTick = currentTick + 1;
    }

    /**
     * make all the portals to check if there's a nonplayer on them, if so, teleport
     * the nonplayer.
     */
    public void teleportNonPlayer() {
        for (NonPlayer enemy : enemies) {
            for (Portal portal : portals) {
                if (enemy.getX() == portal.getX() && enemy.getY() == portal.getY()) {
                    enemy.teleport(portal);
                    break;
                }
            }
        }
    }

    public boolean isHasSunstone() {
        return this.getInventory().isHasSunstone();
    }

    /**
     * check if the dungeon has zombie
     * @return if the dungeon has zombie
     */ 
    public boolean hasZombie() {
        for (NonPlayer enemy : enemies) {
            if (enemy.getType().equals("zombieToast")) {
                return true;
            }
        }
        return false;
    }

    /**
     * get the entity in position p if it is an static entity
     * 
     * @param p
     * @return
     */
    public StaticEntities findStaticEntity(Position p) {
        for (StaticEntities entity : staticEntities) {
            if (entity.getPosition().equals(p)) {
                return entity;
            }
        }
        return null;
    }

    /**
     * find the largest x of all entities
     * 
     * @return
     */
    public int getMaxX() {
        int max = 0;
        for (Entity e : entities) {
            if (max < e.getX()) {
                max = e.getX();
            }
        }
        return max;
    }

    /**
     * find the largest y of all entities
     * 
     * @return
     */
    public int getMaxY() {
        int max = 0;
        for (Entity e : entities) {
            if (max < e.getY()) {
                max = e.getY();
            }
        }
        return max;
    }

    /**
     * find the smallest x of all entities
     * 
     * @return
     */
    public int getMinX() {
        int min = 0;
        for (Entity e : entities) {
            if (min > e.getX()) {
                min = e.getX();
            }
        }
        return min;
    }

    /**
     * find the smallest y of all entities
     * 
     * @return
     */
    public int getMinY() {
        int min = 0;
        for (Entity e : entities) {
            if (min > e.getY()) {
                min = e.getY();
            }
        }
        return min;
    }

    /**
     * add a wire to the dungeon
     * @param wire the wire to be added
     */
    public void wireAdd(Wire wire) {
        this.wires.add(wire);
    }

    /**
     * remove a wire from the dungeon 
     * @param wire the wire to be removed
     */
    public void wireRemove(Wire wire) {
        this.wires.remove(wire);
    }
    /**
     * add a bulb to the dungeon
     * @param bulb the bulb to be added
     */
    public void bulbAdd(LightBulb bulb) {
        this.bulbs.add(bulb);
    }
    /**
     * remove a bulb from the dungeon 
     * @param bulb the bulb to be removed
     */
    public void bulbRemove(LightBulb bulb) {
        this.bulbs.remove(bulb);
    }
    /**
     * add a bomb to the dungeon
     * @param bomb the bomb to be added
     */
    public void bombAdd(BombUsed bomb) {
        this.bombs.add(bomb);
    }
    /**
     * remove a bomb from the dungeon 
     * @param bomb the bomb to be removed
     */
    public void bombRemove(BombUsed bomb) {
        this.bombs.remove(bomb);
    }
    /**
     * add a door to the dungeon
     * @param door the door to be added
     */
    public void doorAdd(SwitchDoor door) {
        this.doors.add(door);
    }
    /**
     * remove a door from the dungeon 
     * @param door the door to be removed
     */
    public void doorRemove(SwitchDoor door) {
        this.doors.remove(door);
    }

    /**
     * get the wire on the given position 
     * @param x x of the position 
     * @param y y of the position 
     * @return the wire if found null if not found
     */
    public Wire getWireByPosition(int x, int y) {
        for (Wire wire : wires) {
            if (wire.getX() == x && wire.getY() == y) {
                return wire;
            }
        }

        return null;
    }
    /**
     * initialize adjacent switches and wires for trigger
     */
    public void initializeTrigger() {
        for (Switch sw : switches) {
            for (Switch sw1 : switches) {
                if (isAdjacent(sw.getPosition(), sw1.getPosition())) {
                    sw.triggersAdd(sw1);
                }
            }

            for (Wire wire : wires) {
                if (isAdjacent(sw.getPosition(), wire.getPosition())) {
                    sw.triggersAdd(wire);
                }
            }
        }

        for (Wire wire : wires) {
            for (Switch sw : switches) {
                if (isAdjacent(wire.getPosition(), sw.getPosition())) {
                    wire.triggersAdd(sw);
                }
            }

            for (Wire wire1 : wires) {
                if (isAdjacent(wire.getPosition(), wire1.getPosition())) {
                    wire.triggersAdd(wire1);
                }
            }
        }
    }
    /**
     * initialize adjacent switches and wires for bomb
     */
    public void initializeBombUsed() {
        for (BombUsed bomb : bombs) {
            for (Switch sw : switches) {
                if (isAdjacent(bomb.getPosition(), sw.getPosition())) {
                    bomb.triggersAdd(sw);
                }
            }

            for (Wire wire1 : wires) {
                if (isAdjacent(bomb.getPosition(), wire1.getPosition())) {
                    bomb.triggersAdd(wire1);
                }
            }
        }
    }
    /**
     * initialize adjacent switches and wires for switch door
     */
    public void initializeSwitchDoor() {
        for (SwitchDoor door : doors) {
            for (Switch sw : switches) {
                if (isAdjacent(door.getPosition(), sw.getPosition())) {
                    door.triggersAdd(sw);
                }
            }

            for (Wire wire1 : wires) {
                if (isAdjacent(door.getPosition(), wire1.getPosition())) {
                    door.triggersAdd(wire1);
                }
            }
        }
    }
    /**
     * initialize adjacent switches and wires for light bulb
     */
    public void initializeLightBulb() {
        for (LightBulb bulb : bulbs) {
            for (Switch sw : switches) {
                if (isAdjacent(bulb.getPosition(), sw.getPosition())) {
                    bulb.triggersAdd(sw);
                }
            }

            for (Wire wire1 : wires) {
                if (isAdjacent(bulb.getPosition(), wire1.getPosition())) {
                    bulb.triggersAdd(wire1);
                }
            }
        }
    }

    /**
     * initialize logic for all logic eneities
     */
    public void initializeLogic() {
        initializeTrigger();
        initializeBombUsed();
        initializeLightBulb();
        initializeSwitchDoor();
    }

    /**
     * update logic state for all the logic eneities
     */
    public void updateLogicState() {
        List<BombUsed> ls = new ArrayList<>();
        for (BombUsed bomb : bombs) {
            ls.add(bomb);
        }

        for (BombUsed bomb : ls) {
            bomb.updatestate();
        }

        for (SwitchDoor door : doors) {
            door.updatestate();
        }

        for (LightBulb bulb : bulbs) {
            bulb.updatestate();
        }
    }

    /**
     * test if two positions are adjacent
     * @param a the position to be checked
     * @param b the position to be checked
     * @return the result of if two positions are adjacent
     */
    private boolean isAdjacent(Position a, Position b) {
        int x = Math.abs(a.getX() - b.getX());
        int y = Math.abs(a.getY() - b.getY());
        return x + y == 1;
    }

    /**
     * update the controling time of allies
     */
    public void updatePlayerAllies() {
        player.checkAlliesControlTime();
    }

    public Random getRandom() {
        return random;
    }

    public int getKeyNum() {
        int keyNum = 0;
        for (CollectableEntities item : collectableEntities) {
            if (item.getType().contains("Key")) {
                keyNum++;
            }
        }
        return keyNum;
    }
}
