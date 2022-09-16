package dungeonmania.movingentity;

import dungeonmania.util.Position;

public class InvincibilityStrategy implements EnemyActionStrategy {

    private Player player;

    public InvincibilityStrategy(Player player) {
        this.player = player;
    }

    @Override
    public Position move(NonPlayer enemy) {
        if (enemy.isAlly()) {
            return enemy.normalMove();
        }
        return enemy.flee(player);
    }

}
