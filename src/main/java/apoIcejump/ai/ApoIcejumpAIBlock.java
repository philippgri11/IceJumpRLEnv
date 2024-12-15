package apoIcejump.ai;

import apoIcejump.entity.ApoIcejumpBlock;

/**
 * Klasse, die einen Eisblock darstellt
 * class for the iceblock
 * @author Dirk Aporius
 *
 */
public class ApoIcejumpAIBlock extends ApoIcejumpAIEntity {

	private ApoIcejumpBlock entity;

	public ApoIcejumpAIBlock(ApoIcejumpBlock entity) {
		super(entity);

		this.entity = entity;
	}

	/**
	 * gibt zur�ck, wie oft der Block noch besprungen werden kann, bevor er sich aufl�st<br />
	 * returns how often the block can be touched until he will blow up ;)
	 * @return gibt zur�ck, wie oft der Block noch besprungen werden kann, bevor er sich aufl�st / returns how often the block can be touched until he will blow up ;)
	 */
	public int getHits() {
		return this.entity.getHits();
	}

}
