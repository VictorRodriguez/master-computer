package comm.control.common;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Julio on 12/11/13.
 * @author Julio Cesar Roa Gil
 * @version 1.0
 */
public class ConnectionDB extends SQLiteOpenHelper{

    public static final String TABLE_MEALS = "MEAL";
    public static final String TABLE_INGREDIENTS = "ingredient";
    public static final String TABLE_RECORDS = "record";
    public static final String TABLE_MAPPING_RECORD = "mapping_record";
    public static final String TABLE_MAPPING_MEAL = "mapping_meal";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_GROUP_ID = "group_id";
    public static final String COLUMN_RECORD_ID = "record_id";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_MEAL_ID = "meal_id";
    public static final String COLUMN_INGREDIENT_ID = "ingredient_id";
    public static final String COLUMN_CALORIE_VALUE = "calorie_value";
    public static final String COLUMN_REGISTER_DATE = "registered_date";

    private static final String DATABASE_NAME = "myDiet.db";
    private static String DB_PATH;
    private static final int DATABASE_VERSION = 1;

    private final Context myContext;
    private SQLiteDatabase myDataBase;

    private String DATABASE_CREATE = "create table ingredient(\n" +
            "\tid \t\t\t\tinteger primary key autoincrement\n" +
            "   ,name\t \t\ttext not null\n" +
            "   ,calorieValue\tinteger not null\n" +
            ");\n" +
            "\n" +
            "create table meal(\n" +
            "\tid \t\t\t\tinteger primary key autoincrement\n" +
            "   ,name\t \t\ttext not null\n" +
            "   ,calorieValue\tinteger not null\n" +
            ");\n" +
            "\n" +
            "create table record(\n" +
            "\tid \t\t\t\tinteger primary key autoincrement\n" +
            "   ,registerDate\ttext not null\n" +
            ");\n" +
            "\n" +
            "create table mapping_record(\n" +
            "\tid \t\t\tinteger primary key autoincrement\n" +
            "   ,record_id \tinteger not null\n" +
            "   ,group_id\tinteger not null\n" +
            "   ,type\t\ttext not null\n" +
            "   ,foreign key (record_id) references record(id)\t\n" +
            ");\n" +
            "\n" +
            "create table mapping_meal(\n" +
            "\tid \t\t\t\tinteger primary key autoincrement\n" +
            "   ,meal_id \t\tinteger not null\n" +
            "   ,ingredient_id\tinteger not null\n" +
            "   ,foreign key (meal_id) references meal(id)\t\n" +
            "   ,foreign key (ingredient_id) references ingredient(id)\n" +
            ");";

    /*
    * @param: Context
    * Manage the opening operation
    * */
    public ConnectionDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        this.DB_PATH = "/data/data/"+ myContext.getPackageName() + "/databases/";
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database...");

            }
        }

    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
        Log.i("+++++++++++++", myInput.toString());
        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DATABASE_NAME;
            //String myPath = DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**

    /*
    * @param: SQLiteDatabase object
    * Called when the database is created for the first time. This is where the creation of tables and the initial population of the tables should happen.
    * */
    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.i("+++++++++++++++++++++++++++","CREATED DATABASE");
        database.execSQL(DATABASE_CREATE);
    }



    /*
    * @param: SQLiteDatabase object
    * @param: oldVersion int number
    * @param: newVersion int number
    *
    * The implementation should use this method to drop tables, add tables, or do anything else it needs to upgrade to the new schema version.
    * */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ConnectionDB.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        onCreate(db);
    }

    public SQLiteDatabase openDataBase(SQLiteDatabase database) throws SQLException {
       try {
            //createDataBase();
            copyDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //this.onCreate(database);
        //this.getReadableDatabase();

        //Open the database
        if(checkDataBase()){
            String myPath = DB_PATH + DATABASE_NAME;
            //String myPath = DATABASE_NAME;
            database = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            return database;
        }

        return null;


    }

    /*
    * @param: SQLiteDatabase object
    * Manage the closing operation
    * */
    public synchronized void close(SQLiteDatabase database) {
        if(database != null)
            database.close();
        super.close();
    }
}
