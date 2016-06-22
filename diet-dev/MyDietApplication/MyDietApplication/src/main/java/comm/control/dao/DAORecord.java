package comm.control.dao;
import java.util.Date;
import java.text.SimpleDateFormat;
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
 * Created by Julio on 17/11/13.
 * @author Julio Cesar Roa Gil
 * @version 1.0
 */
public class DAORecord {

    // Database fields
    private SQLiteDatabase database;
    private ConnectionDB conn;
    private String[] allColumns = { ConnectionDB.COLUMN_ID,
            ConnectionDB.COLUMN_REGISTER_DATE };
    private DAOMappingRecord mappingRecord;
    private static final String MEAL_TYPE = "M";
    private static final String INGREDIENT_TYPE = "I";

    // Queries used to report
    private static final String REPORT_DAILY_QUERY =
            "select * \n" +
            "from (\n" +
            "select \n" +
            "    registerDate \n" +
            "   ,sum(meal.calorieValue) + sum (ing.calorieValue) as calories\n" +
            "from mapping_record map\n" +
            "join record rec on map.record_id = rec.id\n" +
            "left join meal meal on map.group_id = meal.id and map.type =  'M'\n" +
            "left join ingredient ing on map.group_id = ing.id and map.type = 'I'\n" +
            "where date(rec.registerDate) >= date('now')\n" +
            "group by 1)";

    private static final String REPORT_WEEKLY_QUERY ="select * \n" +
            "from (\n" +
            "select \n" +
            "    registerDate \n" +
            "   ,sum(meal.calorieValue) + sum (ing.calorieValue) as calories\n" +
            "from mapping_record map\n" +
            "join record rec on map.record_id = rec.id\n" +
            "left join meal meal on map.group_id = meal.id and map.type =  'M'\n" +
            "left join ingredient ing on map.group_id = ing.id and map.type = 'I'\n" +
            "where date(rec.registerDate) >= date('now','-7 day')\n" +
            "group by 1)";

    //Constructor
    public DAORecord(Context context) {
        conn = new ConnectionDB(context);
    }

    //Opening connection
    public void open() throws SQLException {
        //database = conn.getWritableDatabase();
        database = conn.openDataBase(database);
    }

    //Closing connection
    public void close() {
        conn.close();
    }

    /**
     * Create a new record or entry
     * @param record
     * @return
     */
    public Record createRecord(Record record) {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        ContentValues values = new ContentValues();
        values.put(conn.COLUMN_REGISTER_DATE, dateFormat.format(date));
        //values.put(DBHelper.COLUMN_RECEIVEDATE, record.getRegisterDate().getTime());

        long insertId = database.insert(conn.TABLE_RECORDS, null,
                values);
        Cursor cursor = database.query(conn.TABLE_RECORDS,
                allColumns, conn.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Record newRecord = cursorToRecord(cursor);
        cursor.close();

        addMappingMeal(record);
        addMappingIngredient(record);

        return newRecord;
    }

    /**
     * Add mapping records with meals
     * @param record
     */
    public void addMappingMeal (Record record){
        if (record.getMeals() != null){
            for (int i=0; i < record.getMeals().size(); i++){
                mappingRecord.createMappingRecord(record.getId(),record.getMeals().get(i).getId(),MEAL_TYPE);
            }
        }
    }

    /**
     * Add mapping records with ingredients
     * @param record
     */
    public void addMappingIngredient (Record record){
        if (record.getIngredients() != null){
            for (int i=0; i < record.getIngredients().size(); i++){
                mappingRecord.createMappingRecord(record.getId(),record.getIngredients().get(i).getId(),INGREDIENT_TYPE);
            }
        }
    }

    /**
     * Delete mapping records with meals
     * @param record
     */
    public void dropMappingMeal (Record record){
        if (record.getMeals() != null){
            mappingRecord.deleteMappingRecord(record.getId(),MEAL_TYPE);
        }
    }

    /**
     * Delete mapping records with ingredients
     * @param record
     */
    public void dropMappingIngredient (Record record){
        if (record.getIngredients() != null){
            mappingRecord.deleteMappingRecord(record.getId(),INGREDIENT_TYPE);
        }
    }

    /**
     * Update record
     * @param record
     */
    public void updateIngredient(Record record) {
        long id = record.getId();
        System.out.println("Record updated with id: " + id);

        if(record.getIngredients() != null){
            dropMappingMeal(record);
        }

        if(record.getMeals() != null){
            dropMappingIngredient(record);
        }

        addMappingMeal(record);
        addMappingIngredient(record);
        //database.update(conn.TABLE_INGREDIENTS,newValues, conn.COLUMN_ID + " = " + id, null);
    }

    /**
     * Delete Record
     * @param record
     */
    public void deleteRecord(Record record) {
        long id = record.getId();
        System.out.println("Record deleted with id: " + id);
        database.delete(conn.TABLE_RECORDS, conn.COLUMN_ID
                + " = " + id, null);

        dropMappingMeal(record);
        dropMappingIngredient(record);
    }

    /**
     * List of all Records
     * @return list of Records
     */
    public List<Record> getAllRecords() {
        List<Record> records = new ArrayList<Record>();

        Cursor cursor = database.query(conn.TABLE_RECORDS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Record record = cursorToRecord(cursor);
            records.add(record);
            cursor.moveToNext();
        }
        cursor.close();
        return records;
    }


    /**
     * List of the daily records
     * @return list of Records
     */
    public List<Record> getReportDaily() {
        List<Record> records = new ArrayList<Record>();

        Cursor cursor = database.rawQuery(REPORT_DAILY_QUERY, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Record record = cursorToRecord(cursor);
            records.add(record);
            cursor.moveToNext();
        }
        cursor.close();
        return records;
    }

    /**
     * List of the weekly records
     * @return list of Records
     */
    public List<Record> getReportWeekly() {
        List<Record> records = new ArrayList<Record>();

        Cursor cursor = database.rawQuery(REPORT_WEEKLY_QUERY, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Record record = cursorToRecord(cursor);
            records.add(record);
            cursor.moveToNext();
        }
        cursor.close();
        return records;
    }

    /**
     * Get all the meals related to a particular record
     * @param record
     * @return a record with the meals included
     */
    public Record getMealsFromRecord(Record record) {
        ArrayList<Meal> meals = new ArrayList<Meal>();

        //String[] args = new String[] {name};

        String SQL_SEARCH_BY_RECORD_ID = "select \n" +
                "  case when type='M' then\n" +
                "    meal.name\n" +
                "  else \n" +
                "    ing.name end      \n" +
                "from mapping_record map\n" +
                "join record rec on map.record_id = rec.id\n" +
                "left join meal meal on map.group_id = meal.id and map.type =  'M'\n" +
                "left join ingredient ing on map.group_id = ing.id and map.type = 'I'\n" +
                "where rec.id ="+record.getId();
        Cursor cursor = database.rawQuery(SQL_SEARCH_BY_RECORD_ID, null);

        //Cursor cursor = database.query(conn.TABLE_MEALS, allColumns, conn.COLUMN_NAME, args, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Meal meal = cursorToMeal(cursor);
            meals.add(meal);
            cursor.moveToNext();
        }
        cursor.close();
        Log.w(DAORecord.class.getName(), "Getting all meals with id " + record.getId());

        record.setMeals(meals);
        return record;
    }

    /**
     * Cursor used to set an record object
     * @param cursor
     * @return
     */
    private Record cursorToRecord(Cursor cursor) {
        Record record = new Record();
        record.setRecordDate(cursor.getString(0));
        record.setCalories(cursor.getInt(1));
        return record;
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

    /**
     * Cursor used to set an meal object
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


}
