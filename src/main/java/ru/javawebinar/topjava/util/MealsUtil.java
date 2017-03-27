package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class MealsUtil {

    public static List<Meal> meals = new CopyOnWriteArrayList<>(Arrays.asList(
            new Meal(0, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    ));


    public static void main(String[] args) {

        List<MealWithExceed> mealsWithExceeded = getFilteredWithExceeded(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsWithExceeded.forEach(System.out::println);

    }

    public static List<MealWithExceed>  getFilteredWithExceeded(List<Meal> mealList, LocalTime startTime,
                                                                    LocalTime endTime, int caloriesPerDay) {

        return getWithExceeded(mealList, caloriesPerDay).stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }


    public static List<MealWithExceed> getWithExceeded(List<Meal> mealList, int caloriesPerDay){

        // Делаем мапу с датами и количеством каллорий, чтобы узнать превышено ли количество каллорий за определенный день

        Map<LocalDate, Integer> map = mealList.stream().collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(), Collectors.summingInt(Meal::getCalories)));

        // Возвращаем list, который наполнен UserMealWithExceed. Узнаем exceed или нет, сравнивая установленное количество каллорий
        // с суммарным количеством каллорий за определенный день

        return mealList.stream()
                .map(meal -> new MealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(),
                        meal.getCalories(), caloriesPerDay < map.get(meal.getDateTime().toLocalDate())))
                .collect(Collectors.toList());
    }
}