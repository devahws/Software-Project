/*
 * Writer : �̿켺
 * StudentID : 20155758
 * Program: Hitter Ŭ������ Pitcher Ŭ������ ��ӹ޴� ���� Ŭ����
 */

package player;

public class Player {
	protected String name;		// ������ �̸�
	protected String position;	// ������ ������
	
	// ������ �̸��� �����ϴ� �޼���
	public void setName(String new_name) {
		this.name = new_name;
	}
	
	// ������ �������� ���ϴ� �޼���
	public void setPosition(String pos) {
		this.position = pos;
	}
	
	// ������ �̸��� �������� ����ϴ� �޼���
	public void print() {
		System.out.println(this.name+" "+this.position);
	}
}


