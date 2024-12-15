//package apoIcejump.ai;
//
//import java.awt.Color;
//import java.util.ArrayList;
//
//import com.google.gson.Gson;
//import py4j.GatewayServer;
//
///**
// * Easy Player
// */
//public class Easy extends ApoIcejumpAI {
//	private PythonAgent pythonAgent; // statt Object nun das PythonAgent-Interface
//	private boolean gatewayInitialized = false;
//
//	@Override
//	public String getAuthor() {
//		return "AI Bot";
//	}
//
//	@Override
//	public Color getColor() {
//		return new Color(255, 255, 0);
//	}
//
//	@Override
//	public String getName() {
//		return "Easy";
//	}
//
//	public void think(ApoIcejumpAILevel level) {
//		System.out.println("start think");
//		if (!gatewayInitialized) {
//			try {
//				// Beispiel: Der Python-Server läuft auf localhost:25333
//				// Sie müssen sicherstellen, dass Ihr Python-Code diesen Port nutzt.
//				GatewayServer gateway = new GatewayServer();
//				gateway.start();
//
//				// Hier das Interface angeben
//				this.pythonAgent = (PythonAgent) gateway.getPythonServerEntryPoint(new Class[]{PythonAgent.class});
//				this.gatewayInitialized = true;
//			} catch (Throwable e) {
//				e.printStackTrace();
//			}
//		}
//		System.out.println("init GatewayServer");
//
//		float action = 0.0f;
//		try {
//			String state = getStateFromLevel(level);
//			Object result = callPythonAgentAction(state);
//
//			// Python wird vermutlich einen Double zurückgeben
//			if (result instanceof Double) {
//				action = ((Double) result).floatValue();
//			} else {
//				// Falls etwas anderes zurückkommt, hier ggf. loggen oder behandeln
//				System.err.println("Unerwarteter Rückgabetyp: " + (result != null ? result.getClass() : null));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		performAction(action, level);
//	}
//
//	private String getStateFromLevel(ApoIcejumpAILevel level) {
//		GameState state = new GameState();
//
//		// Grunddaten
//		state.time = level.getTime();
//		state.isSuddenDeath = level.isSuddenDeath();
//
//		// Spieler
//		ApoIcejumpAIPlayer p = level.getPlayer();
//		GameState.EntityState playerState = new GameState.EntityState();
//		playerState.x = p.getX();
//		playerState.y = p.getY();
//		playerState.width = p.getWidth();
//		playerState.height = p.getHeight();
//		playerState.velX = p.getVecX();
//		playerState.velY = p.getVecY();
//		playerState.isFire = false;
//		playerState.isSlow = false;
//		playerState.isFast = false;
//		playerState.slowTimeLeft = 0;
//		playerState.fastTimeLeft = 0;
//		playerState.fireTimeLeft = 0;
//		playerState.highJumpLeft = 0;
//		state.player = playerState;
//
//		// Gegner
//		if (level.getEnemies().size() > 0) {
//			ApoIcejumpAIEnemy e = level.getEnemies().get(0);
//			GameState.EntityState enemyState = new GameState.EntityState();
//			enemyState.x = e.getX();
//			enemyState.y = e.getY();
//			enemyState.width = e.getWidth();
//			enemyState.height = e.getHeight();
//			enemyState.velX = e.getVecX();
//			enemyState.velY = e.getVecY();
//			enemyState.isFire = e.isFire();
//			enemyState.isSlow = e.isSlow();
//			enemyState.isFast = e.isFaster();
//			enemyState.slowTimeLeft = e.getSlowTimeLeft();
//			enemyState.fastTimeLeft = e.getFastTimeLeft();
//			enemyState.fireTimeLeft = e.getFireTime();
//			enemyState.highJumpLeft = e.getHighJumpLeft();
//			state.enemy = enemyState;
//		} else {
//			state.enemy = null;
//		}
//
//		// Blocks
//		ArrayList<ApoIcejumpAIBlock> blocks = level.getBlocks();
//		for (int i = 0; i < Math.min(blocks.size(), 5); i++) {
//			ApoIcejumpAIBlock b = blocks.get(i);
//			GameState.BlockState bs = new GameState.BlockState();
//			bs.x = b.getX();
//			bs.y = b.getY();
//			bs.width = b.getWidth();
//			bs.height = b.getHeight();
//			state.blocks.add(bs);
//		}
//
//		// Goodies
//		ArrayList<ApoIcejumpAIGoodies> goodies = level.getGoodies();
//		for (int i = 0; i < Math.min(goodies.size(), 3); i++) {
//			ApoIcejumpAIGoodies g = goodies.get(i);
//			GameState.GoodieState gs = new GameState.GoodieState();
//			gs.x = g.getX();
//			gs.y = g.getY();
//			gs.width = g.getWidth();
//			gs.height = g.getHeight();
//			gs.type = String.valueOf(g.getGoodie());
//			state.goodies.add(gs);
//		}
//
//		// Birds
//		ArrayList<ApoIcejumpAIEntity> birds = level.getBirds();
//		for (int i = 0; i < Math.min(birds.size(), 3); i++) {
//			ApoIcejumpAIEntity bird = birds.get(i);
//			GameState.BirdState birdState = new GameState.BirdState();
//			birdState.x = bird.getX();
//			birdState.y = bird.getY();
//			birdState.width = bird.getWidth();
//			birdState.height = bird.getHeight();
//			state.birds.add(birdState);
//		}
//
//		Gson gson = new Gson();
//		return gson.toJson(state);
//	}
//
//	private Object callPythonAgentAction(String state) {
//		// getNextAction-Aufruf auf dem Python-Objekt
//		try {
//			return pythonAgent.getNextAction(state);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 0.0;
//		}
//	}
//
//	private void performAction(float action, ApoIcejumpAILevel level) {
//		// Beispiel: setzen Sie die X-Geschwindigkeit des Spielers auf den Wert von "action"
//		level.getPlayer().setVecX(action);
//	}
//
//	// Die folgenden Klassen (GameState, EntityState etc.) müssen hier oder in separaten Dateien definiert sein.
//	// Hier nur ein Beispiel:
//	class GameState {
//		float time;
//		boolean isSuddenDeath;
//		EntityState player;
//		EntityState enemy;
//		ArrayList<BlockState> blocks = new ArrayList<>();
//		ArrayList<GoodieState> goodies = new ArrayList<>();
//		ArrayList<BirdState> birds = new ArrayList<>();
//
//		static class EntityState {
//			float x, y, width, height, velX, velY;
//			boolean isFire, isSlow, isFast;
//			float slowTimeLeft, fastTimeLeft, fireTimeLeft, highJumpLeft;
//		}
//
//		static class BlockState {
//			float x, y, width, height;
//		}
//
//		static class GoodieState {
//			float x, y, width, height;
//			String type;
//		}
//
//		static class BirdState {
//			float x, y, width, height;
//		}
//	}
//}

package apoIcejump.ai;

import java.awt.Color;

import apoIcejump.ApoIcejumpConstants;

/**
 * Easy Player
 * @author Dirk Aporius
 *
 */
public class Easy extends ApoIcejumpAI {

	@Override
	public String getAuthor() {
		return "Dirk Aporius";
	}

	@Override
	public Color getColor() {
		return new Color(255, 255, 0);
	}

//	public String getImage() {
//		return "player_penguin.png";
//	}

	@Override
	public String getName() {
		return "Easy";
	}

	@Override
	public void think(ApoIcejumpAILevel level) {
		// falls es noch einen Eisblock gibt, dann
		if (level.getBlocks().size() > 0) {
			// gib die Differenz zwischen beiden Entit�ten
			float difference = level.getPlayer().getX() + level.getPlayer().getWidth()/2 - level.getBlocks().get(0).getX() - level.getBlocks().get(0).getWidth()/2;
			// falls die Differenz gr��er als 3 ist, dann bewege den Spieler mit der H�lfte der Maximaleschwindigkeit nach links, falls Dif kleiner als -3 dann rechts, ansonsten beweg dich nicht
			if (difference > 3) {
				level.getPlayer().setVecX(-ApoIcejumpConstants.PLAYER_MAX_VEC_X/2);
			} else if (difference < -3) {
				level.getPlayer().setVecX(ApoIcejumpConstants.PLAYER_MAX_VEC_X/2);
			} else {
				level.getPlayer().setVecX(0);
			}
		}
	}

}
