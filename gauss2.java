
public class gauss2 {
    private static double [][] equation; //방정식을 담는 공간
    private static double [] b;
    public static int n; // 방정식 개수(동시에 미지수의 개수) 
    public static double [] answer; //내가 출력할 해
    public static double [] x; //각 열의 x값
    public static double [] s; //각 행의 최대 계수값
    public static double tol = 0.000001;  //특이시스템 검출을 위한 최소한계값
    public static int er;

    // 주어진 방정식 값을 2차원 배열에 대입시킨다
    public static void setting(double [][] info, double [] right){
        //전역으로 선언한 변수를 넘어온 정보(info)로 초기화시켜주자
        n = info.length; //선형방정식의 개수

        /* 2차원, 1차원 배열을 생성하고 초기화시키기 */
        equation = new double[n][n]; //해당 크기로 방정식[좌변] 공간할당
        b= new double [n]; //선형방정식의 개수만큼 해[우변] 공간할당
        x = new double [n]; //미지수의 개수만큼 할당
        
        answer = new double[n]; // 선형방정식의 개수만큼 정답arr 공간할당
        for(int i =0; i<n; i++){
            for (int j =0; j<n; j++){
                equation[i][j] = info[i][j];
            }
            b[i] = right[i]; // 선형방정식의 해
        }

        er =0;           // 특이시스템 판단을 위한 er
        s = new double [n];     // 축적화를 위한 s[]설정

        /* 각 선형방정식(i)의 가장 큰 계수값(S[i]) 찾기 */
        for ( int i =1; i<n; i++){
            s[i] = Math.abs(equation[i][1]); 
            for( int j =2; j<n; j++){
                if ( Math.abs(equation[i][j]) > s[i]){
                    s[i] = Math.abs(equation[i][j]);
                }
            }
        }
        Eliminate(); // 전진소거 시작
        if( er != -1){ //특이시스템이 아님을 확인하고
            Substitute(); //후진대입 시작
        }
    }

    /* 해당 2차원 배열에 대한  pivoting (행의 위치 바꾸기) */
    // 매개변수는 현재 진행중인,  [pivot방정식]인 행의 번호임.
    public static void pivoting(int k){
        int p = k;
        double big = equation[k][k];
        
        /* 축적화 진행 */
        //[pivot방정식] 아래의 식들 중, pivot계수보다 큰 게 있는지 확인
        for( int i= k+1; i <n; i++){
            double dummy = Math.abs(equation[i][k]/s[i]);
            //더 크다면 pivot값 교체
            if(dummy > big){
                big = dummy;
                p = i;
            }
        }

        /* pivot값이 실제로 교체될 시, 그 기준으로 재정렬 */
        if( p != k){

            //p행과 k행의 각 열(j) 계수 교환
            for(int j = k; j<n; j++){
                double dummy = equation[p][j];
                equation[p][j]= equation[k][j];
                equation[k][j] = dummy;
            }

            //p행과 k행 방정식의 해 교환
            double dummy = b[p]; 
            b[p] = b[k];
            b[k] = dummy;

            //바뀐 행(i)에 대한 가장 큰 계수값(S[i]) 설정
            dummy = s[p];
            s[p] = s[k];
            s[k] = dummy;
        }
    }

    public static void Eliminate(){
        for(int k = 1; k < n-1; k++){
            //가장 큰 계수가 pivot방정식이 될 수 있도록 각 행을 pivoting시킴
            pivoting(k);

            /* 특이시스템 검출하기 */
            if( Math.abs(equation[k][k] / s[k]) < tol){
                er = 0;
            }

            for(int i = k+1; i < n; i++){
                double factor = equation[i][k] /equation[k][k];
                for(int j= k+1; j<n; j++){
                    equation[i][j] = equation[i][j] - factor * equation[k][j];
                }
                b[i] = b[i] -factor * b[k];
            }
        }

        /* 특이시스템 검출 */
        // 피봇계수가 상대적으로 너무 작은 값이면 특이시스템으로 간주
        if( Math.abs(equation[n][n]/s[n])<tol){
            er = -1;
        }
    }
    
    public static void Substitute(){
        // 마지막, x[n]을 구하기
        x[n] = b[n] / equation[n][n];

        // 끝의 방정식으로부터 상향식으로 x[i] (i= n... 1)을 구하기
        for(int i=n-1; i>1; i--){
            double sum =0;
            for(int j=i+1; j<n; j++){
                sum += equation[i][j]*x[j];
            }
            x[i] = (b[i]-sum)/equation[i][i];
        }
    }

    public static void printing(){
        System.out.print("----------결과---------\n");

        for(int i=0 ;i<n; i++){
            System.out.print("x" + 9 + ": " + x[i] + "\n" );
        
        }
    }

    public static void main(){
        
        double [][] info = new double[][] {{1,2,-1},{5,2,2},{-3, 5, -1}};
        double [] right = {2,9,1};
        setting(info, right);
        printing();
    }
}