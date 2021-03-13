import java.util.*;
import java.io.*;

public class ExchangeSorting {
    static int n;
    static int cnt;

    public static void main(String[] arsgs) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("<교환정렬>할 정수배열의 원소 개수: ");
        n = Integer.parseInt(br.readLine());
        int[] intArr = new int[n];

        System.out.print("정렬할 정수배열을 한 줄로 나열하시오: ");
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            intArr[i] = Integer.parseInt(st.nextToken());
        }

        sort(intArr);
        System.out.print("\n-----교환정렬결과------\n");
        for (int i = 0; i < n; i++) {
            System.out.print(intArr[i] + " ");
        }

        // 교환정렬의 시간복잡도, T(n) = {n(n-1)}/2 다.
        System.out.print("\n정렬을 위한 비교횟수: " + cnt);
        System.out.print("\n-----------------------");
    }

    static void sort(int[] intArr) {
        int temp;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // 여기서 조건문이 실행되니 cnt를 여기서 세자.
                cnt++;
                if (intArr[i] > intArr[j]) {
                    temp = intArr[i];
                    intArr[i] = intArr[j];
                    intArr[j] = temp;
                }
            }
        }
    }
}
