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
public class DAOMappingRecord {

    // Database fields
    private SQLiteDatabase database;
    private ConnectionDB conn;
    private String[] allColumns = { ConnectionDB.COLUMN_MEAL_ID,
            ConnectionDB.COLUMN_INGREDIENT_ID };

    //Constructor
    public DAOMappingRecord(Context context) {
        conn = new ConnectionDB(context);
    }

    //Opening connection
    public void open() throws SQLException {
        Log.w(DAOMappingRecord.class.getName(), "Opening connection db...");
        database = conn.openDataBase(database);
    }

    //Closing connection
    public void close() {
        Log.w(DAOMappingRecord.class.getName(),"Closing connection db...");
        conn.close();
    }

    /**
     * Create a new mapping between meals and ingredient
     * @param recordId
     * @param groupId
     * @return
     */
    public long createMappingRecord(int recordId, int groupId, String type) {
        ContentValues values = new ContentValues();
        values.put(conn.COLUMN_MEAL_ID, recordId);
        values.put(conn.COLUMN_INGREDIENT_ID, groupId);
        values.put(conn.COLUMN_TYPE, type);
        long insertId = database.insert(conn.TABLE_MAPPING_RECORD, null,
                values);
        Cursor cursor = database.query(conn.TABLE_MAPPING_RECORD,
                allColumns, conn.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();
        Log.w(DAOMappingRecord.class.getName(),"Created mapping record "+insertId);
        return insertId;
    }

    /**
     * Delete an specific mapping for a record. Could be just delete the mapping for meals or ingredients
     * its depends from type parameter
     * @param recordId
     * @param type
     */
    public void deleteMappingRecord(int recordId, String type) {
        database.delete(conn.TABLE_MAPPING_RECORD, conn.COLUMN_MEAL_ID
                + " = " + recordId + " and " + conn.COLUMN_TYPE + " = " + type, null);
        Log.w(DAOMappingRecord.class.getName(),"Deleted mapping ");
    }
}
