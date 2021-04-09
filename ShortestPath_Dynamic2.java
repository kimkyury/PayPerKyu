import java.util.*;
import java.io.*;


public class ShortestPath_Dynamic2 {

    //이번에는 최단길이 뿐만 아니라 최단 경로 P를 나타내보자.
    static int i, j;
    static int k;

    //6x6의 임의의 W를 만들어주자
    static int[][] weights;

    //얘는 최단경로 P
    static int[][] P= new int [7][7];


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        weights = new int[][] { { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 7, 99, 11, 99, 99 }, { 0, 3, 0, 99, 99, 17, 99 },
                { 0, 99, 6, 0, 99, 99, 99 }, { 0, 99, 99, 99, 0, 9, 99 }, { 0, 99, 5, 15, 16, 0, 2 },
                { 99, 99, 11, 99, 8, 0 } };

        int[][] Dp = new int[7][7];
                                
        /*
        // vertex가 몇개인지 정보를 받자
        System.out.print("vertex의 개수는 몇 개예요?: ");
        int n = Integer.parseInt(br.readLine());
        
        int[][] weights = new int[n+1][n+1]; //가중치 공간
        int[][] Dp = new int[n+1][n+1];  //DynamicPath 공간
        
        // 가중치들 입력받기
        for (int row = 1; row <= n; row++) {
            System.out.println(">>> [" + row  + "]vertex 과 연결된 vertex들의 가중치들을 순서대로 작성해주세요: ");   //연결되지 않은 부분은 충분히 큰 수인 999를 넣어버리자.
            st = new StringTokenizer(br.readLine());
            for (int column = 1; column <= n; column++) {
                weights[row][column] = Integer.parseInt(st.nextToken());
            }
        }
        */


        // 시작점(i)과 끝점(j)의 정보를 받자
        System.out.print("어디서(i) 어디까지(j) 가나요? 한 줄로 i와 j를 입력해주세요. : ");
        st = new StringTokenizer(br.readLine());
        i = Integer.parseInt(st.nextToken());
        j = Integer.parseInt(st.nextToken());
        
        //사용할 수 있는 경로, k의 정보를 받자
        System.out.print("경로 몇(k)까지 허용할 건가요?: ");

        k = Integer.parseInt(br.readLine());
        Dp = DynamicSearch(k, weights, Dp, P); // vertex(i) 부터 vertex(j) 까지, k번 째 버텍스만 사용하는 최단경로를 찾는다
        System.out.println("최단경로의 값은: " + Dp[i][j] + "\n최단길이의 값은: " + P[i][j]);

        path(i, j);

    }
    

    static int[][] DynamicSearch(int num, int[][] W, int[][] Dp, int[][] P) {
        // 최단 경로 P를 작성하자
        Dp = W;
        for (int k = 1; k <= num; k++) {
            for (int i = 1; i <= num; i++) {
                for (int j = 1; j <= num; j++) {
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
            System.out.print("v" + P[q][r]);

            //vertex와 r사이에 다른 vertex가 존재하는지 확인해본다
            path(P[q][r], r);
        }
    }
}