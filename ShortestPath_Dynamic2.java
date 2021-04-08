import java.util.*;
import java.io.*;


public class ShortestPath_Dynamic2 {

    //이번에는 최단길이 뿐만 아니라 최단 경로 P를 나타내보자.

    static int i, j;
    static int k;

    //입력하기 귀찮아서 미리 설정하자.
    static int[][] weights = { { 0,0, 1, 3, 1, 4 }, { 0,8, 0, 3, 2, 5 }, { 0, 10, 11, 0, 4, 7 }, {0,6,7,2,0,3},{0, 3,4,6,4,0}};
    static int[][] Dp = new int [6][6];

    //얘는 최단경로 P
    static int[][] P = new int [6][6];


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

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

        Dp = D(k, weights, Dp); // vertex(i) 부터 vertex(j) 까지, k번 째 버텍스만 사용하는 최단경로를 찾는다

        System.out.print("최단경로의 값은: " + Dp[i][j] + "\n최단길이의 값은: " + P[i][j] );

    }
    

    static int [][] D(int num, int [][] W, int [][] D) {
        // 최단 경로 P를 작성하자
        D = W;
        for (int k = 1; k <= num; k++) {
            for (int i = 1; i <= num; i++) {
                for (int j = 1; j <= num; j++) {
                    P[i][j] = k;
                    D[i][j] = Math.min(D[i][j], D[i][k] + D[k][j]);
                }
            }
        }

        return D;
    }
}