package model;

public class GameResultVO {
	private int gno; // 경기번호
	private String gdate, ateam; // 경기날짜, A팀
	private int ascore; // A팀 득점
	private String bteam; //B팀
	private int bscore; // A팀 득점

	public GameResultVO(int gno, String gdate, String ateam, int ascore, String bteam, int bscore) {
		this.gno = gno;
		this.gdate = gdate;
		this.ateam = ateam;
		this.ascore = ascore;
		this.bteam = bteam;
		this.bscore = bscore;
	}

	public int getGno() {
		return gno;
	}

	public void setGno(int gno) {
		this.gno = gno;
	}

	public String getGdate() {
		return gdate;
	}

	public void setGdate(String gdate) {
		this.gdate = gdate;
	}

	public String getAteam() {
		return ateam;
	}

	public void setAteam(String ateam) {
		this.ateam = ateam;
	}

	public int getAscore() {
		return ascore;
	}

	public void setAscore(int ascore) {
		this.ascore = ascore;
	}

	public String getBteam() {
		return bteam;
	}

	public void setBteam(String bteam) {
		this.bteam = bteam;
	}

	public int getBscore() {
		return bscore;
	}

	public void setBscore(int bscore) {
		this.bscore = bscore;
	}
	
}
