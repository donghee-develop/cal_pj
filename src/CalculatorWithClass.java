import java.util.Scanner;


public class CalculatorWithClass {
    private final Scanner scanner;

    public CalculatorWithClass(){
        this.scanner = new Scanner(System.in);
    }

    public void run(){
        while (true){
            System.out.print("식 입력 (exit 입력 시 종료) : ");
            String input = scanner.nextLine();
            input = input.replaceAll(" ", ""); // 공백 제거

            if (input.equals("exit")) {
                break;
            }

        }
    }



    public static void main(String[] args) {
        CalculatorWithClass cal = new CalculatorWithClass();
        cal.run();
    }
}
