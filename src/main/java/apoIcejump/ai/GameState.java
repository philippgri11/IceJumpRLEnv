package apoIcejump.ai;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

// Erstellen Sie eine Hilfsklasse, um den State zu serialisieren
class GameState {
    // Beispiel: Spielerzustand
    static class EntityState {
        @SerializedName("x")        float x;
        @SerializedName("y")        float y;
        @SerializedName("width")    float width;
        @SerializedName("height")   float height;
        @SerializedName("velX")     float velX;
        @SerializedName("velY")     float velY;
        @SerializedName("isFire")   boolean isFire;
        @SerializedName("isSlow")   boolean isSlow;
        @SerializedName("isFast")   boolean isFast;
        @SerializedName("slowLeft") int slowTimeLeft;
        @SerializedName("fastLeft") int fastTimeLeft;
        @SerializedName("fireLeft") int fireTimeLeft;
        @SerializedName("highJumpLeft") int highJumpLeft;
    }

    // Beispiel: Blockzustand (Wir speichern nur die nächsten paar Blöcke)
    static class BlockState {
        @SerializedName("x") float x;
        @SerializedName("y") float y;
        @SerializedName("width") float width;
        @SerializedName("height") float height;
    }

    static class GoodieState {
        @SerializedName("x") float x;
        @SerializedName("y") float y;
        @SerializedName("width") float width;
        @SerializedName("height") float height;
        @SerializedName("type") String type; // Art des Goodies, wenn verfügbar
    }

    static class BirdState {
        @SerializedName("x") float x;
        @SerializedName("y") float y;
        @SerializedName("width") float width;
        @SerializedName("height") float height;
    }

    @SerializedName("time")          int time;
    @SerializedName("isSuddenDeath") boolean isSuddenDeath;

    @SerializedName("player")   EntityState player;
    @SerializedName("enemy")    EntityState enemy;
    @SerializedName("blocks")   List<BlockState> blocks = new ArrayList<>();
    @SerializedName("goodies")  List<GoodieState> goodies = new ArrayList<>();
    @SerializedName("birds")    List<BirdState> birds = new ArrayList<>();
}