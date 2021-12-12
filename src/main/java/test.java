import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        
        int cnt = 0;
        
        for(int i=a-2;i>1;i--){
            cnt++;
        }
        System.out.println("cnt = " + cnt);
    }
}
