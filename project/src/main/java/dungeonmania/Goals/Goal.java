package dungeonmania.Goals;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;

public abstract class Goal {
    private Dungeon dungeon;
    private boolean achieved;
    private boolean isComposite;

    public Goal(Dungeon dungeon) {
        this.dungeon = dungeon;
        this.achieved = false;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public boolean isAchieved() {
        update();
        return achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }

    public boolean isComposite() {
        return isComposite;
    }

    public void setComposite(boolean isComposite) {
        this.isComposite = isComposite;
    }

    public void addSubgoal(Goal goal) {

    }

    public void deleteSubgoal(Goal goal) {

    }

    public List<Goal> getSubgoals() {
        return new ArrayList<>();
    }
    
    public void update(){}
    public abstract String toString();
    
    public String finalGoal() {
        if (this.achieved) {
            return "";
        } else {
            return this.toString();
        }
    }
}
