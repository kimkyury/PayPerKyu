import java.util.*;
import java.io.*;


public class ShortestPath_Dynamic {

    static int i, j;
    static     int k;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 한 v에서 다른 v로의 모든 경로의 길이를 구한뒤 최단경로 찾기 => 무작위 알고리즘
        // 그게 아니라, 단축시킬 수 있는 동적계획법으로 가자

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
        // 시작점(i)과 끝점(j)의 정보를 받자
        System.out.print("어디서(i) 어디까지(j) 가나요? 한 줄로 i와 j를 입력해주세요. : ");
        st = new StringTokenizer(br.readLine());
        i = Integer.parseInt(st.nextToken());
        j = Integer.parseInt(st.nextToken());

        //사용할 수 있는 경로, k의 정보를 받자
        System.out.print("경로 몇(k)까지 허용할 건가요?: ");
   
        k = Integer.parseInt(br.readLine());

        Dp = D(k, weights, Dp); // vertex(i) 부터 vertex(j) 까지, k번 째 버텍스만 사용하는 최단경로를 찾는다

        System.out.print("최단경로의 값은: " + Dp[i][j]);

    }
    

    // D(0), D(1), D(2) 까지 점차 탐색해나간다
    static int [][] D(int num, int [][] W, int [][] D) {
        //D(0) = W[i][k]
        //D(1) = min(D(0),  w[i])

        int i, j, k;

        D = W;
        for (k = 1; k <= num; k++) {
            for (i = 1; i <= num; i++) {
                for (j = 1; j <= num; j++) {
                    D[i][j] = Math.min(D[i][j], D[i][k] + D[k][j]);
                }
            }
        }

        return D;
    }
}