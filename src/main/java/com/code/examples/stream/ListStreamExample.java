package com.code.examples.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListStreamExample {

  public static void main(String[] args) {
    // Create a list of integers
    List<Integer> numbers = new ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    numbers.add(11);

    // Filter even numbers and collect them into a new list
    List<Integer> evenNumbers =
        numbers.stream().filter(n -> n % 2 == 0).collect(Collectors.toList());

    System.out.println("Even Numbers: " + evenNumbers);

    // Map each number to its square and collect the results into a new list
    List<Integer> squaredNumbers = numbers.stream().map(n -> n * n).collect(Collectors.toList());

    System.out.println("Squared Numbers: " + squaredNumbers);

    // Filter odd numbers, map them to strings, and collect the results into a new list
    List<String> oddNumberStrings =
        numbers.stream().filter(n -> n % 2 != 0).map(String::valueOf).collect(Collectors.toList());

    System.out.println("Odd Numbers as Strings: " + oddNumberStrings);

    // Find the sum of all numbers using reduce
    int sum = numbers.stream().reduce(0, Integer::sum);

    System.out.println("Sum of All Numbers: " + sum);
  }
}
