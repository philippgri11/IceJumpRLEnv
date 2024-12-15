//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package apoIcejump.ai;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;

public class Hard extends ApoIcejumpAI {
    private final int AWAY = 35;
    private boolean bOver = true;
    private boolean bEnemyNear = true;
    private boolean bGoLeft = false;
    private boolean bHaveToGo = false;

    public Hard() {
    }

    public String getAuthor() {
        return "Dirk Aporius";
    }

    public String[] getRidicule() {
        String[] ridicule = new String[]{"Apo ist nunmal einfach besser als #enemy", "Gaehn ... ist das alles was du kannst?", "Yipee ya yeah Schweinebacke!", "Hasta la vista, #enemy", "Spring, #enemy, spring", "#enemy, ich bin dein Vater", "Spiels noch einmal, #enemy", "Ich bin der Koenig der Welt", "Mein Eisblock gehoert zu mir, klar?", "Setz dich, nimm dir nen Keks - du #enemy!"};
        return ridicule;
    }

    public Color getColor() {
        return new Color(255, 255, 255, 128);
    }

    public boolean shouldOwnRender() {
        return true;
    }

    public boolean renderPlayer(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(255, 255, 255, 128));
        g.fillRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 11, 11);
        g.setColor(Color.BLACK);
        g.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 11, 11);
        Stroke stroke = g.getStroke();
        g.setStroke(new BasicStroke(3.0F));
        if (this.bEnemyNear) {
            if (!this.bOver) {
                g.drawLine(0 + this.getWidth() / 2 - 4, 0 + this.getHeight() / 2 - 5, 0 + this.getWidth() / 2 - 7, 0 + this.getHeight() / 2);
                g.drawLine(0 + this.getWidth() / 2 + 3, 0 + this.getHeight() / 2 - 5, 0 + this.getWidth() / 2 + 6, 0 + this.getHeight() / 2);
            } else {
                g.drawLine(0 + this.getWidth() / 2 - 4, 0 + this.getHeight() / 2 - 5, 0 + this.getWidth() / 2 - 4, 0 + this.getHeight() / 2);
                g.drawLine(0 + this.getWidth() / 2 + 3, 0 + this.getHeight() / 2 - 5, 0 + this.getWidth() / 2 + 3, 0 + this.getHeight() / 2);
            }
        } else {
            g.drawLine(0 + this.getWidth() / 2 - 7, 0 + this.getHeight() / 2 - 5, 0 + this.getWidth() / 2 - 4, 0 + this.getHeight() / 2);
            g.drawLine(0 + this.getWidth() / 2 + 6, 0 + this.getHeight() / 2 - 5, 0 + this.getWidth() / 2 + 3, 0 + this.getHeight() / 2);
        }

        g.setStroke(stroke);
        if (this.bEnemyNear) {
            Polygon p = new Polygon();
            if (this.bOver) {
                p.addPoint(this.getWidth() / 2 - 4, this.getHeight() / 2 + 5);
                p.addPoint(this.getWidth() / 2 + 4, this.getHeight() / 2 + 5);
                p.addPoint(this.getWidth() / 2, this.getHeight() / 2 + 10);
            } else {
                p.addPoint(this.getWidth() / 2 - 4, this.getHeight() / 2 + 10);
                p.addPoint(this.getWidth() / 2 + 4, this.getHeight() / 2 + 10);
                p.addPoint(this.getWidth() / 2, this.getHeight() / 2 + 5);
            }

            g.setColor(Color.RED);
            g.fillPolygon(p);
            g.setColor(Color.BLACK);
            g.drawPolygon(p);
        } else {
            g.setColor(Color.RED);
            g.setStroke(new BasicStroke(2.0F));
            g.drawLine(this.getWidth() / 2 - 4, this.getHeight() / 2 + 7, this.getWidth() / 2 + 4, this.getHeight() / 2 + 7);
            g.setStroke(stroke);
        }

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        return true;
    }

    public String getImage() {
        return "player_apo.gif";
    }

    public String getName() {
        return "Hard";
    }

    public int getWidth() {
        return 30;
    }

    public int getHeight() {
        return 30;
    }

    public void think(ApoIcejumpAILevel level) {
        if (level.getPlayer().getY() < ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getY()) {
            this.bOver = true;
        } else {
            this.bOver = false;
        }

        boolean bEnemyOver = false;
        float difference;
        if (level.getPlayer().intersects(((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getX() + 1.0F, 0.0F, ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getWidth() - 2.0F, 480.0F)) {
            bEnemyOver = true;
            this.bEnemyNear = true;
            difference = level.getPlayer().getX() + level.getPlayer().getWidth() / 2.0F - ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getX() - ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getWidth() / 2.0F;
            if (level.getPlayer().getY() + level.getPlayer().getHeight() / 2.0F < ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getY()) {
                this.bHaveToGo = false;
                if (difference > 3.0F) {
                    level.getPlayer().setVecX(-0.16F);
                } else if (difference < -3.0F) {
                    level.getPlayer().setVecX(0.16F);
                } else {
                    level.getPlayer().setVecX(0.0F);
                }

                return;
            }

            this.bHaveToGo = true;
        } else {
            this.bEnemyNear = false;
        }

        if (this.bHaveToGo) {
            difference = level.getPlayer().getX() + level.getPlayer().getWidth() / 2.0F - ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getX() - ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getWidth() / 2.0F;
            if (Math.abs(difference) >= 35.0F) {
                this.bHaveToGo = false;
            } else if (level.getPlayer().getX() < 40.0F) {
                this.bGoLeft = false;
            } else if (level.getPlayer().getX() + level.getPlayer().getWidth() > 600.0F) {
                this.bGoLeft = true;
            }

            if (this.bHaveToGo && (double)level.getPlayer().getVecY() < 0.1) {
                if (this.bGoLeft) {
                    level.getPlayer().setVecX(-0.16F);
                } else {
                    level.getPlayer().setVecX(0.16F);
                }

                return;
            }
        }

        if (!bEnemyOver && level.getPlayer().getVecY() < 0.0F) {
            if (level.getPlayer().getX() - ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getX() > 3.0F) {
                level.getPlayer().setVecX(-0.16F);
                return;
            }

            if (level.getPlayer().getX() - ((ApoIcejumpAIEnemy)level.getEnemies().get(0)).getX() < -3.0F) {
                level.getPlayer().setVecX(0.16F);
                return;
            }
        }

        if (level.getBlocks().size() > 0) {
            difference = level.getPlayer().getX() + level.getPlayer().getWidth() / 2.0F - ((ApoIcejumpAIBlock)level.getBlocks().get(0)).getX() - ((ApoIcejumpAIBlock)level.getBlocks().get(0)).getWidth() / 2.0F;
            if (difference > 3.0F) {
                level.getPlayer().setVecX(-0.16F);
            } else if (difference < -3.0F) {
                level.getPlayer().setVecX(0.16F);
            } else {
                level.getPlayer().setVecX(0.0F);
            }
        }

    }
}
