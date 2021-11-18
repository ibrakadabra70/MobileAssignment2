package com.example.assignment2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import static android.view.View.*;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText editAddress;
    Geocoder geocoder;
    SQLHelper db;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        geocoder = new Geocoder(this, Locale.getDefault());


        db = new SQLHelper(MainActivity.this);

        db.getWritableDatabase();
        db.onCreate(db.getWritableDatabase());
        db.createAddresses(new Geocoder(MainActivity.this, Locale.getDefault()));

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){
            private View v;

            @Override
            public void onClick(View v) {
                this.v = v;

                editAddress = findViewById(R.id.address);
                String address = editAddress.getText().toString();
                Cursor cursor = db.getLatLong(address);

                Toast.makeText(context,cursor.getString(0),Toast.LENGTH_SHORT).show();
            }
        });

    }
}