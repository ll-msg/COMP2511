package dungeonmania.StaticEntities;

import java.util.List;

import dungeonmania.ActiveStrategy.ActiveStrategy;

public interface Trigger {
    public boolean getIsOn();
    public void setIsOn(boolean isOn);
    public ActiveStrategy getStrategy();
    public void updateState(List<Trigger> ls);
    public List<Trigger> getAdjTriggers();
    public void triggersAdd(Trigger trigger);
}
