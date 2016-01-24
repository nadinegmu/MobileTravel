package com.example.ngmuender.mobiletravel;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.List;

import ch.schoeb.opendatatransport.IOpenTransportRepository;
import ch.schoeb.opendatatransport.OpenTransportRepositoryFactory;
import ch.schoeb.opendatatransport.model.Connection;
import ch.schoeb.opendatatransport.model.ConnectionList;


public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;


    private DateFormatter dateformatter = new DateFormatter();
    private Button btnDate;
    private Button btnTime;
    private ToggleButton btnToggle;

    private Button btnSearch;

    private List<Connection> connections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnDate = (Button) findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setBtnDate();
            }
        });

        btnTime = (Button) findViewById(R.id.btnTime);
        btnTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setBtnTime();
            }
        });

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LoadConnections();
            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case (R.id.action_settings):
                Snackbar.make(coordinatorLayout, "You selected settings",
                        Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return true;
            case (R.id.action_about):
                Intent intent = new Intent(this,AboutActivity.class);
                startActivity(intent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }



    private void setBtnDate() {
        DatePickerDialog datePicker = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view,
                                          int year, int monthOfYear, int dayOfMonth) {
                        btnDate.setText(dateformatter.GetDateFormat(dayOfMonth, monthOfYear, year));
                    }
                }, dateformatter.GetYear(), dateformatter.GetMonth(), dateformatter.GetDay());
        datePicker.show();
    }

    private void setBtnTime() {
        TimePickerDialog timePicker = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String newTimeString = (hourOfDay < 10 ? "0" :"") +  hourOfDay + ":" + (minute < 10 ? "0" : "") +  minute;
                        btnTime.setText(newTimeString);

                    }
                }, dateformatter.GetHour(), dateformatter.GetMinute(), true);
        timePicker.show();
    }

    private void LoadConnections() {
        new LoaderTask().execute();
    }

    private class LoaderTask extends AsyncTask<Void, Void, ConnectionList> {

        public EditText txtEditStationVon;
        public String stationVon;

        public EditText txtEditStationNach;
        public String stationNach;

        @Override
        protected void onPreExecute() {
            txtEditStationVon = (EditText) findViewById(R.id.stationVon);
            stationVon = txtEditStationVon.getText().toString();
            txtEditStationNach = (EditText) findViewById(R.id.stationNach);
            stationNach = txtEditStationNach.getText().toString();
        }

        @Override
        protected ConnectionList doInBackground(Void... params) {
            // Get Repository
            IOpenTransportRepository repo = OpenTransportRepositoryFactory.CreateOnlineOpenTransportRepository();


            ConnectionList connectionList = repo.searchConnections(stationVon, stationNach);

            return connectionList;
        }


        @Override
        protected void onPostExecute(ConnectionList connectionList) {
            Log.d("ConnectionList", connectionList.toString());
            ConnectionAdapter adapter = new ConnectionAdapter(MainActivity.this,connectionList.getConnections());

            ListView lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(adapter);


        }
    }
}
