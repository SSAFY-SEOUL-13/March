import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_Boj_3190 {
	static LOC [] apple;
	static int N, K, L;
	static int idx = 0;
	static int x = 1; 
	static int y = 1;
	static int answer = 0;
	static int [] dx = {0, 1, 0, -1}; // 우, 하, 좌, 상
	static int [] dy = {1, 0, -1, 0}; // 우, 하, 좌, 상
	static Queue <Move> move = new LinkedList<>();
	static Deque <LOC> snake = new ArrayDeque<>();
	
	static boolean canMove() {
		// 배열의 범위를 넘어서는 경우 종료
		if(1 <= snake.getFirst().x && 1 <= snake.getFirst().y && snake.getFirst().x <= N && snake.getFirst().y <= N) return false;
		// 뱀의 머리가 자신의 몸에 닿는 경우 종료
		if(snake.contains(snake.getFirst())) return false;
		return true;
	}
	
	static boolean eatApple() {
		for(int i = 0; i < apple.length; i++) {
			if(apple[i].x == snake.getFirst().x &&  apple[i].y == snake.getFirst().y) return true;
		}
		return false;
	}
	 
	class LOC{
		int x;
		int y;
		
		LOC(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	
	class Move{
		int sec;
		char direction;
		
		Move(int sec, char direction){
			this.sec = sec;
			this.direction = direction;
		}
		
	}
	
	public Main_Boj_3190() throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		N = Integer.parseInt(br.readLine());
		K = Integer.parseInt(br.readLine());

		apple = new LOC[K];
		
		for(int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			apple[i] = new LOC(x, y);
		}
		
		L = Integer.parseInt(br.readLine());
		
		for(int i = 0; i < L; i++) {
			st = new StringTokenizer(br.readLine());
			int sec = Integer.parseInt(st.nextToken());
			char dic =  st.nextToken().charAt(0);
			move.add(new Move(sec, dic));
		}
		
		snake.addFirst(new LOC(x, y));
		
		while(canMove()) {
			answer++;
			if(move.peek().sec == answer) {
				if(move.peek().direction == 'L') {
					if(idx == 0) idx = 3;
					else idx--;
				}
				else if(move.peek().direction == 'D') {
					if(idx == 3) idx = 0;
					else idx++;
				}
			}
			
			snake.addFirst(new LOC(x + dx[idx], y + dy[idx]));
			
			if(!eatApple()) snake.removeLast();
		}
		
		System.out.println(answer);
		
	}
}
