import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/*
N 1 2 3 4 5 6 7 8 9 10
P 1 1 1 2 2 3 4 5 7 9
  1+1 = 2
    1+1 = 2
      1+2 = 3
        2+2 = 4
        .......
p(n-2)+p(n-3)=p(n)

n이 100까지 => long
 */

public class Main_BJ_9461 {
	
	public static long[] pado = new long[101];
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		pado[1] = 1;
		pado[2] = 1;
		pado[3] = 1;
 
		for (int i = 4; i < 101; i++) {
			pado[i] = pado[i - 2] + pado[i - 3];
		}

		int T = Integer.parseInt(br.readLine());
		while(T-->0) {
			sb.append(pado[Integer.parseInt(br.readLine())]).append('\n');
		}
		System.out.println(sb);
	}
	
	
}
