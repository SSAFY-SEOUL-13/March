import java.io.*;
import java.util.*;

public class Main {
	static int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1 };

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		int K = sc.nextInt();
		Deque<fireball>[][] board = new Deque[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				board[i][j] = new LinkedList<fireball>();
			}
		}
		// 최초 M개의 파이어볼을 생성합니다.
		for (int i = 0; i < M; i++) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			int m = sc.nextInt();
			int s = sc.nextInt();
			int d = sc.nextInt();
			board[x - 1][y - 1].addLast(new fireball(x - 1, y - 1, m, s, d, true));

		}

		// K번 움직이고 분열합니다.
		for (int k = 0; k < K; k++) {
			move(N, board);
			split(N, board);
		}

		// 최종 점수를 계산합니다.
		int score = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j].size() == 0)
					continue;
				int size = board[i][j].size();
				for (int t = 0; t < size; t++) {
					fireball val = board[i][j].poll();
					score += val.m;
				}
			}
		}
		System.out.println(score);

	}

	public static void split(int N, Deque<fireball> board[][]) {
		// move 후 모든 board칸에 대해 검사를 진행합니다.
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j].size() == 0)
					continue; // 파이어볼이 없는 칸은 지나갑니다.
				if (board[i][j].size() == 1) { // 파이어볼이 하나만 있는 칸은 split하지 않고 다음번에 움직일 수 있게 can_move만 변경해 줍니다.
					fireball f = board[i][j].pollFirst();
					f.can_move = true;
					board[i][j].addLast(f);
				} else {

					int sumM = 0;
					int sumS = 0;
					int sumD = 0;
					int oddEven = board[i][j].peekFirst().d;
					boolean token = false;
					int size = board[i][j].size();
					for (int l = 0; l < size; l++) {
						fireball f = board[i][j].pollFirst();
						sumM += f.m;
						sumS += f.s;
						sumD += f.d;
						if (!token && f.d % 2 != oddEven % 2) { // 모든 d의 합이 짝수면 될 거라 생각했는데 아니였습니다(반례 - 1+1+2 = 4로 짝수지만 모든 수가 홀수 or 짝수 X)
							token = true;
						}
					}
					if (sumM / 5 == 0) {
						// 소멸
					} else if (!token) { // 모든 d가 홀수거나 짝수면
						board[i][j].addLast(new fireball(i, j, sumM / 5, sumS / size, 0, true));
						board[i][j].addLast(new fireball(i, j, sumM / 5, sumS / size, 2, true));
						board[i][j].addLast(new fireball(i, j, sumM / 5, sumS / size, 4, true));
						board[i][j].addLast(new fireball(i, j, sumM / 5, sumS / size, 6, true));
					} else {
						board[i][j].addLast(new fireball(i, j, sumM / 5, sumS / size, 1, true));
						board[i][j].addLast(new fireball(i, j, sumM / 5, sumS / size, 3, true));
						board[i][j].addLast(new fireball(i, j, sumM / 5, sumS / size, 5, true));
						board[i][j].addLast(new fireball(i, j, sumM / 5, sumS / size, 7, true));
					}
				}

			}
		}

	}

	public static void move(int N, Deque<fireball> board[][]) {
		// board에 있는 모든 fireball을 움직입니다.
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j].size() == 0)
					continue; // fireball이 없는 칸은 패스합니다.
				int size = board[i][j].size();
				for (int t = 0; t < size; t++) { // poll로
					if (board[i][j].peekFirst().can_move) { // 움직일 수 있으면(이미 어디선가 움직여서 온 fireball이 아니라는 의미입니다)
						fireball f = board[i][j].pollFirst();
						// 음수 모듈러 연산방법을 잘 모르겠습니다.(아래는 임시방편 코드)
						int nx = (f.r + dx[f.d] * f.s >= 0) ? (f.r + dx[f.d] * f.s) % N
								: (N * N * N * N * N + (f.r + dx[f.d] * f.s)) % N;
						int ny = (f.c + dy[f.d] * f.s >= 0) ? (f.c + dy[f.d] * f.s) % N
								: (N * N * N * N * N + (f.c + dy[f.d] * f.s)) % N;
						// 방향으로 속도만큼 이동 후 해당 위치로 불꽃을 옮깁니다.
						f.r = nx;
						f.c = ny;
						f.can_move = false; // 한번 움직였기 때문에 이번턴에는 더 이상 움직일 수 없습니다.
						board[nx][ny].addLast(f);
					}

				}
			}
		}
	}

	static class fireball {
		int r;
		int c;
		int m;
		int s;
		int d;
		boolean can_move;

		public fireball(int r, int c, int m, int s, int d, boolean can_move) {
			super();
			this.r = r;
			this.c = c;
			this.m = m;
			this.s = s;
			this.d = d;
			this.can_move = can_move;
		}

		@Override
		public String toString() {
			return "fireball [r=" + r + ", c=" + c + ", m=" + m + ", s=" + s + ", d=" + d + ", can_move=" + can_move
					+ "]";
		}

	}

}