//주사위굴리기2
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
1. 주사위를 움직인 후에 지도 안에 있으면 이동 방향 유지/ 범위 벗어나면 방향 반대로 바꾸기
2. 주사위 굴리기
3. 바닥이 더 크면 시계방향, 바닥보다 크면 반시계방향
4. 연속으로 얼마나 갈 수 있는지?? dfs
5. 이동 후 점수 계산
 */

public class Main_BJ_23288 {
	public static int N, M, K, score;
	public static int[][] map;
	//  현재 주사위 방향, 현재 주사위 위치 (x,y)
	public static int tempDir, tempX, tempY; 
	public static Dice dice; // 주사위
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 행
		M = Integer.parseInt(st.nextToken()); // 열
		K = Integer.parseInt(st.nextToken()); // 이동횟수
		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}//맵 입력
		tempDir = 0;
		tempX = 0;
		tempY = 0;
		dice = new Dice();
		//K만큼 움직이기
		for (int i = 0; i < K; i++) {
			move();
		}
		//정답 출력
		System.out.println(score);
	}
	
	// dir 0,1,2,3
	static int[] dx = { 0, 1, 0, -1 }; // 우 하 좌 상
	static int[] dy = { 1, 0, -1, 0 };

	public static void move() {
		// 움직인 후에 범위 안에 있으면 이동방향 유지
		int nr = tempX + dx[tempDir];
		int nc = tempY + dy[tempDir];
		if (isIn(nr, nc)) {
			tempX = nr;
			tempY = nc;
		} else {// 범위 벗어나면, 방향 바꿈
			// 반대방향으로 바꿈
			tempDir = (tempDir + 2) % 4; 
			tempX += dx[tempDir];
			tempY += dy[tempDir];
		}
		
		//방향 바꾼 다음에
		//현재 방향으로 주사위 굴리기
		roll();
		
		//도착한 칸에 있는 숫자로 이동방향 결정하기
		int B = map[tempX][tempY];
		//바닥이 B보다 크면 시계방향
		if (dice.bottom > B) {
			changeDir(1); // 시계방향
		} 
		//바닥이 B보다 작으면 반시계방향
		else if (dice.bottom < B) {
			changeDir(2); // 반시계 방향
		}

		//연속해서 이동할 수 있는 칸의 개수???
		C = 0;
		//dfs 호출
		dfs(tempX, tempY, B, new boolean[N][M]);
		//점수 계산하기
		score += (B * C);
	}

	
	// DFS - (연속으로 얼마나 갈 수 있는지) 현재 위치에서 갈 수 있는 위치 개수 계산 
	public static int C = 0;
	public static void dfs(int r, int c, int num, boolean[][] visited) {
		// 다른 숫자면 종료
		if(map[r][c] != num) 
			return; 
		C++;
		visited[r][c] = true;	
		for (int d = 0; d < 4; d++) {
			int nr = r + dx[d];
			int nc = c + dy[d];
			//영역 내에 있고, 방문하지 않은 위치에 대하여 계산
			if(isIn(nr,nc) && !visited[nr][nc])  
				dfs(nr, nc, num, visited);
		}
	}
	
	// 범위 내에 있는지 확인하는 함수
	public static boolean isIn(int r, int c) {
		if (r < 0 || r >= N || c < 0 || c >= M)
			return false;
		return true;
	}
	
	//방향 바꾸는 함수
	// 0: 반대 방향, 1: 시계 방향, 2: 반시계 방향
	public static void changeDir(int c) {
		if (c == 0) {
			tempDir = (tempDir + 2) % 4;
		} else if (c == 1) {
			tempDir = (tempDir + 1) % 4;
		} else if (c == 2) {
			tempDir = (tempDir + 3) % 4;
		}
	}
	
	//주사위 굴리기
	//0 오른쪽 1 아래쪽 2 왼쪽 3 위쪽
	public static void roll() {
		int tmpTop = dice.top;
		int tmpBottom = dice.bottom;
		int tmpFront = dice.front;
		int tmpBack = dice.back;
		int tmpLeft = dice.left;
		int tmpRight = dice.right;
	
		// 오른쪽으로 굴리기
		if(tempDir == 0) {
			dice.bottom = tmpRight;
			dice.top = tmpLeft;
			dice.left = tmpBottom;
			dice.right = tmpTop;
		}
		// 아래로 굴리기
		else if(tempDir == 1) {
			dice.front = tmpTop;
			dice.top = tmpBack;
			dice.back = tmpBottom;
			dice.bottom = tmpFront;
		}
		// 왼쪽으로 굴리기
		else if(tempDir == 2) {
			dice.bottom= tmpLeft;
			dice.top = tmpRight;
			dice.left = tmpTop;
			dice.right = tmpBottom;
		}
		// 위로 굴리기
		else if(tempDir == 3) {
			dice.front = tmpBottom;
			dice.back = tmpTop;
			dice.top = tmpFront;
			dice.bottom = tmpBack;
		}
		
	}
	
	//주사위 클래스
	//  2
	//4 1 3
	//  5
	//  6
	static class Dice {
		int top, bottom, left, right, front, back;
		public Dice() {
			this.top = 1;
			this.bottom = 6;
			this.left = 4;
			this.right = 3;
			this.front = 5;
			this.back = 2;
		}
	}	

}
