package model;

public class TeamVO {
	private int tcode; // 팀 고유번호
	private String tName; // 팀명
	private int twin, tdraw, tlose, tscore, conceded; // 승, 무, 패, 득점, 실점
	
	public TeamVO(int tcode, String tName, int twin, int tdraw, int tlose, int tscore, int conceded) {
		this.tcode = tcode;
		this.tName = tName;
		this.twin = twin;
		this.tdraw = tdraw;
		this.tlose = tlose;
		this.tscore = tscore;
		this.conceded = conceded;
	}
	public int getTcode() {
		return tcode;
	}
	public void setTcode(int tcode) {
		this.tcode = tcode;
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}
	public int getTwin() {
		return twin;
	}
	public void setTwin(int twin) {
		this.twin = twin;
	}
	public int getTdraw() {
		return tdraw;
	}
	public void setTdraw(int tdraw) {
		this.tdraw = tdraw;
	}
	public int getTlose() {
		return tlose;
	}
	public void setTlose(int tlose) {
		this.tlose = tlose;
	}
	public int getTscore() {
		return tscore;
	}
	public void setTscore(int tscore) {
		this.tscore = tscore;
	}
	public int getConceded() {
		return conceded;
	}
	public void setConceded(int conceded) {
		this.conceded = conceded;
	}
	
	
	
}
