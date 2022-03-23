import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_Boj_15685_14500_테트로미노 {
    static int N, M; // 행, 열
    static int answer = Integer.MIN_VALUE; // 최댓값
    static int [][] map; // 종이 값
    static int [][][] block = {
            {{0,0}, {0,1}, {0,2}, {0,3}} ,{{0,0},{1,0},{2,0},{3,0}}, // 일자 블럭
            { {0,0}, {0,1}, {1,0}, {1,1}}, // 정사각형
            {{0,0}, {1,0}, {2,0}, {2,1}}, {{0,0},{0,1},{0,2},{1,0}}, {{0,0},{0,1},{1,1},{2,1}}, {{0,2},{1,0},{1,1},{1,2}}, // ㄴ 모양 블럭
            { {0,0}, {1,0}, {1,1}, {2,1}}, {{0,0},{1,0},{1,1},{2,1}}, {{0,1},{0,2},{1,0},{1,1}}, // 번개모양 블럭
            { {0,0}, {0,1}, {0,2}, {1,1}}, {{0,1},{1,0},{1,1},{1,2}}, {{0,0},{1,0},{1,1},{2,0}}, {{0,1},{1,0},{1,1},{2,1}} // ㅜ 모양 블럭
    };

    static int calMax(int [][] block){ // 정방향에서 최댓값 계산
        int sum = 0;
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                sum = 0;
                for(int k = 0; k < 4; k++){
                    int r = i + block[k][0];
                    int c = j + block[k][1];
                    if(valid(r, c)){
                        sum += map[r][c];
                    }else break;
                }
                if(max < sum) max = sum;
            }
        }
        return max;
    }

    static int calMax2(int [][] block){ // 반시계 방향으로 90도 돌린 값에서 최댓값 계산
        int sum = 0;
        int max = Integer.MIN_VALUE;
        for(int i = M-1; i >= 0; i--){
            for(int j = 0; j < N; j++){
                sum = 0;
                for(int k = 0; k < 4; k++){
                    int r = i + block[k][0];
                    int c = j + block[k][1];
                    if(valid(c, r)){
                        sum += map[c][r];
                    }else break;
                }
                if(max < sum) max = sum;
            }
        }
        return max;
    }

    static int calMax3(int [][] block){ // 반시계 방향으로 180도 돌린 값에서 최댓값 계산
        int sum = 0;
        int max = Integer.MIN_VALUE;
        for(int i = N-1; i >= 0; i--){
            for(int j = M-1; j >= 0; j--){
                sum = 0;
                for(int k = 0; k < 4; k++){
                    int r = i + block[k][0];
                    int c = j + block[k][1];
                    if(valid(r, c)){
                        sum += map[r][c];
                    }else break;
                }
                if(max < sum) max = sum;
            }
        }
        return max;
    }

    static int calMax4(int [][] block){ // 반시계 방향으로 270도 돌린 값에서 최댓값 계산
        int sum = 0;
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < M; i++){
            for(int j = N-1; j >= 0; j--){
                sum = 0;
                for(int k = 0; k < 4; k++){
                    int r = i + block[k][0];
                    int c = j + block[k][1];
                    if(valid(c, r)){
                        sum += map[c][r];
                    }else break;
                }
                if(max < sum) max = sum;
            }
        }
        return max;
    }

    static boolean valid(int r, int c){ // 테트로미노가 놓인 값이 종이의 범위 안에 있는지 유효성 검사
        if(0<=r && 0<=c && r<N && c<M) return true;
        else return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for (int i = 0; i < N; i++) { // 종이 값 입력
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        for (int i = 0; i < block.length; i++) { // 가능한 테트로미노의 경우의 수만큼 반복
            for (int j = 0; j < 4; j++) { // 종이의 4가지 방향에서 가능한 최댓값 모두 구하기
                if (answer < calMax(block[i])) answer = calMax(block[i]);
                else if (answer < calMax2(block[i])) answer = calMax2(block[i]);
                else if (answer < calMax3(block[i])) answer = calMax3(block[i]);
                else if (answer < calMax4(block[i])) answer = calMax4(block[i]);
            }
        }

        System.out.println(answer);

    }
}
