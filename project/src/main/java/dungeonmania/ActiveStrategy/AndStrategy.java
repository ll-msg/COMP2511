package dungeonmania.ActiveStrategy;

import java.util.List;

import dungeonmania.StaticEntities.Trigger;

public class AndStrategy implements ActiveStrategy {

    @Override
    public boolean isActive(List<Trigger> ls) {
        for (Trigger trigger : ls) {
            if (!trigger.getIsOn()) {
                return false;
            }
        }
        
        return true;
    }
}
