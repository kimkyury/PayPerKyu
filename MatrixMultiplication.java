import java.util.*;
import java.io.*;

public class MatrixMultiplication {

    static int cnt;
    static int[][] answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("N x N 행렬, N의 크기: ");
        int n = Integer.parseInt(br.readLine());
        int[][] mx1 = new int[n][n];
        int[][] mx2 = new int[n][n];
        answer = new int[n][n];

        // 행렬 받아오기
        StringTokenizer st;
        System.out.println("\n행렬1을 0과 1로 작성하시오");
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                mx1[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println("\n행렬2를 0과 1로 작성하시오 ");
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                mx2[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        Multiplication(mx1, mx2);

        System.out.print("\n-----계산결과------\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(answer[i][j] + " ");
            }
            System.out.print("\n");
        }

        // T(n) = n*n*n
        System.out.print("\n결과값을 위해 계산을 실행한 횟수: " + cnt);
        System.out.print("\n------------------");
    }

    static void Multiplication(int[][] mx1, int[][] mx2) {
        cnt = 0;

        for (int i = 0; i < mx1.length; i++) {
            for (int j = 0; j < mx1.length; j++) {
                // answer [i][j]공간을 초기화시켜주자. 초기화 시켜줬을 때마다, 해당 공간에 곱셈의 합들을 저장시켜야 한다.
                answer[i][j] = 0;
                // (mx1 i행 k열) * (mx2 k행 j열)의 계산이 이루어진다
                // mx1는 answer의 행의 영향을, mx2는 answer의 열의 영향을 받는 것이다.
                for (int k = 0; k < mx1.length; k++) {
                    cnt++;
                    answer[i][j] += mx1[i][k] * mx2[k][j];
                }
            }
        }
    }
}