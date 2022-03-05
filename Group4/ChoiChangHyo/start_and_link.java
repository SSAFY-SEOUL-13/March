import java.util.*;

public class Main {
	static int N, T1, T2, answer;
	static int[] our_team, total_memb;
	static int[][] board;

	public static void main(String[] args) {

		// 입력값 세팅
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		board = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				board[i][j] = sc.nextInt();
			}
		}
		answer = Integer.MAX_VALUE;

		total_memb = new int[N];
		our_team = new int[N / 2];
		comb(0, 0);
		System.out.println(answer);
	}

	public static void comb(int depth, int start) {
		if (depth == N / 2) {

			// 선택받지 못한 팀(enem_team) 배열을 구합니다.
			List<Integer> total_memb = new ArrayList<Integer>();
			for (int i = 0; i < N; i++) {
				total_memb.add(i);
			}
			for (int i = 0; i < N / 2; i++) {
				total_memb.remove(new Integer(our_team[i]));
			}
			int[] enem_team = new int[N / 2];
			for (int i = 0; i < N / 2; i++) {
				enem_team[i] = total_memb.get(i);
			}

			// 팀에서 2명씩 뽑아 두사람의 시너지를 전체 시너지(T1 혹은 T2)에 더합니다.
			comb2(0, 0, our_team, new int[2], "T1");
			comb2(0, 0, enem_team, new int[2], "T2");

			// 전역변수로 선언했기 때문에 다른 조합에 사용하기 위해 0으로 초기화 합니다.
			T1 = 0;
			T2 = 0;

			// 두 팀의 시너지 합의 차가 가장 적은 경우를 answer로 지정합니다.
			answer = Math.min(answer, Math.abs(T1 - T2));
			return;
		}
		for (int i = start; i < N; i++) {
			our_team[depth] = i;
			comb(depth + 1, i + 1);
		}
	}

	public static void comb2(int depth, int start, int[] our_team, int[] pair, String team) { // 한 팀에서 2명을 뽑아 둘의 시너지를
																								// 계산합니다.
		if (depth == 2) {
			if (team.equals("T1")) {
				T1 += board[our_team[pair[0]]][our_team[pair[1]]];
				T1 += board[our_team[pair[1]]][our_team[pair[0]]];
			} else {
				T2 += board[our_team[pair[0]]][our_team[pair[1]]];
				T2 += board[our_team[pair[1]]][our_team[pair[0]]];
			}
			return;
		}
		for (int i = start; i < our_team.length; i++) {
			pair[depth] = i;
			comb2(depth + 1, i + 1, our_team, pair, team);
		}
	}
}

/*
나머지 선택군을 쉽게 구하는 방법
N/2개를 선택한 뒤 2개를 추출하는 방법
*/