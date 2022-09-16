package dungeonmania.StaticEntities;

import dungeonmania.Dungeon;
import dungeonmania.util.Position;

public class Portal extends StaticEntities {
    public static int count = 0;
    String colour;

    public Portal(int x, int y, Dungeon dungeon, String colour) {
        super(x, y, dungeon);
        super.setId("portal" + count);
        super.setType("portal");
        super.setInteractable(false);
        this.colour = colour;
        this.setInteractable(false);
        this.setCanBlock(false);
        count++;
    }

    public String getColour() {
        return colour;
    }

    @Override
    public void add() {
        super.add();
        getDungeon().portalAdd(this);
    }

    @Override
    public void remove() {
        super.remove();
        getDungeon().portalRemove(this);
    }

    /**
     * teleport the player to the paired portal
     */
    public void teleport() {
        Portal pairedPortal = this.getPairedPortal();
        int layer = getDungeon().getPlayer().getPosition().getLayer();
        getDungeon().getPlayer().setPosition(new Position(pairedPortal.getX(), pairedPortal.getY(), layer));
    }

    /**
     * return the paired portal of this portal
     */
    public Portal getPairedPortal() {
        Portal pairedPortal = getDungeon().getPortals().stream()
                .filter(portal -> (portal.getColour().equals(this.colour) && !portal.getId().equals(this.getId())))
                .findAny().get();
        return pairedPortal;
    }

    @Override
    public void react() {
        teleport();
    }
}
