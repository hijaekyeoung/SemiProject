package model;

public class PlayerStatVO {
	
	public final String ClassName = "PlayerStat";
	
	private int pno; // 선수고유번호
	private String pname, tname; // 선수이름, 팀명
	private int gcount, goal, assists, shots, // 출전경기수, 득점, 도움, 슈팅
	shot_on_goal, foul, ycard, rcard, offside; // 유효슈팅, 파울, 경고, 퇴장, 오프사이드
	
//	public PlayerStatVO(int pno, String pname, String tname, int gcount, int goal, int assists, int shots,
//			int shot_on_goal, int foul, int ycard, int rcard, int offside) {
//		this.pno = pno;
//		this.pname = pname;
//		this.tname = tname;
//		this.gcount = gcount;
//		this.goal = goal;
//		this.assists = assists;
//		this.shots = shots;
//		this.shot_on_goal = shot_on_goal;
//		this.foul = foul;
//		this.ycard = ycard;
//		this.rcard = rcard;
//		this.offside = offside;
//	}

	public int getPno() {
		return pno;
	}

	public void setPno(int pno) {
		this.pno = pno;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public int getGcount() {
		return gcount;
	}

	public void setGcount(int gcount) {
		this.gcount = gcount;
	}

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public int getAssists() {
		return assists;
	}

	public void setAssists(int assists) {
		this.assists = assists;
	}

	public int getShots() {
		return shots;
	}

	public void setShots(int shots) {
		this.shots = shots;
	}

	public int getShot_on_goal() {
		return shot_on_goal;
	}

	public void setShot_on_goal(int shot_on_goal) {
		this.shot_on_goal = shot_on_goal;
	}

	public int getFoul() {
		return foul;
	}

	public void setFoul(int foul) {
		this.foul = foul;
	}

	public int getYcard() {
		return ycard;
	}

	public void setYcard(int ycard) {
		this.ycard = ycard;
	}

	public int getRcard() {
		return rcard;
	}

	public void setRcard(int rcard) {
		this.rcard = rcard;
	}

	public int getOffside() {
		return offside;
	}

	public void setOffside(int offside) {
		this.offside = offside;
	}
	
	
}
