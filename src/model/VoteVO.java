package model;

public class VoteVO {
	private int gno; //경기번호
	private int avote, bvote; // A팀득표, B팀득표
	
	public VoteVO(int gno, int avote, int bvote) {
		this.gno = gno;
		this.avote = avote;
		this.bvote = bvote;
	}

	public int getGno() {
		return gno;
	}

	public void setGno(int gno) {
		this.gno = gno;
	}

	public int getAvote() {
		return avote;
	}

	public void setAvote(int avote) {
		this.avote = avote;
	}

	public int getBvote() {
		return bvote;
	}

	public void setBvote(int bvote) {
		this.bvote = bvote;
	}
	
}
