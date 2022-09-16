package dungeonmania.Goals;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Dungeon;

public class And extends Goal{
    private List<Goal> subgoals = new ArrayList<>();

    public And(Dungeon dungeon) {
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
        setAchieved(subgoals.stream().allMatch(Goal::isAchieved));
    }

    @Override
    public String toString() {
        Goal goal1 = subgoals.get(0);
        Goal goal2 = subgoals.get(1);
        if (goal1.isAchieved()) {
            return goal2.finalGoal();
        } else if (goal2.isAchieved()) {
            return goal1.finalGoal();
        } else {
            return goal1.finalGoal() + " AND " + goal2.finalGoal();
        }
    }
} 
