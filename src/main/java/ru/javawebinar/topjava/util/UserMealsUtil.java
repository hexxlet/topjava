package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );

        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000));
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
                                                                    LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

        List<UserMealWithExceed> mealsWithExceed = getWithExceeded(mealList, caloriesPerDay);

        List<UserMealWithExceed> result = new ArrayList<>();

        for (UserMealWithExceed meal : mealsWithExceed){
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                result.add(meal);
        }

        return result;
    }


    public static List<UserMealWithExceed> getWithExceeded(List<UserMeal> mealList, int caloriesPerDay){

        // Делаем мапу с датами и количеством каллорий, чтобы узнать превышено ли количество каллорий
        Map<LocalDate, Integer> datesAndCalories = new HashMap<>();

        for (UserMeal meal : mealList){
            if (!datesAndCalories.containsKey(meal.getDateTime().toLocalDate()))
                datesAndCalories.put(meal.getDateTime().toLocalDate(), meal.getCalories());
            else {
                int calories = datesAndCalories.get(meal.getDateTime().toLocalDate());
                calories += meal.getCalories();
                datesAndCalories.remove(meal.getDateTime().toLocalDate());
                datesAndCalories.put(meal.getDateTime().toLocalDate(), calories);
            }
        }

        List<UserMealWithExceed> result = new ArrayList<>();

        for (UserMeal meal : mealList){
            if (datesAndCalories.get(meal.getDateTime().toLocalDate()) > caloriesPerDay)
                result.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(),
                        meal.getCalories(), true));
            else result.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(),
                    meal.getCalories(), false));
        }

        return result;
    }
}
