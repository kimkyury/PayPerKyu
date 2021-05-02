import java.util.*;

public class FixedPoint_Iteration2{
     // 고정적 반복법을 통해 실근을 구할 것이다
     // fx와, x0과, es값은 주어졌다
    static float x0 = (float)5.00000;
    //static double x0 = 0;

    static double es = 1;
    static double ea;
    static double imax = 20;
    static int iter;

    static double f(double x){
        double y = -0.9*Math.pow(x,2) + 1.7*x + 2.5;
        //double y = Math.pow(Math.E,-x) *(-x);
        return y;
    }

    //이차함수인 f(x)에 따른 g(x)의 세 가지 버전
    static float g1(double xr){
        float x =  (float) ((9* Math.pow(xr,2) - 25 )/17);
        //double x =  Math.pow(Math.E, -xr);
        return x;
    }
    static float g2(double xr){
        float x = (float)(Math.sqrt((17*xr + 25)/9));
        return x;
    }
    static float g3(double xr){
        float x = (float)-(Math.sqrt((17*xr + 25)/9));
        return x;
    }

    static double Fixpt(float x0, double es, int imax, double ea, int version){
        //가상코드 바탕의 고정점 반복법 코드
        float xr_old;
        float xr = x0;
        iter =0;
        while(true){
            xr_old = xr;
            
            System.out.print("\n" + iter + "번째\t\t" + xr + "\t\t" + ea );
            //고른 버전에 따른 gx함수 호출
            if(version ==1){
                xr = g1(xr_old);
            } else if(version ==2){
                xr = g2(xr_old);
            } else if(version ==3){
                xr = g3(xr_old);
            }

            //xr을 구하는 과정에 대한 iter + 1
            iter +=1;

            if(xr != 0){
                ea = Math.abs((xr-xr_old)/xr) * 100;
            }

            if(ea<es || iter >= imax){
                break;
            }
        }
        return xr;
    }

    public static void main(String [] args){
        //fx에 따른 g(x) 식이 3개가 나올 것이니, 그 g(x)값을 무엇을 사용할지 묻자
        Scanner sc = new Scanner(System.in);
        System.out.print("f(x)를 통해 구한 x=g(x)는 다음과 같습니다. \n(1) g(x) = 9*x^2 - 25 )/17 \n(2) g(x) = sqrt((17*xr + 25)/9)) \n(3) g(x) = - sqrt((17*xr + 25)/9))");
        System.out.print("\n\n사용할 g(x)의 버전은 어떤 것입니까? ");
        int version = sc.nextInt();


        System.out.print("\n\t\tx0의 값" + "\t\t\t ea의 값");
        //구한 정보 출력
        System.out.print("\n\n" + "최종 xr값: " + Fixpt(5,0.01,100,100,version) + "\t 반복횟수: " + (iter-1));
        sc.close();
    }
}