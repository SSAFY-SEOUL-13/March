import java.util.*;

public class Main {
	static int[] dx = { 0, 1, 0, -1 }; // 오른, 아래, 왼, 위
	static int[] dy = { 1, 0, -1, 0 };
	static int N, M, K, d, answer;
	static int[][] board, ScoreBoard;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		K = sc.nextInt();
		board = new int[N][M];
		ScoreBoard = new int[N][M];
		answer = 0;
		// 지도정보를 받아옵니다.
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				board[i][j] = sc.nextInt();
			}
		}
		// 주사위가 해당 칸에 왔을 때 몇 점을 얻게 되는지를 ScoreBoard에 기록합니다.
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				BFS(i, j);
			}
		}

		// 주사위 객체를 생성합니다
		dice D = new dice(4, 3, 5, 2, 6, 1, 0, 0); // 변수는 (좌, 우, 앞, 뒤, 아래, 위, x좌표,y좌표) 입니다.
		d = 0; // 주사위가 나아갈 방향을 알려주는 direction변수입니다.
		for (int i = 0; i < K; i++) { // 주사위는 K번 움직입니다.
			// 주사위가 해당 진행방향으로 진행할 수 없다면
			if (0 > D.x + dx[d] || D.x + dx[d] >= N || 0 > D.y + dy[d] || D.y + dy[d] >= M) {
				d = (d + 2) % 4; // 반대방향(앞<->뒤, 왼쪽<->오른쪽)으로 방향을 바꿉니다.
			}
			// 해당방향으로 움직입니다.
			int nx = D.x + dx[d];
			int ny = D.y + dy[d];

			// 움직인 방향에 따라 주사위 값을 변경해 줍니다.
			switch (d) {
			case 0: // 주사위가 오른쪽으로 움직여 [nx,ny]에 도달했다면
				D.rollRight(); // 오른쪽 구르기 메서드를 진행합니다.
				// 굴린 뒤 주사위 위치를 갱신시켜 줍니다.
				D.x = nx;
				D.y = ny;
				answer += ScoreBoard[nx][ny]; // 현재 위치에서 얻을 수 있는 점수를 획득합니다.
				// 문제조건에 맞게 다음방향을 선택합니다
				if (D.bottom > board[D.x][D.y]) {
					d = (d + 1) % 4;
				} else if (D.bottom < board[D.x][D.y]) {
					d = (4 + d - 1) % 4; // d가0인경우 값이 이상해지므로 임의로 4를 더했습니다.
				}
				break;
			case 1:
				D.rollFront();
				D.x = nx;
				D.y = ny;
				answer += ScoreBoard[nx][ny];
				if (D.bottom > board[D.x][D.y]) {
					d = (d + 1) % 4;
				} else if (D.bottom < board[D.x][D.y]) {
					d = (4 + d - 1) % 4;
				}
				break;
			case 2:
				D.rollLeft();
				D.x = nx;
				D.y = ny;
				answer += ScoreBoard[nx][ny];
				if (D.bottom > board[D.x][D.y]) {
					d = (d + 1) % 4;
				} else if (D.bottom < board[D.x][D.y]) {
					d = (4 + d - 1) % 4;
				}
				break;
			case 3:
				D.rollBack();
				D.x = nx;
				D.y = ny;
				answer += ScoreBoard[nx][ny];
				if (D.bottom > board[D.x][D.y]) {
					d = (d + 1) % 4;
				} else if (D.bottom < board[D.x][D.y]) {
					d = (4 + d - 1) % 4;
				}
				break;
			}
		}

		System.out.println(answer);

	}

	public static void BFS(int x, int y) {
		Queue<int[]> q = new LinkedList<int[]>();
		boolean[][] v = new boolean[N][M];
		int[] start = { x, y };
		q.add(start);
		v[x][y] = true;
		int SumValue = board[x][y];
		while (!q.isEmpty()) {
			int[] val = q.poll();
			for (int d = 0; d < 4; d++) {
				int nx = val[0] + dx[d];
				int ny = val[1] + dy[d];
				if (0 <= nx && nx < N && 0 <= ny && ny < M && !v[nx][ny] && board[x][y] == board[nx][ny]) {
					v[nx][ny] = true;
					SumValue += board[nx][ny];
					int[] temp = { nx, ny };
					q.add(temp);
				}
			}
		}
		ScoreBoard[x][y] = SumValue;
	}

	static class dice {
		int left;
		int right;
		int front;
		int back;
		int bottom;
		int up;
		int x;
		int y;

		public dice(int left, int right, int front, int back, int bottom, int up, int x, int y) {
			super();
			this.left = left;
			this.right = right;
			this.front = front;
			this.back = back;
			this.bottom = bottom;
			this.up = up;
			this.x = x;
			this.y = y;
		}

		public void rollLeft() {
			int tempLeft = this.left;
			int tempRight = this.right;
			int tempFront = this.front;
			int tempBack = this.back;
			int tempBottom = this.bottom;
			int tempUp = this.up;

			this.left = tempUp;
			this.right = tempBottom;
			this.bottom = tempLeft;
			this.up = tempRight;

		}

		public void rollRight() {
			int tempLeft = this.left;
			int tempRight = this.right;
			int tempFront = this.front;
			int tempBack = this.back;
			int tempBottom = this.bottom;
			int tempUp = this.up;

			this.left = tempBottom;
			this.right = tempUp;
			this.bottom = tempRight;
			this.up = tempLeft;

		}

		public void rollFront() {
			int tempLeft = this.left;
			int tempRight = this.right;
			int tempFront = this.front;
			int tempBack = this.back;
			int tempBottom = this.bottom;
			int tempUp = this.up;

			this.bottom = tempFront;
			this.up = tempBack;
			this.front = tempUp;
			this.back = tempBottom;

		}

		public void rollBack() {
			int tempLeft = this.left;
			int tempRight = this.right;
			int tempFront = this.front;
			int tempBack = this.back;
			int tempBottom = this.bottom;
			int tempUp = this.up;

			this.bottom = tempBack;
			this.up = tempFront;
			this.front = tempBottom;
			this.back = tempUp;

		}

	}

}