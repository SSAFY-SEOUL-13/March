import java.util.*;

public class Main {
	static int N, M;
	static int[][] board;
	static int highscore = Integer.MIN_VALUE;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		board = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				board[i][j] = sc.nextInt();
			}
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				List<block> block_lst = new ArrayList<block>();
				// 문제에서 알려준 5가지 블록
				block_lst.add(new block(new pos(i, j), new pos(i, j + 1), new pos(i, j + 2), new pos(i, j + 3)));
				block_lst.add(new block(new pos(i, j), new pos(i, j + 1), new pos(i + 1, j), new pos(i + 1, j + 1)));
				block_lst.add(new block(new pos(i, j), new pos(i + 1, j), new pos(i + 2, j), new pos(i + 2, j + 1)));
				block_lst.add(new block(new pos(i, j), new pos(i + 1, j), new pos(i + 1, j + 1), new pos(i + 2, j + 1)));
				block_lst.add(new block(new pos(i, j), new pos(i, j + 1), new pos(i, j + 2), new pos(i + 1, j + 1)));
				
				// 문제의 그림에는 없지만 필요한 블록
				block_lst.add(new block(new pos(i, j), new pos(i + 1, j), new pos(i + 2, j), new pos(i + 2, j - 1)));
				block_lst.add(new block(new pos(i, j), new pos(i + 1, j), new pos(i + 1, j - 1), new pos(i + 2, j - 1)));

				for (block b : block_lst) { // 모든 블록을 대입해 봅니다.
					List<block> lst = turn(b); // 블록을 돌립니다.
					for (block blo : lst) { // 돌린 블록을 하나씩 꺼냅니다.
						if (validate(blo)) { // 블록이 board좌표를 벗어나지 않으면 
							highscore = Math.max(highscore, score(blo)); // 점수를 계산해 최댓값갱신을 시도합니다.
						}
					}
				}
			}
		}
		System.out.println(highscore);
	}

	public static int score(block b) { // 블록을 놓았을 때 얻는 점수입니다.
		return board[b.zero.x][b.zero.y] + board[b.one.x][b.one.y] + board[b.two.x][b.two.y]
				+ board[b.three.x][b.three.y];
	}

	public static boolean validate(block b) {
		// 블록의 4좌표가 모두 board안에 있어야 합니다.
		if (0 <= b.zero.x && b.zero.x < N && 0 <= b.zero.y && b.zero.y < M && 0 <= b.one.x && b.one.x < N
				&& 0 <= b.one.y && b.one.y < M && 0 <= b.two.x && b.two.x < N && 0 <= b.two.y && b.two.y < M
				&& 0 <= b.three.x && b.three.x < N && 0 <= b.three.y && b.three.y < M) {
			return true;
		}
		return false;
	}

	public static List<block> turn(block b) { // 4방향으로 도형을 돌립니다.
		// 회전해도 동일한 모양의 블록이 있기 때문에 Set을 쓰면 시간이 단축될 거라 생각했는데 HashSet을 쓰면 오히려 시간초과가 발생했습니다.
		List<block> lst = new ArrayList<block>(); 
		// 중심
		int x = b.zero.x; 
		int y = b.zero.y;
		// 첫번째 점과 중심과의 거리
		int a1 = (b.one.x - x);
		int b1 = (b.one.y - y);
		// 두번째 점과 중심과의 거리
		int a2 = (b.two.x - x);
		int b2 = (b.two.y - y);
		// 세번째 점과 중심과의 거리
		int a3 = (b.three.x - x);
		int b3 = (b.three.y - y);

		// a1~b3까지의 값을 계산할 때 절댓값을 붙이면 원하는 정답이 나오지 않습니다.
		
		// 회전해서 생긴 도형을 lst에 담습니다.
		lst.add(b);
		lst.add(new block(new pos(x, y), new pos(x - b1, y + a1), new pos(x - b2, y + a2), new pos(x - b3, y + a3)));
		lst.add(new block(new pos(x, y), new pos(x - a1, y - b1), new pos(x - a2, y - b2), new pos(x - a3, y - b3)));
		lst.add(new block(new pos(x, y), new pos(x + b1, y - a1), new pos(x + b2, y - a2), new pos(x + b3, y - a3)));

		return lst;
	}

	public static class block { // 좌표 4개로 블록을 만드는 객체입니다.
		pos zero;
		pos one;
		pos two;
		pos three;

		public block(pos head, pos one, pos two, pos three) {
			super();
			this.zero = head;
			this.one = one;
			this.two = two;
			this.three = three;
		}

		@Override
		public String toString() {
			return "block [" + zero + ", " + one + ", " + two + ", " + three + "]";
		}

	}

	public static class pos { // 좌표를 나타내는 객체입니다.
		int x;
		int y;

		public pos(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "(" + x + " " + y + ")";
		}

	}