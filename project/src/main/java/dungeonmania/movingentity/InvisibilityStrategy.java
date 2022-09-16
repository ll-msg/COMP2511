package dungeonmania.movingentity;

import dungeonmania.util.Position;

public class InvisibilityStrategy implements EnemyActionStrategy {

    @Override
    public Position move(NonPlayer enemy) {
        return enemy.invisibleMove();
    }

}
