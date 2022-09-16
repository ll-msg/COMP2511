package dungeonmania;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import dungeonmania.ActiveStrategy.ActiveStrategy;
import dungeonmania.ActiveStrategy.AndStrategy;
import dungeonmania.ActiveStrategy.CoandStrategy;
import dungeonmania.ActiveStrategy.NotStrategy;
import dungeonmania.ActiveStrategy.OrStrategy;
import dungeonmania.ActiveStrategy.XorStrategy;
import dungeonmania.CollectableEntities.Anduril;
import dungeonmania.CollectableEntities.Armour;
import dungeonmania.CollectableEntities.Arrows;
import dungeonmania.CollectableEntities.BombCollectable;
import dungeonmania.CollectableEntities.HealthPotion;
import dungeonmania.CollectableEntities.InvincibilityPotion;
import dungeonmania.CollectableEntities.InvisibilityPotion;
import dungeonmania.CollectableEntities.Key;
import dungeonmania.CollectableEntities.Sunstone;
import dungeonmania.CollectableEntities.Sword;
import dungeonmania.CollectableEntities.TheOneRing;
import dungeonmania.CollectableEntities.Treasure;
import dungeonmania.CollectableEntities.Wood;
import dungeonmania.Goals.And;
import dungeonmania.Goals.BouldersGoal;
import dungeonmania.Goals.Or;
import dungeonmania.Goals.TreasureGoal;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Door;
import dungeonmania.StaticEntities.Exit;
import dungeonmania.StaticEntities.Portal;
import dungeonmania.StaticEntities.SwampTile;
import dungeonmania.StaticEntities.Switch;
import dungeonmania.StaticEntities.SwitchDoor;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.StaticEntities.LightBulb;
import dungeonmania.StaticEntities.Wire;
import dungeonmania.StaticEntities.ZombieToastSpawner;
import dungeonmania.movingentity.Assassin;
import dungeonmania.movingentity.Hydra;
import dungeonmania.movingentity.Mercenary;
import dungeonmania.movingentity.Spider;
import dungeonmania.movingentity.ZombieToast;
import dungeonmania.Goals.EnemiesGoal;
import dungeonmania.Goals.ExitGoal;
import dungeonmania.Goals.Goal;
import dungeonmania.util.FileLoader;

public class DungeonLoader {
    private Dungeon dungeon;
    private JSONObject contents;

    /**
     * set up all the entities in dungeon from given filename
     * @param dungeonName given filename
     * @param dungeon target dungeon
     */
    public DungeonLoader(String dungeonName, Dungeon dungeon) {
        JSONObject jsonContents = null;
        try {
            jsonContents = new JSONObject(new JSONTokener(FileLoader.loadResourceFile(dungeonName)));
        } catch (JSONException e) {
            jsonContents = null;
        } catch (IOException e) {
            jsonContents = null;
        }

        this.contents = jsonContents;
        this.dungeon = dungeon;
    }
    /**
     * load a dungeon, set up all the entities and goals with given json file
     * 
     */
    public void loadDungeonJson() {
        JSONArray jsonEntities = contents.getJSONArray("entities");
        for (int i = 0; i < jsonEntities.length(); i++) {
			loadEntity(jsonEntities.getJSONObject(i));
		}

        JSONObject goals = contents.getJSONObject("goal-condition");
        Goal goal = loadGoal(goals, dungeon);
        dungeon.setGoals(goal);
    }

    /**
     * load goal to dungeon with accourding to json files
     * @param json target json file
     * @param dungeon the dungeon which the goal is loaded
     * @return the dungeon's goal
     */
    private Goal loadGoal(JSONObject json, Dungeon dungeon) {
        String goalType = json.getString("goal");
        Goal goal = new ExitGoal(dungeon);

        switch (goalType) {
        case "exit":
            goal = new ExitGoal(dungeon);
            break;
        case "enemies":
            goal = new EnemiesGoal(dungeon);
            break;
        case "boulders":
            goal = new BouldersGoal(dungeon);
            break;
        case "treasure":
            goal = new TreasureGoal(dungeon);
            break;
        case "AND":
            goal = new And(dungeon);
            break;
        case "OR":
            goal = new Or(dungeon);
            break;
        }
    
        if (goal.isComposite()) {
            JSONArray subGoals = json.getJSONArray("subgoals");
            for (int i = 0; i < subGoals.length(); i++) {
                Goal subgoal = loadGoal(subGoals.getJSONObject(i), dungeon);
                goal.addSubgoal(subgoal);
            }
        }
        
        goal.update();
        return goal;
    }
 
    /**
     * load entities to dungeon with accourding to json files
     * @param json target json file
     */
    private void loadEntity(JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");
        int keyDoorID = 0;
        String portalColour = null;
        String logic = "or";
        ActiveStrategy strategy = new OrStrategy();
        int movement_factor = 0;
        if ("door".equals(type) || "key".equals(type)) {
            keyDoorID = json.getInt("key");
        } else if ("portal".equals(type)) {
            portalColour = json.getString("colour");
        } else if ("swamp_tile".equals(type)) {
            movement_factor = json.getInt("movement_factor");
        } else if (type.equals("switch_door") || type.equals("bomb") || type.equals("switch") || type.equals("light_bulb_off")) {
            if (json.has("logic")) {
                logic = json.getString("logic");
            }

            if (logic.equals("and")) {
                strategy = new AndStrategy();
            } else if (logic.equals("xor")) {
                strategy = new XorStrategy();
            } else if (logic.equals("not")) {
                strategy = new NotStrategy();
            } else if (logic.equals("co_and")) {
                strategy = new CoandStrategy();
            } else {
                strategy = new OrStrategy();
            }
        }

        Entity entity = null;
        switch (type) {
        case "player":
            dungeon.getPlayer().initialisePlayerPosition(x, y);
            entity = dungeon.getPlayer();
            dungeon.setMercenarySpawnerPosition(x, y);
            break;
        case "wall":
            Wall wall = new Wall(x, y, dungeon);
            entity = wall;
            break;
        case "exit":
            Exit exit = new Exit(x, y, dungeon);
            entity = exit;
            break;
        case "treasure":
            Treasure treasure = new Treasure(x, y, dungeon);
            entity = treasure;
            break;
        case "door":
            Door door = new Door(x, y, dungeon, keyDoorID);
            entity = door;
            break;
        case "key":
            Key key = new Key(x, y, dungeon, keyDoorID);
            entity = key;
            break;
        case "boulder":
            Boulder boulder = new Boulder(x, y, dungeon);
            entity = boulder;
            break;
        case "portal":
            Portal portal = new Portal(x, y, dungeon, portalColour);
            entity = portal;
            break;
        case "sword":
            Sword sword = new Sword(x, y, dungeon);
            entity = sword;
            break;
        case "armour":
            Armour armour = new Armour(x, y, dungeon);
            entity = armour;
            break;
        case "invincibility_potion":
            InvincibilityPotion invincibilityPotion = new InvincibilityPotion(x, y, dungeon);
            entity = invincibilityPotion;
            break;
        case "invisibility_potion":
            InvisibilityPotion invisibilityPotion = new InvisibilityPotion(x, y, dungeon);
            entity = invisibilityPotion;
            break;
        case "health_potion":
            HealthPotion healthPotion = new HealthPotion(x, y, dungeon);
            entity = healthPotion;
            break;
        case "zombie_toast_spawner":
            ZombieToastSpawner zombieToastSpawner = new ZombieToastSpawner(x, y, dungeon);
            entity = zombieToastSpawner;
            break;
        case "spider":
            Spider spider = new Spider(x, y, dungeon, true);
            entity = spider;
            break;
        case "zombie_toast":
            ZombieToast zombieToast = new ZombieToast(x, y, dungeon);
            entity = zombieToast;
            break;
        case "switch":
            Switch switch1 = new Switch(x, y, dungeon, strategy);
            entity = switch1;
            break;
        case "mercenary":
            Mercenary mercenary = new Mercenary(x, y, dungeon);
            entity = mercenary;
            break;
        case "wood":
            Wood wood = new Wood(x, y, dungeon);
            entity = wood;
            break;
        case "arrow":
            Arrows arrows = new Arrows(x, y, dungeon);
            entity = arrows;
            break;
        case "bomb":
            BombCollectable bomb = new BombCollectable(x, y, dungeon, strategy);
            entity = bomb;
            break;
        case "swamp_tile":
            SwampTile swampTile = new SwampTile(x, y, dungeon, movement_factor);
            entity = swampTile;
            break;
        case "hydra":
            Hydra hydra = new Hydra(x, y, dungeon);
            entity = hydra;
            break;
        case "one_ring":
            TheOneRing ring = new TheOneRing(x, y, dungeon);
            entity = ring;
            break;
        case "anduril":
            Anduril anduril = new Anduril(x, y, dungeon);
            entity = anduril;
            break;
        case "sun_stone":
            Sunstone stone = new Sunstone(x, y, dungeon);
            entity = stone;
            break;
        case "assassin":
            Assassin assassin = new Assassin(x, y, dungeon);
            entity = assassin;
            break;
        case "switch_door":
            SwitchDoor switchdoor = new SwitchDoor(x, y, dungeon, strategy);
            entity = switchdoor;
            break;
        case "light_bulb_off":
            LightBulb bulb = new LightBulb(x, y, dungeon, strategy);
            entity = bulb;
            break;
        case "wire":
            Wire wire = new Wire(x, y, dungeon);
            entity = wire;
            break;
        }

        if (entity != null) {
            entity.add();
        }
    }
}
