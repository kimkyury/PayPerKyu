import java.util.*;
import java.io.*;

//순차검색 알고리즘
public class SequentialSearch {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("탐색할 정수정렬의 원소 개수: ");
        int n = Integer.parseInt(br.readLine());
        int[] intArr = new int[n];

        System.out.print("탐색할 정수정렬을 한 줄로 나열하시오: ");
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            intArr[i] = Integer.parseInt(st.nextToken());
        }

        System.out.print("서치할 데이터의 값: ");
        int scan = Integer.parseInt(br.readLine());
        int index = search(intArr, scan);

        System.out.print("\n-----탐색결과------\n");
        if (index == -1) {
            System.out.print("데이터가 배열 안에 존재하지 않음");
        } else
            System.out.print("  " + index + "번 째에 있음");
        System.out.print("\n------------------");

    }

    static int search(int[] arr, int search) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == search) {
                return i + 1;
            }
        }
        return -1;
    }
}
