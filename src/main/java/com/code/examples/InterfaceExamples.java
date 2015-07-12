package com.code.examples;

/**
 * Created by rtheramb on 7/12/2015.
 */
public class InterfaceExamples {

    public  Integer process(Integer[] values, IOperation<Integer> operation) {
        ICalculator<Integer> calculator = new IntegerCalculator<Integer>();
        return calculator.process(operation, values);
    }

}

interface IOperation<T> {
    T process(T[] nos);
}



class Addition implements IOperation<Integer> {

    @Override
    public Integer process(Integer[] nos) {
        int result = 0;
        for (int no : nos) {
            result += no;
        }
        return result;
    }

}


class Subtraction implements IOperation<Integer> {

    @Override
    public Integer process(Integer[] nos) {
        Integer result = 0;
        boolean elementZero = true;
        for (int no : nos) {
            if (elementZero) {
                result = no;
                elementZero = false;
            } else {
                result -= no;
            }
        }
        return result;
    }

}

interface ICalculator<T> extends IOperation<T>{
    T process(IOperation<T> operation, T[] nos);
}

class IntegerCalculator<T> implements ICalculator<T> {

    private IOperation<T> operation;

    public T process(IOperation<T> operation, T[] nos) {
        this.operation = operation;
        return this.process(nos);
    }

    @Override
    public T process(T[] nos) {
        return operation.process(nos);
    }

}