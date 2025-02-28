public enum Operator {
    ADD("+") {
        @Override
        public float apply(float a, float b) {
            return a + b;
        }
    },
    SUBTRACT("-") {
        @Override
        public float apply(float a, float b) {
            return a - b;
        }
    },
    MULTIPLY("*") {
        @Override
        public float apply(float a, float b) {
            return a * b;
        }
    },
    DIVIDE("/") {
        @Override
        public float apply(float a, float b) {
            if (b == 0) throw new ArithmeticException("0으로 나눌 수 없음");
            return a / b;
        }
    },
    QUO("%"){
        @Override
        public float apply(float a, float b) {
            if (b == 0) throw new ArithmeticException("0으로 나눌 수 없음");
            return a % b;
        }
    };
    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }
    public abstract float apply(float a, float b);
    public static Operator getOperator(String symbol) {
        for (Operator op : values()) {
            if (op.symbol.equals(symbol)) {
                return op;
            }
        }
        return null;
    }
}
