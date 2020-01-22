package ru.javawebinar.topjava;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo application</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {
    public static void main(String[] args) {
        LocalTime time=LocalTime.of(7,0);

        System.out.println(time.toString());
    }
}
