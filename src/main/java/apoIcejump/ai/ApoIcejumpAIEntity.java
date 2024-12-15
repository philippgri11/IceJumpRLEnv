package apoIcejump.ai;

import org.apogames.entity.ApoEntity;

/**
 * repr�sentiert eine Entity (also davon erben, Spieler, Gegner, Eisbl�cke) im Spiel<br />
 * class for all entities<br />
 * @author Dirk Aporius
 *
 */
public class ApoIcejumpAIEntity {

	private ApoEntity entity;

	public ApoIcejumpAIEntity(ApoEntity entity) {
		this.entity = entity;
	}

	/**
	 * gibt die X-Position der Entity zur�ck<br />
	 * 0 = ganz links, ApoIcejumpAIConstants.GAME_WIDTH - getWidth() = ganz rechts<br />
	 * returns the x-Position of the entity<br />
	 * 0 = left, ApoIcejumpAIConstants.GAME_WIDTH - getWidth() = right<br />
	 * @return gibt die X-Position der Entity zur�ck / returns the x-Position of the entity
	 */
	public float getX() {
		return (float)this.entity.getRec().getX();
	}

	/**
	 * gibt die X-Position der Entity zur�ck<br />
	 * 0 oben im Frame<br />
	 * returns the x-Position of the entity<br />
	 * 0 above<br />
	 * @return gibt die X-Position der Entity zur�ck / returns the x-Position of the entity
	 */
	public float getY() {
		return (float)this.entity.getRec().getY();
	}

	/**
	 * gibt die Breite der Entity zur�ck<br />
	 * returns the width of the entity<br />
	 * @return gibt die Breite der Entity zur�ck / returns the width of the entity
	 */
	public float getWidth() {
		return (float)this.entity.getRec().getWidth();
	}

	/**
	 * gibt die H�he der Entity zur�ck<br />
	 * returns the height of the entity<br />
	 * @return gibt die H�he der Entity zur�ck / returns the height of the entity
	 */
	public float getHeight() {
		return (float)this.entity.getRec().getHeight();
	}

	/**
	 * gibt die Geschwindigkeit in X-Richtung zur�ck <br />
	 * Um auszurechnen, wo eine Entity beim n�chsten Aufruf ist<br />
	 * rechnet einfach getVecX() * ApoicejumpAIConstants.WAIT_TIME_THINK <br />
	 * Beispiel eine Geschwindigkeit von 0.16 bedeutet bei 10 Millisekunden bis zum n�chsten Aufruf <br />
	 * das die Entity beim n�chsten Aufruf 1.6 Pixel weiter rechts ist.<br />
	 * eturns the speed in x-direction of the entity per ms <br />
	 * To know where the entity in the next call is<br />
	 * calculate getVecX() * ApoicejumpAIConstants.WAIT_TIME_THINK <br />
	 * Excample a speed of 0.16 means that in 10 ms to the next call<br />
	 * the entity is 1.6 pixel further to the right.<br />
	 * @return gibt die Geschwindigkeit in X-Richtung zur�ck / returns the speed in x-direction of the entity per ms
	 */
	public float getVecX() {
		return this.entity.getVecX();
	}

	/**
	 * gibt die Geschwindigkeit in Y-Richtung zur�ck (siehe getVecX() zum Verst�ndnis)<br />
	 * returns the speed in y-direction of the entity per ms (see getVecX() to know how to calculate the next step)<br />
	 * @return gibt die Geschwindigkeit in Y-Richtung zur�ck / returns the speed in y-direction of the entity per ms
	 */
	public float getVecY() {
		return this.entity.getVecY();
	}

	/**
	 * gibt zur�ck, ob diese Entity sich mit der �bergebenen Entity schneidet<br />
	 * returns whether the entity intersects another entity<br />
	 * @param entity : zu �berpr�fende Entity / other entity<br />
	 * @return TRUE, beide Entities schneiden sich, sonst FALSE / TRUE, entity intersects other entity, else FALSE<br />
	 */
	public boolean intersects(ApoIcejumpAIEntity entity) {
		return this.entity.intersects(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
	}

	/**
	 * gibt zur�ck, ob sich diese Entity mit dem �bergebenen Rechteck schneidet<br />
	 * returns whether the entity intersects with a rectangle<br />
	 * @param x : X-Wert des Rechtecks (oben links) / x value from the rec (left above)
	 * @param y : Y-Wert des Rechtecks (oben links) / y value from the rec (left above)
	 * @param width : Breite des Rechtecks / width of the rec
	 * @param height : H�he des Rechtecks / height of the rec
	 * @return TRUE, Entity schneidet Rechteck, sonst FALSE / TRUE, entity intersects the rec, else FALSE<br />
	 */
	public boolean intersects(float x, float y, float width, float height) {
		return this.entity.intersects(x, y, width, height);
	}

}
