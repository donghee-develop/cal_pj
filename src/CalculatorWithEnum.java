import java.util.*;

public class CalculatorWithEnum {
    private final Scanner scanner;

    public CalculatorWithEnum(){
        this.scanner = new Scanner(System.in);
    }

    public void run(){
        while (true){
            System.out.print("식 입력 (exit 입력 시 종료, 사용 가능 연산 +,-,*,/,%,^) : ");
            String input = scanner.nextLine();
            input = input.replaceAll(" ", ""); // 공백 제거

            if (input.equals("exit")) {
                break;
            }

            if (!isValid(input)) {
                continue;
            }
            List<String> postfix = changePostfix(input);

            Float result = calculatePostfix(postfix);
            if (result == null) {
                continue;
            }
            if (result == result.intValue()) {
                System.out.println("계산 결과 : " + result.intValue());
            } else {
                System.out.println("계산 결과: " + result);
            }
        }
    }

    public boolean isValid(String input){
        if (!input.matches("[0-9+\\-*/%^().]+")) {
            System.out.println("잘못된 값 또는 연산자 입력");
            return false;
        }
        if(input.contains("()")){
            System.out.println("빈 괄호 허용하지 않음");
            return false;
        }
        if(input.matches(".*\\)\\d.*")){
            System.out.println("닫는 괄호 뒤 숫자 바로 못 옴");
            return false;
        }
        if (input.matches(".*[+\\-*/%^]{2,}.*")) {
            System.out.println("연산자가 연속으로 사용될 수 없음");
            return false;
        }
        int open = 0;
        int close = 0;
        for (char c : input.toCharArray()) {
            if (c == '(') open++;
            else if (c == ')') close++;
        }

        if (open != close) {
            System.out.println("괄호 개수 에러");
            return false;
        }

        Stack<Character> valid = new Stack<>();
        boolean invalid = false;

        for(char c: input.toCharArray()) {
            if(c == '('){
                valid.push(c);
            }
            else if(c == ')'){
                if(valid.isEmpty()){
                    invalid = true;
                    break;
                }
                valid.pop();
            }
        }
        if(invalid){
            System.out.println("괄호 순서가 잘못됨");
            return false;
        }
        return true;
    }

    public List<String> changePostfix(String input){
        Map<Character, Integer> seq = Map.of(
                '+', 1, '-', 1,
                '*', 2, '/', 2,
                '%', 2, '^', 3,
                '(', 0
        );

        List<String> postfix = new ArrayList<>();
        Stack<Character> operators = new Stack<>();
        StringBuilder number = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                number.append(c);
            } else {
                if (!number.isEmpty()) {
                    postfix.add(number.toString());
                    number.setLength(0);
                }
                if (c == '(') {
                    operators.push(c);
                } else if (c == ')') {
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        postfix.add(String.valueOf(operators.pop()));
                    }
                    operators.pop();
                } else {
                    while (!operators.isEmpty() && seq.getOrDefault(operators.peek(), -1) >= seq.getOrDefault(c, -1)) {
                        postfix.add(String.valueOf(operators.pop()));
                    }
                    operators.push(c);
                }
            }
        }
        if (!number.isEmpty()) {
            postfix.add(number.toString());
        }
        while (!operators.isEmpty()) {
            postfix.add(String.valueOf(operators.pop()));
        }

        return postfix;
    }

    public Float calculatePostfix(List<String> postfix) {
        Stack<Float> stack = new Stack<>();
        boolean error = false;

        for (String text : postfix) {
            if (text.matches("\\d+(\\.\\d+)?")) {
                stack.push(Float.parseFloat(text));
            } else {
                if (stack.size() < 2) {
                    System.out.println("잘못된 수식 (연산자가 숫자보다 많음)");
                    return null;
                }

                float b = stack.pop();
                float a = stack.pop();

                Operator op = Operator.getOperator(text);
                if (op == null) {
                    System.out.println("알 수 없는 연산자: " + text);
                    return null;
                }

                if (text.equals("/") && b == 0) {
                    System.out.println("0으로 나눌 수 없음");
                    return null;
                }
                if (text.equals("%") && b == 0) {
                    System.out.println("0으로 나머지 못구함");
                    return null;
                }


                stack.push(op.apply(a, b));
            }
        }
        return error || stack.isEmpty() ? null : stack.pop();
    }




    public static void main(String[] args) {
        CalculatorWithEnum CalculatorWithEnum = new CalculatorWithEnum();
        CalculatorWithEnum.run();
    }
}
