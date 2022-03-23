import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_Boj_14499_주사위굴리기 {
	static int N, M, x, y, K;
	static int [][] map;
	static int [] dx = {0, 0, 1, -1}; // 동서남북
	static int [] dy = {1, -1, 0, 0}; // 동서남북
	static int [] dice = new int[7];
	static int [] temp = new int[7];

	static void copy(){
		for(int i = 1; i < dice.length; i++){
			temp[i] = dice[i];
		}
	}

	static void moveEast(){
		copy();
		dice[1] = temp[4]; // top
		dice[6] = temp[3]; // bottom
		dice[3] = temp[1]; // east
		dice[4] = temp[6]; // west
	}

	static void moveWest(){
		copy();
		dice[1] = temp[3]; // top
		dice[6] = temp[4]; // bottom
		dice[3] = temp[6]; // east
		dice[4] = temp[1]; //west
	}

	static void moveSouth(){
		copy();
		dice[1] = temp[2]; // top
		dice[6] = temp[5]; // bottom
		dice[5] = temp[1]; // south
		dice[2] = temp[6]; // north
	}

	static void moveNorth(){
		copy();
		dice[1] = temp[5]; // top
		dice[6] = temp[2]; // bottom
		dice[5] = temp[6]; // south
		dice[2] = temp[1]; // north
	}

	static boolean valid(int idx){
		int newX = x;
		int newY = y;
		newX += dx[idx];
		newY += dy[idx];
		if(0 <= newX && 0 <= newY && newX < N && newY < M){
			x = newX;
			y = newY;
			return true;
		}
		else return false;
	}


	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		y = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		for(int i = 0; i < N; i++){
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++){
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < K; i++){
			int num = Integer.parseInt(st.nextToken());
			if(num == 1){
				if(valid(0)){
					moveEast(); // 주사위를 동쪽으로 이동
					if(map[x][y] == 0){
						map[x][y] = dice[6];
					} else {
						dice[6] = map[x][y];
						map[x][y] = 0;
					}
					sb.append(dice[1]).append('\n');
				}
			}
			else if(num == 2){
				if(valid(1)){
					moveWest(); // 주사위를 서쪽으로 이동
					if(map[x][y] == 0){
						map[x][y] = dice[6];
					} else {
						dice[6] = map[x][y];
						map[x][y] = 0;
					}
					sb.append(dice[1]).append('\n');
				}
			}
			if(num == 3){
				if(valid(3)){
					moveNorth(); // 주사위를 북쪽으로 이동
					if(map[x][y] == 0){
						map[x][y] = dice[6];
					} else {
						dice[6] = map[x][y];
						map[x][y] = 0;
					}
					sb.append(dice[1]).append('\n');
				}
			}
			if(num == 4){
				if(valid(2)){
					moveSouth(); // 주사위를 남쪽으로 이동
					if(map[x][y] == 0){
						map[x][y] = dice[6];
					} else {
						dice[6] = map[x][y];
						map[x][y] = 0;
					}
					sb.append(dice[1]).append('\n');
				}
			}

		}

		System.out.print(sb);
		br.close();
	}
}
