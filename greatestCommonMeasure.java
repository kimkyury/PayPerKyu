import java.util.*;
import java.io.*;

public class greatestCommonMeasure {
    public static void main(String[] args) throws IOException {

        System.out.println("최대공약수를 확인할 두 수를, 큰 수 부터 한 줄로 입력해주세요. ");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());

        calculate(A, B);
    }

    static void calculate(int a, int b) {
        if (a % b == 0) {
            System.out.print(b);
            return;
        }

        else {
            calculate(b, a % b);
        }

    }
}