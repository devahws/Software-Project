/*
 * Program	: 야구 게임이 진행되는 메인 프로그램
 * CAUTION 	: 콘솔 입력에는 오로지 숫자 입력만 가능합니다!
 * How To Play
 * 	1) 게임이 시작되면 콘솔창에 숫자 1 또는 2를 입력해서 팀을 선택합니다.
 * 	2) 팀을 선택했으면 게임을 진행할 이닝을 입력합니다.
 * 	3) 1번 팀을 선택하면 선공, 2번 팀을 선택하면 후공을 하게 됩니다.
 * 	** Attack
 * 		1) 콘솔창에 숫자 1을 입력하면 스윙, 2를 입력하면 스윙하지 않고 기다립니다.
 * 		2) 스윙(1번)을 선택하면 게임 내에 정해진 확률에 따라 헛스윙, 땅볼, 뜬볼, 안타, 2루타, 3루타, 홈런이 결정됩니다.
 * 		3) 기다리는 걸(2번) 선택하면 게임 내에 정해진 확률에 따라 스트라이크, 또는 볼이 결정됩니다.
 * 		4) 실제 야구 규칙과 마찬가지로 아웃카운트가 3개가 되면 공격 모드가 종료되고 수비 모드로 바뀝니다.
 * 	** Defense
 * 		1) 콘솔창에서 숫자 1 ~ 3 중 하나를 입력해서 구종을 선택합니다.
 * 		2) 다음으로는 숫자 1 ~ 9 중 하나를 입력해서 스트라이크 존에서 던질 영역을 선택합니다.
 * 		3) 구종과 스트라이크 존을 선택하면 게임 내에 정의된 공식을 통해 볼, 스트라이크, 땅볼, 뜬볼, 안타, 2루타, 3루타, 홈런이 결정됩니다.
 * 		4) 실제 야구 규칙과 마찬가지로 아웃카운트가 3개가 되면 수비 모드가 종료되고 공격 모드로 바뀝니다.
 * *** Etc
 * 		1) 스코어는 이닝이 교체될 때, 그리고 점수에 변화가 있을 때 출력됩니다.
 * 		2) 경기가 종료된 뒤에는 사용자가 선택한 팀 선수들의 당일 기록이 출력됩니다.
 */

package game;
import java.io.*;
import java.util.*;

import player.*;

public class GameApp {
	private Team team1;	// 한화 이글스
	private Team team2;	// 엘지 트윈스
	
	private int score_team1 = 0;	// 한화 이글스의 점수
	private int score_team2 = 0;	// 엘지 트윈스의 점수
	
	private int inning;				// 경기에서 진행할 총 이닝
	int gameinning = 1;				// 실제 진행중인 이닝
	
	private int[] BASE = new int[3];	// 게임에서 사용할 베이스
	private boolean teamFlag = true;  	// 팀 선택
	// true -> 한화 이글스 (원정팀. 초 공격)
	// false -> 엘지 트윈스(홈팀. 말 공격)
	private boolean hitFlag = false;	// 안타 발생하면 변화
	private boolean outFlag = false;	// 아웃 발생하면 변화
	private boolean onBaseFlag = false; // 주자의 출루 발생하면 변화
	private boolean kSign = false; 		// 삼진 발생하면 변화
	private boolean scoreFlag = false; 	// 점수가 증가하면 변화
	
	private boolean myAttack = true;	// 사용자의 공격차례와 컴퓨터의 공격차례 구분
	
	int OC = 0; 					// 아웃 카운트(Out Counts)
	int[] BC = new int[2]; 			// 볼 카운트(Ball Counts) 
	// BC[0] = 스트라이크 카운트
	// BC[1] = 볼 카운트
	
	//// Team 객체를 생성하고 파일을 읽어와서 세팅하는 메서드
	// 매개변수로 받는 이름의 파일에 저장된 선수 목록을 가져와서 저장
	public Team saveTeam(String filename) throws IOException {	
		Team team = new Team(); // Team 객체 생성
		
		File Team = new File(filename);
		FileInputStream fin = new FileInputStream(Team);
		InputStreamReader in = new InputStreamReader(fin, "utf-8");
		BufferedReader br = new BufferedReader(in);
		for(int i=0; i<9; i++) {		// 1번타자부터 9번타자까지 저장
			String line = br.readLine();
			StringTokenizer st = new StringTokenizer(line, " ");
			team.setHitter(st.nextToken(), st.nextToken(), i);
		}
		String line = br.readLine();
		StringTokenizer st = new StringTokenizer(line, " ");
		team.setPitcher(st.nextToken(), st.nextToken());	// 투수 저장
		
		fin.close();
		in.close();
		br.close();
		
		return team;	
	}
	
	// 현재 점수를 보여주는 메서드
	public void showScore() {
		System.out.println("< 한화 이글스 |"+Integer.toString(this.score_team1)+"|:|"+Integer.toString(this.score_team2)+"| 엘지 트윈스 >\n");
	}
	
	// 현재 아웃 카운트, 볼 카운트, 베이스의 주자를 보여주는 메서드
	public void showCountBase() {
		// 아웃 카운트
		System.out.print("O: ");
		if(this.OC == 0)
			System.out.print("○ ○ ");
		else if(this.OC == 1)
			System.out.print("○ ● ");
		else if(this.OC == 2)
			System.out.print("● ● ");
		// 2루 베이스
		if(this.BASE[1] == 0)	// 베이스가 비어있을 때
			System.out.println("          ◇");
		else					// 베이스에 주자가 있을 때
			System.out.println("          ◆");
		// 스트라이크 카운트
		System.out.print("S: ");
		if(this.BC[0] == 0)
			System.out.print("○ ○\n");
		else if(this.BC[0] == 1)
			System.out.print("○ ●\n");
		else if(this.BC[0] == 2)
			System.out.print("● ●\n");
		// 볼 카운트
		System.out.print("B: ");
		if(this.BC[1] == 0)
			System.out.print("○ ○ ○  ");
		else if(this.BC[1] == 1)
			System.out.print("○ ○ ●  ");
		else if(this.BC[1] == 2)
			System.out.print("○ ● ●  ");
		else if(this.BC[1] == 3)
			System.out.print("● ● ●  ");
		// 3루 베이스
		if(this.BASE[2] == 0)
			System.out.print("◇   ");
		else
			System.out.print("◆   ");
		// 1루 베이스
		if(this.BASE[0] == 0)
			System.out.println("   ◇");
		else
			System.out.println("   ◆");
	}
	
	public void newInning() {
		this.outFlag = false;		// 아웃 플래그를 초기화
		this.onBaseFlag = false; 	// 출루 플래그를 초기화
		this.OC = 0;				// 아웃카운트를 초기화
		this.BC[0] = 0;				// 스트라이크 카운트 초기화
		this.BC[1] = 0;				// 볼 카운트 초기화
		for(int i=0; i<3; i++)		// 베이스를 초기화
			this.BASE[i] = 0;
	}

	//// 베이스 설정 메서드
	// 1. getBase() : 현재 베이스에 주자가 몇 명 있는지 리턴. 홈런 이벤트가 발생했을 때 사용
	public int getBase() {
		int r = 0;
		for(int i=0; i<3; i++)
			if(this.BASE[i] != 0)
				r++;	
		return r;
	}
	
	// 2. isFullB() : 현재 베이스가 꽉 찼는지 확인하는 메서드
	public boolean isFullB() {
		if(this.BASE[0] == 1 && this.BASE[1] == 1 && this.BASE[2] == 1)
			return true;
		else
			return false;
	}
	
	// 3. emptyBase() : 베이스를 모두 비우는 메서드. 홈런 이벤트 발생, 이닝이 종료될 때 사용
	public void emptyBase() {
		for(int i=0; i<3; i++)
			this.BASE[i] = 0;
	}
	
	// 4. setBase() : 안타, 볼넷이 발생했을 때 주자를 옮기고 점수를 추가하는 메서드
	public void setBase(int hit) {
		int plusScore = 0;	// 베이스 이동으로 추가될 점수
		
		if(hit == 0) {			// 0. 볼넷
			if(this.isFullB())	// 베이스가 꽉 찼을 때
				plusScore++;	// 점수를 1점 추가
			else if(this.BASE[0] == 1) {	// 1루에 주자가 있고
				if(this.BASE[1] == 1)		// 2루에도 주자가 있을 때
					for(int i=0; i<3; i++)	// 베이스에 주자를 모두 채워줌
						this.BASE[i] = 1;
				else { 						// 1루에만 주자가 있고 2루에는 주자가 없을 때
					for(int i=0; i<2; i++)	// 2루까지만 주자를 채워줌
						this.BASE[i] = 1;
				}
			}
			else if(this.BASE[0] == 0) {	// 1루가 비었을 때
				this.BASE[0] = 1;			// 1루를 채움
			}
			System.out.println("\n $ 볼넷으로 출루합니다.");
		}
		else if(hit == 1) {		  // 1. 일반 안타
			if(this.BASE[2] == 1) // 3루에 주자가 있을 때
				plusScore++; 	  // 1점 득점
			this.BASE[2] = this.BASE[1]; //	 2루 주자를 3루로
			this.BASE[1] = this.BASE[0]; //	 1루 주자를 2루로
			this.BASE[0] = 1;			 //  1루에 주자를 채움
			System.out.println("\n $ 안타입니다!");
		}
		else if(hit==2) {			// 2. 2루타
			if(this.BASE[2] == 1)	// 3루에 주자가 있었다면
				plusScore++;		// +1득점
			if(this.BASE[1] == 1)	// 2루에 주자가 있었다면 
				plusScore++;		// +1득점
			this.BASE[2] = this.BASE[0]; // 1루 주자를 3루로
			this.BASE[1] = 1;			 // 2루타를 친 타자를 2루에 위치
			this.BASE[0] = 0;		 	 // 1루를 비우기
			System.out.println("\n $ 2루타입니다!");
		}
		else if(hit == 3) {				// 3. 3루타 -> 루상의 모든 주자를 불러들임
			for(int i=0; i<3; i++) {
				if(this.BASE[i] == 1) {	// 루에 주자가 존재하면 +1 득점
					plusScore++;
					this.BASE[i] = 0;	// 루를 비워줌
				}
			}
			this.BASE[2] = 1; 			// 타자를 3루에 위치
			System.out.println("\n $ 3루타입니다!");
		}
		else if(hit == 4) {			// 4. 홈런
			plusScore += getBase();	// 루상에 있는 주자 모두 득점
			plusScore++;			// 타자까지 득점
			for(int i=0; i<3; i++)
				this.BASE[i] = 0; 	// 모든 루를 비우기
			System.out.println("\n $ 홈런입니다!");
		}
		if(plusScore != 0) {						// 점수가 증가했을 때
			if(this.teamFlag == true) {				// (1) 사용자가 한화 이글스를 선택했을 때
				if(this.myAttack == true)			// (1) - 1. 사용자의 공격 차례
					this.score_team1 += plusScore;	// 			한화 이글스의 점수 증가
				else								// (1) - 2. 컴퓨터의 공격 차례
					this.score_team2 += plusScore;	// 			엘지 트윈스의 점수 증가
			}
			else if(this.teamFlag == false) { 		// (2) 사용자가 엘지 트윈스를 선택했을 때
				if(this.myAttack == true)			// (2) - 1. 사용자의 공격 차례
					this.score_team2 += plusScore;	//			엘지 트윈스의 점수 증가
				else 								// (2) - 2. 컴퓨터의 공격 차례	
					this.score_team1 += plusScore;	//			한화 이글스의 점수 증가
			}
			this.scoreFlag = true;
		}
		this.onBaseFlag = true;					// 주자가 출루했으므로 onBaseFlag 변화를 통해 타순 rotate
	}

	//// 투수와 관련된 메서드
	// 1. incBall() : 투수가 볼을 던졌을 때 발생하는 메서드
	public void incBall() {	
		this.BC[1]++;			// 볼 카운트 하나 증가
		if(this.BC[1] > 3) {	// 볼이 4개가 됐을 때
			this.BC[1] = 0;		// 볼을 0으로 초기화
			this.setBase(0); 	// 볼넷 이벤트 호출
		}
	}
	
	// 2. incStrike() : 투수가 스트라이크를 던졌을 때 발생하는 메서드
	public void incStrike() {	
		this.BC[0]++; 			// 스트라이크를 하나 증가
		if(this.BC[0] > 2) {	// 스트라이크가 3개이면
			System.out.println(" $ 삼진 아웃입니다.");
			this.BC[0] = 0;		// 스트라이크를 0으로 초기화
			this.BC[1] = 0;		// 볼 카운트를 0으로 초기화
			this.OC++;			// 아웃카운트를 하나 증가
			this.outFlag = true;// outFlag를 true로 변화시켜서 타순 rotate 혹은 이닝 변경
			this.kSign = true;	// kSign을 true로 변화시켜서 투수의 탈삼진 기록에 추가
		}
	}
	
	// 3. calcStrike() : 사용자의 Defense 모드에서 투수가 공을 던지고, 타자가 스윙하지 않았을 때 스트라이크가 될 확률을 리턴하는 메서드 
	public double calcStrike(int com1, int com2) {
		double type = 0;	// 구종 타입
		double pos = 0;		// 스트라이크 존 위치
		
		// commman1 구종 옵션
		if(com1 == 1)		// 1. 직구
			type = 0.9;		// 0.9의 확률값 
		else if(com1 == 2)	// 2. 커브
			type = 0.85;	// 0.85의 확률값
		else if(com1 == 3)	// 3. 체인지업
			type = 0.7;		// 0.7의 확률값
		
		// command2 스트라이크존 옵션
		if(com2 % 2 == 0) // 스트라이크존 2, 4, 6, 8 중 하나일 때
			pos = 0.85;	  // 0.85의 확률값
		else if(com2 % 2 == 1 && com2 != 5) // 스트라이크존 1, 3, 7, 9 중 하나일 때
			pos = 0.7;	  					// 0.7의 확률값
		else if(com2 == 5)	// 스트라이크존 한 가운데 5번 일 때
			pos = 0.95; 	// 0.95의 확률값
		
		return type*pos; 	// 구종과 스트라이크존 옵션을 곱한 최종 확률을 리턴
	}
	
	//// 게임 진행 메서드
	// 1. Attack() : 사용자의 공격 모드 메서드
	public void Attack() {
		this.myAttack = true;	// 플레이어의 공격 순서
		Team team;		// Attack() 메서드에서 공격하는 팀
		Team ComTeam;	// Attack() 메서드에서 수비하는 팀
		if(this.teamFlag == true) {	// 사용자가 한화 이글스를 선택
			team = this.team1;
			ComTeam = this.team2;
		}
		else {						// 사용자가 엘지 트윈스를 선택
			team = this.team2;
			ComTeam = this.team1;
		}
		// 공격 시작 안내문구 출력
		if(this.teamFlag == true)
			System.out.println(Integer.toString(this.gameinning)+"회초 공격을 시작합니다.\n");
		else if(this.teamFlag == false)
			System.out.println(Integer.toString(this.gameinning)+"회말 공격을 시작합니다.\n");
		
		// 스코어 출력
		this.showScore();	
		// 공격을 시작할 때 베이스를 초기화
		for(int i=0; i<3; i++) {	
			this.BASE[i] = 0;
		}
	
		
		// 아웃카운트 3개가 모두 찰 때 까지 진행
		while(this.OC < 3) {
			this.outFlag = false;		// 아웃 플래그를 초기화
			this.onBaseFlag = false; 	// 출루 플래그를 초기화
			
			// 타자 입장
			ComTeam.showPitcher();		// 상대 팀의 투수 정보 출력
			team.showHitterRecord();	// 타자 성적 출력
			
			BC[0] = 0; // 스트라이크 카운트를 0으로 초기화
			BC[1] = 0; // 볼 카운트를 0으로 초기화

			// (1) 아웃이 되거나(outFlag), (2) 안타 또는 볼넷으로 출루하면(onBaseFlag) 다음 타자로 변경
			while(this.outFlag == false && this.onBaseFlag == false) {
				this.showCountBase();	// 볼카운트와 아웃카운트 출력
				
				// 타자 옵션 1) 스윙		2) 지켜보기
				System.out.print(" (1) 스윙 \n (2) 지켜보기 \n >> ");	
				Scanner scan = new Scanner(System.in);	// 사용자 명령 입력받음
				String command = scan.next();
				if(command.equals("1")) {				// (1) 스윙을 선택했을 때
					ComTeam.incBalls();		// 사용팀 투수의 투구수 증가
					double rate = Math.random();		// random 함수를 통해 생성된 의사난수를 저장하는 rate 생성
					if(rate > 0.97) {   					// rate > 0.97 일 때 홈런 이벤트
						this.setBase(4);
						team.hit(4);	// team.hit() 메서드를 통해 타자의 기록에 추가
					}
					else if(rate <= 0.97 && rate > 0.93) {	// 0.93 ~ rate ~ 0.97 일 때 3루타 이벤트
						this.setBase(3);
						team.hit(3);	// team.hit() 메서드를 통해 타자의 기록에 추가
					}
					else if(rate <= 0.93 && rate > 0.85) {	// 0.85 ~ rate ~ 0.93 일 때 2루타 이벤트
						this.setBase(2);
						team.hit(2);	// team.hit() 메서드를 통해 타자의 기록에 추가
					}
					else if(rate <= 0.85 && rate > 0.70) {	// 0.70 ~ rate ~ 0.85 일 때 안타 이벤트
						this.setBase(1);
						team.hit(1);	// team.hit() 메서드를 통해 타자의 기록에 추가
					}
					else if(rate <= 0.70 && rate > 0.55) {	// 0.55 ~ rate ~ 0.70 일 때 땅볼 이벤트
						System.out.println("\n $ 땅볼 아웃입니다.");
						this.OC++;				// 아웃 카운트 추가
						this.outFlag = true;	// 아웃 플래그 변경
						team.out();				// team.out() 플래그를 통해 타자의 기록에 추가
					}
					else if(rate <= 0.55 && rate > 0.40) {	// 0.40 ~ rate ~ 0.55 일 때 땅볼 이벤트
						System.out.println("\n $ 뜬볼 아웃입니다.");
						this.OC++;				// 아웃 카운트 추가
						this.outFlag = true;	// 아웃 플래그 변경
						team.out();				// team.out() 플래그를 통해 타자의 기록에 추가
					}
					else {						// rate < 0.40 일 때 헛스윙
						System.out.println("\n $ 헛스윙입니다.");
						this.incStrike(); 		// 스트라이크 증가
					}
								
				}
				else if(command.equals("2")) {	// (2) 스윙하지 않고 기다리는 걸 선택했을 때
					ComTeam.incBalls();		// 사용팀 투수의 투구수 증가
					double rate = Math.random();	// random 함수를 통해 생성된 의사난수를 저장하는 rate 생성
					if(rate > 0.60) {				// rate > 0.60 일 때 스트라이크
						System.out.println("\n $ 스트라이크입니다.");
						this.incStrike();			// 스트라이크 증가
					}
					else {  						// rate < 0.60 일 때 볼
						System.out.println("\n $ 볼입니다.");
						this.incBall();				// 볼 증가
					}
						
				}
				else {	// 1이나 2가 아닌 다른 입력을 했을 때
					System.out.println(" $ 입력이 올바르지 않습니다. 1 혹은 2를 입력해주세요");
					continue;
				}
				System.out.println();
				
			}
			if(this.scoreFlag == true) {	// 타자가 변경될 때 점수의 변화가 있다면 점수 출력
				this.showScore();
				this.scoreFlag = false;
			}
			if(this.kSign == true) {	// 삼진 이벤트로 인해서 아웃이 발생했다면
				team.out();				// 삼진당한 타자 아웃처리
				ComTeam.increaseK(); 	// 컴퓨터 팀의 투수의 탈삼진 증가
				this.kSign = false;		// kSign을 다시 false상태로 돌려놓음
			}
			// 사용자가 홈팀이고 마지막 이닝에서 원정팀을 역전했을 때
			if(this.teamFlag == false && (this.gameinning == this.inning) && (this.score_team1 < this.score_team2))
				break;
			team.incHitterturn(); 		// 다음 타순으로 변경
		}
		// 아웃카운트가 3이 되었을 때
		if(this.teamFlag == true)
			System.out.println(Integer.toString(this.gameinning)+"회초 공격이 종료되었습니다.\n");
		else if(this.teamFlag == false)
			System.out.println(Integer.toString(this.gameinning)+"회말 공격이 종료되었습니다.\n");
		this.newInning(); 			// 새로운 이닝으로 초기화
		this.myAttack = false;
	}

	public void Defense() {
		this.myAttack = false; 		// 컴퓨터의 공격 순서로 세팅
		if(this.teamFlag == true)	// 사용자의 팀 선택에 따라 수비 시작 메시지 출력
			System.out.println(Integer.toString(this.gameinning)+"회말 수비를 시작합니다.\n");
		else if(this.teamFlag == false)
			System.out.println(Integer.toString(this.gameinning)+"회초 수비를 시작합니다.\n");
		
		Team UserTeam;		// 사용자의 팀
		Team ComTeam;		// 컴퓨터의 팀
		
		if(this.teamFlag == true) {	// 한화 이글스 선택
			UserTeam = this.team1;
			ComTeam = this.team2;
		}
		else {						// 엘지 트윈스 선택
			UserTeam = this.team2;
			ComTeam = this.team1;
		}
		
		this.showScore();
		
		while(this.OC < 3) {			// 아웃카운트가 3개가 될 때 까지 진행
			// 컴퓨터 팀의 점수 변화를 체크하기 위한 originScore 생성

			
			UserTeam.showPitcher();		// 사용자 팀의 투수 정보 출력
			this.outFlag = false;		// 아웃 플래그를 초기화
			this.onBaseFlag = false; 	// 출루 플래그를 초기화
			
			// 타자 입장
			ComTeam.showHitterRecord();	// 상대팀 타자 성적 출력
			BC[0] = 0; // 스트라이크 카운트를 0으로 초기화
			BC[1] = 0; // 볼 카운트를 0으로 초기화
			
			// (1) 아웃이 되거나, (2) 안타 또는 볼넷으로 출루하면 다음 타자로 변경
			while(this.outFlag == false && this.onBaseFlag == false) {
				this.showCountBase();					// 볼카운트와 아웃카운트 출력
				// 스트라이크존 이미지 생성
				System.out.println(" ① | ② | ③ ");
				System.out.println(" ----------");
				System.out.println(" ④ | ⑤ | ⑥ ");
				System.out.println(" ----------");
				System.out.println(" ⑦ | ⑧ | ⑨ ");
				// 구종 선택
				System.out.println(" $ 구종을 선택해주세요");	
				System.out.print(" (1) 직구 \n (2) 커브 \n (3) 체인지업 \n >> ");	
				Scanner scan = new Scanner(System.in);
				String command1 = scan.next();
				// 스트라이크존 선택
				System.out.println("\n $ 공을 던질 영역을 선택해주세요");
				System.out.print(" ① ~ ⑨ 번 영역 중 선택 \n >> ");
				Scanner scan2 = new Scanner(System.in);
				String command2 = scan.next();
				
				UserTeam.incBalls();	// 사용자 팀 투수의 투구수 증가
				double swing = Math.random(); // 타자가 스윙할 확률을 저장할  swing 변수 생성
				
				// 사용자가 선택한 구종과 스트라이크 존을 인자로 strike 확률 계산
				double strike = this.calcStrike(Integer.parseInt(command1), Integer.parseInt(command2));
				
				if(swing >= 0.5) {					// swing > 0.5 일 때 상대팀 타자가 스윙
					// Attack() 메서드와 동일
					double rate = Math.random();
					if(rate > 0.97) { 						// 홈런 이벤트
						this.setBase(4);
						ComTeam.hit(4);
					}
					else if(rate <= 0.97 && rate > 0.93) {	// 3루타 이벤트
						this.setBase(3);
						ComTeam.hit(3);
					}
					else if(rate <= 0.93 && rate > 0.85) {	// 2루타 이벤트
						this.setBase(2);
						ComTeam.hit(2);
					}
					else if(rate <= 0.85 && rate > 0.70) {	// 안타 이벤트
						this.setBase(1);
						ComTeam.hit(1);
					}
					else if(rate <= 0.70 && rate > 0.55) {	// 땅볼 이벤트
						System.out.println("\n $ 땅볼 아웃입니다.");
						this.OC++;
						this.outFlag = true;
					}
					else if(rate <= 0.55 && rate > 0.40) {	// 뜬볼 이벤트
						System.out.println("\n $ 뜬볼 아웃입니다.");
						this.OC++;
						this.outFlag = true;
					}
					else {
						System.out.println("\n $ 헛스윙입니다.");	// 헛스윙 이벤트
						this.incStrike(); 	// 헛스윙
					}	
				}
				else {								// swing < 0.5 일 때 상대팀 타자가 스윙하지 않음
					if(strike > 0.5) {				// strike값이 0.5를 넘으면 스트라이크
						System.out.println("\n $ 스트라이크입니다.");
						this.incStrike();
					}
					else {							// strike값이 0.5를 넘지 않으면 볼
						System.out.println("\n $ 볼입니다.");
						this.incBall();
					}	
				}
				System.out.println();			
			}
			
			if(this.scoreFlag == true) {	// 타자가 변경될 때 점수의 변화가 있다면 점수 출력
				this.showScore();
				this.scoreFlag = false;
			}
			
			if(this.kSign == true) {		// 상대팀 타자가 삼진으로 아웃당했다면
				ComTeam.out();				// 삼진당한 타자 아웃처리
				UserTeam.increaseK();		// 투수의 기록에 탈삼진 1개 추가
				this.kSign = false;			// kSign을 다시 false상태로 돌려놓음
			}
			
			// 사용자가 원정팀이고 마지막 이닝에서 원정팀에게 역전을 당했을 때
			if(this.teamFlag == true && (this.gameinning == this.inning) && (this.score_team1 < this.score_team2))
				break;
			ComTeam.incHitterturn(); 		// 다음 타순으로 변경
		}
		
		// 수비 모드 종료
		if(this.teamFlag == true)		// 한화이글스(원정팀) 수비 종료 메시지
			System.out.println(Integer.toString(this.gameinning)+"회말 수비가 종료되었습니다.");
		else if(this.teamFlag == false)	// 엘지트윈스(홈팀) 수비 종료 메시지
			System.out.println(Integer.toString(this.gameinning)+"회초 수비가 종료되었습니다.");
		this.newInning(); 			// 새로운 이닝으로 초기화
		this.myAttack = true;
	}
	
	// 3. 게임 결과를 알려주는 메서드
	public void showResult() {
		if(this.score_team1 == this.score_team2) // 무승부
			System.out.println("  <<-- 무승부입니다 -->>");
		else if(this.score_team1 > this.score_team2)
			if(this.teamFlag == true)
				System.out.println("  <<== 승리했습니다 ==>>");
			else
				System.out.println("  <<-- 패배했습니다 -->>");
		else
			if(this.teamFlag == true)
				System.out.println("  <<-- 패배했습니다 -->>");
			else
				System.out.println("  <<== 승리했습니다 ==>>");
		this.showScore();
	}
	
	// GameApp() 클래스의 생성자
	public GameApp() throws IOException {
		team1 = saveTeam("Team1.txt");	// Team 객체 team1에 한화 이글스 저장
		team2 = saveTeam("Team2.txt");	// Team 객체 team2에 엘지 트윈스 저장
		String signal;		// 팀을 선택한 signal 변수 생성
		
		while(true) {
			System.out.println("팀을 선택해주세요.\n(1) 한화 이글스(Away)\n(2) 엘지 트윈스(Home)");	 // 팀 선택
			System.out.print(" >> ");
			

			Scanner scan1 = new Scanner(System.in);
			signal = scan1.next();
			
			if(signal.equals("1")) {			// 1번 -> 한화 이글스
				team1.printPlayer("한화 이글스");
				break;
			}
			else if(signal.equals("2")) {	// 2번 -> 엘지 트윈스
				team2.printPlayer("엘지 트윈스");
				this.teamFlag = false;	// true로 설정된 teamFlag를 false로 변경
				break;
			}
			else {	// 1번도 2번도 아닌 다른 키를 입력했을 때
				System.out.println(" $ 입력이 올바르지 않습니다. 숫자 1 또는 2를 입력해주세요");
				continue;
			}
		}

		System.out.print("게임을 진행할 이닝을 입력해주세요 >> ");
		Scanner scan2 = new Scanner(System.in);
		signal = scan2.next();
		this.inning = Integer.parseInt(signal);
		System.out.println("게임 이닝 : "+signal);
		
		if(this.teamFlag == true) {	// 한화 이글스 선택
			while(gameinning <= this.inning) {	// 사용자가 정한 이닝만큼만 게임 진행
				this.Attack();
				// 원정팀의 마지막 이닝 공격이 끝났는데 홈팀보다 점수가 낮다면 
				if((this.gameinning == this.inning) && (this.score_team1 < this.score_team2))
					break;
				this.Defense();
				this.gameinning++;
			}
		}
		else {	// 엘지 트윈스 선택
			while(gameinning <= this.inning) {	// 사용자가 정한 이닝만큼만 게임 진행
				this.Defense();
				// 원정팀의 마지막 이닝 공격이 끝났는데 홈팀보다 점수가 낮다면 
				if((this.gameinning == this.inning) && (this.score_team1 < this.score_team2))
					break;
				this.Attack();
				this.gameinning++;
			}
		}
		
		// 경기 종료
		System.out.println("\n\n << 경기가 종료되었습니다 >> ");
		this.showResult();			// 경기 결과 출력
		if(this.teamFlag == true)	// 사용자가 선택한 팀 선수들의 당일 기록 출력
			team1.printRecord();
		else
			team2.printRecord();
	}		

	public static void main(String[] args) throws IOException {
		new GameApp();
	}

}
