/*
 * Program: Hitter 클래스와 Pitcher 클래스가 상속받는 슈퍼 클래스
 */

package player;

public class Player {
	protected String name;		// 선수의 이름
	protected String position;	// 선수의 포지션
	
	// 선수의 이름을 저장하는 메서드
	public void setName(String new_name) {
		this.name = new_name;
	}
	
	// 선수의 포지션을 정하는 메서드
	public void setPosition(String pos) {
		this.position = pos;
	}
	
	// 선수의 이름과 포지션을 출력하는 메서드
	public void print() {
		System.out.println(this.name+" "+this.position);
	}
}


