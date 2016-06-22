package com.dietdev.mydietapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageButton add_entry = (ImageButton) findViewById(R.id.newEntry);
        add_entry.setOnClickListener(button_add_entry_listener);

        final ImageButton view_records = (ImageButton) findViewById(R.id.viewRec);
        view_records.setOnClickListener(button_vew_records_listener);

        final ImageButton add_meal = (ImageButton) findViewById(R.id.addMeal);
        add_meal.setOnClickListener(button_add_meal_listener);

        final ImageButton new_ingredient = (ImageButton) findViewById(R.id.addingredient);
        new_ingredient.setOnClickListener(button_add_ingredient_listener);

     }

    OnClickListener button_add_entry_listener = new View.OnClickListener() {
        public void onClick(View v) {

            Toast msg = Toast.makeText(MainActivity.this, "Starting Add Entry ", Toast.LENGTH_SHORT);
            msg.show();
            startActivity(new Intent("android.intent.action.AddEntryActivity"));
        }
    };

    OnClickListener button_vew_records_listener = new View.OnClickListener() {
        public void onClick(View v) {

            Toast msg = Toast.makeText(MainActivity.this, "Starting View Records ", Toast.LENGTH_SHORT);
            msg.show();
            startActivity(new Intent("android.intent.action.ViewRecordsActivity"));
        }
    };

    OnClickListener button_add_meal_listener = new View.OnClickListener() {
        public void onClick(View v) {

            Toast msg = Toast.makeText(MainActivity.this, "Starting Add Meal ", Toast.LENGTH_SHORT);
            msg.show();
            startActivity(new Intent("android.intent.action.AddMeal"));
        }
    };


    OnClickListener button_add_ingredient_listener = new View.OnClickListener() {
        public void onClick(View v) {

            Toast msg = Toast.makeText(MainActivity.this, "Starting Add ingredient ", Toast.LENGTH_SHORT);
            msg.show();
            startActivity(new Intent("android.intent.action.AddIngredient"));
        }
    };

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        return false;
    }


    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
