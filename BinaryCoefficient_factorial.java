import java.io.*;
import java.util.*;

public class BinaryCoefficient_factorial {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("이항계수 nCr의 n과 c를 차례로 한 줄에 작성하세요: ");
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        BinaryCoefficient(n, r);

        //이항계수를 구할 거임
        //이항계수는 nCr, nCr이란 'n개 중에서 중복이 허용된 r개를 뽑은 조합의 수'
        // nCr = [ n!/ {r!*(n-r)!} ]   = 모든경우의 수/((a의 중복의 경우 수) * (b의 중복의 경우 수))
    }
    
    static void BinaryCoefficient(int n, int r) {

        int answer;
        answer = factorial(n) / (factorial(r) * factorial(n - r));

        System.out.print("\n\n이항계수 nCr의 값은: " + answer);


    }

    static int factorial(int num) {
        int sum = 1;
        
        for (int i = 2; i <= num; i++) {
            sum = sum * i;
        }

        return sum;
    }
    

}