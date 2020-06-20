/*
 * Program 	 : GameApp 클래스에서 사용할 타자 객체를 정의하는 클래스
 */

package player;

public class Hitter extends Player{
	private int[] H = new int[4]; // 안타(Hits) H[0]: 1루타, H[1]: 2루타, H[2]:3루타, H[3]:홈런
	private int TH = 0; // 전체 안타 개수(Total Hits)
 	private int AB = 0; // 타수(At Bats)
 	private int SB = 0; // 도루(Stolen Base)
	private float AVG = 0; // 타율(Batting Average)
	
	// 안타를 쳤을 때 호출하는 함수
	public void hit(int hit_flag) {	
		switch(hit_flag) {
			case 1:
				this.H[0]++;
			case 2:
				this.H[1]++;
			case 3:
				this.H[2]++;
			case 4:
				this.H[3]++;
		}
		this.TH++;	// 전체 안타 증가
		this.AB++;	// 전체 타수 증가
		calAVG();	// 타율 계산
	}
	
	// 아웃이 됐을 때 호출하는 함수
	public void out() {
		this.AB++;	// 전체 타수 증가
		calAVG();	// 타율 계산
	}
	
	// 타율을 계산하는 함수
	public void calAVG() {			// 타율을 계산하는 함수
		this.AVG = this.TH/this.AB; // 타율 = 전체 안타 수 / 전체 타율 수
	}
	
	// 타석에 들어설 때 기록을 보여주는 함수
	public void showRecord() {
		System.out.println(this.name+" | \n| 타율     : "+(Math.round(this.AVG*1000)/1000.0)+"  |\n| 당일기록 : "+this.TH+"안타 / "+this.AB+"타수 |");
	}
	
	// 경기 종료 후 그날의 성적을 보여주는 함수
	public void showAllRecord() {
		System.out.println(this.name+"  "+(Math.round(this.AVG*1000)/1000.0)+"  "+this.TH);
	}
}
