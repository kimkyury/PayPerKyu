public class Falseposition_search{
    
    //모든 초기값 설정
    static double xl = 0.5;
    static double xu = 1.0;
    static double es = 0.02;
    static int imax = 100;
    
    static int iter = 0;
    static double xr;
    static double ea; //근사백분율 상대오차

    public static void main(String [] args) {
        //가장 처음의 xr(중간)값
        xr = xu - (f(xu)*(xl-xu)/(f(xl)-f(xu)));;
        iter++;

        //한 번 진행 한 뒤에 함수를 호출시킬 수 있다(상대오차[(new-old)/new]를 계산하기 위해)
        if (f(xl) * f(xr) < 0) {
            xu = xr;
        } else if (f(xl) * f(xr) > 0) {
            xl = xr;
        } else if (f(xl) * f(xr) == 0) {
            System.out.print("\n반복횟수: " + iter + "\n찾는 근: " + xr);
            return;
        }

        // 근을 찾고 출력
        double answer;
        answer = cal();
        System.out.print("\n반복횟수: " + iter + "\n찾는 근: " + answer);
    }
    
    //주어진 함수
    static double f(double x) {
        double cal = (-26) + (85 * x) + ((-91) * Math.pow(x, 2)) + (44 * Math.pow(x, 3)) + ((-8) * Math.pow(x, 4)) + (Math.pow(x,5));
        return cal;
    }
    
    // 계산하기  
    static double cal() {
        double fl = f(xl);
        double fu = f(xu);
        while (true) {

            // ea를 구하기 위해 xr_old와 기존의 xr이 필요하다
            double xr_old = xr;
            xr = xu - (fu*(xl-xu)/(fl-fu));

            double fr = f(xr);
            iter++;

            if (xr != 0) {
                ea = Math.abs((xr - xr_old) / xr) * 100;
            }

            double test = fl * fr;
            if (test < 0) {
                xu = xr;
            } else if (test > 0) {
                xl = xr;
            } else if (test == 0) {
                //근을 찾았으므로, xr은 그대로 유지 And 상대오차값은 0으로 바꿈
                ea = 0;
            }

            if (ea < es || iter >= imax) {
                //근을 찾지 못했더라도 실행횟수가 너무 많았거나 근이 오차범위에 들면 빠져나감
                break;
            }
        }

        //리턴되는 xr값 = [(1)해석해 || (2)수치해(in오차범위 || over실행횟수)]
        return xr;
    }
}