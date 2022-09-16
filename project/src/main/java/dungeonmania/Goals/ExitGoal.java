package dungeonmania.Goals;

import java.util.List;

import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.movingentity.Player;

public class ExitGoal extends Goal {
    public ExitGoal(Dungeon dungeon) {
        super(dungeon);
        setComposite(false);
    }

    @Override
    public void update() {
        Player player = getDungeon().getPlayer();
        List<Entity> playerAt = getDungeon().getEntitiesWithPosition(player.getX(), player.getY());
        boolean isPlayerOnExit = playerAt.stream().anyMatch(entity -> entity.getType().equals("exit"));
        setAchieved(isPlayerOnExit);
    }

    @Override
    public String toString() {
        return ":exit";
    }
}
