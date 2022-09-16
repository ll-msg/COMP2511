package dungeonmania.ActiveStrategy;

import java.util.List;

import dungeonmania.StaticEntities.Trigger;

public interface ActiveStrategy {
    public boolean isActive(List<Trigger> ls);
}
