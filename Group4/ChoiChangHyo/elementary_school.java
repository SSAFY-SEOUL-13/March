import java.io.*;
import java.util.*;

public class Main {
	static int[] dx = {0,1,0,-1};
	static int[] dy = {1,0,-1,0};
	static int N;
	static HashMap<Integer,List<Integer>> map;
	static int[][] board;
	static int max_score;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		board = new int[N][N];
		map = new HashMap<Integer,List<Integer>>();
		
		int[] key_lst = new int[N*N]; // hashmap에서는 입력순서가 유지되지 않아 별도로 입력 순서를 기억하는 리스트를 만들었습니다.
		
		// 데이터 입력
		for (int i = 0; i < N*N; i++) {
			String[] temp = br.readLine().split(" ");
			List<Integer> lst = new ArrayList<Integer>();
			for (int j = 1; j < 5; j++) {
				lst.add(Integer.parseInt(temp[j]));
			}
			key_lst[i] = Integer.parseInt(temp[0]);
			map.put(Integer.parseInt(temp[0]), lst);
		}
		
		
		board[1][1] = key_lst[0]; // board에 아무런 데이터가 존재하지 않는 1번친구는 반드시 (1,1)에 앉게 됩니다.
		
		for (int i = 1; i < N*N; i++) { // 2번친구부터 입장합니다.
			int val = key_lst[i];
			int max_val = Integer.MIN_VALUE; // i친구가 모든 자리에 앉아보면서 선호도를 갱신합니다.
			int max_x=0;
			int max_y=0;
			int empty_space=0;

			// 현재의 board상황에서 모든 자리를 다 앉아보면서 조건에 맞는 곳을 선택합니다.
			for (int x = 0; x < N; x++) {
				for (int y = 0; y < N; y++) {
					if(board[x][y] != 0) continue; //이미 앉아있는 자리는 검사하지 않습니다.
					int empty = 0;
					int nx = 0;
					int ny = 0;
					// 선호도가 동일할 때에는 빈공간이 많은 곳에 앉아야 합니다. 그걸 위해 (x,y)에서의 빈공간을 미리 구합니다.
					for (int d = 0; d < 4; d++) {
						nx = x+dx[d];
						ny = y+dy[d];
						if(0<= nx && nx < N && 0<= ny && ny < N && board[nx][ny] == 0)empty++;
					}
					
					if(preference(val,x,y,N)>max_val ) { // 현재 자리에서의 선호도가 가장 높다면 지금까지의 후보 중 가장 좋은 자리는 (x,y)가 됩니다.
						max_val = preference(val,x,y,N);
						max_x = x;
						max_y = y;
						empty_space = empty;
					} else if(preference(val,x,y,N)==max_val && empty > empty_space) { // 선호도가 동일할 때에는 빈공간이 많은 곳을 선택합니다.						
						max_val = preference(val,x,y,N);
						max_x = x;
						max_y = y;
						empty_space = empty;						
					}
					// 3번조건(빈공간도 동일하면 행,열을 기준)은 (0,0)->(N,N)으로 순차탐색하면서 자연스럽게 고려됩니다.
				}
			}
			max_score += max_val;
			board[max_x][max_y] = val;
		}
		System.out.println(score(board,N));
	}
	
	
	public static int preference(int val,int x, int y, int N) {
		int pair_cnt = 0;
		for (int d = 0; d < 4; d++) {
			int nx = x+dx[d];
			int ny = y+dy[d];
			// board안에 포함되며 거기 앉은 친구가 제가 선호하는 친구라면 1점을 더합니다.
			if(0<=nx && nx < N && 0<= ny && ny < N && map.get(val).contains(board[nx][ny])) pair_cnt++;
			}
		
		return pair_cnt;

		
	}
	// 최종점수를 계산합니다.
	public static int score(int[][]board,int N) {
		int total = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				int cnt = 0 ;
				int val = board[i][j];
				for (int d = 0; d < 4; d++) {
					int nx = i+dx[d];
					int ny = j+dy[d];
					if(0<=nx && nx < N && 0<= ny && ny < N && map.get(val).contains(board[nx][ny])) cnt++;
				}
				total+=(cnt==0)?0:Math.pow(10,(cnt-1)); 
			}
		}
		return total;
	}
	
	
}