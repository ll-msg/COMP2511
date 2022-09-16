package dungeonmania.Goals;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;

public class Or extends Goal{
    private List<Goal> subgoals = new ArrayList<>();

    public Or(Dungeon dungeon) {
        super(dungeon);
        setComposite(true);
    }

    @Override
    public void deleteSubgoal(Goal goal) {
        subgoals.remove(goal);
    }

    @Override
    public void addSubgoal(Goal goal) {
        subgoals.add(goal);
    }

    @Override
    public List<Goal> getSubgoals() {
        return subgoals;
    }

    @Override
    public void update() {
        subgoals.stream().forEach(Goal::update);
        setAchieved(subgoals.stream().anyMatch(Goal::isAchieved));
    }

    @Override
    public String toString() {
        return subgoals.get(0).toString() + "/" + subgoals.get(1).toString();
    }
}
