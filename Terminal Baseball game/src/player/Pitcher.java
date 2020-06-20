/*
 * Writer 	 : �̿켺
 * StudentID : 20155758
 * Program 	 : GameApp Ŭ�������� ����� ���� ��ü�� �����ϴ� Ŭ����
 */

package player;

public class Pitcher extends Player{
	private int IP = 0; // ���� �̴�(Innings Pitched)
	private int BP = 0;	// ������(Ball Pitched)
	private int W = 0; 	// �¸� Ƚ��(Wins)
	private int L = 0; 	// ���� Ƚ��(Loses)
	private float ERA = 0; // �����(Earned Run Average)
	private int BB = 0; // ����(Base on Balls)
	private int K = 0; 	// ����
	
	// ��� �� ������ ����ϴ� �޼���
	public void showRecord() {
		System.out.println("| ���� | "+this.name +" |\n| ������ | "+this.BP+"�� |");
		System.out.println("| Ż���� | "+this.K+"�� |");
		System.out.println("_____________________\n");
	}
	
	// ��� ���� �� �׳��� ������ �����ִ� �޼���
	public void showAllRecord() {
		System.out.println(this.name+"   "+this.BP+"     "+this.K);
	}
	
	// �������� ������Ű�� �޼���
	public void incBall() {
		this.BP++;
	}
	
	// Ż������ ������Ű�� �޼���
	public void incK() {
		this.K++;
	}

}
