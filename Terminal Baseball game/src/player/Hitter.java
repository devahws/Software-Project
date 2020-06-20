/*
 * Writer 	 : �̿켺
 * StudentID : 20155758
 * Program 	 : GameApp Ŭ�������� ����� Ÿ�� ��ü�� �����ϴ� Ŭ����
 */

package player;

public class Hitter extends Player{
	private int[] H = new int[4]; // ��Ÿ(Hits) H[0]: 1��Ÿ, H[1]: 2��Ÿ, H[2]:3��Ÿ, H[3]:Ȩ��
	private int TH = 0; // ��ü ��Ÿ ����(Total Hits)
 	private int AB = 0; // Ÿ��(At Bats)
 	private int SB = 0; // ����(Stolen Base)
	private float AVG = 0; // Ÿ��(Batting Average)
	
	// ��Ÿ�� ���� �� ȣ���ϴ� �Լ�
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
		this.TH++;	// ��ü ��Ÿ ����
		this.AB++;	// ��ü Ÿ�� ����
		calAVG();	// Ÿ�� ���
	}
	
	// �ƿ��� ���� �� ȣ���ϴ� �Լ�
	public void out() {
		this.AB++;	// ��ü Ÿ�� ����
		calAVG();	// Ÿ�� ���
	}
	
	// Ÿ���� ����ϴ� �Լ�
	public void calAVG() {			// Ÿ���� ����ϴ� �Լ�
		this.AVG = this.TH/this.AB; // Ÿ�� = ��ü ��Ÿ �� / ��ü Ÿ�� ��
	}
	
	// Ÿ���� �� �� ����� �����ִ� �Լ�
	public void showRecord() {
		System.out.println(this.name+" | \n| Ÿ��     : "+(Math.round(this.AVG*1000)/1000.0)+"  |\n| ���ϱ�� : "+this.TH+"��Ÿ / "+this.AB+"Ÿ�� |");
	}
	
	// ��� ���� �� �׳��� ������ �����ִ� �Լ�
	public void showAllRecord() {
		System.out.println(this.name+"  "+(Math.round(this.AVG*1000)/1000.0)+"  "+this.TH);
	}
}
