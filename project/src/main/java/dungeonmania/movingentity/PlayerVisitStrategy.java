package dungeonmania.movingentity;

import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Door;
import dungeonmania.StaticEntities.SwitchDoor;
import dungeonmania.StaticEntities.Wall;

public interface PlayerVisitStrategy {
    /**
     * Check whether the player's position will be blocked by the wall in the
     * current moving state
     * 
     * @param wall that overlaps the player's position
     * @return true if the player won't be blocked by the input wall, otherwise
     *         false
     */
    public boolean visit(Wall wall);

    /**
     * Check whether the player's position will be blocked by the input boulder in
     * the current moving state
     * 
     * @param boulder that overlaps the player's position
     * @return true if the player won't be blocked by the input boulder, otherwise
     *         false
     */
    public boolean visit(Boulder boulder);

    /**
     * Check whether the player's position will be blocked by the input door in the
     * current moving state
     * 
     * @param door that overlaps the player's position
     * @return true if the player won't be blocked by the input door, otherwise
     *         false
     */
    public boolean visit(Door door);

    /**
     * Check whether the player's position will be blocked by the input swithdoor in
     * the current moving state
     * 
     * @param door that overlaps the player's position
     * @return true if the player won't be blocked by the input switchdoor,
     *         otherwise false
     */
    public boolean visit(SwitchDoor switchdoor);
}
