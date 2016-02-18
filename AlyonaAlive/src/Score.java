public class Score {
	

	public void setScoreId(String scoreId) {
		this.count = scoreId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return score;
	}
	
	public Score(String number, String count, String score) {
		this.number = number;
		this.count = count;
		this.score = score;
	}

	private String number;
	private String count;
	private String score;

	public String getClientId() {
		return number;
	}

	public void setClientId(String clientId) {
		this.number = clientId;
	}

	public String getScoreId() {
		return count;
	}

}
