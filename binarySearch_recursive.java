import java.util.*;
import java.io.*;


//이진탐색을 재귀로 작성하기
//정렬된 배열에서 내가 찾는 원소는 몇번 째 배열인지 인덱스값을 출력한다
public class binarySearch_recursive {
    static int [] arr;
    static int x;
    static int cnt;
    static int index;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n;
        // 먼저, 서치할 배열의 크기와 값을 입력받자
        System.out.print("입력할 배열의 크기 n을 입력하세요: ");
        n = Integer.parseInt(br.readLine());
        arr = new int[n+1];

        System.out.print("오름차순 정렬된 키의 배열 S를 입력하세요: ");
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 검색할 원소 x를 입력받자
        System.out.print("검색할 원소 x를 입력하세요: ");
        x = Integer.parseInt(br.readLine());

        //arr에서 x원소의 위치를 찾는다
        search(arr.length/2);

        //위의 search함수에서 index값은 조정되어 나타난다
        System.out.print("\n----검색결과------\n");
        System.out.print(x + "의 위치는 [" + index + "]");
    }

    static void search(int i) {

        if (arr[i] == x) {
            index = i;

            return;
        }
        if (arr[i] > x) {
            //중간값이 X보다 크니, index를 좌측 중간값으로 설정
            i = i / 2 + 1;
            System.out.println(i);
            search(i);
        } else if (arr[i] < x) {
            i = i + i / 2 + 1;
            System.out.println(i);
            search(i);
            //중간값이 x보다 작으니, index를 우측 중간값으로 설정
        } 
    }
}