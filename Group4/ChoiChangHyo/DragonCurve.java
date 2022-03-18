import java.util.*;

public class Main {
	static int[] dx = { 1, 0, -1, 0 };
	static int[] dy = { 0, -1, 0, 1 };
	static boolean[][] board;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		board = new boolean[101][101];
		int N = sc.nextInt();
		for (int i = 0; i < N; i++) {
			int x = sc.nextInt();
			int y = sc.nextInt();
			int d = sc.nextInt();
			int age = sc.nextInt();
			DragonCurve(x, y, d, age);
		}
		System.out.println(Score());
	}

	public static void DragonCurve(int x, int y, int d, int age) {
		List<pos> lst = new LinkedList<pos>();
		// 0세대
		lst.add(new pos(x, y));
		lst.add(new pos(x + dx[d], y + dy[d]));

		for (int a = 1; a <= age; a++) {
			int size = lst.size();
			pos Std = lst.get(size - 1);// 기준점
			for (int i = size - 1 - 1; i >= 0; i--) { // 기준점을 제외한 lst의 모든 원소를 의미합니다.
				lst.add(Turn(Std, lst.get(i))); // 회전한 좌표를 lst에 넣습니다.
			}
		}

		// 드래곤커브를 완성 후 board에 좌표를 표시하는 작업입니다.
		for (int i = 0; i < lst.size(); i++) {
			pos temp = lst.get(i);
			if (0 <= temp.x && temp.x < 101 && 0 <= temp.y && temp.y < 101) {
				board[temp.x][temp.y] = true;
			}
		}

	}

	public static pos Turn(pos Std, pos Mov) {
		int xDist = Math.abs(Std.x - Mov.x);
		int yDist = Math.abs(Std.y - Mov.y);
		// 중심축을 기준으로 비교대상이 어디 위치하느냐에 따라 결과값이 다릅니다.
		if (Mov.x >= Std.x && Mov.y > Std.y) { // 좌표계 회전에 첨부한 그림을 기준으로 회전할 좌표가 4사분면에 있으면
			return (new pos(Std.x - yDist, Std.y + xDist));
		}
		if (Mov.x < Std.x && Mov.y >= Std.y) { // 좌표계 회전에 첨부한 그림을 기준으로 회전할 좌표가 3사분면에 있으면
			return (new pos(Std.x - yDist, Std.y - xDist));
		}
		if (Mov.x <= Std.x && Mov.y < Std.y) { // 좌표계 회전에 첨부한 그림을 기준으로 회전할 좌표가 2사분면에 있으면
			return (new pos(Std.x + yDist, Std.y - xDist));
		}
		if (Mov.x > Std.x && Mov.y <= Std.y) { // 좌표계 회전에 첨부한 그림을 기준으로 회전할 좌표가 1사분면에 있으면
			return (new pos(Std.x + yDist, Std.y + xDist));
		}
		return Std;
	}

	public static int Score() {
		int cnt = 0;
		for (int i = 0; i <= 99; i++) {
			for (int j = 0; j <= 99; j++) {
				if (board[i][j] && board[i + 1][j] && board[i][j + 1] && board[i + 1][j + 1])
					cnt++;
			}
		}
		return cnt;

	}

	static class pos {
		int x;
		int y;

		public pos(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "pos [x=" + x + ", y=" + y + "]";
		}
	}
}