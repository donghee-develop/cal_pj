# 다양한 방법의 계산기 구현하기


## 개발 목적


###  콘솔 기반 예외 처리가 된 사칙연산 및 제곱, 몫 기능이 있는 계산기를 개발


## 요구사항

1. 사칙연산과 제곱근, 제곱, 괄호 사용이 가능한 계산기
2. 사용자 인터페이스는 콘솔 방식으로 제작

## 예외 처리 및 체크 리스트

1. 사용자가 쉽게 사용할 수 있는가?

2. 잘못된 입력에 대한 예외 처리를 하였는가?

3. 정수 외 소수 계산에 대하여 예외 처리를 하였는가?

## 로직

1. 정수가 아닌 식 입력을 받는다.
2. 입력 값과 괄호의 개수를 검사한다.
3. 후위식으로 변환한다.
4. 후위식을 계산하여 결과를 도출한다.

## 트러블 슈팅

### 괄호를 어떻게 인식 시키고 계산에 적용 시킬 수 있을까?
- #### 괄호는 연산 순윌르 가장 높게 잡아야 하고, 괄호의 닫힘 방향이 일치해야 제대로 된 계산식이 성립된다.
- #### 일반적인 중위식 (20+3)/2 와 같은 식은 괄호에 대한 조건을 만들기 힘들다.
- #### 중위식을 후위식으로 변환한다.

## 해결법

1. 먼저 입력받을 때 괄호에 대한 예외 처리, 괄호를 담는 valid 스택, 괄호 순서 플래그인 invalid 생성 후 입력 문자열 검사,
만약 '(' 이면 push, ')' 인데 스택이 비었으면 짝이 안맞으므로 오류 아니면 '()' 한 쌍을 없앤다. 그리고 invalid가 true가 되면 오류 발생하는 로직 작성



