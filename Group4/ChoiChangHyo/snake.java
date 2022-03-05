import java.io.*;
import java.util.*;

public class Main {
	static int[] dx = { 0, 1, 0, -1 }; // 우 하 좌 상
	static int[] dy = { 1, 0, -1, 0 };
	static int N, time;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		int[][] board = new int[N][N]; // 보드를 만듭니다.
		int K = Integer.parseInt(br.readLine());
		// 보드에 사과를 놓습니다.(1이면 사과가 놓여있다는 뜻입니다)
		StringTokenizer st = null;
		for (int k = 0; k < K; k++) {
			st = new StringTokenizer(br.readLine(), " ");
			int row = Integer.parseInt(st.nextToken());
			int col = Integer.parseInt(st.nextToken());
			board[row - 1][col - 1] = 1; // 입력은 인덱스가 1부터 시작하므로 1을 뺐습니다.
		}
		int L = Integer.parseInt(br.readLine()); 
		Deque<turn> deq = new LinkedList<turn>(); // 방향변환정보를 담을 변수입니다.
		
		// 방향변환정보를 deq에 담습니다.
		for (int l = 0; l < L; l++) {
			st = new StringTokenizer(br.readLine());
			int X = Integer.parseInt(st.nextToken());
			char C = st.nextToken().charAt(0);
			deq.add(new turn(X, C));
		}
		
		// 뱀을 만듭니다.
		Snake snake = new Snake(1, 0, 0, new LinkedList<int[]>());

		// 시작점을 설정합니다
		int[] start = { 0, 0 };
		snake.trace.add(start);
		
		int d = 0; // 현재 진행방향을 나타내는 변수입니다.
		while (true) {
			// 시간이 흐릅니다.
			++time;
			
			// 현재방향(d)으로 전진합니다.
			int nx = snake.x + dx[d];
			int ny = snake.y + dy[d];
			int[] temp = { nx, ny };
			
			// 보드를 벗어나거나 자신의 몸에 닿으면 종료합니다.
			if (0 > nx || nx >= N || 0 > ny || ny >= N || touch_test(snake, temp)) break;

			// 보드에 사과가 있으면 사과를 먹고 길이가 1 증가합니다.
			if (board[nx][ny] == 1) {
				snake.length++;
				board[nx][ny] = 0;
			}
			
			// 뱀의 위치를 변경시켜줍니다.
			snake.x = nx; 
			snake.y = ny;
			
			// 사과를 먹으면 뱀의 길이가 길어지는 게 아니라 머리가 움직인 칸을 더 많이 기억하도록(?) 구현했습니다.
			// 이전에 움직였던 좌표들을 뱀의 길이만큼 기억하게 했습니다.
			// snake.trace의 길이를 항상 snake.length로 유지합니다.
			if (snake.length > snake.trace.size()) {
				snake.trace.add(temp);
			} else if (snake.length == snake.trace.size()) { 
				snake.trace.remove(0); // 기존의 값을 지우고
				snake.trace.add(temp); // 새로운 값을 넣습니다.
			}

			// 방향변환정보를 체크합니다.
			if (!deq.isEmpty() && time == deq.peekFirst().X) { // 현재시간이 X초라면 방향을 변경합니다
				if (deq.peekFirst().C == 'D') { // D면
					d = (d + 1) % 4; // 오른쪽으로 돕니다.
				} else { // L이면
					d = (4 + d - 1) % 4; // 왼쪽으로 돕니다.
				}
				deq.pollFirst(); // 방향을 변환했기 때문에 deq에서 제거합니다.
			}

		}
		System.out.println(time);
	}
	
	// 뱀이 자신의 몸에 닿았는지 확인하는 메서드 입니다.
	public static boolean touch_test(Snake snake, int[] temp) { 
		int x = temp[0];
		int y = temp[1];
		// 처음에 contains메서드를 썼는데 각자가 다른 객체라 원하는 결과값이 나오지 않았습니다.
		// 그래서 for문을 통해 각각의 값을 비교하는 방법으로 변경했습니다.
		for (int i = 0; i < snake.trace.size(); i++) {  
			int tx = snake.trace.get(i)[0];
			int ty = snake.trace.get(i)[1];
			if (x == tx && y == ty)
				return true;
		}
		return false;
	}
	
	// 방향변환 변수를 담는 객체입니다.
	static class turn { 
		int X;
		char C;

		public turn(int X, char C) {
			super();
			this.X = X;
			this.C = C;
		}

		@Override
		public String toString() {
			return "turn [X=" + X + ", C=" + C + "]";
		}
	}

	// 뱀의 길이(length), 위치(x,y), 현재 닿아있는 칸(trace) 변수를 담은 객체입니다. 
	static class Snake {
		int length;
		int x;
		int y;
		
		List<int[]> trace; // 뱀의 길이가 3이면 현재 뱀이 밟고있는 위치는 현재의(x,y), 1초 전의(x,y), 2초 전의(x,y)가 됩니다.
		

		public Snake(int length, int x, int y, List<int[]> trace) {
			super();
			this.length = length;
			this.x = x;
			this.y = y;
			this.trace = trace;
		}

	}
}