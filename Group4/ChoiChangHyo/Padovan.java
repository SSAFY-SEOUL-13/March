import java.util.*;

class Main {
	static long[] arr = new long[101];
	public static void main(String[] args) {
		arr[1] =1;
		arr[2] =1;
		arr[3] =1;
		arr[4] =2;
		arr[5] =2;
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for (int t = 0; t < T; t++) {
			System.out.println(func(sc.nextInt()));
		}
//		func(100);
//		System.out.println(Arrays.toString(arr));
	}
	
	public static long func(int N) {
		if(N <= 5) {
			return arr[N];
		}else {
			if(arr[N] == 0) {
				arr[N] = func(N-1)+func(N-5);
				return arr[N];
			}else {
				return arr[N];
			}
		}		
	}
}