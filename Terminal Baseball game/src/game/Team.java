/*
 * Writer: �̿켺
 * StudentID: 20155758
 * Program: GameApp Ŭ�������� ����� Team ��ü�� �����ϴ� Ŭ����
 */

package game;
import player.*;

public class Team {
	private Hitter[] hitter = new Hitter[9];	// 9���� Ÿ�� ��ü�� �迭�� ����
	private Pitcher pitcher = new Pitcher();	// ���� ��ü 
	private int hitterTurn = 0; 				// Ÿ���� �� Ÿ���� ����

	//// Ÿ�� �޼���
	// Ÿ������ �̸��� �������� �����ϴ� �޼���
	public void setHitter(String name, String pos, int i) {
		this.hitter[i] = new Hitter();
		this.hitter[i].setName(name);
		this.hitter[i].setPosition(pos);
	};
	
	// ��� �� Ÿ���� ����� ��Ÿ���� �޼���
	public void showHitterRecord() {
		// Ÿ�� | �̸� | ���� ���� ���
		System.out.print("| "+Integer.toString(this.hitterTurn+1)+"��Ÿ�� | ");
		this.hitter[this.hitterTurn].showRecord();
	}
	
	// Ÿ�ڰ� ģ ��Ÿ�� ����ϴ� �޼���
	public void hit(int hit_flag) {			
		this.hitter[this.hitterTurn].hit(hit_flag);
	}
	
	// Ÿ���� �ƿ��� ����ϴ� �޼���
	public void out() {
		this.hitter[this.hitterTurn].out();
	}
	
	// Ÿ���� Ÿ���� ������Ű�� �޼���
	public void incHitterturn() {
		this.hitterTurn = (this.hitterTurn+1)%9;
	}
	
	//// ���� �޼���
	// ������ �̸��� �������� �����ϴ� �޼���
	public void setPitcher(String name, String pos) {
		this.pitcher.setName(name);
		this.pitcher.setPosition(pos);
	}
	
	// ������ ����� ����ϴ� �޼���
	public void showPitcher() {
		this.pitcher.showRecord();
	}
	
	// ������ Ż������ ����ϴ� �޼���
	public void increaseK() {
		this.pitcher.incK();
	}
	
	// ������ �������� ������Ű�� �޼���
	public void incBalls() {
		this.pitcher.incBall();
	}
	
	//// ���� ����ϴ� �޼��� 
	// �� ��ü�� ���
	public void printPlayer(String teamname) {
		System.out.println("����� ��  < "+teamname+" >");
		System.out.println("__________________");
		System.out.println("Ÿ��  �̸�   ������");
		System.out.println("------------------");
		for(int i=0; i<9; i++) {
			System.out.print(Integer.toString(i+1)+"�� ");
			hitter[i].print();
		}
		System.out.println("__________________");
		System.out.println("��������");
		System.out.println("------------------");
		pitcher.print();
		System.out.println("__________________\n");
	}
	
	// ��� ���� �� ������ ����ϴ� �޼���
	public void printRecord() {
		System.out.println("__________________");
		System.out.println("           �������        ");
		System.out.println("__________________");
		System.out.println("Ÿ��  �̸�   Ÿ��    ��Ÿ");
		System.out.println("------------------");
		for(int i=0; i<9; i++) {
			System.out.print(Integer.toString(i+1)+"�� ");
			hitter[i].showAllRecord();
		}
		System.out.println("__________________");
		System.out.println(" �̸�    ������   Ż����");
		pitcher.showAllRecord();
		System.out.println("__________________\n");
	}
}
