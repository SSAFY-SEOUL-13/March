import java.util.*;
import java.io.*;

public class Main {
	static List<Character>[] Gear; // index로 2번과 6번에 접근하기 위해 Deque가 아닌 lst를 썼습니다.
	static boolean[] Checked = new boolean[4];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Gear = new LinkedList[4];
		StringTokenizer st;
		for (int i = 0; i < 4; i++) {
			char[] chars = br.readLine().toCharArray();
			Gear[i] = new LinkedList<Character>();
			for (int j = 0; j < 8; j++) {
				Gear[i].add(chars[j]);
			}
		}

		int N = Integer.parseInt(br.readLine());
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			int StartGearNum = Integer.parseInt(st.nextToken());
			boolean IsClockWise = (Integer.parseInt(st.nextToken()) == 1) ? true : false;
			Turn(StartGearNum - 1, IsClockWise);
			Arrays.fill(Checked, false);

		}

		System.out.println(Score());

	}

	public static void Turn(int index, boolean direction) {
		Checked[index] = true;
		Splash(index, direction); // 톱니를 돌리기 전에 맞닿은 톱니의 확인을 먼저 해줘야 합니다.
		if (direction) {
			TurnClockWise(Gear[index]);
		} else {
			TurnCounterClockWise(Gear[index]);
		}

	}

	public static void TurnClockWise(List<Character> lst) {
		char temp = lst.remove(lst.size() - 1); // pollLast
		lst.add(0, temp); // addFirst

	}

	public static void TurnCounterClockWise(List<Character> lst) {
		char temp = lst.remove(0); // pollFirst
		lst.add(temp); // addLast

	}

	public static void Splash(int now, boolean direction) {
		// 왼쪽
		if (0 <= now - 1 && !Checked[now - 1]) {
			Checked[now - 1] = true;
			if (Gear[now - 1].get(2) != Gear[now].get(6)) {
				Turn(now - 1, !direction);
			}
		}
		// 오른쪽
		if (now + 1 < 4 && !Checked[now + 1]) {
			Checked[now + 1] = true;
			if (Gear[now].get(2) != Gear[now + 1].get(6)) {
				Turn(now + 1, !direction);
			}

		}
	}

	public static int Score() {
		int score = 0;
		for (int i = 0; i < Gear.length; i++) {
			if (Gear[i].get(0) == '1')
				score += Math.pow(2, i);
		}
		return score;
	}
}