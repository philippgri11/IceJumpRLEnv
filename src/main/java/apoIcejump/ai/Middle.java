//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package apoIcejump.ai;

import java.awt.Color;

public class Middle extends ApoIcejumpAI {
    public Middle() {
    }

    public String getAuthor() {
        return "Dirk Aporius";
    }

    public Color getColor() {
        return new Color(120, 120, 120);
    }

    public String getImage() {
        return "player_snowman.png";
    }

    public String getName() {
        return "Middle";
    }

    public void think(ApoIcejumpAILevel level) {
        if (level.getPlayer().getVecY() < 0.0F) {
            if (level.getPlayer().getX() - ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getX() > 4.0F) {
                level.getPlayer().setVecX(-0.12F);
                return;
            }

            if (level.getPlayer().getX() - ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getX() < -4.0F) {
                level.getPlayer().setVecX(0.12F);
                return;
            }
        }

        if (level.getBlocks().size() > 0) {
            float difference = level.getPlayer().getX() + level.getPlayer().getWidth() / 2.0F - ((ApoIcejumpAIBlock)level.getBlocks().get(0)).getX() - ((ApoIcejumpAIBlock)level.getBlocks().get(0)).getWidth() / 2.0F;
            if (difference > 4.0F) {
                level.getPlayer().setVecX(-0.12F);
            } else if (difference < -4.0F) {
                level.getPlayer().setVecX(0.12F);
            } else {
                level.getPlayer().setVecX(0.0F);
            }
        }

    }
}
