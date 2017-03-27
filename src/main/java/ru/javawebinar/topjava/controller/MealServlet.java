package ru.javawebinar.topjava.controller;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInt;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    MealDaoInt dao = new MealDao();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to meals");

        String action = request.getParameter("action");
        List<MealWithExceed> mealList = MealsUtil.getWithExceeded(dao.getAllMeals(), 2000);
        request.setCharacterEncoding("UTF-8");

        if (action == null){
            request.setAttribute("meals", mealList);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
        if (action.equalsIgnoreCase("delete")){
            int mealId = Integer.parseInt(request.getParameter("id"));
            dao.deleteMeal(mealId);
            request.setAttribute("meals", MealsUtil.getWithExceeded(dao.getAllMeals(), 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
        if (action.equalsIgnoreCase("edit")){
            int mealId = Integer.parseInt(request.getParameter("id"));
            Meal meal = dao.getMealById(mealId);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/editmeals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        LocalDate date = LocalDate.parse(req.getParameter("dateTime"), formatter);
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(0, 0));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));

        if (id == null || id.isEmpty()){
                dao.addMeal(new Meal(dao.getAllMeals().size(), dateTime, description, calories));
        }
        else {
            dao.updateMeal(new Meal(Integer.parseInt(id), dateTime, description, calories));
        }
        req.setAttribute("meals", MealsUtil.getWithExceeded(dao.getAllMeals(), 2000));
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
