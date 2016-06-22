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
/**
 * Created by Julio on 17/11/13.
 * @author Julio Cesar Roa Gil
 * @version 1.0
 */
public class DAOIngredient {

    // Database fields
    private SQLiteDatabase database;
    private ConnectionDB conn;
    private String[] allColumns = { ConnectionDB.COLUMN_ID,
            ConnectionDB.COLUMN_NAME, ConnectionDB.COLUMN_CALORIE_VALUE };

    //Constructor
    public DAOIngredient(Context context) {
        conn = new ConnectionDB(context);
    }

    //Opening connection
    public void open() throws SQLException {
        Log.w(DAOIngredient.class.getName(),"Opening connection db...");
        database = conn.openDataBase(database);
    }

    //Closing connection
    public void close() {
        Log.w(DAOIngredient.class.getName(),"Closing connection db...");
        conn.close();
    }

    /**
     * Create a new ingredient
     * @param ingredient
     * @return
     */
    public Ingredient createIngredient(Ingredient ingredient) {
        ContentValues values = new ContentValues();
        values.put(conn.COLUMN_NAME, ingredient.getName());
        values.put(conn.COLUMN_CALORIE_VALUE, ingredient.getCalorieValue());
        long insertId = database.insert(conn.TABLE_INGREDIENTS, null,
                values);
        Cursor cursor = database.query(conn.TABLE_INGREDIENTS,
                allColumns, conn.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Ingredient newIngredient = cursorToIngredient(cursor);
        cursor.close();
        Log.w(DAOIngredient.class.getName(),"Created ingredient "+insertId);
        return newIngredient;
    }

    /**
     * Update ingredient
     * @param ingredient
     */
    public void updateIngredient(Ingredient ingredient) {
        long id = ingredient.getId();
        System.out.println("Ingredient updated with id: " + id);
        ContentValues newValues = new ContentValues();
        newValues.put(conn.COLUMN_NAME,ingredient.getName());
        newValues.put(conn.COLUMN_CALORIE_VALUE, ingredient.getCalorieValue());
        database.update(conn.TABLE_INGREDIENTS,newValues, conn.COLUMN_ID + " = " + id, null);
        Log.w(DAOIngredient.class.getName(),"Updated ingredient "+ingredient.getId());
    }

    /**
     * Delete ingredient
     * @param ingredient
     */
    public void deleteIngredient(Ingredient ingredient) {
        long id = ingredient.getId();
        System.out.println("Ingredient deleted with id: " + id);
        database.delete(conn.TABLE_INGREDIENTS, conn.COLUMN_ID
                + " = " + id, null);
        Log.w(DAOIngredient.class.getName(),"Deleted ingredient "+ingredient.getId());
    }

    /**
     * List of all ingredients
     * @return list of ingredients
     */
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();

        Cursor cursor = database.query(conn.TABLE_INGREDIENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Ingredient ingredient = cursorToIngredient(cursor);
            ingredients.add(ingredient);
            cursor.moveToNext();
        }
        cursor.close();
        Log.w(DAOMeal.class.getName(), "Getting all ingredients...");
        return ingredients;
    }

    /***
     * Search meals based on his name
     * @param name
     * @return
     */
    public List<Ingredient> searchIngredients(String name) {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        //String[] args = new String[] {name};

        String SQL_SEARCH_BY_NAME = "SELECT * FROM " + conn.TABLE_INGREDIENTS + " WHERE " + conn.COLUMN_NAME + "='%" + name + "%'";
        Cursor cursor = database.rawQuery(SQL_SEARCH_BY_NAME, null);

        //Cursor cursor = database.query(conn.TABLE_MEALS, allColumns, conn.COLUMN_NAME, args, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Ingredient ingredient = cursorToIngredient(cursor);
            ingredients.add(ingredient);
            cursor.moveToNext();
        }
        cursor.close();
        Log.w(DAOIngredient.class.getName(), "Getting all ingredients with name "+name);
        return ingredients;
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
