package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        HashMap<String, Integer> sumCaloriesPerDay=new HashMap<>();
        LinkedList<UserMeal> filteredByTime=new LinkedList<>();
        for (UserMeal userMeal:meals){
            sumCaloriesPerDay.merge(userMeal.getDateTime().toLocalDate().toString(), userMeal.getCalories(), Integer::sum);
            LocalTime time=userMeal.getDateTime().toLocalTime();
            if (time.isAfter(startTime)&time.isBefore(endTime)){
                filteredByTime.add(userMeal);
            }
        }

        List<UserMealWithExcess> result=new LinkedList<>();
        for (UserMeal meal:filteredByTime){
                result.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), getCaloriesPerDayForMeal(sumCaloriesPerDay, meal)>caloriesPerDay));

        }
        return result;
    }

    private static int getCaloriesPerDayForMeal(HashMap<String, Integer> map, UserMeal meal){
        return map.get(meal.getDateTime().toLocalDate().toString());
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        meals.stream()
                .collect(Collectors.groupingBy(UserMealsUtil::getDateOfMeal, Collectors.summingInt(UserMeal::getCalories)));
        return null;
    }

    private static String getDateOfMeal(UserMeal meal){
        return meal.getDateTime().toLocalDate().toString();
    }
}
