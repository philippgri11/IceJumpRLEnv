//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package apoIcejump.ai;

import java.awt.Color;
import java.util.ArrayList;

public class JoJo extends ApoIcejumpAI {
    private int status = 4;
    private ApoIcejumpAIPlayer player = null;
    private ApoIcejumpAIEntity enemy = null;
    private ArrayList<ApoIcejumpAIBlock> blocks = null;

    public JoJo() {
    }

    public String getImage() {
        return "player_monkey.png";
    }

    public String getName() {
        return "JoJo";
    }

    public String getAuthor() {
        return "Johannes Hense";
    }

    public Color getColor() {
        return Color.CYAN;
    }

    public void think(ApoIcejumpAILevel level) {
        this.player = level.getPlayer();
        this.enemy = (ApoIcejumpAIEntity)level.getEnemies().get(0);
        this.blocks = level.getBlocks();
        this.reviewSituation();
        if (this.blocks.size() < 1) {
            this.getToEnemy();
        } else {
            switch (this.status) {
                case 1:
                    this.escapeFromEnemy();
                    break;
                case 2:
                    this.getToEnemy();
                    break;
                case 3:
                    this.moveTo(((ApoIcejumpAIBlock)this.blocks.get(0)).getX(), ((ApoIcejumpAIBlock)this.blocks.get(0)).getWidth() / 4.0F);
            }
        }

    }

    private void getToEnemy() {
        this.moveTo(this.enemy.getX(), this.enemy.getWidth() / 4.0F);
    }

    private void escapeFromEnemy() {
        float v = this.player.getX() <= 300.0F ? 0.16F : -0.16F;
        ApoIcejumpAIBlock block = (ApoIcejumpAIBlock)this.blocks.get(this.calcLatestReachableBlock(v));
        this.moveTo(block.getX(), block.getWidth() / 4.0F);
    }

    private void moveTo(float x, float d) {
        if (x - d > this.player.getX() + this.player.getWidth() / 2.0F) {
            this.player.setVecX(0.16F);
        } else if (x + d < this.player.getX() + this.player.getWidth() / 2.0F) {
            this.player.setVecX(-0.16F);
        } else {
            this.player.setVecX(0.0F);
        }

    }

    private int calcLatestReachableBlock(float v) {
        return 1;
    }

    public void reviewSituation() {
        if (this.enemy.getX() + this.enemy.getWidth() > this.player.getX() && this.enemy.getX() + this.enemy.getWidth() > this.player.getX() + this.player.getWidth()) {
            if (this.enemy.getY() > this.player.getY()) {
                this.status = 2;
            } else {
                this.status = 1;
            }
        } else {
            this.status = 3;
        }

    }
}
