import java.util.*;
import java.io.*;

public class FixedPoint_Iteration2 {
    
    static double x0 = 5;
    static double et = 0.01;

    // 고정적 반복법을 할 시, g(x)가 3개가 나오므로 이를 선택하는 num을 설정한다
    static int num;

    static public double Fx(double x)
    {
        double fx= (-0.9)*Math.pow(x,2) + 1.7*x + 25;
        return fx;
    }
    
    static public double G( double x_last) {
        // Gx의 식은 어떻게 생겼는가
        // fx를 x=g(x) 꼴로 변형시키자
        double x_new = 0;

        double x1 = Math.sqrt((17 * x + 250) / 9);
        double x2 = -Math.sqrt((17 * x + 250) / 9);
        double x3 = (9 * Math.pow(x, 2) - 250) / 17;

        if (num == 1) {
            x_new = x1;
        } else if (num == 2) {
            x_new = x2;
        } else if (num == 3) {
            x_new = x3;
        }
        return x_new;
    }


    public static void main(String[] args) {
        double xr = x0;
        int iter = 0;
        
        System.out.print("함수 g(x)의 버전을 1, 2, 3 중에 택하세요: ");
        num = System.in.read();


        While(true){
            double xr_old = xr;
            xr = G(xr_old);

            //xr을 구했으니 반복횟수 1증가
            iter = iter+1;

            //참근이 아니라면 Ea를 구하자
            if (xr != 0){
                ea = Math.abs((xr-xr_old)/xr)*100;
            }

            if(ea < es || iter >= imax){
                break;
            }
        }
        
    }    
}
