public class gauss2 {
    public static int n;            // 방정식 개수(동시에 미지수의 개수) 
    private static double [][] eq; //방정식을 담는 공간
    private static double [] b;
    
    public static double [] x; //각 열의 x값
    public static double [] s; //각 행의 최대 계수값
    public static double [] answer; 
    
    public static int er;           // 특이시스템 판정
    public static double tol = 0.000001;  //특이시스템 검출을 위한 최소한계값

    // 주어진 방정식 값을 2차원 배열에 대입시킨다
    public static void setting(double [][] info, double [] right){
        //전역으로 선언한 변수를 넘어온 정보(info)로 초기화시켜주자
        n = info.length; //선형방정식의 개수

        /* 2차원, 1차원 배열을 생성하고 초기화시키기 */
        eq = new double[n][n]; //해당 크기로 방정식[좌변] 공간할당
        b= new double [n];           //선형방정식의 개수만큼 해[우변] 공간할당
        x = new double [n];          //미지수의 개수만큼 할당
        answer = new double[n];      // 선형방정식의 개수만큼 정답arr 공간할당
        for(int i =0; i<n; i++){
            for (int j =0; j<n; j++){
                eq[i][j] = info[i][j];
            }
            b[i] = right[i];    //선형방정식의 해
        }

        System.out.print("\n========입력한 연립방정식=========\n");
        display();


        er = 0;                 // 특이시스템 판단을 위한 er
        s = new double [n];     // 방정식 개수 공간 할당

        /* 각 선형방정식(i)의 가장 큰 계수값(S[i]) 찾기 */
        for ( int i =0; i<n; i++){
            s[i] = Math.abs(eq[i][0]); 
            for( int j = 1; j<n; j++){
                if ( Math.abs(eq[i][j]) > s[i]){
                    s[i] = Math.abs(eq[i][j]);  //최대 계수값을 가지도록 교체
                }
            }
        }

        Eliminate();        // 전진소거 시작
        if( er != -1){      // 특이시스템이 아님을 확인하고
            Substitute();   // 후진대입 시작
        }
    }


    public static void Eliminate(){
        for(int k = 0; k < n-1; k++){
            //가장 큰 계수가 pivot방정식이 될 수 있도록 각 행을 pivoting시킴
            pivoting(k);

            /* 특이시스템 검출 */
            if( Math.abs(eq[k][k] / s[k]) < tol){
                er = -1;
            }

            for(int i = k+1; i < n; i++){
                double factor = eq[i][k] /eq[k][k];
                for(int j= k; j < n; j++){
                    eq[i][j] = eq[i][j] - factor * eq[k][j];
                }
                b[i] = b[i] - factor * b[k];
            }
            System.out.print("\n========피봇팅 & x[" + k + "]전진소거 결과=========\n");
            display();
        }

        /* 특이시스템 검출 */
        if( Math.abs(eq[n-1][n-1]/s[n-1]) < tol){
            er = -1;
        }
    }

    /* 해당 2차원 배열에 대한  pivoting (행의 위치 바꾸기) */
    public static void pivoting(int k){  // k = [피봇방정식]의 행 번호, 0 ~ n-1까지
        int p = k;
        double big = Math.abs(eq[k][k]/s[k]); // 피봇계수의 상대적인 값
        
        /* 축적화 진행, k행과 i행의 비교 */
        //[pivot방정식] 아래의 식들 중, pivot계수보다 큰 게 있는지 확인
        for( int i= k+1; i < n; i++){
            double dummy = Math.abs(eq[i][k]/s[i]); 
            // 더 크다면 pivot값 교체
            if(dummy > big){
                big = dummy;
                p = i;
            }
        }

        /* 바뀌었다면 행 교체 */
        if( p != k){
            for(int j = k; j < n; j++){   // p행과 k행의 미지수들 교체
                double dummy = eq[p][j];
                eq[p][j]= eq[k][j];
                eq[k][j] = dummy;
            }

            double dummy = b[p];        // p행과 k행의 방정식 해 교체
            b[p] = b[k];
            b[k] = dummy;

            //바뀐 행(i)에 대한 가장 큰 계수값(S[i]) 설정
            dummy = s[p];
            s[p] = s[k];
            s[k] = dummy;
        }
    }
    
    public static void Substitute(){
        // 마지막 미지수 구하기
        x[n-1] = b[n-1] / eq[n-1][n-1];

        // 끝의 방정식으로부터 상향식으로 x[i] (i= n... 1)을 구하기
        for(int i = n-2; i >= 0; i--){
            double sum =0;
            for(int j = i+1; j < n; j++){
                sum += eq[i][j]*x[j];
            }
            x[i] = (b[i]-sum)/eq[i][i];
        }
    }

    public static void display(){
        for(int i =0; i<n; i++){
            for(int j =0; j<n; j++){
                System.out.print(eq[i][j] + " x[" + j + "] ");
                if( j<n-1)
                    System.out.print("+ ");
                else
                    System.out.print("= ");
            }
            System.out.print(b[i] +"\n");
        }
    }

    public static void printing(){
        System.out.print("\n========최종 값=========\n");

        for(int i=0 ;i<n; i++){
            System.out.print("x" + i+ ": " + x[i] + "\n" );
        }
    }

    public static void main(String [] args){
        double [][] info = new double[][] {{1,2,-1},{5,2,2},{-3, 5, -1}};
        double [] right = new double [] {2,9,1};
        setting(info, right);
        
        printing();
    }
}