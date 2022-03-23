import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_Boj_23288_주사위굴리기2 {
    static int N, M, K, A, B; // 행, 열, 이동횟수, 주사위 아랫면의 값, 연속해서 이동할 수 있는 칸의 수
    static int answer = 0; // 이동에서 획득하는 점수의 합 결과
    static int dir = 0; // 동서남북 방향 표시
    static int x = 0; // 행 위치
    static int y = 0; // 열 위치
    static int [][] map; // 지도
    static boolean [][] canMove; // 방문한 위치를 표시하는 배열
    static int [] dx = {0, 1, 0, -1}; // 동남서북
    static int [] dy = {1, 0, -1, 0}; // 동남서북
    static int [] dice = {0, 1, 2, 3, 4, 5, 6}; // 주사위 값
    static int [] temp = new int[7]; // 기존 주사위 위치에 있는 값을 저장하는 배열

    static void copy(){ // 기존 주사위의 값을 temp 배열에 복사하는 함수
        for(int i = 1; i < dice.length; i++){
            temp[i] = dice[i];
        }
    }

    static void moveEast(){
        x += dx[0];
        y += dy[0];
        copy();
        dice[1] = temp[4]; // top
        dice[6] = temp[3]; // bottom
        dice[3] = temp[1]; // east
        dice[4] = temp[6]; // west
    }

    static void moveWest(){ // 서쪽으로 이동
        x += dx[2];
        y += dy[2];
        copy();
        dice[1] = temp[3]; // top
        dice[6] = temp[4]; // bottom
        dice[3] = temp[6]; // east
        dice[4] = temp[1]; //west
    }

    static void moveSouth(){ // 남쪽으로 이동
        x += dx[1];
        y += dy[1];
        copy();
        dice[1] = temp[2]; // top
        dice[6] = temp[5]; // bottom
        dice[5] = temp[1]; // south
        dice[2] = temp[6]; // north
    }

    static void moveNorth(){ // 북쪽으로 이동
        x += dx[3];
        y += dy[3];
        copy();
        dice[1] = temp[5]; // top
        dice[6] = temp[2]; // bottom
        dice[5] = temp[6]; // south
        dice[2] = temp[1]; // north
    }

    static boolean valid(int idx){ // 주사위 이동 위치의 유효성을 검사하는 함수
        int newX = x;
        int newY = y;
        newX += dx[idx];
        newY += dy[idx];
        if(0 <= newX && 0 <= newY && newX < N && newY < M) return true;
        else return false;
    }

    static void direction1(){ // 1번 규칙(주사위의 이동 방향을 결정하는 함수)
        if(!valid(dir)){ // 유효하지 않다면 == 이동 방향에 칸이 없다면 이동 방향을 반대로 변경
            if(dir == 0 || dir == 1) dir += 2;
            else dir -= 2;
        }
    }

    static void move(){ // 이동 방향에 따라 주사위를 움직이는 함수
        switch (dir){
            case 0:
                moveEast();
                break;
            case 1:
                moveSouth();
                break;
            case 2:
                moveWest();
                break;
            case 3:
                moveNorth();
                break;
            default:
                break;
        }
    }

    static void direction2(){ // 2번 규칙(주사위의 이동 방향을 결정하는 함수)
        A = dice[6]; // 주사위의 아랫면에 있는 값을 A에 저장
        if(A > B) { // A > B인 경우 이동 방향을 90도 시계 방향으로 회전시킨다.
            dir++;
            if(dir == 4) dir = 0;
        }
        else if(A < B){ // A < B인 경우 이동 방향을 90도 반시계 방향으로 회전시킨다.
            dir--;
            if(dir < 0) dir = 3;
        }
        // 이외의 경우는 A = B인 경우밖에 없는데 이동 방향의 변화가 없기 때문에 아무 동작도 하지 않는다.
    }

    static void canMove(int r, int c){ // (r,c)에서 동서남북 방향으로 연속해서 이동할 수 있는지를 구하는 함수
        for(int i = 0; i < 4; i++){
            int newX = r + dx[i];
            int newY = c + dy[i];

            if(0 <= newX && 0 <= newY && newX < N && newY < M){
                if(map[newX][newY] == B && !canMove[newX][newY]){
                    canMove[newX][newY] = true; // 방문한 값은 표시
                    canMove(newX, newY);
                }
            }
        }
    }

    static int countMove(){ // 지도에서 방문한 칸의 개수를 구하는 함수
        int cnt = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(canMove[i][j]) cnt++;
            }
        }
        return cnt;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 행 크기
        M = Integer.parseInt(st.nextToken()); // 열 크기
        K = Integer.parseInt(st.nextToken()); // 이동 횟수

        map = new int[N][M];

        for(int i = 0; i < N; i++){ // 지도 입력
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < K; i++){ // 이동 횟수만큼 반복

            direction1();
            move();

            B = map[x][y]; // 2번 행동 => 주사위가 도착한 칸 (x,y)에 대한 점수를 획득한다.

            canMove = new boolean[N][M]; // 방문한 위치를 표시할 배열 선언
            canMove[x][y] = true; // 현재 위치는 방문했다고 표시

            direction2();
            canMove(x, y);
            answer += (B * countMove()); // (B * 이동할 수 있는 칸의 수)를 점수에 더해준다.
        }

        System.out.println(answer);
        br.close();

    }

}
