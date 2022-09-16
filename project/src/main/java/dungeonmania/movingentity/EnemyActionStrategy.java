package dungeonmania.movingentity;

import dungeonmania.util.Position;

public interface EnemyActionStrategy {

    /**
     * decide the next movement position of the input non-player moving entity
     * 
     * @param enemy input non-player moving entity
     * @return the position of the next movement
     */
    public Position move(NonPlayer enemy);

}
