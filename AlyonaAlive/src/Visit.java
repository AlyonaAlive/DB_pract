import java.sql.Date;

public class Visit {
	

	public void setScoreId(String scoreId) {
		this.scoreId = scoreId;
	}

	public String getBefore() {
		return before;
	}

	public void setBefore(String before) {
		this.before = before;
	}

	public String getAftter() {
		return aftter;
	}

	public void setAftter(String aftter) {
		this.aftter = aftter;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Visit(String clientId, String scoreId, String before, String aftter, Date date) {
		super();
		this.clientId = clientId;
		this.scoreId = scoreId;
		this.before = before;
		this.aftter = aftter;
		this.date = date;
	}

	private String clientId;
	private String scoreId;
	private String before;
	private String aftter;
	private Date date;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getScoreId() {
		return scoreId;
	}
}
