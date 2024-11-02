package com.unipi.anastasis.mypois;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LocationListener {

    EditText name, descriptionofyourpoi;
    Button insert, update, delete, view, search;
    TextView textView, textView2;

    LocationManager locationManager;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String Date;
    TextView GetDateAndTime;
    DBHelper DB;
    String locat;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    GetDateAndTime = findViewById(R.id.textView2);
    calendar =Calendar.getInstance();
    simpleDateFormat =new SimpleDateFormat("dd-MM-yyyy HH:mm::ss");
    Date =simpleDateFormat.format(calendar.getTime());
    locationManager =(LocationManager) getSystemService(LOCATION_SERVICE);

    textView = findViewById(R.id.textView);
    textView2= findViewById(R.id.textView2);

    spinner =
    findViewById(R.id.spinner);
    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.locations, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    name = findViewById(R.id.name);
    search= findViewById(R.id.search);
    descriptionofyourpoi = findViewById(R.id.descriptionofyourpoi);
    insert = findViewById(R.id.btnInsert);
    update = findViewById(R.id.btnUpdate);
    delete = findViewById(R.id.btnDelete);
    view = findViewById(R.id.btnView);
    DB =new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick (View view){
        String nameTXT = name.getText().toString();
        String descriptionofyourpoiTXT = descriptionofyourpoi.getText().toString();
        String loc = locat;
        String timestamp = Date;
        String category = spinner.getSelectedItem().toString();
        textView.setText(locat);
        textView2.setText(Date);
        Boolean checkinsertdata = DB.insertpoidata(nameTXT, descriptionofyourpoiTXT, loc, timestamp, category);
        if (checkinsertdata == true)
            Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
    }
    });

        update.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick (View view){
        String nameTXT = name.getText().toString();
        String descriptionofyourpoiTXT = descriptionofyourpoi.getText().toString();
        String loc = locat;
        String timestamp = Date;
        String category = spinner.getSelectedItem().toString();
        textView.setText(locat);
        textView2.setText(Date);
        Boolean checkupdatedata = DB.updatepoidata(nameTXT, descriptionofyourpoiTXT, loc, timestamp, category);
        if (checkupdatedata == true)
            Toast.makeText(MainActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();
    }
    });

        delete.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick (View view){
        String nameTXT = name.getText().toString();
        Boolean checkudeletedata = DB.deletedata(nameTXT);
        if (checkudeletedata == true)
            Toast.makeText(MainActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
    }
    });

        view.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick (View view){
        Cursor res = DB.getdata();
        if (res.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Name :" + res.getString(0) + "\n");
            buffer.append("Description :" + res.getString(1) + "\n");
            buffer.append("Location :" + res.getString(2) + "\n");
            buffer.append("Time :" + res.getString(3) + "\n");
            buffer.append("Category :" + res.getString(4) + "\n\n");

        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Poi Entries");
        builder.setMessage(buffer.toString());
        builder.show();
    }
    });
        search.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick (View view){
        String nameTXT = name.getText().toString();
        SQLiteDatabase db = DB.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from poidetails where name='" + nameTXT + "'", null);
        if (res.getCount() == 0) {
            Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Name :" + res.getString(0) + "\n");
            buffer.append("Description :" + res.getString(1) + "\n");
            buffer.append("Location :" + res.getString(2) + "\n");
            buffer.append("Time :" + res.getString(3) + "\n");
            buffer.append("Category :" + res.getString(4) + "\n\n");

        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(true);
        builder.setTitle(nameTXT + " Entrie");
        builder.setMessage(buffer.toString());
        builder.show();
    }
    });
}
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        textView.setText(location.getLatitude()+","+location.getLongitude());
        locat=location.getLatitude()+","+location.getLongitude();
    }

    public void go1(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

}