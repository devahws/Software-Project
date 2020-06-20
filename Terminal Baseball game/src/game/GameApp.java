/*
 * Writer 	: �̿켺
 * StudentID: 20155758
 * Program	: �߱� ������ ����Ǵ� ���� ���α׷�
 * CAUTION 	: �ܼ� �Է¿��� ������ ���� �Է¸� �����մϴ�!
 * How To Play
 * 	1) ������ ���۵Ǹ� �ܼ�â�� ���� 1 �Ǵ� 2�� �Է��ؼ� ���� �����մϴ�.
 * 	2) ���� ���������� ������ ������ �̴��� �Է��մϴ�.
 * 	3) 1�� ���� �����ϸ� ����, 2�� ���� �����ϸ� �İ��� �ϰ� �˴ϴ�.
 * 	** Attack
 * 		1) �ܼ�â�� ���� 1�� �Է��ϸ� ����, 2�� �Է��ϸ� �������� �ʰ� ��ٸ��ϴ�.
 * 		2) ����(1��)�� �����ϸ� ���� ���� ������ Ȯ���� ���� �꽺��, ����, �Ẽ, ��Ÿ, 2��Ÿ, 3��Ÿ, Ȩ���� �����˴ϴ�.
 * 		3) ��ٸ��� ��(2��) �����ϸ� ���� ���� ������ Ȯ���� ���� ��Ʈ����ũ, �Ǵ� ���� �����˴ϴ�.
 * 		4) ���� �߱� ��Ģ�� ���������� �ƿ�ī��Ʈ�� 3���� �Ǹ� ���� ��尡 ����ǰ� ���� ���� �ٲ�ϴ�.
 * 	** Defense
 * 		1) �ܼ�â���� ���� 1 ~ 3 �� �ϳ��� �Է��ؼ� ������ �����մϴ�.
 * 		2) �������δ� ���� 1 ~ 9 �� �ϳ��� �Է��ؼ� ��Ʈ����ũ ������ ���� ������ �����մϴ�.
 * 		3) ������ ��Ʈ����ũ ���� �����ϸ� ���� ���� ���ǵ� ������ ���� ��, ��Ʈ����ũ, ����, �Ẽ, ��Ÿ, 2��Ÿ, 3��Ÿ, Ȩ���� �����˴ϴ�.
 * 		4) ���� �߱� ��Ģ�� ���������� �ƿ�ī��Ʈ�� 3���� �Ǹ� ���� ��尡 ����ǰ� ���� ���� �ٲ�ϴ�.
 * *** Etc
 * 		1) ���ھ�� �̴��� ��ü�� ��, �׸��� ������ ��ȭ�� ���� �� ��µ˴ϴ�.
 * 		2) ��Ⱑ ����� �ڿ��� ����ڰ� ������ �� �������� ���� ����� ��µ˴ϴ�.
 */

package game;
import java.io.*;
import java.util.*;

import player.*;

public class GameApp {
	private Team team1;	// ��ȭ �̱۽�
	private Team team2;	// ���� Ʈ����
	
	private int score_team1 = 0;	// ��ȭ �̱۽��� ����
	private int score_team2 = 0;	// ���� Ʈ������ ����
	
	private int inning;				// ��⿡�� ������ �� �̴�
	int gameinning = 1;				// ���� �������� �̴�
	
	private int[] BASE = new int[3];	// ���ӿ��� ����� ���̽�
	private boolean teamFlag = true;  	// �� ����
	// true -> ��ȭ �̱۽� (������. �� ����)
	// false -> ���� Ʈ����(Ȩ��. �� ����)
	private boolean hitFlag = false;	// ��Ÿ �߻��ϸ� ��ȭ
	private boolean outFlag = false;	// �ƿ� �߻��ϸ� ��ȭ
	private boolean onBaseFlag = false; // ������ ��� �߻��ϸ� ��ȭ
	private boolean kSign = false; 		// ���� �߻��ϸ� ��ȭ
	private boolean scoreFlag = false; 	// ������ �����ϸ� ��ȭ
	
	private boolean myAttack = true;	// ������� �������ʿ� ��ǻ���� �������� ����
	
	int OC = 0; 					// �ƿ� ī��Ʈ(Out Counts)
	int[] BC = new int[2]; 			// �� ī��Ʈ(Ball Counts) 
	// BC[0] = ��Ʈ����ũ ī��Ʈ
	// BC[1] = �� ī��Ʈ
	
	//// Team ��ü�� �����ϰ� ������ �о�ͼ� �����ϴ� �޼���
	// �Ű������� �޴� �̸��� ���Ͽ� ����� ���� ����� �����ͼ� ����
	public Team saveTeam(String filename) throws IOException {	
		Team team = new Team(); // Team ��ü ����
		
		File Team = new File(filename);
		FileInputStream fin = new FileInputStream(Team);
		InputStreamReader in = new InputStreamReader(fin, "utf-8");
		BufferedReader br = new BufferedReader(in);
		for(int i=0; i<9; i++) {		// 1��Ÿ�ں��� 9��Ÿ�ڱ��� ����
			String line = br.readLine();
			StringTokenizer st = new StringTokenizer(line, " ");
			team.setHitter(st.nextToken(), st.nextToken(), i);
		}
		String line = br.readLine();
		StringTokenizer st = new StringTokenizer(line, " ");
		team.setPitcher(st.nextToken(), st.nextToken());	// ���� ����
		
		fin.close();
		in.close();
		br.close();
		
		return team;	
	}
	
	// ���� ������ �����ִ� �޼���
	public void showScore() {
		System.out.println("< ��ȭ �̱۽� |"+Integer.toString(this.score_team1)+"|:|"+Integer.toString(this.score_team2)+"| ���� Ʈ���� >\n");
	}
	
	// ���� �ƿ� ī��Ʈ, �� ī��Ʈ, ���̽��� ���ڸ� �����ִ� �޼���
	public void showCountBase() {
		// �ƿ� ī��Ʈ
		System.out.print("O: ");
		if(this.OC == 0)
			System.out.print("�� �� ");
		else if(this.OC == 1)
			System.out.print("�� �� ");
		else if(this.OC == 2)
			System.out.print("�� �� ");
		// 2�� ���̽�
		if(this.BASE[1] == 0)	// ���̽��� ������� ��
			System.out.println("          ��");
		else					// ���̽��� ���ڰ� ���� ��
			System.out.println("          ��");
		// ��Ʈ����ũ ī��Ʈ
		System.out.print("S: ");
		if(this.BC[0] == 0)
			System.out.print("�� ��\n");
		else if(this.BC[0] == 1)
			System.out.print("�� ��\n");
		else if(this.BC[0] == 2)
			System.out.print("�� ��\n");
		// �� ī��Ʈ
		System.out.print("B: ");
		if(this.BC[1] == 0)
			System.out.print("�� �� ��  ");
		else if(this.BC[1] == 1)
			System.out.print("�� �� ��  ");
		else if(this.BC[1] == 2)
			System.out.print("�� �� ��  ");
		else if(this.BC[1] == 3)
			System.out.print("�� �� ��  ");
		// 3�� ���̽�
		if(this.BASE[2] == 0)
			System.out.print("��   ");
		else
			System.out.print("��   ");
		// 1�� ���̽�
		if(this.BASE[0] == 0)
			System.out.println("   ��");
		else
			System.out.println("   ��");
	}
	
	public void newInning() {
		this.outFlag = false;		// �ƿ� �÷��׸� �ʱ�ȭ
		this.onBaseFlag = false; 	// ��� �÷��׸� �ʱ�ȭ
		this.OC = 0;				// �ƿ�ī��Ʈ�� �ʱ�ȭ
		this.BC[0] = 0;				// ��Ʈ����ũ ī��Ʈ �ʱ�ȭ
		this.BC[1] = 0;				// �� ī��Ʈ �ʱ�ȭ
		for(int i=0; i<3; i++)		// ���̽��� �ʱ�ȭ
			this.BASE[i] = 0;
	}

	//// ���̽� ���� �޼���
	// 1. getBase() : ���� ���̽��� ���ڰ� �� �� �ִ��� ����. Ȩ�� �̺�Ʈ�� �߻����� �� ���
	public int getBase() {
		int r = 0;
		for(int i=0; i<3; i++)
			if(this.BASE[i] != 0)
				r++;	
		return r;
	}
	
	// 2. isFullB() : ���� ���̽��� �� á���� Ȯ���ϴ� �޼���
	public boolean isFullB() {
		if(this.BASE[0] == 1 && this.BASE[1] == 1 && this.BASE[2] == 1)
			return true;
		else
			return false;
	}
	
	// 3. emptyBase() : ���̽��� ��� ���� �޼���. Ȩ�� �̺�Ʈ �߻�, �̴��� ����� �� ���
	public void emptyBase() {
		for(int i=0; i<3; i++)
			this.BASE[i] = 0;
	}
	
	// 4. setBase() : ��Ÿ, ������ �߻����� �� ���ڸ� �ű�� ������ �߰��ϴ� �޼���
	public void setBase(int hit) {
		int plusScore = 0;	// ���̽� �̵����� �߰��� ����
		
		if(hit == 0) {			// 0. ����
			if(this.isFullB())	// ���̽��� �� á�� ��
				plusScore++;	// ������ 1�� �߰�
			else if(this.BASE[0] == 1) {	// 1�翡 ���ڰ� �ְ�
				if(this.BASE[1] == 1)		// 2�翡�� ���ڰ� ���� ��
					for(int i=0; i<3; i++)	// ���̽��� ���ڸ� ��� ä����
						this.BASE[i] = 1;
				else { 						// 1�翡�� ���ڰ� �ְ� 2�翡�� ���ڰ� ���� ��
					for(int i=0; i<2; i++)	// 2������� ���ڸ� ä����
						this.BASE[i] = 1;
				}
			}
			else if(this.BASE[0] == 0) {	// 1�簡 ����� ��
				this.BASE[0] = 1;			// 1�縦 ä��
			}
			System.out.println("\n $ �������� ����մϴ�.");
		}
		else if(hit == 1) {		  // 1. �Ϲ� ��Ÿ
			if(this.BASE[2] == 1) // 3�翡 ���ڰ� ���� ��
				plusScore++; 	  // 1�� ����
			this.BASE[2] = this.BASE[1]; //	 2�� ���ڸ� 3���
			this.BASE[1] = this.BASE[0]; //	 1�� ���ڸ� 2���
			this.BASE[0] = 1;			 //  1�翡 ���ڸ� ä��
			System.out.println("\n $ ��Ÿ�Դϴ�!");
		}
		else if(hit==2) {			// 2. 2��Ÿ
			if(this.BASE[2] == 1)	// 3�翡 ���ڰ� �־��ٸ�
				plusScore++;		// +1����
			if(this.BASE[1] == 1)	// 2�翡 ���ڰ� �־��ٸ� 
				plusScore++;		// +1����
			this.BASE[2] = this.BASE[0]; // 1�� ���ڸ� 3���
			this.BASE[1] = 1;			 // 2��Ÿ�� ģ Ÿ�ڸ� 2�翡 ��ġ
			this.BASE[0] = 0;		 	 // 1�縦 ����
			System.out.println("\n $ 2��Ÿ�Դϴ�!");
		}
		else if(hit == 3) {				// 3. 3��Ÿ -> ����� ��� ���ڸ� �ҷ�����
			for(int i=0; i<3; i++) {
				if(this.BASE[i] == 1) {	// �翡 ���ڰ� �����ϸ� +1 ����
					plusScore++;
					this.BASE[i] = 0;	// �縦 �����
				}
			}
			this.BASE[2] = 1; 			// Ÿ�ڸ� 3�翡 ��ġ
			System.out.println("\n $ 3��Ÿ�Դϴ�!");
		}
		else if(hit == 4) {			// 4. Ȩ��
			plusScore += getBase();	// ��� �ִ� ���� ��� ����
			plusScore++;			// Ÿ�ڱ��� ����
			for(int i=0; i<3; i++)
				this.BASE[i] = 0; 	// ��� �縦 ����
			System.out.println("\n $ Ȩ���Դϴ�!");
		}
		if(plusScore != 0) {						// ������ �������� ��
			if(this.teamFlag == true) {				// (1) ����ڰ� ��ȭ �̱۽��� �������� ��
				if(this.myAttack == true)			// (1) - 1. ������� ���� ����
					this.score_team1 += plusScore;	// 			��ȭ �̱۽��� ���� ����
				else								// (1) - 2. ��ǻ���� ���� ����
					this.score_team2 += plusScore;	// 			���� Ʈ������ ���� ����
			}
			else if(this.teamFlag == false) { 		// (2) ����ڰ� ���� Ʈ������ �������� ��
				if(this.myAttack == true)			// (2) - 1. ������� ���� ����
					this.score_team2 += plusScore;	//			���� Ʈ������ ���� ����
				else 								// (2) - 2. ��ǻ���� ���� ����	
					this.score_team1 += plusScore;	//			��ȭ �̱۽��� ���� ����
			}
			this.scoreFlag = true;
		}
		this.onBaseFlag = true;					// ���ڰ� ��������Ƿ� onBaseFlag ��ȭ�� ���� Ÿ�� rotate
	}

	//// ������ ���õ� �޼���
	// 1. incBall() : ������ ���� ������ �� �߻��ϴ� �޼���
	public void incBall() {	
		this.BC[1]++;			// �� ī��Ʈ �ϳ� ����
		if(this.BC[1] > 3) {	// ���� 4���� ���� ��
			this.BC[1] = 0;		// ���� 0���� �ʱ�ȭ
			this.setBase(0); 	// ���� �̺�Ʈ ȣ��
		}
	}
	
	// 2. incStrike() : ������ ��Ʈ����ũ�� ������ �� �߻��ϴ� �޼���
	public void incStrike() {	
		this.BC[0]++; 			// ��Ʈ����ũ�� �ϳ� ����
		if(this.BC[0] > 2) {	// ��Ʈ����ũ�� 3���̸�
			System.out.println(" $ ���� �ƿ��Դϴ�.");
			this.BC[0] = 0;		// ��Ʈ����ũ�� 0���� �ʱ�ȭ
			this.BC[1] = 0;		// �� ī��Ʈ�� 0���� �ʱ�ȭ
			this.OC++;			// �ƿ�ī��Ʈ�� �ϳ� ����
			this.outFlag = true;// outFlag�� true�� ��ȭ���Ѽ� Ÿ�� rotate Ȥ�� �̴� ����
			this.kSign = true;	// kSign�� true�� ��ȭ���Ѽ� ������ Ż���� ��Ͽ� �߰�
		}
	}
	
	// 3. calcStrike() : ������� Defense ��忡�� ������ ���� ������, Ÿ�ڰ� �������� �ʾ��� �� ��Ʈ����ũ�� �� Ȯ���� �����ϴ� �޼��� 
	public double calcStrike(int com1, int com2) {
		double type = 0;	// ���� Ÿ��
		double pos = 0;		// ��Ʈ����ũ �� ��ġ
		
		// commman1 ���� �ɼ�
		if(com1 == 1)		// 1. ����
			type = 0.9;		// 0.9�� Ȯ���� 
		else if(com1 == 2)	// 2. Ŀ��
			type = 0.85;	// 0.85�� Ȯ����
		else if(com1 == 3)	// 3. ü������
			type = 0.7;		// 0.7�� Ȯ����
		
		// command2 ��Ʈ����ũ�� �ɼ�
		if(com2 % 2 == 0) // ��Ʈ����ũ�� 2, 4, 6, 8 �� �ϳ��� ��
			pos = 0.85;	  // 0.85�� Ȯ����
		else if(com2 % 2 == 1 && com2 != 5) // ��Ʈ����ũ�� 1, 3, 7, 9 �� �ϳ��� ��
			pos = 0.7;	  					// 0.7�� Ȯ����
		else if(com2 == 5)	// ��Ʈ����ũ�� �� ��� 5�� �� ��
			pos = 0.95; 	// 0.95�� Ȯ����
		
		return type*pos; 	// ������ ��Ʈ����ũ�� �ɼ��� ���� ���� Ȯ���� ����
	}
	
	//// ���� ���� �޼���
	// 1. Attack() : ������� ���� ��� �޼���
	public void Attack() {
		this.myAttack = true;	// �÷��̾��� ���� ����
		Team team;		// Attack() �޼��忡�� �����ϴ� ��
		Team ComTeam;	// Attack() �޼��忡�� �����ϴ� ��
		if(this.teamFlag == true) {	// ����ڰ� ��ȭ �̱۽��� ����
			team = this.team1;
			ComTeam = this.team2;
		}
		else {						// ����ڰ� ���� Ʈ������ ����
			team = this.team2;
			ComTeam = this.team1;
		}
		// ���� ���� �ȳ����� ���
		if(this.teamFlag == true)
			System.out.println(Integer.toString(this.gameinning)+"ȸ�� ������ �����մϴ�.\n");
		else if(this.teamFlag == false)
			System.out.println(Integer.toString(this.gameinning)+"ȸ�� ������ �����մϴ�.\n");
		
		// ���ھ� ���
		this.showScore();	
		// ������ ������ �� ���̽��� �ʱ�ȭ
		for(int i=0; i<3; i++) {	
			this.BASE[i] = 0;
		}
	
		
		// �ƿ�ī��Ʈ 3���� ��� �� �� ���� ����
		while(this.OC < 3) {
			this.outFlag = false;		// �ƿ� �÷��׸� �ʱ�ȭ
			this.onBaseFlag = false; 	// ��� �÷��׸� �ʱ�ȭ
			
			// Ÿ�� ����
			ComTeam.showPitcher();		// ��� ���� ���� ���� ���
			team.showHitterRecord();	// Ÿ�� ���� ���
			
			BC[0] = 0; // ��Ʈ����ũ ī��Ʈ�� 0���� �ʱ�ȭ
			BC[1] = 0; // �� ī��Ʈ�� 0���� �ʱ�ȭ

			// (1) �ƿ��� �ǰų�(outFlag), (2) ��Ÿ �Ǵ� �������� ����ϸ�(onBaseFlag) ���� Ÿ�ڷ� ����
			while(this.outFlag == false && this.onBaseFlag == false) {
				this.showCountBase();	// ��ī��Ʈ�� �ƿ�ī��Ʈ ���
				
				// Ÿ�� �ɼ� 1) ����		2) ���Ѻ���
				System.out.print(" (1) ���� \n (2) ���Ѻ��� \n >> ");	
				Scanner scan = new Scanner(System.in);	// ����� ��� �Է¹���
				String command = scan.next();
				if(command.equals("1")) {				// (1) ������ �������� ��
					ComTeam.incBalls();		// ����� ������ ������ ����
					double rate = Math.random();		// random �Լ��� ���� ������ �ǻ糭���� �����ϴ� rate ����
					if(rate > 0.97) {   					// rate > 0.97 �� �� Ȩ�� �̺�Ʈ
						this.setBase(4);
						team.hit(4);	// team.hit() �޼��带 ���� Ÿ���� ��Ͽ� �߰�
					}
					else if(rate <= 0.97 && rate > 0.93) {	// 0.93 ~ rate ~ 0.97 �� �� 3��Ÿ �̺�Ʈ
						this.setBase(3);
						team.hit(3);	// team.hit() �޼��带 ���� Ÿ���� ��Ͽ� �߰�
					}
					else if(rate <= 0.93 && rate > 0.85) {	// 0.85 ~ rate ~ 0.93 �� �� 2��Ÿ �̺�Ʈ
						this.setBase(2);
						team.hit(2);	// team.hit() �޼��带 ���� Ÿ���� ��Ͽ� �߰�
					}
					else if(rate <= 0.85 && rate > 0.70) {	// 0.70 ~ rate ~ 0.85 �� �� ��Ÿ �̺�Ʈ
						this.setBase(1);
						team.hit(1);	// team.hit() �޼��带 ���� Ÿ���� ��Ͽ� �߰�
					}
					else if(rate <= 0.70 && rate > 0.55) {	// 0.55 ~ rate ~ 0.70 �� �� ���� �̺�Ʈ
						System.out.println("\n $ ���� �ƿ��Դϴ�.");
						this.OC++;				// �ƿ� ī��Ʈ �߰�
						this.outFlag = true;	// �ƿ� �÷��� ����
						team.out();				// team.out() �÷��׸� ���� Ÿ���� ��Ͽ� �߰�
					}
					else if(rate <= 0.55 && rate > 0.40) {	// 0.40 ~ rate ~ 0.55 �� �� ���� �̺�Ʈ
						System.out.println("\n $ �Ẽ �ƿ��Դϴ�.");
						this.OC++;				// �ƿ� ī��Ʈ �߰�
						this.outFlag = true;	// �ƿ� �÷��� ����
						team.out();				// team.out() �÷��׸� ���� Ÿ���� ��Ͽ� �߰�
					}
					else {						// rate < 0.40 �� �� �꽺��
						System.out.println("\n $ �꽺���Դϴ�.");
						this.incStrike(); 		// ��Ʈ����ũ ����
					}
								
				}
				else if(command.equals("2")) {	// (2) �������� �ʰ� ��ٸ��� �� �������� ��
					ComTeam.incBalls();		// ����� ������ ������ ����
					double rate = Math.random();	// random �Լ��� ���� ������ �ǻ糭���� �����ϴ� rate ����
					if(rate > 0.60) {				// rate > 0.60 �� �� ��Ʈ����ũ
						System.out.println("\n $ ��Ʈ����ũ�Դϴ�.");
						this.incStrike();			// ��Ʈ����ũ ����
					}
					else {  						// rate < 0.60 �� �� ��
						System.out.println("\n $ ���Դϴ�.");
						this.incBall();				// �� ����
					}
						
				}
				else {	// 1�̳� 2�� �ƴ� �ٸ� �Է��� ���� ��
					System.out.println(" $ �Է��� �ùٸ��� �ʽ��ϴ�. 1 Ȥ�� 2�� �Է����ּ���");
					continue;
				}
				System.out.println();
				
			}
			if(this.scoreFlag == true) {	// Ÿ�ڰ� ����� �� ������ ��ȭ�� �ִٸ� ���� ���
				this.showScore();
				this.scoreFlag = false;
			}
			if(this.kSign == true) {	// ���� �̺�Ʈ�� ���ؼ� �ƿ��� �߻��ߴٸ�
				team.out();				// �������� Ÿ�� �ƿ�ó��
				ComTeam.increaseK(); 	// ��ǻ�� ���� ������ Ż���� ����
				this.kSign = false;		// kSign�� �ٽ� false���·� ��������
			}
			// ����ڰ� Ȩ���̰� ������ �̴׿��� �������� �������� ��
			if(this.teamFlag == false && (this.gameinning == this.inning) && (this.score_team1 < this.score_team2))
				break;
			team.incHitterturn(); 		// ���� Ÿ������ ����
		}
		// �ƿ�ī��Ʈ�� 3�� �Ǿ��� ��
		if(this.teamFlag == true)
			System.out.println(Integer.toString(this.gameinning)+"ȸ�� ������ ����Ǿ����ϴ�.\n");
		else if(this.teamFlag == false)
			System.out.println(Integer.toString(this.gameinning)+"ȸ�� ������ ����Ǿ����ϴ�.\n");
		this.newInning(); 			// ���ο� �̴����� �ʱ�ȭ
		this.myAttack = false;
	}

	public void Defense() {
		this.myAttack = false; 		// ��ǻ���� ���� ������ ����
		if(this.teamFlag == true)	// ������� �� ���ÿ� ���� ���� ���� �޽��� ���
			System.out.println(Integer.toString(this.gameinning)+"ȸ�� ���� �����մϴ�.\n");
		else if(this.teamFlag == false)
			System.out.println(Integer.toString(this.gameinning)+"ȸ�� ���� �����մϴ�.\n");
		
		Team UserTeam;		// ������� ��
		Team ComTeam;		// ��ǻ���� ��
		
		if(this.teamFlag == true) {	// ��ȭ �̱۽� ����
			UserTeam = this.team1;
			ComTeam = this.team2;
		}
		else {						// ���� Ʈ���� ����
			UserTeam = this.team2;
			ComTeam = this.team1;
		}
		
		this.showScore();
		
		while(this.OC < 3) {			// �ƿ�ī��Ʈ�� 3���� �� �� ���� ����
			// ��ǻ�� ���� ���� ��ȭ�� üũ�ϱ� ���� originScore ����

			
			UserTeam.showPitcher();		// ����� ���� ���� ���� ���
			this.outFlag = false;		// �ƿ� �÷��׸� �ʱ�ȭ
			this.onBaseFlag = false; 	// ��� �÷��׸� �ʱ�ȭ
			
			// Ÿ�� ����
			ComTeam.showHitterRecord();	// ����� Ÿ�� ���� ���
			BC[0] = 0; // ��Ʈ����ũ ī��Ʈ�� 0���� �ʱ�ȭ
			BC[1] = 0; // �� ī��Ʈ�� 0���� �ʱ�ȭ
			
			// (1) �ƿ��� �ǰų�, (2) ��Ÿ �Ǵ� �������� ����ϸ� ���� Ÿ�ڷ� ����
			while(this.outFlag == false && this.onBaseFlag == false) {
				this.showCountBase();					// ��ī��Ʈ�� �ƿ�ī��Ʈ ���
				// ��Ʈ����ũ�� �̹��� ����
				System.out.println(" �� | �� | �� ");
				System.out.println(" ----------");
				System.out.println(" �� | �� | �� ");
				System.out.println(" ----------");
				System.out.println(" �� | �� | �� ");
				// ���� ����
				System.out.println(" $ ������ �������ּ���");	
				System.out.print(" (1) ���� \n (2) Ŀ�� \n (3) ü������ \n >> ");	
				Scanner scan = new Scanner(System.in);
				String command1 = scan.next();
				// ��Ʈ����ũ�� ����
				System.out.println("\n $ ���� ���� ������ �������ּ���");
				System.out.print(" �� ~ �� �� ���� �� ���� \n >> ");
				Scanner scan2 = new Scanner(System.in);
				String command2 = scan.next();
				
				UserTeam.incBalls();	// ����� �� ������ ������ ����
				double swing = Math.random(); // Ÿ�ڰ� ������ Ȯ���� ������  swing ���� ����
				
				// ����ڰ� ������ ������ ��Ʈ����ũ ���� ���ڷ� strike Ȯ�� ���
				double strike = this.calcStrike(Integer.parseInt(command1), Integer.parseInt(command2));
				
				if(swing >= 0.5) {					// swing > 0.5 �� �� ����� Ÿ�ڰ� ����
					// Attack() �޼���� ����
					double rate = Math.random();
					if(rate > 0.97) { 						// Ȩ�� �̺�Ʈ
						this.setBase(4);
						ComTeam.hit(4);
					}
					else if(rate <= 0.97 && rate > 0.93) {	// 3��Ÿ �̺�Ʈ
						this.setBase(3);
						ComTeam.hit(3);
					}
					else if(rate <= 0.93 && rate > 0.85) {	// 2��Ÿ �̺�Ʈ
						this.setBase(2);
						ComTeam.hit(2);
					}
					else if(rate <= 0.85 && rate > 0.70) {	// ��Ÿ �̺�Ʈ
						this.setBase(1);
						ComTeam.hit(1);
					}
					else if(rate <= 0.70 && rate > 0.55) {	// ���� �̺�Ʈ
						System.out.println("\n $ ���� �ƿ��Դϴ�.");
						this.OC++;
						this.outFlag = true;
					}
					else if(rate <= 0.55 && rate > 0.40) {	// �Ẽ �̺�Ʈ
						System.out.println("\n $ �Ẽ �ƿ��Դϴ�.");
						this.OC++;
						this.outFlag = true;
					}
					else {
						System.out.println("\n $ �꽺���Դϴ�.");	// �꽺�� �̺�Ʈ
						this.incStrike(); 	// �꽺��
					}	
				}
				else {								// swing < 0.5 �� �� ����� Ÿ�ڰ� �������� ����
					if(strike > 0.5) {				// strike���� 0.5�� ������ ��Ʈ����ũ
						System.out.println("\n $ ��Ʈ����ũ�Դϴ�.");
						this.incStrike();
					}
					else {							// strike���� 0.5�� ���� ������ ��
						System.out.println("\n $ ���Դϴ�.");
						this.incBall();
					}	
				}
				System.out.println();			
			}
			
			if(this.scoreFlag == true) {	// Ÿ�ڰ� ����� �� ������ ��ȭ�� �ִٸ� ���� ���
				this.showScore();
				this.scoreFlag = false;
			}
			
			if(this.kSign == true) {		// ����� Ÿ�ڰ� �������� �ƿ����ߴٸ�
				ComTeam.out();				// �������� Ÿ�� �ƿ�ó��
				UserTeam.increaseK();		// ������ ��Ͽ� Ż���� 1�� �߰�
				this.kSign = false;			// kSign�� �ٽ� false���·� ��������
			}
			
			// ����ڰ� �������̰� ������ �̴׿��� ���������� ������ ������ ��
			if(this.teamFlag == true && (this.gameinning == this.inning) && (this.score_team1 < this.score_team2))
				break;
			ComTeam.incHitterturn(); 		// ���� Ÿ������ ����
		}
		
		// ���� ��� ����
		if(this.teamFlag == true)		// ��ȭ�̱۽�(������) ���� ���� �޽���
			System.out.println(Integer.toString(this.gameinning)+"ȸ�� ���� ����Ǿ����ϴ�.");
		else if(this.teamFlag == false)	// ����Ʈ����(Ȩ��) ���� ���� �޽���
			System.out.println(Integer.toString(this.gameinning)+"ȸ�� ���� ����Ǿ����ϴ�.");
		this.newInning(); 			// ���ο� �̴����� �ʱ�ȭ
		this.myAttack = true;
	}
	
	// 3. ���� ����� �˷��ִ� �޼���
	public void showResult() {
		if(this.score_team1 == this.score_team2) // ���º�
			System.out.println("  <<-- ���º��Դϴ� -->>");
		else if(this.score_team1 > this.score_team2)
			if(this.teamFlag == true)
				System.out.println("  <<== �¸��߽��ϴ� ==>>");
			else
				System.out.println("  <<-- �й��߽��ϴ� -->>");
		else
			if(this.teamFlag == true)
				System.out.println("  <<-- �й��߽��ϴ� -->>");
			else
				System.out.println("  <<== �¸��߽��ϴ� ==>>");
		this.showScore();
	}
	
	// GameApp() Ŭ������ ������
	public GameApp() throws IOException {
		team1 = saveTeam("Team1.txt");	// Team ��ü team1�� ��ȭ �̱۽� ����
		team2 = saveTeam("Team2.txt");	// Team ��ü team2�� ���� Ʈ���� ����
		String signal;		// ���� ������ signal ���� ����
		
		while(true) {
			System.out.println("���� �������ּ���.\n(1) ��ȭ �̱۽�(Away)\n(2) ���� Ʈ����(Home)");	 // �� ����
			System.out.print(" >> ");
			

			Scanner scan1 = new Scanner(System.in);
			signal = scan1.next();
			
			if(signal.equals("1")) {			// 1�� -> ��ȭ �̱۽�
				team1.printPlayer("��ȭ �̱۽�");
				break;
			}
			else if(signal.equals("2")) {	// 2�� -> ���� Ʈ����
				team2.printPlayer("���� Ʈ����");
				this.teamFlag = false;	// true�� ������ teamFlag�� false�� ����
				break;
			}
			else {	// 1���� 2���� �ƴ� �ٸ� Ű�� �Է����� ��
				System.out.println(" $ �Է��� �ùٸ��� �ʽ��ϴ�. ���� 1 �Ǵ� 2�� �Է����ּ���");
				continue;
			}
		}

		System.out.print("������ ������ �̴��� �Է����ּ��� >> ");
		Scanner scan2 = new Scanner(System.in);
		signal = scan2.next();
		this.inning = Integer.parseInt(signal);
		System.out.println("���� �̴� : "+signal);
		
		if(this.teamFlag == true) {	// ��ȭ �̱۽� ����
			while(gameinning <= this.inning) {	// ����ڰ� ���� �̴׸�ŭ�� ���� ����
				this.Attack();
				// �������� ������ �̴� ������ �����µ� Ȩ������ ������ ���ٸ� 
				if((this.gameinning == this.inning) && (this.score_team1 < this.score_team2))
					break;
				this.Defense();
				this.gameinning++;
			}
		}
		else {	// ���� Ʈ���� ����
			while(gameinning <= this.inning) {	// ����ڰ� ���� �̴׸�ŭ�� ���� ����
				this.Defense();
				// �������� ������ �̴� ������ �����µ� Ȩ������ ������ ���ٸ� 
				if((this.gameinning == this.inning) && (this.score_team1 < this.score_team2))
					break;
				this.Attack();
				this.gameinning++;
			}
		}
		
		// ��� ����
		System.out.println("\n\n << ��Ⱑ ����Ǿ����ϴ� >> ");
		this.showResult();			// ��� ��� ���
		if(this.teamFlag == true)	// ����ڰ� ������ �� �������� ���� ��� ���
			team1.printRecord();
		else
			team2.printRecord();
	}		

	public static void main(String[] args) throws IOException {
		new GameApp();
	}

}
