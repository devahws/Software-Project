/*
 * Writer 	 : 이우성
 * StudentID : 20155758
 * Program 	 : GameApp 클래스에서 사용할 투수 객체를 정의하는 클래스
 */

package player;

public class Pitcher extends Player{
	private int IP = 0; // 투구 이닝(Innings Pitched)
	private int BP = 0;	// 투구수(Ball Pitched)
	private int W = 0; 	// 승리 횟수(Wins)
	private int L = 0; 	// 패전 횟수(Loses)
	private float ERA = 0; // 방어율(Earned Run Average)
	private int BB = 0; // 볼넷(Base on Balls)
	private int K = 0; 	// 삼진
	
	// 경기 중 성적을 출력하는 메서드
	public void showRecord() {
		System.out.println("| 투수 | "+this.name +" |\n| 투구수 | "+this.BP+"개 |");
		System.out.println("| 탈삼진 | "+this.K+"개 |");
		System.out.println("_____________________\n");
	}
	
	// 경기 종료 후 그날의 성적을 보여주는 메서드
	public void showAllRecord() {
		System.out.println(this.name+"   "+this.BP+"     "+this.K);
	}
	
	// 투구수를 증가시키는 메서드
	public void incBall() {
		this.BP++;
	}
	
	// 탈삼진을 증가시키는 메서드
	public void incK() {
		this.K++;
	}

}
