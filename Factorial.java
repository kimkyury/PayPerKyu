import java.util.*;
import java.io.*;

public class Factorial {
    static int sum = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("0보다 큰 양수인 숫자를 입력하세요: ");
        // 팩토리얼 숫자를 입력받는다
        int num = Integer.parseInt(br.readLine());
        System.out.print("팩토리얼 시행한 값: " + recursive_function(num));
    }

    // 재귀함수는 종료조건이 필수다. (계속 불러내므로)
    static int recursive_function(int n) {

        // 반복하다보면 마지막(종료조건)에 n이 1이 될 것이다. 이 때는 시작값인 1를 반환시켜주자
        if (n <= 1) {
            return 1;
        }
        return n * recursive_function(n - 1);
    }
}
