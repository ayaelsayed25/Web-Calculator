package calc;

import org.springframework.util.StringUtils;

public class Model {
    double result = 0;
    double finalResult = 0;
    public String operations(String firstOperand, String secondOperand, String operator)
    {
        double firstNum = 0;
        double secondNum = 0;
        boolean check1 = false;
        boolean check2 = false;
        if (evaluateExpression(firstOperand)) {
            check1 = true;
            firstNum = this.result;
        }
        if (evaluateExpression(secondOperand)) {
            check2 = true;
            secondNum = this.result;

        }
        if (check1 && check2) {
            System.out.println(firstNum + "   " + secondNum);
            if (performOperation(firstNum, secondNum, operator))
                return String.valueOf(finalResult);
            else
                return "Error";
        } else {
            return "Error";
        }
    }
    public boolean performOperation(double firstNum, double secondNum, String operator) {
        if (operator.equals("+")) {
            this.finalResult = firstNum + secondNum;
        } else if (operator.equals("-")) {
            this.finalResult = firstNum - secondNum;
        } else if (operator.equals("÷")) {
            if (secondNum == 0)
                return false;
            this.finalResult = (firstNum / secondNum);
        } else {
            this.finalResult = firstNum * secondNum;
        }
        return true;
    }
    public boolean evaluateExpression(String operand) {
        double number = 0;
        boolean negative = false;
        int occurrences = StringUtils.countOccurrencesOf(operand, ".");
        if (occurrences > 1) {
            System.out.println("Done regex");
            return false;
        }
        if (operand.contains("/")) {
            if (operand.charAt(0) == '-') {
                negative = true;
                operand = operand.substring(1);
            }
            number = getNum(operand, 3, operand.length()-1);
            if (number == 0)
                return false;
            number = 1 / number;
            if (negative)
                result = number * -1;
            else
                result = number;
        } else if (operand.contains("√")) {
            if (operand.charAt(0) == '-') {
                negative = true;
                operand = operand.substring(1);
            }
            number = getNum(operand, 2, operand.length()-1);
            if (number < 0)
                return false;
            number = Math.sqrt(number);
            if (negative)
                result = number * -1;
            else
                result = number;
        } else if (operand.contains("%")) {
            if (operand.charAt(0) == '-') {
                negative = true;
                operand = operand.substring(1);
            }
            number = getNum(operand, 1, operand.length() - 2);
            number = number * 100;
            if (negative)
                result = number * -1;
            else
                result = number;
        } else if (operand.contains("²")) {
            if (operand.charAt(0) == '-') {
                negative = true;
                operand = operand.substring(1, operand.length());
            }
            number = getNum(operand, 1, operand.length() - 2);
            number = number * number;
            if (negative)
                result = number * -1;
            else
                result = number;
        } else {
            result = Double.parseDouble(operand);
        }
        return true;
    }
    public double getNum(String operand, int startPoint, int endPoint) {
        String num = operand.substring(startPoint, endPoint);
        return Double.parseDouble(num);
    }

    public String backspace(String operand)
    {
        boolean check = false;
        if (operand.contains("/"))
        {
            if(operand.charAt(0) == '-')
            {
                operand = operand.substring(1);
                check = true;
            }
            operand = operand.substring(3, operand.length() - 1);
        }
        else if(operand.contains("%"))
        {
            if(operand.charAt(0) == '-')
            {
                operand = operand.substring(1);
                check = true;
            }
            operand = operand.substring(1, operand.length() - 2);
        }
        else if(operand.contains("√"))
        {
            if(operand.charAt(0) == '-')
            {
                operand = operand.substring(1);
                check = true;
            }
            operand = operand.substring(2, operand.length() - 1);
        }
        else if(operand.contains("²"))
        {
            if(operand.charAt(0) == '-')
            {
                operand = operand.substring(1);
                check = true;
            }
            operand = operand.substring(1, operand.length() - 2);
        }
        if(check)
        {
            if(!( Double.parseDouble(operand) < 0 ))
            {
                operand = "-" + operand;
            }
        }
        return operand;
    }

}
