package comm.control.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import comm.control.common.ConnectionDB;

/**
 * Created by Julio on 23/11/13.
 * @author Julio Cesar Roa Gil
 * @version 1.0
 */
public class DAOMappingMeal {
    // Database fields
    private SQLiteDatabase database;
    private ConnectionDB conn;
    private String[] allColumns = { ConnectionDB.COLUMN_MEAL_ID,
            ConnectionDB.COLUMN_INGREDIENT_ID };

    //Constructor
    public DAOMappingMeal(Context context) {
        conn = new ConnectionDB(context);
    }

    //Opening connection
    public void open() throws SQLException {
        Log.w(DAOMappingMeal.class.getName(), "Opening connection db...");
        database = conn.openDataBase(database);
    }

    //Closing connection
    public void close() {
        Log.w(DAOMappingMeal.class.getName(),"Closing connection db...");
        conn.close();
    }

    /**
     * Create a new mapping between meals and ingredient
     * @param mealId
     * @param ingredientId
     * @return
     */
    public long createMappingMeal(int mealId, int ingredientId) {
        ContentValues values = new ContentValues();
        values.put(conn.COLUMN_MEAL_ID, mealId);
        values.put(conn.COLUMN_INGREDIENT_ID, ingredientId);
        long insertId = database.insert(conn.TABLE_MAPPING_MEAL, null,
                values);
        Cursor cursor = database.query(conn.TABLE_MAPPING_MEAL,
                allColumns, conn.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();
        Log.w(DAOMappingMeal.class.getName(),"Created mapping meal "+insertId);
        return insertId;
    }

    /**
     * Delete an specific mapping for a meal
     * @param mealId
     */
    public void deleteMappingMeal(int mealId) {
        database.delete(conn.TABLE_MAPPING_MEAL, conn.COLUMN_MEAL_ID
                + " = " + mealId, null);
        Log.w(DAOMappingMeal.class.getName(),"Deleted mapping for mealID " + mealId);
    }

}
