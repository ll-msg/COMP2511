package dungeonmania.ActiveStrategy;

import java.util.List;

import dungeonmania.StaticEntities.Trigger;

public class OrStrategy implements ActiveStrategy {

    @Override
    public boolean isActive(List<Trigger> ls) {
        for (Trigger trigger : ls) {
            if (trigger.getIsOn()) {
                return true;
            }
        }
        
        return false;
    }
}
