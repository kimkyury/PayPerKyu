import java.util.*;
import java.io.*;

public class AddArray {

    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("더할 배열의 원소 개수: ");
        n = Integer.parseInt(br.readLine());
        int[] intArr = new int[n];

        System.out.print("배열의 원소를 한 줄로 나열하시오: ");
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            intArr[i] = Integer.parseInt(st.nextToken());
        }

        int sum = add(intArr);

        System.out.print("\n-----계산결과------\n");
        System.out.print("배열의 원소들의 합: " + sum);
        System.out.print("\n------------------");
    }

    static int add(int[] intArr) {
        int result = 0;
        for (int i = 0; i < n; i++) {
            result += intArr[i];
        }
        return result;
    }
}
