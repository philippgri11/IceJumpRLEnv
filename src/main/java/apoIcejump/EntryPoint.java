package apoIcejump;

import apoIcejump.entity.*;
import com.google.gson.Gson;
import py4j.GatewayServer;
import java.util.ArrayList;
import java.util.Random;

import apoIcejump.game.ApoIcejumpPanel;

public class EntryPoint {

    private ApoIcejumpPanel panel;
    private boolean gameStarted = false;

    // Zusätzliche Variablen analog zur Simulation:
    private int gameTime;
    private int goodieTime;
    private int birdTime;
    private Random random;
    private long startNanoTime;

    // Variable für den Gewinner
    private String winnerName = null;

    public EntryPoint() {
        // Optional: Konstruktor-Logik
    }

    /**
     * Startet das eigentliche Spiel
     */
    public void startGame() {
        if (this.panel == null) {
            this.panel = new ApoIcejumpPanel();
        }

        this.panel.init();

        this.startNanoTime = System.nanoTime();
        this.random = new Random(this.startNanoTime);

        this.panel.makeBlocks(this.startNanoTime);

        this.gameTime = 0;
        this.goodieTime = (int)(this.random.nextInt(10000) + 3000);
        this.birdTime = (int)(this.random.nextInt(9500) + 8000);

        for (ApoIcejumpPlayer p : this.panel.getPlayers()) {
            p.init();
        }

        this.panel.makeNewParticle();

        this.gameStarted = true;
        this.winnerName = null; // Reset winner, falls ein neues Spiel gestartet wird
    }

    /**
     * Setzt die Aktion für einen bestimmten Spieler
     */
    public void setPlayerAction(int playerIndex, float action) {
        if (!this.gameStarted) {
            System.out.println("Das Spiel wurde noch nicht gestartet. Bitte erst startGame() aufrufen.");
            return;
        }
        ApoIcejumpPlayer[] players = this.panel.getPlayers();
        if (playerIndex >= 0 && playerIndex < players.length) {
            players[playerIndex].setVecX(action);
        }
    }

    /**
     * Simuliert einen Schritt
     */
    public void step() {
        if (!this.gameStarted || isGameOver()) {
            System.out.println("Das Spiel wurde nicht gestartet oder ist bereits vorbei.");
            return;
        }

        int delta = 16;
        this.gameTime += delta;

        this.panel.thinkPlayers(delta, true);

        // Überprüfen, ob ein Spieler nicht mehr sichtbar ist => anderer Spieler gewinnt
        // Wir gehen hier von zwei Spielern aus.
        ApoIcejumpPlayer[] players = this.panel.getPlayers();
        if (players.length == 2) {
            boolean player0Visible = players[0].isBVisible();
            boolean player1Visible = players[1].isBVisible();
            if (!player0Visible && player1Visible) {
                // Spieler 1 gewinnt
                this.winnerName = players[1].getName();
            } else if (!player1Visible && player0Visible) {
                // Spieler 0 gewinnt
                this.winnerName = players[0].getName();
            } else if (!player0Visible && !player1Visible) {
                // Beide unsichtbar? Unentschieden oder Sonderfall
                // Hier könnte man winnerName = "Draw"; setzen
                this.winnerName = "Draw";
            }
        } else {
            // Falls mehr als 2 Spieler, müsste hier eine andere Logik rein
            // z.B. den letzten sichtbaren Spieler ermitteln
        }

        if (isGameOver()) {
            return; // Keine weiteren Updates nötig
        }

        // Goodies
        this.goodieTime -= delta;
        if (this.goodieTime <= 0) {
            this.goodieTime = (int)(this.random.nextInt(10000) + 3000);
            this.panel.makeGoodie();
        }
        this.panel.thinkGoodies(delta);

        // Vögel
        this.birdTime -= delta;
        if (this.birdTime <= 0) {
            this.birdTime = (int)(this.random.nextInt(8500) + 7000);
            this.panel.makeBird(1, false);
        }
        this.panel.thinkBird(delta);

        // Blöcke und Partikel
        this.panel.thinkBlocks(delta);
        this.panel.thinkParticles(delta);
    }

    /**
     * Liefert den aktuellen Spielzustand als JSON zurück.
     */
    public String getState() {
        if (!this.gameStarted) {
            System.out.println("Das Spiel wurde noch nicht gestartet. Bitte erst startGame() aufrufen.");
            return "{}";
        }

        GameState gs = new GameState();
        gs.time = this.gameTime;

        ApoIcejumpPlayer[] players = this.panel.getPlayers();
        for (ApoIcejumpPlayer p : players) {
            GameState.PlayerState ps = new GameState.PlayerState();
            ps.x = p.getX();
            ps.y = p.getY();
            ps.visible = p.isBVisible();
            ps.name = p.getName();
            gs.players.add(ps);
        }

        // Winner
        gs.winner = this.winnerName;

        // Blocks (Front + Back)
        for (ApoIcejumpBlock block : this.panel.getBackBlocks()) {
            GameState.BlockState bs = new GameState.BlockState();
            bs.x = block.getX();
            bs.y = block.getY();
            bs.width = block.getWidth();
            bs.height = block.getHeight();
            bs.hits = block.getHits();
            gs.blocks.add(bs);
        }

        for (ApoIcejumpBlock block : this.panel.getFrontBlocks()) {
            GameState.BlockState bs = new GameState.BlockState();
            bs.x = block.getX();
            bs.y = block.getY();
            bs.width = block.getWidth();
            bs.height = block.getHeight();
            bs.hits = block.getHits();
            gs.blocks.add(bs);
        }

        // Goodies
        for (ApoIcejumpGoodie goodie : this.panel.getGoodies()) {
            if (goodie.isBVisible()) {
                GameState.GoodieState gsG = new GameState.GoodieState();
                gsG.x = goodie.getX();
                gsG.y = goodie.getY();
                gsG.type = goodie.getGoodie(); // der interne Goodie-Typ
                gs.goodies.add(gsG);
            }
        }

        // Birds
        for (ApoIcejumpBird bird : this.panel.getBirds()) {
            if (bird.isBVisible()) {
                GameState.BirdState bsB = new GameState.BirdState();
                bsB.x = bird.getX();
                bsB.y = bird.getY();
                gs.birds.add(bsB);
            }
        }


        Gson gson = new Gson();
        return gson.toJson(gs);
    }

    /**
     * Überprüft, ob das Spiel vorbei ist.
     * Spiel vorbei, wenn winnerName != null.
     */
    public boolean isGameOver() {
        return this.winnerName != null;
    }

    /**
     * Gibt den Namen des Gewinners zurück, oder null wenn noch keiner feststeht.
     */
    public String getWinner() {
        return this.winnerName;
    }

    public static void main(String[] args) {
        EntryPoint ep = new EntryPoint();
        GatewayServer server = new GatewayServer(ep);
        server.start();
        System.out.println("Java-Server läuft und wartet auf Anfragen...");
    }

    public static class GameState {
        float time;
        ArrayList<PlayerState> players = new ArrayList<>();
        ArrayList<BlockState> blocks = new ArrayList<>();
        ArrayList<GoodieState> goodies = new ArrayList<>();
        ArrayList<BirdState> birds = new ArrayList<>();
        String winner; // neuer Eintrag für Gewinner

        static class PlayerState {
            float x, y;
            boolean visible;
            String name;
        }

        static class BlockState {
            float x, y;
            float width, height;
            int hits;
        }

        static class GoodieState {
            float x, y;
            int type; // z.B. ApoIcejumpConstants.GOODIE_FIRE etc.
        }

        static class BirdState {
            float x, y;
        }
    }
}