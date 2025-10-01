package com.sachin.fintrack.utils;

import com.sachin.fintrack.R;
import com.sachin.fintrack.models.Category;

import java.util.ArrayList;

public class Constants {
    public static String INCOME = "INCOME";
    public static String EXPENSE = "EXPENSE";

    public static ArrayList<Category> categories;

    public static int DAILY = 0;
    public static int MONTHLY = 1;
    public static int CALENDER = 2;
    public static int SUMMARY = 3;
    public static int NOTES = 4;

    public static int SELECTED_TAB = 0;
    public static int SELECTED_TAB_STATS = 0;
    public static String SELECTED_STATS_TYPE = Constants.INCOME;

    public static void setCategories(){
        categories = new ArrayList<>();
        categories.add(new Category("Salary", R.drawable.ic_salary, R.color.category1));
        categories.add(new Category("Business", R.drawable.ic_business, R.color.category2));
        categories.add(new Category("Freelance", R.drawable.ic_freelance, R.color.category3));
        categories.add(new Category("Investment", R.drawable.ic_investment, R.color.category4));
        categories.add(new Category("Interest", R.drawable.ic_interest, R.color.category5));
        categories.add(new Category("Refund", R.drawable.ic_refund, R.color.category6));
        categories.add(new Category("Dining Out", R.drawable.ic_dining, R.color.category7));
        categories.add(new Category("Rent", R.drawable.ic_rent, R.color.category8));
        categories.add(new Category("Utilities", R.drawable.ic_utilities, R.color.category9));
        categories.add(new Category("Internet", R.drawable.ic_internet, R.color.category10));
        categories.add(new Category("Transport", R.drawable.ic_fuel, R.color.category11));
        categories.add(new Category("Ride", R.drawable.ic_taxi, R.color.category12));
        categories.add(new Category("Vehicle Maint", R.drawable.ic_maintenance, R.color.category13));
        categories.add(new Category("Shopping", R.drawable.ic_shopping, R.color.category14));
        categories.add(new Category("Subscriptions", R.drawable.ic_subscriptions, R.color.category15));
        categories.add(new Category("Health", R.drawable.ic_health, R.color.category16));
        categories.add(new Category("Education", R.drawable.ic_education, R.color.category17));
        categories.add(new Category("Gift", R.drawable.ic_gift, R.color.category18));
        categories.add(new Category("Cash Withdrawal", R.drawable.ic_withdrawal, R.color.category19));
        categories.add(new Category("Other", R.drawable.ic_other, R.color.category20));

    }

    public static Category getCategoryDetails(String categoryName){
        for (Category cat : categories){
            if (cat.getCategoryName().equals(categoryName)){
                return cat;
            }
        }
        return null;
    }

    public static int getAccountsColor(String accountName){
        switch (accountName){
            case "Bank":
                return R.color.bank_color;
            case "Cash":
                return R.color.cash_color;
            case "Card":
                return R.color.card_color;
            default:
                return R.color.default_color;
        }
    }

}
