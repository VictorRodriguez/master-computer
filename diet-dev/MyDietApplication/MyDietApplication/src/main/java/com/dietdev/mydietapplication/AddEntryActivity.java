package com.dietdev.mydietapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.List;

import comm.control.dao.DAOMeal;
import comm.model.Meal;

public class AddEntryActivity extends ActionBarActivity {

    EditText Input_User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        final ImageButton search_button = (ImageButton) findViewById(R.id.search_button);
        search_button.setOnClickListener(button_search_button_listener);

        EditText Input_User = (EditText)findViewById(R.id.searchName);
    }


    View.OnClickListener button_search_button_listener = new View.OnClickListener() {



        public void onClick(View v) {

            /*
            Entra BD
             */

           // final CharSequence[] items = {"Red", "Green", "Blue"};


            //String input_user = Input_User.getText().toString();
//            Log.v("EditText", Input_User.getText().toString());

            DAOMeal daoMeal = new DAOMeal(AddEntryActivity.this);

            daoMeal.open();

            List<Meal> meals = new ArrayList<Meal>();
            String name = "Burger";
            meals = daoMeal.searchMeals(name);


            Log.i("+++++++++++++++++++++++++++++++++++",Integer.toString(meals.size()));
            String items[]=new String[meals.size()];
            String x = meals.get(0).getName();
            Log.i("+++++++++++++++++++++++++++++++++++",meals.get(0).getName());
            for (int i=0; i < meals.size(); i++){
                items [i] =  meals.get(i).getName();
            }

            daoMeal.close();
            AlertDialog.Builder builder = new AlertDialog.Builder(AddEntryActivity.this);

            builder.setNegativeButton("Close",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            builder.setPositiveButton("Select",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            builder.setTitle("Pick a color");
            builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    //Toast.makeText(AddEntryActivity.this, items[item], Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_records, menu);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_add_entry, container, false);
            return rootView;
        }
    }

}
