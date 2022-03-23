import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main_Boj_14891_톱니바퀴 {
    static Deque<Integer> [] gear = new Deque[4];
//    static Deque<Integer> gear1 = new ArrayDeque<>();
//    static Deque<Integer> gear2 = new ArrayDeque<>();
//    static Deque<Integer> gear3 = new ArrayDeque<>();
//    static Deque<Integer> gear4 = new ArrayDeque<>();
    static int K;
    static int [][] move;
    static int [] dir = new int[5]; // 1번부터 사용하기 위해

//    static void rotation(int idx){
//        int num = move[idx][0];
//        int d = move[idx][1];
//
//        switch (num){
//            case 1:
//                if(d == 1)
//            case 2:
//            case 3:
//            case 4:
//            default:
//                break;
//        }
//    }

    static void samePole(int idx){
        switch (idx){
            case 1:
            case 2:
            case 3:
            case 4:
            default:
                break;
        }
    }

    static boolean valid(int idx){
        if(idx != 0 && 0 <= idx && idx <= 4) return true;
        return false;
    }

    static void setDir(int num, int d){
        int idx = num;
        dir[num] = d;
        int [] dx = {1, -1};
        while (full()){
            while (valid(idx)){
                if(idx == num) {
                    idx++;
                    continue;
                }

            }
        }
    }

    static boolean full(){
        boolean full = true;
        for(int i = 1; i <= 4; i++){
            if(dir[i] == 0) full = false;
        }
        return full;
    }

    static void clockWise(Deque<Integer> gear){
        gear.addFirst(gear.pollLast());
    }

    static void counterClockWise(Deque<Integer> gear){
        gear.addLast(gear.pollFirst());
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;


        for(int i = 0; i < 4; i++){
            String s1 = br.readLine();
            Deque<Integer> gearAdd = new ArrayDeque<>();
            for(int j = 0; j < 8; j++) {
                gearAdd.add((int)s1.charAt(j) - '0');
            }
            gear[i] = gearAdd;
            gearAdd.clear();
        }


        K = Integer.parseInt(br.readLine());
        move = new int[K][2];

        for(int i = 0; i < K; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < 2; j++){
                move[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        print();

        for(int m = 0; m < K; m++){

        }

    }

    static void print(){

        for (int i = 0; i < gear.length; i++){
            for(int j = 0; j < 8; j++) {
                System.out.print(gear[i].pollFirst() + " ");
            }
            System.out.println();
        }





    }
}
