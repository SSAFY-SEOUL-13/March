import java.util.*;

public class Main {
	public static void main(String[] args) {
    	// 입력값 처리
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		int x = sc.nextInt();
		int y = sc.nextInt();
		int K = sc.nextInt();
		int[][] board = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				board[i][j] = sc.nextInt();
			}
		}
        // 주사위를 만듭니다.
		dice Dice = new dice(x,y,0,0,0,0,0,0);
		for (int k = 0; k < K; k++) {
			int d = sc.nextInt(); // 이동 방향
            // nx,ny는 다음 이동칸을 나타냅니다.
			int nx = 0;
			int ny = 0;
			switch (d) {
			case 1://동
            	// 동쪽으로 1칸 
				nx = Dice.x;
				ny = Dice.y+1;
                // 이동할 수 없다면 다음 움직임을 진행합니다.
				if(0>nx || nx >= N || 0>ny || ny>= M) continue; 
                // continue되지 않았다는 건 이동이 가능하다는 의미입니다.
                
                // 주사위의 좌표를 업데이트 합니다.
				Dice.x = nx;
				Dice.y = ny;
                
                // 주사위를 굴립니다.
				Dice.roll_right();
				if(board[nx][ny] == 0) { // 보드값이 0이면
					board[nx][ny] = Dice.bottom; // 보드에 주사위의 바닥값을 복사합니다.
				}else { // 보드값이 0이 아니면
					Dice.bottom = board[nx][ny]; // 보드값을 주사위 바닥에 복사합니다.
					board[nx][ny] = 0; // 보드의 값은 0이 됩니다.
				}
				System.out.println(Dice.top);
				break;
			case 2://서
				nx = Dice.x;
				ny = Dice.y-1;
				if(0>nx || nx >= N || 0>ny || ny>= M) continue;
				Dice.x = nx;
				Dice.y = ny;
				Dice.roll_left();
				if(board[nx][ny] == 0) {
					board[nx][ny] = Dice.bottom;
				}else {
					Dice.bottom = board[nx][ny];
					board[nx][ny] = 0;
				}
				System.out.println(Dice.top);
				break;
			case 3://북
				nx = Dice.x-1;
				ny = Dice.y;
				if(0>nx || nx >= N || 0>ny || ny>= M) continue;
				Dice.x = nx;
				Dice.y = ny;
				Dice.roll_back();
				if(board[nx][ny] == 0) {
					board[nx][ny] = Dice.bottom;
				}else {
					Dice.bottom = board[nx][ny];
					board[nx][ny] = 0;
				}
				System.out.println(Dice.top);
				break;
			case 4://남
				nx = Dice.x+1;
				ny = Dice.y;
				if(0>nx || nx >= N || 0>ny || ny>= M) continue;
				Dice.x = nx;
				Dice.y = ny;
				Dice.roll_front();
				if(board[nx][ny] == 0) {
					board[nx][ny] = Dice.bottom;
				}else {
					Dice.bottom = board[nx][ny];
					board[nx][ny] = 0;
				}
				System.out.println(Dice.top);
				break;
			}
		}

	}
	
	static class dice{
		int x;
		int y;
		int top;
		int bottom;
		int left;
		int right;
		int front;
		int back;
		public dice(int x, int y, int top, int bottom, int left, int right, int front, int back) {
			super();
			this.x = x;
			this.y = y;
			this.top = top;
			this.bottom = bottom;
			this.left = left;
			this.right = right;
			this.front = front;
			this.back = back;
		}
		// prev변수들은 굴리기 전의 주사위 값입니다.
		public  void roll_right() {
			int prev_top = top;
			int prev_bottom = bottom;
			int prev_left = left;
			int prev_right = right;
			int prev_front = front;
			int prev_back = back;
			right = prev_top;
			top = prev_left;
			left = prev_bottom;
			bottom = prev_right;
		}
		public  void roll_left() {
			int prev_top = top;
			int prev_bottom = bottom;
			int prev_left = left;
			int prev_right = right;
			int prev_front = front;
			int prev_back = back;
			right = prev_bottom;
			top = prev_right;
			left = prev_top;
			bottom = prev_left;
		}
		public  void roll_front() {
			int prev_top = top;
			int prev_bottom = bottom;
			int prev_left = left;
			int prev_right = right;
			int prev_front = front;
			int prev_back = back;
			front = prev_top;
			top = prev_back;
			back = prev_bottom;
			bottom = prev_front;
		}
		public  void roll_back() {
			int prev_top = top;
			int prev_bottom = bottom;
			int prev_left = left;
			int prev_right = right;
			int prev_front = front;
			int prev_back = back;
			front = prev_bottom;
			top = prev_front;
			back = prev_top;
			bottom = prev_back;
		}
		
		
		@Override
		public String toString() {
			return "dice [x=" + x + ", y=" + y + ", top=" + top + ", bottom=" + bottom + ", left=" + left + ", right="
					+ right + ", front=" + front + ", back=" + back + "]";
		}
		
	}
}