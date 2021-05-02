public class Newton_Rapshon {
     // 뉴턴을 통해 실근을 구할 것이다
     // fx와, x0과, es값은 주어졌다
    static float x0 = (float)5.00000;
    static double es;
    static double ea;
    static double imax;
    static int iter;

    static float f(float x){
        float y = (float) ((float) -0.9*Math.pow(x,2) + 1.7*x + 2.5);
        return y;
    }

    static float f_Derivaive(float xr){
        float y = (float) ((float)-1.8*xr + 1.7);
        return y;
    }

    static double Fixpt(float x0, double es, int imax, double ea){
        //가상코드 바탕의 고정점 반복법 코드
        float xr_old;
        float xr = x0;
        iter =0;
        while(true){
            xr_old = xr;
            System.out.print("\n" + iter + "번째\t\t" + xr + "\t\t" + ea );
            xr = xr - f(xr_old)/f_Derivaive(xr);

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
        System.out.print("\n\t\tx0의 값" + "\t\t\t ea의 값");
        //구한 정보 출력
        System.out.print("\n\n" + "최종 xr값: " + Fixpt(5,0.01,100,100) + "\t 반복횟수: " + (iter-1));
    }
}
