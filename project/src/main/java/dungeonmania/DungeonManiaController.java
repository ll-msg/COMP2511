package dungeonmania;

import dungeonmania.Goals.ExitGoal;
import dungeonmania.Goals.Goal;
import dungeonmania.StaticEntities.Exit;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.movingentity.Player;
import dungeonmania.movingentity.NonPlayer;
import dungeonmania.response.models.AnimationQueue;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DungeonManiaController {
    private Map<String, Dungeon> dungeons = new HashMap<>();
    private Dungeon currentDungeon = null;
    List<AnimationQueue> animations = new ArrayList<>();

    public DungeonManiaController() {
    }

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    public List<String> getGameModes() {
        return Arrays.asList("standard", "peaceful", "hard");
    }

    /**
     * /dungeons
     * 
     * Done for you.
     */
    public static List<String> dungeons() {
        try {
            return FileLoader.listFileNamesInResourceDirectory("/dungeons");
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Create a new game based on the given dungeonName and gameMode
     * 
     * @param dungeonName: the name of the dungeon map
     * @param gameMode:    the mode of the game
     * @return DungeonResponse
     * @throws IllegalArgumentException
     */
    public DungeonResponse newGame(String dungeonName, String gameMode) throws IllegalArgumentException {

        if (gameMode.equals("standard") == false && gameMode.equals("peaceful") == false
                && gameMode.equals("hard") == false) {
            throw new IllegalArgumentException("GameMode is invalid.");
        }

        if (dungeons().contains(dungeonName) == false) {
            throw new IllegalArgumentException("DungeonName is invalid.");
        }

        Dungeon newDungeon = new Dungeon(dungeonName + dungeons.size(), dungeonName);
        newDungeon.setGameMode(gameMode);
        DungeonLoader loader = new DungeonLoader("/dungeons/" + dungeonName + ".json", newDungeon);
        loader.loadDungeonJson();
        this.dungeons.put(dungeonName + dungeons.size(), newDungeon);
        newDungeon.initializeLogic();
        newDungeon.updateLogicState();
        currentDungeon = newDungeon;

        animations.add(new AnimationQueue("PostTick", currentDungeon.getPlayer().getId(),
                Arrays.asList("healthbar set 1", "healthbar tint 0x00ff00"), false, -1));
        List<NonPlayer> enemies = currentDungeon.getEnemies();
        for (NonPlayer enemy : enemies) {
            animations.add(new AnimationQueue("PostTick", enemy.getId(),
                    Arrays.asList("healthbar set 1", "healthbar tint 0xff0000"), false, -1));
        }

        return new DungeonResponse(currentDungeon.getId(), currentDungeon.getDungeonName(),
                currentDungeon.getEntitiesResponse(), currentDungeon.getInventory().getItemResponse(),
                currentDungeon.getInventory().getBuildableList(), currentDungeon.getGoalString(), animations);
    }

    /**
     * Saves the current game state with the given name
     * 
     * @param dungeonName: the name of the dungeon map to be saved
     * @return DungeonResponse
     * @throws IllegalArgumentException
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        dungeons.remove(currentDungeon.getId());
        currentDungeon.setId(name);
        this.dungeons.put(name, currentDungeon);
        return new DungeonResponse(currentDungeon.getId(), currentDungeon.getDungeonName(),
                currentDungeon.getEntitiesResponse(), currentDungeon.getInventory().getItemResponse(),
                currentDungeon.getInventory().getBuildableList(), currentDungeon.getGoalString());
    }

    /**
     * Loads the game with the given id from the existing games saved
     * 
     * @param dungeonName: the name of the dungeon map to be loaded
     * @return DungeonResponse
     * @throws IllegalArgumentException
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        if (dungeons.get(name) == null) {
            throw new IllegalArgumentException("Id is invalid.");
        }

        currentDungeon = dungeons.get(name);
        return new DungeonResponse(currentDungeon.getId(), currentDungeon.getDungeonName(),
                currentDungeon.getEntitiesResponse(), currentDungeon.getInventory().getItemResponse(),
                currentDungeon.getInventory().getBuildableList(), currentDungeon.getGoalString());
    }

    /**
     * Returns a list containing all the saved games that are currently stored.
     * 
     * @return List<String>: names of the dungeons
     */
    public List<String> allGames() {
        return new ArrayList<String>(dungeons.keySet());
    }

    /**
     * Ticks the game state. 1. The player moves in the specified direction one
     * square 2. All enemies move respectively 3. Any items which are used are
     * 'actioned' and interact with the relevant entity
     * 
     * @param itemUsed:          the name of the item to be used
     * @param movementDirection: the direction that the player would move
     * @return DungeonResponse
     * @throws IllegalArgumentException, InvalidActionException
     */
    public DungeonResponse tick(String itemUsed, Direction movementDirection)
            throws IllegalArgumentException, InvalidActionException {

        currentDungeon.useItem(itemUsed);
        currentDungeon.movePlayerWithDirection(movementDirection);
        currentDungeon.updateLogicState();
        currentDungeon.enemiesMove();
        currentDungeon.spawnEnemies();
        currentDungeon.tickPlayerAfterMoving();
        currentDungeon.updatePlayerAllies();
        currentDungeon.teleportNonPlayer();
        currentDungeon.updateGoal();

        // create animations
        List<AnimationQueue> animations = new ArrayList<>();

        double healthbar = 0.0;

        // animation for zombie moving
        List<NonPlayer> enemies = currentDungeon.getEnemies();
        for (NonPlayer enemy : enemies) {
            healthbar = enemy.getHealthBar();
            animations.add(new AnimationQueue("PostTick", enemy.getId(),
                    Arrays.asList("healthbar set " + healthbar, "healthbar tint 0xff0000"), false, -1));
        }

        healthbar = DoubleCalculation.div(currentDungeon.getPlayer().getHealth(), 100);
        // animation for healthbar
        animations.add(new AnimationQueue("PostTick", currentDungeon.getPlayer().getId(),
                Arrays.asList("healthbar set " + healthbar, "healthbar tint 0x00ff00"), false, -1));

        return new DungeonResponse(currentDungeon.getId(), currentDungeon.getDungeonName(),
                currentDungeon.getEntitiesResponse(), currentDungeon.getInventory().getItemResponse(),
                currentDungeon.getInventory().getBuildableList(), currentDungeon.getGoalString(), animations);
    }

    /**
     * Interacts with a mercenary (where the character bribes the mercenary) or a
     * zombie spawner, where the character destroys the spawner.
     * 
     * @param entityId: the name of the entity to be interacted
     * @return DungeonResponse
     * @throws IllegalArgumentException, InvalidActionException
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        Entity entity = currentDungeon.findEntityById(entityId);
        if (entity == null) {
            throw new IllegalArgumentException();
        }

        if (entity.isInteractable()) {
            entity.interact(currentDungeon.getPlayer());
        }

        return new DungeonResponse(currentDungeon.getId(), currentDungeon.getDungeonName(),
                currentDungeon.getEntitiesResponse(), currentDungeon.getInventory().getItemResponse(),
                currentDungeon.getInventory().getBuildableList(), currentDungeon.getGoalString());
    }

    /**
     * Builds the given entity, where buildable is one of bow and shield.
     * 
     * @param buildable: the item to be built
     * @return DungeonResponse
     * @throws IllegalArgumentException, InvalidActionException
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        Inventory inventory = currentDungeon.getInventory();
        if (!buildable.equals("bow") && !buildable.equals("shield") && !buildable.equals("sceptre")
                && !buildable.equals("midnight_armour")) {
            throw new IllegalArgumentException();
        }

        if (!inventory.getBuildableList().contains(buildable)) {
            throw new InvalidActionException("the player does not have sufficient items to craft the buildable.");
        }

        if (buildable.equals("bow")) {
            inventory.buildBow();
        }

        if (buildable.equals("shield")) {
            inventory.buildShield();
        }

        if (buildable.equals("sceptre")) {
            inventory.buildSceptre();
        }

        if (buildable.equals("midnight_armour")) {
            inventory.buildMidnightArmour();
        }

        return new DungeonResponse(currentDungeon.getId(), currentDungeon.getDungeonName(),
                currentDungeon.getEntitiesResponse(), currentDungeon.getInventory().getItemResponse(),
                currentDungeon.getInventory().getBuildableList(), currentDungeon.getGoalString());
    }

    /**
     * generate a random dungeon with width 50 and height 50 with an exit goal
     * @param xStart the x of the position of the player
     * @param yStart the y of the position of the player
     * @param xEnd the x of the position of the exit
     * @param yEnd the y of the position of the exit
     * @param gameMode the gamemode of the dungeon
     * @return dungeon response of the generated dungeon
     * @throws IllegalArgumentException
     */
    public DungeonResponse generateDungeon(int xStart, int yStart, int xEnd, int yEnd, String gameMode)
    throws IllegalArgumentException {
        Boolean[][] maze = RandomDungeonGenerator.dungeonGenerator(50, 50, xStart, yStart, xEnd, yEnd);
        Dungeon dungeon = new Dungeon("random"+ dungeons.size(), "random");
        for (int x = 0; x < 50; x++){
            for (int y = 0; y < 50; y++){
                if (maze[x][y] == false) {
                    Wall wall = new Wall(x, y, dungeon);
                    wall.add();
                }
            }
        }
        dungeon.setPlayer(new Player(xStart, yStart, dungeon));
        dungeon.getPlayer().add();
        dungeon.getPlayer().initialisePlayerPosition(xStart, yStart);
        Exit exit = new Exit(xEnd, yEnd, dungeon);
        exit.add();
        Goal goal = new ExitGoal(dungeon);
        dungeon.setGoals(goal);
        dungeon.setMercenarySpawnerPosition(xStart, yStart);
        currentDungeon = dungeon;
        return new DungeonResponse(currentDungeon.getId(), currentDungeon.getDungeonName(),
                currentDungeon.getEntitiesResponse(), currentDungeon.getInventory().getItemResponse(),
                currentDungeon.getInventory().getBuildableList(), currentDungeon.getGoalString());
    }

    public void addAnimations(AnimationQueue animation) {
        animations.add(animation);
    }
}