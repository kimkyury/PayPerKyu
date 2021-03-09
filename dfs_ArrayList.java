import java.util.*;
import java.io.*;

//이중 AraayList를 이용한 DFS풀이다
public class dfs_ArrayList {

    static boolean[] passed;
    static ArrayList<ArrayList<Integer>> map = new ArrayList<ArrayList<Integer>>();
    static int n;

    public static void main(String[] arsg) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("vertex의 개수를 입력하세요:");
        n = Integer.parseInt(br.readLine());
        passed = new boolean[n];

        // 초기화시켜주자, ArrayList의 목차들을 설정해주는 것이다.
        for (int i = 0; i < n; i++) {
            map.add(new ArrayList<Integer>());
        }

        // '0 0'을 입력하면 종료하도록 만들어주자.
        StringTokenizer st;
        while (true) {
            st = new StringTokenizer(br.readLine());
            int first = Integer.parseInt(st.nextToken());
            int second = Integer.parseInt(st.nextToken());

            if (first == 0 && second == 0) {
                break;
            }
            map.get(first).add(second); // ArrayList의, first번 째 ArrayList에 seconds값 대입
        }
        dfs(1);
    }

    static void dfs(int start) {
        passed[start] = true;
        System.out.print(start + " ");
        for (int i = 0; i < map.get(start).size(); i++) {
            int tmp = map.get(start).get(i); // ArrayList의, start번 째 arrayList의, i번 째 입력값을 가져온다
            if (!passed[tmp])
                dfs(tmp);
        }
    }
}
