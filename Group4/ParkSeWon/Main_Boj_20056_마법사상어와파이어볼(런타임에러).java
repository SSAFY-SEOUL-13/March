import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_Boj_20056_마법사상어와파이어볼 {
    static int N, M, K; // 격자의 크기, 파이어볼의 개수, 이동 횟수
    static ArrayList<fireball> list = new ArrayList<>();
    static ArrayList<fireball> listAdd = new ArrayList<>();
    static ArrayList<Integer> listIdx = new ArrayList<>();
    static int [] dx = {-1, -1, 0, 1, 1, 1, 0, -1}; // 문제에 나온 8칸의 방향 번호 순서대로
    static int [] dy = {0, 1, 1, 1, 0, -1, -1, -1}; // 문제에 나온 8칸의 방향 번호 순서대로
    static int [][] printMap; // 나중에 지울 것 출력용

    static void move(){
        for(int i = 0; i < list.size(); i++){
            int x = list.get(i).x;
            int y = list.get(i).y;
            int d = list.get(i).d;
            int s = list.get(i).s;

            for(int j = 0; j < s; j++){
                x += dx[d];
                y += dy[d];
                if(x <= 0) x += 4;
                else if(N < x) x %= 4;

                if(y <= 0) y += 4;
                else if(N < y) y %= 4;
            }
            list.get(i).x = x;
            list.get(i).y = y;
        }
        print();
    }

    static void merged(int start, int x, int y){
        listIdx.clear();
        for(int i = start; i < list.size(); i++){
            if(list.get(i).x == x && list.get(i).y == y){
                listIdx.add(i);
            }
        }
//        print2();
        if(listIdx.size() >= 2) move2(x, y);
        else return;
    }

    static void move2(int x, int y){
        int m = 0;
        int s = 0;
        int d = list.get(listIdx.get(0)).d % 2;
        boolean all = true; // 모두 홀수이거나 모두 짝수일때
        for(int i = 0; i < listIdx.size(); i++){
            m += list.get(listIdx.get(i)).m;
            s += list.get(listIdx.get(i)).s;
            if(d != list.get(listIdx.get(i)).d % 2) all = false; // 홀수, 짝수가 다를 때
        }

//        System.out.println("#move2 : " + m + " " + s + " " + all);
//        System.out.println("idx size : " + listIdx.size());

//        for(int i = 0; i < listIdx.size(); i++){
//            list.remove((int)listIdx.get(i));
//        }
        for(int i = listIdx.size()-1; i >= 0; i--){
            list.remove((int)listIdx.get(i));
        }
//        System.out.println("idx size : " + listIdx.size());
//        System.out.print("### remove: ");
//        print();

        m /= 5;
        s /= listIdx.size();

        if(m == 0) return;

        if(all){
            for(int i = 0; i < 8; i += 2){
                int newX = x + dx[i];
                int newY = y + dy[i];
                if(newX <= 0) newX += 4;
                else if(N < newX) newX %= 4;

                if(newY <= 0) newY += 4;
                else if(N < newY) newY %= 4;
                listAdd.add(new fireball(newX , newY , m, s, i));
            }
        }else{
            for(int i = 1; i < 8; i += 2){
                int newX = x + dx[i];
                int newY = y + dy[i];
                if(newX <= 0) newX += 4;
                else if(N < newX) newX %= 4;

                if(newY <= 0) newY += 4;
                else if(N < newY) newY %= 4;
                listAdd.add(new fireball(x + dx[i], y + dy[i], m, s, i));
            }
        }
    }

    static void split(int x, int y){

    }

    static int sumM(){
        int result = 0;
        for(int i = 0; i < list.size(); i++){
            result += list.get(i).m;
        }
        return result;
    }


    static class fireball{
        int  x, y, m,s,d; //  행, 열, 질량, 속력, 방향

        fireball(int x, int y,int m, int s, int d){
            this.x = x;
            this.y = y;
            this.m = m;
            this.s = s;
            this.d = d;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        printMap = new int[N][N];


        for(int i = 0; i < M; i++){ // 초기 파이어볼 정보 입력
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            list.add(new fireball(x, y, m, s, d));
        }


        for (int i = 0; i < K; i++){
            move();

            for(int j = list.size()-1; j >= 0; j--){
                merged(j, list.get(j).x, list.get(j).y);
            }

            list.addAll(listAdd);
            listAdd.clear();
            print();
        }

        System.out.println(sumM());

    }

    static void print(){

        System.out.println("-----------------------------------------------------");

        printMap = new int[N][N];

        for (int i = 0; i < list.size(); i++){
           printMap[list.get(i).x -1][list.get(i).y -1]++;
        }


        for(int i  = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                System.out.print(printMap[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("==================");

        for (int i = 0; i < list.size(); i++){
            System.out.println(list.get(i).x + " " + list.get(i).y + " " + list.get(i).m + " " + list.get(i).s + " "  + list.get(i).d);
        }
    }

    static void print2(){
        System.out.print("listIdx : ");
        for (int i = 0; i < listIdx.size(); i++){
            System.out.print(listIdx.get(i) + " ");
        }
        System.out.println();
    }
}
