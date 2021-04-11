import java.util.*;
import java.io.*;

public class ShortestPath_Dynamic2 {

    //6x6의 임의의 W를 만들어주자
    static int[][] weights = { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 7, 99, 11, 99, 99 }, { 0, 3, 0, 99, 99, 17, 99 },
                { 0, 99, 6, 0, 99, 99, 99 }, { 0, 99, 99, 99, 0, 9, 99 }, { 0, 99, 5, 15, 16, 0, 2 },
                { 0, 99, 99, 11, 99, 8, 0 } };
    static int[][] Dp = new int [7][7];
    //얘는 최단경로 P
    static int[][] P= new int [7][7];

    public static void main(String[] args) throws IOException {

        int i, j;
        int k;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 시작점(i)과 끝점(j)의 정보를 받자
        System.out.print(">>> 어디서(i) 어디까지(j) 가나요? 한 줄로 i와 j를 입력해주세요. : ");
        st = new StringTokenizer(br.readLine());
        i = Integer.parseInt(st.nextToken());
        j = Integer.parseInt(st.nextToken());
        
        //사용할 수 있는 경로, k의 정보를 받자
        System.out.print(">>> 경로 몇(k)까지 허용할 건가요?: ");

        k = Integer.parseInt(br.readLine());
        Dp = DynamicSearch(k, weights, Dp, P); 
        System.out
                .println("\n\nD[" + i + "][" + j + "]의 값은: " + Dp[i][j] + "\nP[" + i + "][" + j + "]의 값은: " + P[i][j]);

        if (P[i][j] != 0) {
                    System.out.print("최단 경로순(시작점과 끝점 포함): v" + i + "  ");
            path(i, j);
             System.out.print("v" + j);
         } else {
             System.out.print("경로가 존재하지 않음");
        }

        System.out.println("\n\n---------------<DP정보>--------------");
        show(Dp);
        System.out.println("\n---------------<P정보>---------------");
        show(P);
    }
    

    static int[][] DynamicSearch(int num, int[][] W, int[][] Dp, int[][] P) {
        // 최단 경로 P를 작성하자
        Dp = W;
        for (int k = 1; k <= num; k++) {
            for (int i = 1; i < Dp.length; i++) {
                for (int j = 1; j < Dp.length; j++) {
                    if (Dp[i][k] + Dp[k][j] < Dp[i][j]) {
                        P[i][j] = k;
                        Dp[i][j] = Dp[i][k] + Dp[k][j];
                    }
                }
            }
        }
        return Dp;
    }

    static void path(int q, int r) {
        //q=1, r=3이 들어올 것이다

        //q와 r사이에 또 다른 vertex가 존재한다면
        if (P[q][r] != 0) {

            // q와 vertex사이에 다른 vertex가 존재하는지 확인해본다
            //만약 존재하면 존재하는 거 먼저 print될 것이고, 그게 끝나면
            path(q, P[q][r]);

            //얘가 실행된다 ( P[q][r]의 값)
            System.out.print("v" + P[q][r] + "  ");

            //vertex와 r사이에 다른 vertex가 존재하는지 확인해본다
            path(P[q][r], r);
        }
    }

    static void show(int[][] map) {
        for(int i=0; i<map.length; i++){
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + "\t");
            }
            System.out.println("");
        }
    }
}