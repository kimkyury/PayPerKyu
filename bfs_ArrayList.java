import java.util.*;
import java.io.*;

//정확히는 ArrayList와 Queue를 이용한 풀이.
public class bfs_ArrayList {
    static ArrayList<ArrayList<Integer>> map = new ArrayList<ArrayList<Integer>>();
    static int n;
    static boolean[] visit;

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("vertex의 개수는 얼마예요? ");
        // vertex개수 가져오기
        n = Integer.parseInt(br.readLine());
        visit = new boolean[n];

        // 지정한 vertex개수 만큼 초기화해주기
        for (int i = 0; i < n; i++) {
            map.add(new ArrayList<Integer>());
        }

        StringTokenizer st;
        while (true) {
            st = new StringTokenizer(br.readLine());
            int first = Integer.parseInt(st.nextToken());
            int second = Integer.parseInt(st.nextToken());
            if (first == 0 & second == 0) {
                break;
            }
            map.get(first).add(second);
        }
        System.out.println("\n계산해볼게요!");
        bfs(1);
    }

    static void bfs(int start) {
        Queue<Integer> q = new LinkedList<>();
        // q에 start를 집어넣는데, 이 때 add를 쓸지 offer를 쓸 지는 큐가 꽉 찼을 경우의 리턴값 차이다(예외/false)
        q.add(start);
        visit[start] = true;

        // Queue가 다 될 때까지, 다 출력될 때까지 하자.
        while (!q.isEmpty()) {
            int num = q.poll();
            System.out.print(num + " ");

            // num과 연결된 아이들은 방문하지 않았다면 넣는다.
            for (int i = 0; i < map.get(num).size(); i++) {
                int num2 = map.get(num).get(i);

                // q에 넣었다면 방문했다는 것이므로, true표시를 해 주자.
                if (!visit[num2]) {
                    q.add(num2);
                    visit[num2] = true;
                }
            }
        }
    }
}
