import java.io.*;
import java.util.*;


public class BinaryCoefficient_recursive {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("이항계수 nCr의 n과 c를 차례로 한 줄에 작성하세요: ");
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        int answer;
        answer = recursive(n, r);

        System.out.print("이항계수 nCr의 값은: " + answer);
    }
    
    static int recursive(int n, int r) {

        int sum = 0;
        // 호출하다보면 맨 처음 시작인 1C0을 구하게 되어있음
        if (r == 0) {
            // nCr에서, [n개 中 0개]를 뽑는 경우는 [1개]다.
            sum = 1;
            return sum;
        } else if (n == r) {
            // nCr에서, [n개 中 n개]를 뽑는 경우는 [1개]다.
            sum = 1;
            return sum;
        }

        // nCr = (n-1)C(r-1) + (n-1)C(r)
        sum = recursive(n - 1, r - 1) + recursive(n - 1, r);
        return sum;
    }
}