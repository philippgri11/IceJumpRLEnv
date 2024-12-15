package apoIcejump.ai;

import py4j.GatewayServer;
import java.util.List;
import apoIcejump.entity.*;

public class MyPythonBot extends ApoIcejumpAI {

    private GatewayServer gatewayServer;
    private PythonModelInterface pythonModel;

    // Konstanten für maximale Anzahl von Objekten
    private static final int MAX_BLOCKS = 20;
    private static final int MAX_GOODIES = 5;
    private static final int MAX_BIRDS = 5;
    private static final int MAX_FISH = 5;

    public MyPythonBot() {
        this.pythonModel = null;
        this.gatewayServer = new GatewayServer(this, 25333);
        this.gatewayServer.start();
        System.out.println("Py4J GatewayServer für MyPythonBot läuft auf Port 25333. Bitte mit Python verbinden und setPythonModel aufrufen.");
    }

    public void setPythonModel(PythonModelInterface model) {
        this.pythonModel = model;
        System.out.println("Python-Modell wurde erfolgreich in MyPythonBot registriert!");
    }

    @Override
    public String getAuthor() {
        return "Ihr Name";
    }

    @Override
    public String getName() {
        return "MyPythonBot";
    }

    @Override
    public void think(ApoIcejumpAILevel level) {
        if (this.pythonModel == null) {
            level.getPlayer().setVecX(0.0f);
            return;
        }

        // Spieler-Zustand extrahieren
        ApoIcejumpAIPlayer player = level.getPlayer();
        ApoIcejumpAIEnemy enemy = level.getEnemies().get(0);

        float x0 = player.getX();
        float y0 = player.getY();
        float v0 = 1;
        float x1 = enemy.getX();
        float y1 = enemy.getY();
        float v1 = 1;

        // OBS: Spieler (6 Werte)
        float[] obsPlayers = new float[] { x0, y0, v0, x1, y1, v1 };

        // Blöcke holen - wir nehmen an ApoIcejumpAILevel hat getBlocks(), die Liste von ApoIcejumpBlock liefert
        List<ApoIcejumpAIBlock> blocks = level.getBlocks(); // Methode muss existieren oder entsprechend ergänzt werden
        float[] obsBlocks = new float[MAX_BLOCKS * 4];
        int idx = 0;
        for (int i = 0; i < MAX_BLOCKS; i++) {
            if (i < blocks.size()) {
                ApoIcejumpAIBlock b = blocks.get(i);
                obsBlocks[idx++] = b.getX();
                obsBlocks[idx++] = b.getY();
                obsBlocks[idx++] = (float)b.getHits();
            } else {
                obsBlocks[idx++] = 0.0f;
                obsBlocks[idx++] = 0.0f;
                obsBlocks[idx++] = 0.0f;
            }
        }

        // Goodies
        List<ApoIcejumpAIGoodies> goodies = level.getGoodies(); // Methode muss bereitstehen
        float[] obsGoodies = new float[MAX_GOODIES * 4];
        idx = 0;
        for (int i = 0; i < MAX_GOODIES; i++) {
            if (i < goodies.size()) {
                ApoIcejumpAIGoodies g = goodies.get(i);
                obsGoodies[idx++] = g.getX();
                obsGoodies[idx++] = g.getY();
                obsGoodies[idx++] = (float)g.getGoodie();
            } else {
                obsGoodies[idx++] = 0.0f;
                obsGoodies[idx++] = 0.0f;
                obsGoodies[idx++] = 0.0f;
            }
        }

        // Birds
        List<ApoIcejumpAIEntity> birds = level.getBirds(); // Methode muss bereitstehen
        float[] obsBirds = new float[MAX_BIRDS * 3];
        idx = 0;
        for (int i = 0; i < MAX_BIRDS; i++) {
            if (i < birds.size()) {
                ApoIcejumpAIEntity bd = birds.get(i);
                obsBirds[idx++] = bd.getX();
                obsBirds[idx++] = bd.getY();
            } else {
                obsBirds[idx++] = 0.0f;
                obsBirds[idx++] = 0.0f;
            }
        }



        // Gesamtes obs-Array zusammenfügen
        float[] obs = new float[6 + (MAX_BLOCKS*3) + (MAX_GOODIES*3) + (MAX_BIRDS*2) ];
        // obsPlayers (6)
        System.arraycopy(obsPlayers, 0, obs, 0, 6);
        // obsBlocks (20 Werte)
        System.arraycopy(obsBlocks, 0, obs, 6, MAX_BLOCKS*3);
        // obsGoodies (20 Werte)
        System.arraycopy(obsGoodies, 0, obs, 6+MAX_BLOCKS*3, MAX_GOODIES*3);
        // obsBirds (15 Werte)
        System.arraycopy(obsBirds, 0, obs, 6+MAX_BLOCKS*3+MAX_GOODIES*3, MAX_BIRDS*2);

        // Aktion vom Python-Modell anfragen
        float action = 0.0f;
        try {
            action = this.pythonModel.predictAction(obs);
            System.out.println(action);
        } catch (Exception e) {
            System.err.println("Fehler bei predictAction: " + e.getMessage());
        }

        // Aktion setzen
        level.getPlayer().setVecX(action);
    }
}