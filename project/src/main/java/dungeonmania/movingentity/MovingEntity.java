package dungeonmania.movingentity;

import java.util.List;

import dungeonmania.DoubleCalculation;
import dungeonmania.Dungeon;
import dungeonmania.Entity;
import dungeonmania.util.Position;
import dungeonmania.StaticEntities.StaticEntities;
import dungeonmania.StaticEntities.SwitchDoor;

public abstract class MovingEntity extends Entity implements MovingEntityVisitor {
    public MovingEntity(int x, int y, Dungeon dungeon) {
        super(x, y, dungeon);
    }

    private double health;
    private int attackDamage;

    public double getHealth() {
        return health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * Check for the death of the moving entity
     * 
     * @return true if the moving entity is dead, otherwise false
     */
    public boolean checkDeath() {
        if (Double.compare(health, 0.0) > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Update the health of the moving entity according to the input damage
     * 
     * @param damage attack on the moving entity
     */
    public void receiveDamage(double damage) {
        setHealth(DoubleCalculation.sub(getHealth(), damage));
    }

    public void setHealth(double health) {
        this.health = health;
    }

    /**
     * Check whether the input position is valid for moving entity to move
     * 
     * @param TargetPosition target next movement position of the moving entity
     * @return true if the position is valid, otherwise false
     */
    protected Boolean checkPositionValid(Position TargetPosition) {
        int targetX = TargetPosition.getX();
        int targetY = TargetPosition.getY();
        List<StaticEntities> entities = getDungeon().getStaticEntitiesWithPosition(targetX, targetY);
        for (StaticEntities entity : entities) {
            if (!entity.accepted(this)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean visit(SwitchDoor switchDoor) {
        return switchDoor.isActive();
    }
}
