import java.util.*;

class Main {
	static int MaxVal = 0;
	static int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] dy = { 0, -1, -1, -1, 0, 1, 1, 1 };

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		List<Fish> lst = new LinkedList<Fish>();
		Fish[][] board = new Fish[4][4];
		int Score = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int num = sc.nextInt();
				int d = sc.nextInt();
				if (i == 0 && j == 0) {
					Score += num;
					Fish f = new Fish(i, j, 99, d - 1);
					board[i][j] = f;
				} else {
					Fish f = new Fish(i, j, num, d - 1);
					board[i][j] = f;
				}
			}
		}

		BackT(0, board[0][0], board, Score, lst);

		System.out.println(MaxVal);

	}

	public static void BackT(int depth, Fish shark, Fish[][] board, int Score, List<Fish> lst) {

		Init(lst, board);
		shark = lst.get(15);
		OneCycle(lst, board);

		List<int[]> fd = Food(shark, board);
		if (fd.size() == 0) {
			MaxVal = Math.max(MaxVal, Score);
			return;
		}

		for (int i = 0; i < fd.size(); i++) {
        	// food_x와 food_y는 잡아먹힐 물고기의 좌표입니다.
			int food_x = fd.get(i)[0];
			int food_y = fd.get(i)[1];
			if (board[food_x][food_y].num == 98)
				continue;

			int foodD = board[food_x][food_y].d;
			int foodNum = board[food_x][food_y].num;
			int[] temp = { shark.x, shark.y };

			// 깊은 복사
			Fish[][] CopyBoard = new Fish[4][4];
			for (int j = 0; j < 4; j++) {
				for (int j2 = 0; j2 < 4; j2++) {
					CopyBoard[j][j2] = new Fish(board[j][j2].x, board[j][j2].y, board[j][j2].num, board[j][j2].d);
				}
			}

			// 상어가 temp위치에 있는 물고기를 먹습니다.
			Swap(temp, fd.get(i), board);

			// shark.x와 shark.y는 '물고기를 잡아먹고 이동한 뒤 상어의 위치'가 아니라 '입력당시 상어의 위치' 입니다.
			board[shark.x][shark.y].num = 98; // temp를 잡아먹기 전 상어가 있던 위치는 빈 칸(98)이 됩니다.
			board[shark.x][shark.y].d = shark.d;
			board[food_x][food_y].d = foodD; // 물고기를 잡아먹었기 때문에 food_x,food_y에는 상어가 있습니다. 상어의 방향을 원래 있던 물고기 방향으로 갱신합니다.

			// 점수 갱신
			Score += foodNum;

			// 다음 먹이 찾기
			BackT(depth + 1, board[food_x][food_y], board, Score, lst);

			// 이동 전으로 롤백
			for (int j = 0; j < 4; j++) {
				for (int j2 = 0; j2 < 4; j2++) {
					board[j][j2] = new Fish(CopyBoard[j][j2].x, CopyBoard[j][j2].y, CopyBoard[j][j2].num,
							CopyBoard[j][j2].d);
				}
			}

			Score -= foodNum;

		}

	}
    // 상어의 현재 위치 및 방향에서 먹을 수 있는 물고기들을 구합니다.
	public static List<int[]> Food(Fish shark, Fish[][] board) {
		List<int[]> fd = new ArrayList<int[]>();
		int x = shark.x;
		int y = shark.y;

		while (true) {
			int nx = x + dx[shark.d];
			int ny = y + dy[shark.d];
			if (0 > nx || nx >= 4 || 0 > ny || ny >= 4) {
				break;
			}
			x = nx;
			y = ny;
			if (board[x][y].num == 98)
				continue;
			int[] temp = { x, y };
			fd.add(temp);
		}
		return fd;
	}

	// 상어를 제외한 모든 물고기를 움직입니다.
	public static void OneCycle(List<Fish> lst, Fish[][] board) { 
		for (int i = 0; i < 15; i++) {
			Fish f = lst.get(i);
			Move(f, board);
			Init(lst, board);
		}
	}

	// 물고기 하나를 움직입니다.
	public static void Move(Fish f, Fish[][] board) {
		if (f.num == 98) return; // 빈칸은 물고기가 아닙니다.
		for (int dd = 0; dd < 8; dd++) {
			int nd = (f.d + dd) % 8;
			int nx = f.x + dx[nd];
			int ny = f.y + dy[nd];
			if (0 <= nx && nx < 4 && 0 <= ny && ny < 4 && board[nx][ny].num != 99) {
				f.d = nd;
				int[] b1 = { f.x, f.y };
				int[] b2 = { nx, ny };
				Swap(b1, b2, board);
				return;
			}
		}

	}

	//b1위치에 있는 물고기와 b2위치에 있는 물고기의 위치를 서로 바꿉니다.
	public static void Swap(int[] b1, int[] b2, Fish[][] board) {
		Fish f1 = board[b1[0]][b1[1]];
		Fish f2 = board[b2[0]][b2[1]];

		board[f2.x][f2.y] = new Fish(f2.x, f2.y, f1.num, f1.d);
		board[f1.x][f1.y] = new Fish(f1.x, f1.y, f2.num, f2.d);

	}

	// board를 기준으로 lst의 값을 다시 갱신합니다.
	public static void Init(List<Fish> lst, Fish[][] board) {
		lst.clear();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				lst.add(board[i][j]);
			}
		}
		Collections.sort(lst);
	}

	static class Fish implements Comparable<Fish> {
		int x;
		int y;
		int num;
		int d;

		public Fish(int x, int y, int num, int d) {
			super();
			this.x = x;
			this.y = y;
			this.num = num;
			this.d = d;
		}

		@Override
		public String toString() {
			return "Fish [x=" + x + ", y=" + y + ", num=" + num + ", d=" + d + "]";
		}

		@Override
		public int compareTo(Fish o) {
			return this.num - o.num;
		}

	}
}