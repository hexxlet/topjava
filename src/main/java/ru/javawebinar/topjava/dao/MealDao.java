package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class MealDao implements MealDaoInt{
    private static AtomicInteger counter = new AtomicInteger(0);

    @Override
    public synchronized void addMeal(Meal meal) {
        MealsUtil.meals.add(meal);
        counter.getAndIncrement();
    }

    @Override
    public synchronized void deleteMeal(int id) {
        MealsUtil.meals.remove(id);
        IntStream.range(0, MealsUtil.meals.size()).forEach(i -> MealsUtil.meals.get(i).setId(i));
    }

    @Override
    public synchronized void updateMeal(Meal meal) {
        MealsUtil.meals.remove(meal.getId());
        MealsUtil.meals.add(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAllMeals() {
        return MealsUtil.meals;
    }

    @Override
    public Meal getMealById(int id) {
        return MealsUtil.meals.get(id);
    }
}
