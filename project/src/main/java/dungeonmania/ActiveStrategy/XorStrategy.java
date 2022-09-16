package dungeonmania.ActiveStrategy;

import java.util.List;

import dungeonmania.StaticEntities.Trigger;

public class XorStrategy implements ActiveStrategy {

    @Override
    public boolean isActive(List<Trigger> ls) {
        int count = 0;
        for (Trigger trigger : ls) {
            if (trigger.getIsOn()) {
                count++;
            }
        }
        
        if (count == 1) {
            return true;
        }

        return false;
    }
}