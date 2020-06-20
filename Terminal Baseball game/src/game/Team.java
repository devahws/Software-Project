/*
 * Writer: 이우성
 * StudentID: 20155758
 * Program: GameApp 클래스에서 사용할 Team 객체를 정의하는 클래스
 */

package game;
import player.*;

public class Team {
	private Hitter[] hitter = new Hitter[9];	// 9명의 타자 객체를 배열로 관리
	private Pitcher pitcher = new Pitcher();	// 투수 객체 
	private int hitterTurn = 0; 				// 타석에 들어갈 타자의 순서

	//// 타자 메서드
	// 타순별로 이름과 포지션을 저장하는 메서드
	public void setHitter(String name, String pos, int i) {
		this.hitter[i] = new Hitter();
		this.hitter[i].setName(name);
		this.hitter[i].setPosition(pos);
	};
	
	// 경기 중 타자의 기록을 나타내는 메서드
	public void showHitterRecord() {
		// 타순 | 이름 | 당일 성적 출력
		System.out.print("| "+Integer.toString(this.hitterTurn+1)+"번타자 | ");
		this.hitter[this.hitterTurn].showRecord();
	}
	
	// 타자가 친 안타를 기록하는 메서드
	public void hit(int hit_flag) {			
		this.hitter[this.hitterTurn].hit(hit_flag);
	}
	
	// 타자의 아웃을 기록하는 메서드
	public void out() {
		this.hitter[this.hitterTurn].out();
	}
	
	// 타자의 타순을 증가시키는 메서드
	public void incHitterturn() {
		this.hitterTurn = (this.hitterTurn+1)%9;
	}
	
	//// 투수 메서드
	// 투수의 이름과 포지션을 저장하는 메서드
	public void setPitcher(String name, String pos) {
		this.pitcher.setName(name);
		this.pitcher.setPosition(pos);
	}
	
	// 투수의 기록을 출력하는 메서드
	public void showPitcher() {
		this.pitcher.showRecord();
	}
	
	// 투수의 탈삼진을 기록하는 메서드
	public void increaseK() {
		this.pitcher.incK();
	}
	
	// 투수의 투구수를 증가시키는 메서드
	public void incBalls() {
		this.pitcher.incBall();
	}
	
	//// 팀을 출력하는 메서드 
	// 팀 전체를 출력
	public void printPlayer(String teamname) {
		System.out.println("당신의 팀  < "+teamname+" >");
		System.out.println("__________________");
		System.out.println("타순  이름   포지션");
		System.out.println("------------------");
		for(int i=0; i<9; i++) {
			System.out.print(Integer.toString(i+1)+"번 ");
			hitter[i].print();
		}
		System.out.println("__________________");
		System.out.println("선발투수");
		System.out.println("------------------");
		pitcher.print();
		System.out.println("__________________\n");
	}
	
	// 경기 종료 후 성적을 출력하는 메서드
	public void printRecord() {
		System.out.println("__________________");
		System.out.println("           최종기록        ");
		System.out.println("__________________");
		System.out.println("타순  이름   타율    안타");
		System.out.println("------------------");
		for(int i=0; i<9; i++) {
			System.out.print(Integer.toString(i+1)+"번 ");
			hitter[i].showAllRecord();
		}
		System.out.println("__________________");
		System.out.println(" 이름    투구수   탈삼진");
		pitcher.showAllRecord();
		System.out.println("__________________\n");
	}
}
