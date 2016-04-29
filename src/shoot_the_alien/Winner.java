package shoot_the_alien;


/**
 * There are the attributes to register a new winner.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 26/04/16
 */
public class Winner {

	private String name;
	private String identification;
	private int finalScore;
	
	
	
	/**
	 * Build a new winner and sets up its attributes.
	 * 
	 * @param name
	 * @param id
	 * @param score
	 */
	public Winner(String name, String id, int score) {
		this.name = name;
		this.identification = id;
		this.finalScore = score;
	}
	
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return The identification
	 */
	public String getIdentification() {
		return identification;
	}


	/**
	 * An winner identification can be the school where he studes
	 * or the city where he lives.
	 * 
	 * @param identification An winner identification.
	 */
	public void setIdentification(String identification) {
		this.identification = identification;
	}


	/**
	 * @return the finalScore
	 */
	public int getFinalScore() {
		return finalScore;
	}


	/**
	 * @param finalScore the finalScore to set
	 */
	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}
	
	
	
	
	
	
	
}
