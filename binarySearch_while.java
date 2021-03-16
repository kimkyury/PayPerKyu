import java.util.*;
import java.io.*;

//이진탐색을 반복문으로 작성하기
public class binarySearch_while {
    static int[] arr;
    static int n;
    static int cnt;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("양수 n을 입력하세요: ");
        n = Integer.parseInt(br.readLine());
        arr = new int[n];

        System.out.print("오름차순 정렬된 키의 배열 S를 입력하세요: ");
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        System.out.print("검색할 원소 x를 입력하세요: ");
        int x = Integer.parseInt(br.readLine());

        int num = search(arr, x);

        System.out.print("\n----검색결과------\n");
        System.out.print(x + "의 위치는 [" + num + "]");

        // T(n) = ?
        System.out.print("\n결과값을 위해 계산을 실행한 횟수: " + cnt);
        System.out.print("\n------------------");
    }

    static int search(int[] arr, int x) {
        // 중간값을 가져온다
        int index = arr.length / 2;
        cnt = 0;

        // x와 비교하여, x가 더 크면: 우측탐색, x가 더 작으면: 좌측탐색
        // 종료조건: 동일할 때
        while (true) {
            cnt++;
            if (arr[index] > x) {
                index = (index / 2) + 1;
                continue;
            }

            else if (arr[index] < x) {
                index = index + (index / 2) + 1;
                continue;
            }

            if (arr[index] == x) {
                return index;
            }
        }
    }
}