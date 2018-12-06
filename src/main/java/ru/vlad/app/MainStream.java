package ru.vlad.app;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStream {
    public static void main(String[] args) {
        int[] values1 = {1, 2, 3, 3, 2, 3};
        int[] values2 = {9, 8};
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

        System.out.println("\n1. minValue");
        System.out.println(Arrays.toString(values1) + "  ->  " + minValue(values1));
        System.out.println(Arrays.toString(values2) + "  ->  " + minValue(values2));
        System.out.println("\n2. oddOrEven");
        System.out.println(list1 + " (sum " + list1.stream().mapToInt(x -> x).sum() + ")  ->  " + oddOrEven(list1));
        System.out.println(list2 + " (sum " + list2.stream().mapToInt(x -> x).sum() + ")  ->  " + oddOrEven(list2));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .sorted()
                .distinct()
                .reduce((v1, v2) -> v1 * 10 + v2).orElse(0);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return Stream.of(integers.stream().mapToInt(x -> x).sum() % 2)
                .flatMap((x) -> integers.stream().filter((i) -> (i % 2 != x)))
                .collect(Collectors.toList());
    }
}
