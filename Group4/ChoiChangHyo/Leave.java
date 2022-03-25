import java.util.*;

/*
N의 값이 작아 이분탐색과 백트래킹이 떠올랐습니다.
현재가 i일이고 i일에 예약된 상담을 퇴사일 이전에 끝낼 수 있다면 해당 상담은 진행 가능합니다.
	if(i + Ti(lst.get(i)[0])<N) // 진행해도됨
	진행이 가능할 뿐 해당 상담이 반드시 최대이익을 가져오는 건 아닙니다.
진행가능한 상담의 조합을 모두 만들어서 해당 금액이 최대금액인지 확인합니다.
*/

public class Main {
	static int N, Sum, answer;
	static List<int[]> lst;
	static boolean[] v;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		lst = new ArrayList<int[]>();
		for (int i = 0; i < N; i++) {
			int t = sc.nextInt();
			int p = sc.nextInt();
			int[] temp = { t, p };
			lst.add(temp);
		}

		for (int i = 0; i < N; i++) {
			v = new boolean[N];
			Sum = 0;
			BackT(i);
		}
		System.out.println(answer);
	}

	public static void BackT(int start) {
		answer = Math.max(answer, Sum);
		for (int i = start; i < N; i++) {
			if (i + lst.get(i)[0] <= N) { // 종료 조건
				Sum += lst.get(i)[1];
				BackT(i + lst.get(i)[0]);
				Sum -= lst.get(i)[1];
			}

		}
	}

}