package model;

public class TeamVO {
	private int tcode,twin, tdraw, tlose, tscore, tconceded; // 팀 고유번호 ,승, 무, 패, 득점, 실점
	private String tname; // 팀명
	private int gd; // 득실차
	
	public int getGd() {
		return gd;
	}
	public void setGd(int gd) {
		this.gd = gd;
	}
	public int getTcode() {
		return tcode;
	}
	public void setTcode(int tcode) {
		this.tcode = tcode;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tName) {
		this.tname = tName;
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
	public int getTconceded() {
		return tconceded;
	}
	public void setTconceded(int tconceded) {
		this.tconceded = tconceded;
	}	
	
}
