import java.util.*;
import java.io.*;

public class dichotomous_search {
    static int cnt = 0;
    static double ea;

    //이분법 알고리즘을 실행하기위해 참근, 참오차범위, 초기함수는 주워져있음
    static double f_Value = 4 - Math.sqrt(2);
    static double es = 0.5;
    static double funtion(double x) {
        return (x - 4) * (x - 4) + 2;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //참오차을 찾기 위해 실행한 횟수를 셈
        System.out.println("\n\n------[f(xl)*f(xu) <0]인, xl과 xu를 하나씩 입력하시오----");
        System.out.print("xl: ");
        double xl = Double.parseDouble(br.readLine()); //시작 위치
        System.out.print("xu: ");
        double xu = Double.parseDouble(br.readLine()); //종결 위치

        cal(xl, xu);
        System.out.print("실행횟수: " + cnt + "\n절대오차: " + ea + "\n\n--------------------------------------------------");
    }
    
    static void cal(double xl, double xu) {
        cnt++;

        // xl과 xu의 중간값 xr의 위치를 구한다
        double xr = xl + ((xu - xl) / 2);
        // f_xr의 값을 구한다
        double f_xr = funtion(xr);
        
        // f_xr에 대한 절대오차를 구한다 
        double gap = Math.abs(f_Value - f_xr);  //f_xr에 대한 오차 gap, f_Value은 참값임
        
        //절대오차가 참오차범위 안에 있으면, 정답을 확정하고 중단한다
        if (gap <= es) {
            ea = gap;
            return;
        }
        
        // 절대오차가 참오차범위 안에 없으면 (1)길이를 조정한다
        else if (gap > es) {
            double f_xl = funtion(xl);
            
            // (1)-1 f(xl)*f(xr) < 0 이면, (xu = xr)로 조정
            if (f_xl * f_xr < 0) {
                xu = xr;
                cal(xl, xu);
            }
            
            // (1)-2 1이 아니면 (xl = xr) 로 조정
            else{
                xl = xr;
                cal(xl, xu);
            }
        }
    }

}