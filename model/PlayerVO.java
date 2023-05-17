package model;

public class PlayerVO {
	private int pno, tcode, uno; // 선수고유번호, 팀고유번호, 등번호
	private String pname, position; // 선수이름, 포지션
	private int height, weight, age; // 키, 몸무게, 나이

	public int getPno() {
		return pno;
	}

	public void setPno(int pno) {
		this.pno = pno;
	}

	public int getTcode() {
		return tcode;
	}

	public void setTcode(int tcode) {
		this.tcode = tcode;
	}

	public int getUno() {
		return uno;
	}

	public void setUno(int uno) {
		this.uno = uno;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
}
