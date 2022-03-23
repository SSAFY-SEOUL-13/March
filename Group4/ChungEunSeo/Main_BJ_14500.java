//테트로미노
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

//dfs로 하고싶은데 ㅗ 때문에 잘모르겟음
//나올수 있는 모든 경우에 합 구하고 갱신.. 
//범위를 넘어가는 경우

public class Main_BJ_14500 {
	static int map [][];
	static int x, y;
	static int max=0;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String p = br.readLine();
		StringTokenizer st = new StringTokenizer(p, " ");
		y = Integer.parseInt(st.nextToken());
		x = Integer.parseInt(st.nextToken());
		//범위 벗어나게 블록 놔지는 경우 있음
		//왼쪽 +3 오른쪽 +3 아래 +3 위 +3 해서 거기에 0으로 채움
		map = new int[x+6][y+6];
		/*
		0 0 0
		0 1 0
		0 0 0
		 */
		//기존 y보다3크게 3번째칸부터 채워넣기 (3,3)
		for(int i = 3; i<y+3; i++){
			p = br.readLine();
			st = new StringTokenizer(p, " ");
			for(int j = 3; j<x+3; j++){
				map[j][i] = Integer.parseInt(st.nextToken());
			}
		}//입력
		
		for(int j=0; j<x+3; j++){
			for(int i=0; i<y+3; i++){
				sumCnt(j, i);
			}
		}
		System.out.println(max);

		}
	
	//합 구하는 함수
	//모든 경우 합 비교하기..
	static void sumCnt(int a, int b){
		int sum=0;
		//1. ㅣ모양
		sum += map[a][b];
		sum += map[a+1][b];
		sum += map[a+2][b];
		sum += map[a+3][b];
		if(max<sum){
			max = sum;
		}
		//1. ㅡ 모양
		sum=0;
		sum += map[a][b];
		sum += map[a][b+1];
		sum += map[a][b+2];
		sum += map[a][b+3];
		if(max<sum){
			max = sum;
		}
		// 2. ㅁ 모양
		sum=0;
		sum += map[a][b];
		sum += map[a+1][b];
		sum += map[a+1][b+1];
		sum += map[a][b+1];
		if(max<sum){
			max = sum;
		}
		//3. ㄴ 모양 (8개 경우)
		// 3. ㄴ 모양 (왼쪽에서 시작)
		sum=0;
		sum += map[a][b];
		sum += map[a+1][b];
		sum += map[a+2][b];
		sum += map[a+2][b+1];
		if(max<sum){
			max = sum;
		}
		// 3. ㄴ 모양 (위에랑 대칭)
		sum=0;
		sum += map[a][b+1];
		sum += map[a+1][b+1];
		sum += map[a+2][b+1];
		sum += map[a+2][b];
		if(max<sum){
			max = sum;
		}
		// 3. ㄴ 모양 (왼쪽 아래시작)
		sum=0;
		sum += map[a][b];
		sum += map[a+1][b];
		sum += map[a][b+1];
		sum += map[a][b+2];
		if(max<sum){
			max = sum;
		}
		// 3. ㄴ모양 (위에랑 대칭)
		sum=0;
		sum += map[a][b];
		sum += map[a+1][b+2];
		sum += map[a][b+1];
		sum += map[a][b+2];
		if(max<sum){
			max = sum;
		}
		// 3. ㄴ (왼쪽 위 시작 시계방향)
		sum=0;
		sum += map[a][b];
		sum += map[a][b+1];
		sum += map[a+1][b+1];
		sum += map[a+2][b+1];
		if(max<sum){
			max = sum;
		}
		// 3. ㄴ (위랑대칭)
		sum=0;
		sum += map[a][b];
		sum += map[a][b+1];
		sum += map[a+1][b];
		sum += map[a+2][b];
		if(max<sum){
			max = sum;
		}
		// 3. ㄴ case // 오른 상단시작 왼 하단 종료
		sum=0;
		sum += map[a][b+2];
		sum += map[a+1][b+2];
		sum += map[a+1][b+1];
		sum += map[a+1][b];
		if(max<sum){
			max = sum;
		}
		//3
		sum=0;
		sum += map[a+1][b+2];
		sum += map[a+1][b+1];
		sum += map[a+1][b];
		sum += map[a][b];
		if(max<sum){
			max = sum;
		} // 3 종료
		
		//4. ㄴㄱ 모양 - 4가지
		// ㄴㄱ
		sum=0;
		sum += map[a][b];
		sum += map[a+1][b];
		sum += map[a+1][b+1];
		sum += map[a+2][b+1];
		if(max<sum){
			max = sum;
		}
		//ㄴㄱ
		sum=0;
		sum += map[a][b+2];
		sum += map[a][b+1];
		sum += map[a+1][b+1];
		sum += map[a+1][b];
		if(max<sum){
			max = sum;
		}
		// ㄴㄱ
		sum=0;
		sum += map[a+2][b];
		sum += map[a+1][b];
		sum += map[a+1][b+1];
		sum += map[a][b+1];
		if(max<sum){
			max = sum;
		}
		// ㄱㄴ
		sum=0;
		sum += map[a][b];
		sum += map[a][b+1];
		sum += map[a+1][b+1];
		sum += map[a+1][b+2];
		if(max<sum){
			max = sum;
		}//4 끝
		
		// 5. ㅗ 모양 - 4가지
		//ㅜ
		sum=0;
		sum += map[a][b];
		sum += map[a][b+1];
		sum += map[a][b+2];
		sum += map[a+1][b+1];
		if(max<sum){
			max = sum;
		}
		//ㅓ
		sum=0;
		sum += map[a][b+1];
		sum += map[a+1][b+1];
		sum += map[a+2][b+1];
		sum += map[a+1][b];
		if(max<sum){
			max = sum;
		}
		//ㅗ
		sum=0;
		sum += map[a+1][b];
		sum += map[a+1][b+1];
		sum += map[a+1][b+2];
		sum += map[a][b+1];
		if(max<sum){
		max = sum;
		}
		//ㅏ
		sum=0;
		sum += map[a][b];
		sum += map[a+1][b];
		sum += map[a+2][b];
		sum += map[a+1][b+1];
		if(max<sum){
			max = sum;
			}
		}



}
