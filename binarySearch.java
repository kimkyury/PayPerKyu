import java.util.*;
import java.io.*;

public class binarySearch {
    static int[] arr;

    static int n;

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("양수 n을 입력하세요: ");
        n = Integer.parseInt(br.readLine());
        arr = new int[n];

        System.out.print("정렬된 키의 배열 S를 입력하세요: ");
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 행렬 받아오기
        StringTokenizer st;
        System.out.println("\n행렬1을 0과 1로 작성하시오");

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

    static void search() {
    }
}