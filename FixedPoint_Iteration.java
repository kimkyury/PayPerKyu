import java.util.*;

public class FixedPoint_Iteration {

    // f(x) = x^3-x -1.344일 때 초기값 2를 사용하여 고정점 반복법으로 구해보자.
    // x= g(x) = (x -1.344)^(1/3) 로 가자.


    // search를 시작할 초기 값 
    static float x0 = 2;
    static float x_past;
    static float x_now;

    // 오차범위 es(허용 오차범위), ex(현재 오차범위)
    static double es = 0.0000001;
    static double ea;
    static int iter=10;

    // 이 함수를 쓰기 위해, f(x)를 x=g(x) 로 구해야하는데, 이 때 g(x)는 여러개 일 수도 있다.
    // 여러개의 g(x) 중에 확인할 것을 임의로 정해두고 시작함을 가정한다

    public static void main(String[] args) {
        // f(x) = 0 을 x = g(x) 형태로 정리하자
        // 초기값 x0(근이라고 가정)을 설정하자
        // 새로운 근 x0=g(0)을 추정하자

        // 새로운 근 x1의 오차가 허용치 안에 들지 않는다면 다음단계의 새로운 근을 다시 계산
        // 오차범위에 들 때까지 반복

        // 자, 다시 생각하자
        // (1) x0을 시작으로 ,x1을 구한다 (반복의 시작)
        // (2) x1과 x0을 통하여 상대오차 ex를 구한다
        // (3) ex가 es의 구간 안에 허용하는지 확인하고 반복을 할 지 정한다 ( 이게 종료조건이 되겠지)
        x_past = x0;
        x_now = g(x_past);
        int cnt = 0;

        while (cnt <iter) {
            cnt++;
            ea = ea(x_past, x_now);
            if (ea < es) {

                System.out.println("\n(찾았다!) 실행횟수: " + cnt + "\nf(x)값 :" + f(x_past));
                return;
            }
            System.out.println("\n(" + cnt + "번 째) 실행횟수: " + cnt + "\nf(x)값 :" +  f(x_past));
            x_past = x_now;
            x_now = g(x_past);
        }
        System.out.println("\n(횟수초과!) 실행횟수: " + cnt + "\nf(x)값 :" +  f(x_past));
    }

    static float g(float x) {
        float cal = (float) (Math.pow(x+1.344, 1/3));
        return cal;
    }

    static float f(float x) {
        float cal = (float) (Math.pow(x, 3) - x - 1.344);
        return cal;
    }

    static public double ea(double x1, double x2){
        double epsilon;
        epsilon = Math.abs((x2- x1 )/ x2)*100;
        return epsilon;
    }
}