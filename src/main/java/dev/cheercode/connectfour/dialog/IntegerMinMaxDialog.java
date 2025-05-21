package dev.cheercode.connectfour.dialog;

import java.util.Scanner;

public class IntegerMinMaxDialog implements Dialog<Integer> {
    private String title;
    private String error;
    private final int min;
    private final int max;
    private final Scanner scanner;

    public IntegerMinMaxDialog(String title, String error, int min, int max) {
        this.title = title;
        this.error = error;
        this.min = min;
        this.max = max;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public Integer input() {
        System.out.println(title);
        while (true) {
            try {
                int answer = Integer.parseInt(scanner.nextLine().trim());
                if (answer >= min && answer <= max) {
                    return answer;
                }
            } catch (Exception ignored) {
            }
            System.out.println(error);
        }
    }
}
