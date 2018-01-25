package com.restcalc.ServerPack;

import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by Stanislav on 24.01.2018.
 */
public class CalcClass {


    private String exp;
    private double result;
    private String time;

   public double calculated(String exp){
       try {
           SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yy");
           time = dateFormat.format(new Date());
           exp = rpn(exp);

           return calculate(exp);
       } catch (Exception e) {
           System.out.println(e.getMessage());
       }
       return 0;
   }

    //Reverse Polish notation
    private  String rpn(String sIn) throws Exception {
        StringBuilder sbStack = new StringBuilder(""), sbOut = new StringBuilder("");
        char cIn, cTmp;

        for (int i = 0; i < sIn.length(); i++) {
            cIn = sIn.charAt(i);
            if (isOp(cIn)) {
                while (sbStack.length() > 0) {
                    cTmp = sbStack.substring(sbStack.length()-1).charAt(0);
                    if (isOp(cTmp) && (opPriority(cIn) <= opPriority(cTmp))) {
                        sbOut.append(" ").append(cTmp).append(" ");
                        sbStack.setLength(sbStack.length()-1);
                    } else break;
                }
                sbOut.append(" ");
                sbStack.append(cIn);
            } else if ('(' == cIn) {
                sbStack.append(cIn);
            } else if (')' == cIn) {
                cTmp = sbStack.substring(sbStack.length()-1).charAt(0);
                while ('(' != cTmp) {
                    if (sbStack.length() < 1) {
                        throw new Exception("Ошибка разбора скобок. Проверьте правильность выражения.");
                    }
                    sbOut.append(" ").append(cTmp);
                    sbStack.setLength(sbStack.length()-1);
                    cTmp = sbStack.substring(sbStack.length()-1).charAt(0);
                }
                sbStack.setLength(sbStack.length()-1);
            } else {
                sbOut.append(cIn);
            }
        }
        while (sbStack.length() > 0) {
            sbOut.append(" ").append(sbStack.substring(sbStack.length()-1));
            sbStack.setLength(sbStack.length()-1);
        }

        return  sbOut.toString();
    }

    private boolean isOp(char c) {
        switch (c) {
            case '-':
            case '+':
            case '*':
            case '/':
            case '^':
                return true;
        }
        return false;
    }
    //operand priority
    private byte opPriority(char op) {
        switch (op) {
            case '^':
                return 3;
            case '*':
            case '/':
            case '%':
                return 2;
        }
        return 1; // +  -
    }
    private  double calculate(String sIn) throws Exception {
        double dA = 0, dB = 0;
        String sTmp;
        Deque<Double> stack = new ArrayDeque<>();
        StringTokenizer st = new StringTokenizer(sIn);
        while(st.hasMoreTokens()) {
            try {
                sTmp = st.nextToken().trim();
                if (1 == sTmp.length() && isOp(sTmp.charAt(0))) {
                    if (stack.size() < 2 && sTmp.charAt(0) != '-') {
                        throw new Exception("Неверное количество данных в стеке для операции " + sTmp);
                    }
                    dB = stack.pop();
                    if (stack.size() != 0){
                        dA = stack.pop();
                    }else dA = 0;
                    switch (sTmp.charAt(0)) {
                        case '+':
                            dA += dB;
                            break;
                        case '-':
                            dA -= dB;
                            break;
                        case '/':
                            dA /= dB;
                            break;
                        case '*':
                            dA *= dB;
                            break;
                        case '%':
                            dA %= dB;
                            break;
                        case '^':
                            dA = Math.pow(dA, dB);
                            break;
                        default:
                            throw new Exception("Недопустимая операция " + sTmp);
                    }
                    stack.push(dA);
                } else {
                    dA = Double.parseDouble(sTmp);
                    stack.push(dA);
                }
            } catch (Exception e) {
                throw new Exception("Недопустимый символ в выражении");
            }
        }
        if (stack.size() > 1) {
            throw new Exception("Количество операторов не соответствует количеству операндов");
        }
        return stack.pop();
    }

    public String getExp() {
        return exp;
    }

    public String getTime() {
        return time;
    }
}
