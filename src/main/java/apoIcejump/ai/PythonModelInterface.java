package apoIcejump.ai;

public interface PythonModelInterface {
    /**
     * Diese Methode soll von der Python-Seite implementiert werden.
     * Sie erhält den aktuellen Zustand als float-Array (z. B. [playerX, playerY, enemyX, enemyY, ...])
     * und gibt die vorhergesagte Aktion (z. B. eine horizontale Geschwindigkeit) zurück.
     */
    float predictAction(float[] obs);
}
