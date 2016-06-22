package comm.control.dao;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import comm.control.common.ConnectionDB;
import comm.model.Ingredient;
import comm.model.Meal;
import comm.model.Record;

/**
 * Created by Julio on 12/11/13.
 * @author Julio Cesar Roa Gil
 * @version 1.0
 */
public class DAOMeal {
    // Database fields
    private SQLiteDatabase database;
    private ConnectionDB conn;
    private String[] allColumns = { ConnectionDB.COLUMN_ID,
            ConnectionDB.COLUMN_NAME, ConnectionDB.COLUMN_CALORIE_VALUE };
    private DAOMappingMeal mappingMeal;

    //Constructor
    public DAOMeal(Context context) {
        conn = new ConnectionDB(context);
    }

    //Opening connection
    public void open() throws SQLException {
        database = conn.openDataBase(database);
        //database = conn.getWritableDatabase();
        //database = conn.getReadableDatabase();
        Log.w(DAOMeal.class.getName(), "Opening database...");
    }

    //Closing connection
    public void close() {
        conn.close();
        Log.w(DAOMeal.class.getName(), "Closing database...");
    }

    /**
     * Create a new meal
     * @param meal
     * @return
     */
    public Meal createMeal(Meal meal) {
        ContentValues values = new ContentValues();
        values.put(conn.COLUMN_NAME, meal.getName());
        values.put(conn.COLUMN_CALORIE_VALUE, meal.calculateCalories());
        long insertId = database.insert(conn.TABLE_MEALS, null,
                values);
        Cursor cursor = database.query(conn.TABLE_MEALS,
                allColumns, conn.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Meal newMeal = cursorToMeal(cursor);
        cursor.close();

        if (meal.getIngredients() != null){
            for (int i=0; i < meal.getIngredients().size(); i++){
                mappingMeal.createMappingMeal(meal.getId(),meal.getIngredients().get(i).getId());
            }
        }

        Log.w(DAOMeal.class.getName(), "Created meal " + meal.getId());
        return newMeal;
    }

    /**
     * Update meal
     * @param meal
     */
    public void updateMeal(Meal meal) {
        long id = meal.getId();
        System.out.println("Meal updated with id: " + id);
        ContentValues newValues = new ContentValues();
        newValues.put(conn.COLUMN_NAME,meal.getName());
        newValues.put(conn.COLUMN_CALORIE_VALUE, meal.calculateCalories());
        database.update(conn.TABLE_MEALS,newValues, conn.COLUMN_ID + " = " + id, null);

        if (meal.getIngredients() != null){
            mappingMeal.deleteMappingMeal(meal.getId());
            for (int i=0; i < meal.getIngredients().size(); i++){
                mappingMeal.createMappingMeal(meal.getId(),meal.getIngredients().get(i).getId());
            }
        }

        Log.w(DAOMeal.class.getName(), "Updated meal " + meal.getId());
    }

    /**
     * Delete meal
     * @param meal
     */
    public void deleteMeal(Meal meal) {
        long id = meal.getId();
        System.out.println("Meal deleted with id: " + id);
        database.delete(conn.TABLE_MEALS, conn.COLUMN_ID
                + " = " + id, null);

        mappingMeal.deleteMappingMeal(meal.getId());

        Log.w(DAOMeal.class.getName(), "Deleted meal " + meal.getId());
    }

    /**
     * List of all meals
     * @return list of meals
     */
    public List<Meal> getAllMeals() {
        List<Meal> meals = new ArrayList<Meal>();

        Cursor cursor = database.query(conn.TABLE_MEALS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Meal meal = cursorToMeal(cursor);
            meals.add(meal);
            cursor.moveToNext();
        }
        cursor.close();
        Log.w(DAOMeal.class.getName(), "Getting all meals...");
        return meals;
    }

    /***
     * Search meals based on his name
     * @param name
     * @return
     */
    public List<Meal> searchMeals(String name) {
        List<Meal> meals = new ArrayList<Meal>();
        //String[] args = new String[] {name};

        //String SQL_SEARCH_BY_NAME = "SELECT * FROM " + conn.TABLE_MEALS + " WHERE " + conn.COLUMN_NAME + " like '%" + name + "%'";
        String SQL_SEARCH_BY_NAME = "SELECT * FROM meal WHERE name like '%" + name + "%'";
        Log.i("Cursor",SQL_SEARCH_BY_NAME);

        Cursor cursor = database.rawQuery(SQL_SEARCH_BY_NAME, null);

        //Cursor cursor = database.query(conn.TABLE_MEALS, allColumns, conn.COLUMN_NAME, args, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Meal meal = cursorToMeal(cursor);
            meals.add(meal);
            cursor.moveToNext();
        }
        cursor.close();
        Log.w(DAOMeal.class.getName(), "Getting all meals with name "+name);
        return meals;
    }

    /**
     * Get all the ingredients that belong a particular meal
     * @param meal
     * @return a meal with ingredients included
     */
    public Meal getIngredientsFromMeal(Meal meal) {
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

        //String[] args = new String[] {name};

        String SQL_SEARCH_BY_MEAL_ID = "select ing.name\n" +
                "from mapping_meal map\n" +
                "join meal meal on map.meal_id = meal.id\n" +
                "join ingredient ing on map.ingredient_id = ing.\n" +
                "where meal.id=" + meal.getId();
        Cursor cursor = database.rawQuery(SQL_SEARCH_BY_MEAL_ID, null);

        //Cursor cursor = database.query(conn.TABLE_MEALS, allColumns, conn.COLUMN_NAME, args, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Ingredient ingredient = cursorToIngredient(cursor);
            ingredients.add(ingredient);
            cursor.moveToNext();
        }
        cursor.close();
        Log.w(DAOMeal.class.getName(), "Getting all ingredients with id " + meal.getId());

        meal.setIngredients(ingredients);
        return meal;
    }

    /**
     *Cursor used to set an meal object
     * @param cursor
     * @return
     */
    private Meal cursorToMeal(Cursor cursor) {
        Meal meal = new Meal();
        meal.setId(cursor.getInt(0));
        meal.setName(cursor.getString(1));
        meal.setCalorieValue(cursor.getInt(2));
        return meal;
    }

    /**
     * Cursor used to set an ingredient object
     * @param cursor
     * @return
     */

    private Ingredient cursorToIngredient(Cursor cursor) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(cursor.getInt(0));
        ingredient.setName(cursor.getString(1));
        ingredient.setCalorieValue(cursor.getInt(2));
        return ingredient;
    }
}
