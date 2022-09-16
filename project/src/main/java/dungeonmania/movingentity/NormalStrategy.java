package dungeonmania.movingentity;

import dungeonmania.util.Position;

public class NormalStrategy implements EnemyActionStrategy {

    @Override
    public Position move(NonPlayer enemy) {
        return enemy.normalMove();
    }

}
