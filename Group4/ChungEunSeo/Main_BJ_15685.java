//드래곤커브
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

//1. 다 가기 -> 간데에 1 넣기
//2. 완전탐색으로 네모 개수 확인

public class Main_BJ_15685 {
	   static boolean[][] map = new boolean[101][101];
	   //인덱스 별로 0,1,2,3 방향
	   //0: 오른쪽 x++
	   //1: 위쪽 y--
	   //2: 왼쪽 x--
	   //3: 아래쪽 y++
	    static int[] dx = {1,0,-1,0};
	    static int[] dy = {0,-1,0,1};
	 
	    public static void main(String[] args) throws Exception{
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));    
	        int N = Integer.parseInt(br.readLine());
	        
	        for(int t=0; t<N; t++) {
	            StringTokenizer st = new StringTokenizer(br.readLine());
	            int x = Integer.parseInt(st.nextToken());
	            int y = Integer.parseInt(st.nextToken());
	            int d = Integer.parseInt(st.nextToken());
	            int g = Integer.parseInt(st.nextToken());	
	            //입력
	            dragonCurve(x,y,d,g);
	        }
	        System.out.println(getNumberOfSquares());
	
	    }
	    //방향
	    //0: 오른쪽 x++
	    //1: 위쪽 y--
		//2: 왼쪽 x--
		//3: 아래쪽 y++
	    public static void dragonCurve(int x,int y, int d, int g) {
	        //각 방향을 담을 수 있는 ArrayList
	        ArrayList<Integer> arr = new ArrayList<Integer>();	        
	        int tempD = d;
	        arr.add(tempD);
	        //g세대만큼 반복
	        for(int i=1; i<=g; i++) {
	            //맨 뒤에서부터 접근
	            for(int j=arr.size()-1; j>=0; j--) {
	                //나누기 연산자를 통해 0,1,2,3 방향 
	            	//(기존의 d + 1) % 4   : 3->0
	                tempD = (arr.get(j) + 1)%4;
	                arr.add(tempD);
	            }
	        }	        
	        int tempX = x;
	        int tempY = y;        
	        for(int i=0; i<arr.size(); i++) {
	        	//가는데에 true넣어줌
	            map[tempY][tempX] = true;
	            tempX += dx[arr.get(i)];
	            tempY += dy[arr.get(i)];
	        }
	        //마지막에도 넣으줌
	        map[tempY][tempX] = true;
	    }
	    
	    //완전탐색으로 네모가 됐는지 확인
	    //네 꼭짓점이 모두 드래곤 커브의 일부인지
	    private static int getNumberOfSquares() {
	    	int count = 0;
	    	for (int x = 0; x < 100; x++) {
	    		for (int y = 0; y < 100; y++) {
	    			if (map[x][y] && map[x + 1][y] && map[x][y + 1] && map[x + 1][y + 1])
	    				count++;
	    		}
	    	}
	    	return count;
	    }


}
