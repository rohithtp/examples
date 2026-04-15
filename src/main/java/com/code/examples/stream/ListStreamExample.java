package com.code.examples.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListStreamExample {

    public static void main(String[] args) {
        // Create a list of integers
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // Filter even numbers
        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("Even Numbers: " + evenNumbers);

        // Map numbers to their squares
        List<Integer> squaredNumbers = numbers.stream()
                .map(n -> n * n)
                .collect(Collectors.toList());

        System.out.println("Squared Numbers: " + squaredNumbers);

        // Filter odd numbers, map them to strings, and collect the results
        List<String> oddNumberStrings = numbers.stream()
                .filter(n -> n % 2 != 0)
                .map(String::valueOf)
                .collect(Collectors.toList());

        System.out.println("Odd Numbers as Strings: " + oddNumberStrings);

        // Find the sum of all numbers
        int sum = numbers.stream()
                .reduce(0, Integer::sum);

        System.out.println("Sum of All Numbers: " + sum);
    }
}
