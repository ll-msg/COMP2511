package dungeonmania.ActiveStrategy;

import java.util.List;

import dungeonmania.StaticEntities.Trigger;

public class NotStrategy implements ActiveStrategy {

    @Override
    public boolean isActive(List<Trigger> ls) {
        int count = 0;
        for (Trigger trigger : ls) {
            if (trigger.getIsOn()) {
                count++;
            }
        }
        
        if (count == 0) {
            return true;
        }

        return false;
    }
}
