package com.example.listycity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Tells the activity what it's looking like

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list); // Visual Element

        String[] cities = {"Edmonton", "Paris", "London", "Ottawa", "Vancouver"};
        dataList = new ArrayList<String>();
        dataList.addAll(Arrays.asList(cities));

        cityList.setOnItemClickListener(((parent, view, position, id) -> {
            EditText editText = (EditText) findViewById(R.id.addCity_text);
            editText.setText(dataList.get(position));

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete selected city?");
            builder.setPositiveButton("Yes", (dialog, id1) -> deleteCity(view));
            builder.setNegativeButton("No", (dialog, id2) -> {
                dialog.dismiss();
                editText.setText("");
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
        (findViewById(R.id.confirm_button)).setOnClickListener(((view) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose an action");
            builder.setPositiveButton("Add", (dialog, id1) -> addCity(view));
            builder.setNegativeButton("Delete", (dialog, id2) -> deleteCity(view));
            AlertDialog dialog = builder.create();
            dialog.show();
        }));
    }

    public void addCity(View view) {
        EditText editText = (EditText) findViewById(R.id.addCity_text);
        String city = editText.getText().toString();

        if (!city.matches("")) {
            editText.getText().clear();
            dataList.add(city);
            cityAdapter.notifyDataSetChanged();
        }
    }

    public void deleteCity(View view) {
        EditText editText = (EditText) findViewById(R.id.addCity_text);
        String city = editText.getText().toString();
        if (!dataList.contains(city)) {
            Toast.makeText(this, "City is not in the list", Toast.LENGTH_SHORT).show();
        }

        if (!city.matches("")) {
            editText.getText().clear();
            dataList.remove(city);
            cityAdapter.notifyDataSetChanged();
        }
    }

}