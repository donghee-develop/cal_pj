import java.util.*;

public class Calculator {
    public static void main(String[] args) {
        while (true) {
            System.out.print("식 입력 (exit 입력 시 종료) : ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            input = input.replaceAll(" ", ""); // 공백 제거

            if (input.equals("exit")) {
                break;
            }

            if (!input.matches("[0-9+\\-*/%^().]+")) {
                System.out.println("잘못된 값 또는 연산자 입력");
                continue;
            }
            if(input.contains("()")){
                System.out.println("빈 괄호 허용하지 않음");
                continue;
            }
            if(input.matches(".*\\)\\d.*")){
                System.out.println("닫는 괄호 뒤 숫자 바로 못 옴");
                continue;
            }
            if (input.matches(".*[+\\-*/%^]{2,}.*")) {
                System.out.println("연산자가 연속으로 사용될 수 없음");
                continue;
            }
            int open = 0;
            int close = 0;
            for (char c : input.toCharArray()) {
                if (c == '(') open++;
                else if (c == ')') close++;
            }

            if (open != close) {
                System.out.println("괄호 개수 에러");
                continue;
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
                continue;
            }
            // 연산자 우선순위 설정
            Map<Character, Integer> seq = Map.of(
                    '+', 1, '-', 1,
                    '*', 2, '/', 2,
                    '%', 2, '^', 3,
                    '(', 0
            );

            // 후위식 리스트
            List<String> postfix = new ArrayList<>();
            // 연산 스택
            Stack<Character> operators = new Stack<>();
            StringBuilder number = new StringBuilder();

            for (char c : input.toCharArray()) {
                // 소수점 입력 시 . 조건 없으면 소수 확인을 못함
                if (Character.isDigit(c) || c == '.') {
                    number.append(c); // 버퍼에 숫자일 경우 계속 추가
                } else {
                    if (!number.isEmpty()) { // 숫자가 있으면 추가 후 초기화
                        postfix.add(number.toString());
                        number.setLength(0);
                    }
                    if (c == '(') {
                        operators.push(c);
                    } else if (c == ')') {
                        while (!operators.isEmpty() && operators.peek() != '(') {
                            postfix.add(String.valueOf(operators.pop()));
                        }
                        operators.pop(); // '(' 제거
                    } else {
                        while (!operators.isEmpty() && seq.getOrDefault(operators.peek(), -1) >= seq.getOrDefault(c, -1)) {
                            postfix.add(String.valueOf(operators.pop()));
                        }
                        operators.push(c);
                    }
                }
            }
            if (!number.isEmpty()) { // 마지막 숫자 추가
                postfix.add(number.toString());
            }
            while (!operators.isEmpty()) { // 남은 연산자 추가
                postfix.add(String.valueOf(operators.pop()));
            }

            System.out.println("후위 표기식: " + postfix);

            Stack<Double> stack = new Stack<>();
            boolean error = false;
            for (String text : postfix) {
                if (text.matches("\\d+(\\.\\d+)?")){
                    stack.push(Double.parseDouble(text));
                }
                else {

                    double b = stack.pop();
                    double a = stack.pop();

                    switch (text) {
                        case "+" ->
                            stack.push(a + b);
                        case "-" ->
                            stack.push(a - b);
                        case "*" ->
                            stack.push(a * b);
                        case "/" -> {
                            if (b == 0) {
                                System.out.println("0으로 나눌 수 없음");
                                error = true;
                            } else {
                                stack.push(a / b);
                            }
                        }
                        case "%" -> {
                            if (b == 0) {
                                System.out.println("0으로 나머지를 구할 수 없음");
                                error = true;
                            } else {
                                stack.push(a % b);
                            }
                        }
                        case "^" ->
                            stack.push(Math.pow(a, b));
                    }
                }
            }
            if (!error) {
                double result = stack.pop();
                if(result == (int) result){
                    System.out.println("계산 결과 : " + (int) result);
                }
                else {
                    System.out.println("계산 결과: " + String.format("%.6f",result));
                }
            }
        }
    }
}
