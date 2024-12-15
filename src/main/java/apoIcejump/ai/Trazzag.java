//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package apoIcejump.ai;

import java.awt.Color;
import java.util.ArrayList;

public class Trazzag extends ApoIcejumpAI {
    private ApoIcejumpAIEntity block;

    public Trazzag() {
    }

    public String getImage() {
        return "player_duke.png";
    }

    public String getName() {
        return "Trazzag";
    }

    public String getAuthor() {
        return "Christopher de Bruin";
    }

    public Color getColor() {
        return Color.RED;
    }

    private ApoIcejumpAIEntity findNextBlock(ApoIcejumpAIPlayer player, ApoIcejumpAIEntity enemy, ArrayList<ApoIcejumpAIBlock> blocks) {
        for(int i = 0; i < blocks.size(); ++i) {
            if (player.getX() < enemy.getX()) {
                if (((ApoIcejumpAIBlock)blocks.get(i)).getX() > player.getX() + 30.0F) {
                    return (ApoIcejumpAIEntity)blocks.get(i);
                }
            } else if (player.getX() > enemy.getX() && ((ApoIcejumpAIBlock)blocks.get(i)).getX() + ((ApoIcejumpAIBlock)blocks.get(i)).getWidth() + 30.0F < player.getX()) {
                return (ApoIcejumpAIEntity)blocks.get(i);
            }
        }

        if (blocks.size() > 0) {
            return (ApoIcejumpAIEntity)blocks.get(0);
        } else {
            return null;
        }
    }

    private void reachNearestBlock(ApoIcejumpAIPlayer player, ArrayList<ApoIcejumpAIBlock> blocks) {
        if (blocks.size() > 0) {
            if (((ApoIcejumpAIBlock)blocks.get(0)).getX() > player.getX() + player.getWidth() / 2.0F) {
                player.setVecX(0.16F);
            } else if (((ApoIcejumpAIBlock)blocks.get(0)).getX() + ((ApoIcejumpAIBlock)blocks.get(0)).getWidth() < player.getX() + player.getWidth() / 2.0F) {
                player.setVecX(-0.16F);
            } else {
                player.setVecX(0.0F);
            }

        }
    }

    private void reachNextBlock(ApoIcejumpAIPlayer player) {
        if (player.getX() < this.block.getX() && player.getX() + player.getWidth() <= this.block.getX()) {
            player.setVecX(2.0F);
        } else if (player.getX() > this.block.getX() && player.getX() >= this.block.getX() + this.block.getWidth()) {
            player.setVecX(-2.0F);
        } else {
            player.setVecX(0.0F);
        }

    }

    public void think(ApoIcejumpAILevel level) {
        boolean links = false;
        boolean rechts = false;
        boolean tiefer = false;
        boolean hoeher = false;
        boolean steigend = false;
        if (level.getPlayer().getVecX() == 0.0F) {
            this.block = this.findNextBlock(level.getPlayer(), (ApoIcejumpAIEntity)level.getEnemies().get(0), level.getBlocks());
        }

        if (this.block != null) {
            if (level.getPlayer().getX() < ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getX() && level.getPlayer().getX() + level.getPlayer().getWidth() > ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getX()) {
                links = true;
            }

            if (level.getPlayer().getX() > ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getX() && level.getPlayer().getX() < ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getX() + ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getWidth()) {
                rechts = true;
            }

            if (level.getPlayer().getY() - level.getPlayer().getHeight() > ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getY()) {
                tiefer = true;
            }

            if (level.getPlayer().getY() < ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getY() + ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getHeight()) {
                hoeher = true;
            }

            if (level.getPlayer().getVecY() < 0.0F) {
                steigend = true;
            }

            if (level.getPlayer().getX() == ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getX() && hoeher) {
                level.getPlayer().setVecX(((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getVecX());
            }

            if (level.getPlayer().getX() == ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getX() && !hoeher) {
                if (steigend) {
                    this.reachNextBlock(level.getPlayer());
                } else {
                    this.reachNearestBlock(level.getPlayer(), level.getBlocks());
                }
            } else if (links && tiefer) {
                if (steigend) {
                    level.getPlayer().setVecX(-0.16F);
                } else {
                    this.reachNearestBlock(level.getPlayer(), level.getBlocks());
                }
            } else if (rechts && tiefer) {
                if (steigend) {
                    level.getPlayer().setVecX(0.16F);
                } else {
                    this.reachNearestBlock(level.getPlayer(), level.getBlocks());
                }
            } else if (rechts && hoeher) {
                level.getPlayer().setVecX(((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getVecX() + -0.16F);
            } else if (links && hoeher) {
                level.getPlayer().setVecX(((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getVecX() + 0.16F);
            } else if (level.getPlayer().getX() < ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getX() && !links) {
                if (level.getPlayer().getX() + level.getPlayer().getWidth() + 20.0F > ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getX()) {
                    if (steigend) {
                        level.getPlayer().setVecX(0.16F);
                    } else {
                        this.reachNextBlock(level.getPlayer());
                    }
                } else {
                    this.reachNextBlock(level.getPlayer());
                }
            } else if (level.getPlayer().getX() > ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getX() && !rechts) {
                if (level.getPlayer().getX() < ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getX() + ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getWidth() + 20.0F) {
                    if (steigend) {
                        level.getPlayer().setVecX(-0.16F);
                    } else {
                        this.reachNextBlock(level.getPlayer());
                    }
                } else {
                    this.reachNextBlock(level.getPlayer());
                }
            }

        }
    }
}
