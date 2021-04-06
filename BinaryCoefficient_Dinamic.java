import java.util.*;
import java.io.*;

public class BinaryCoefficient_Dinamic {

    
    // 팩토리얼/재귀호출은 시간복잡도가 높다. 따라서 해당 값들은 배열에 저장시키고 불러옴으로써, 계산횟수를 줄여보자
    // 즉, 동적으로 구해보자.

    //배열을 만들자
    static int[][] Combination;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("이항계수 nCr의 n과 c를 차례로 한 줄에 작성하세요: ");
        StringTokenizer st = new StringTokenizer(br.readLine());

        //nCr에서, r은 0개부터 n개까지 가질 수 있으니 배열을 n*n으로 초기화하자
        int n = Integer.parseInt(st.nextToken());
        Combination = new int[n + 1][n + 1];

        int r = Integer.parseInt(st.nextToken());

        //map에는

        //0C0
        //1C0 1C1
        //2C0 2C1 2C2 
        //3C0 3C1 3C2 3C3
        //4C0 4C1 4C2 4C3 4C4

        //이렇게 저장되도록 해야한다. 함수로 해놓자.
        //이때, 예를들어 4C2 = 3C1+ 3C2 다. 계산을 다 하는게 아니라, 계산을 이미 해놓은 결과값만 가져오자.
        //라는 생각으로 쓴 dynamic함수를 호출하자.
        dynamic(n, r);

        //이제 내가 찾는 값은 배열에 저장된 n행 r열 값이다
        // map[n][r] = map[n-1][r-1] + map[n-1][r]
        int answer = Combination[n][r];
        System.out.print("이항계수 nCr의 값은: " + answer);
    }
    
    //리턴할 값 없다. void
    static void dynamic(int n, int r) {
        for (int i = 0; i <= n; i++) {
            //nC0 값은 항상 0이니 이것도 초기화는 미리 해주자.
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j==i) {
                    Combination[i][j] = 1;
                    continue;
                }
                //ㄱ자 형태의 덧셈이 진행된다
                Combination[i][j] = Combination[i - 1][j - 1] + Combination[i - 1][j];
            }
        }
    }
    
    // 계산된 값을 확인하려고 임의로 쓴 print함수임.
    static void print(int i, int j) {
        System.out.println(i + "_C_" + j + "의 값은: " + Combination[i][j]);
    }
}
