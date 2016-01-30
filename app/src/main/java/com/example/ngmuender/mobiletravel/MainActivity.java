package com.example.ngmuender.mobiletravel;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.example.ngmuender.mobiletravel.dummy.ConnectionsRequest;

import java.util.ArrayList;

import ch.schoeb.opendatatransport.IOpenTransportRepository;
import ch.schoeb.opendatatransport.OpenTransportRepositoryFactory;
import ch.schoeb.opendatatransport.model.Station;
import ch.schoeb.opendatatransport.model.StationList;


public class MainActivity extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;

    private AutoCompleteTextView currentAutoCompleteTV;
    private AutoCompleteTextView autoTxtViewFrom;
    private AutoCompleteTextView autoTxtViewTo;
    private AutoCompleteTextView autoTxtViewVia;

    public Context mainContext;

    public ArrayList<String> proposalStations;
    public String searchString;


    private Formatter dateformatter = new Formatter();
    private Button btnDate;
    private Button btnTime;
    private ToggleButton btnToggle;
    private boolean isArrivalTime = false;

    private Button btnSearch;

    public IOpenTransportRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);

        repo = OpenTransportRepositoryFactory.CreateOnlineOpenTransportRepository();

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
                searchConnections();
            }
        });

        mainContext = this;
        proposalStations = new ArrayList<String>();

        autoTxtViewFrom = (AutoCompleteTextView) findViewById(R.id.stationFrom);
        autoTxtViewFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentAutoCompleteTV = autoTxtViewFrom;
                proposeStations(s, start, before, count);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        autoTxtViewTo = (AutoCompleteTextView) findViewById(R.id.stationTo);
        autoTxtViewTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentAutoCompleteTV = autoTxtViewTo;
                proposeStations(s, start, before, count);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        autoTxtViewVia = (AutoCompleteTextView) findViewById(R.id.stationVia);
        autoTxtViewVia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentAutoCompleteTV = autoTxtViewVia;
                proposeStations(s, start, before, count);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnToggle = (ToggleButton) findViewById(R.id.togBtnDepArr);
        btnToggle.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  isArrivalTime = btnToggle.isChecked();
              }
          }
        );

        ImageButton btnReverseDirection = (ImageButton) findViewById(R.id.btnReverse);
        btnReverseDirection.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
           reverseDirections();
                                                   }
                                               }
        );
    }

    private void reverseDirections() {
        String buffer = autoTxtViewTo.getText().toString();
        autoTxtViewTo.setText(autoTxtViewFrom.getText());
        autoTxtViewFrom.setText(buffer);
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

    private void searchConnections() {
        Intent i = new Intent(MainActivity.this,ConnectionsActivity.class);

        autoTxtViewFrom = (AutoCompleteTextView) findViewById(R.id.stationFrom);
        autoTxtViewTo = (AutoCompleteTextView) findViewById(R.id.stationTo);
        autoTxtViewVia = (AutoCompleteTextView) findViewById(R.id.stationVia);

        ConnectionsRequest req = new ConnectionsRequest(
                autoTxtViewFrom.getText().toString(),
                autoTxtViewTo.getText().toString(),
                autoTxtViewVia.getText().toString(),
                btnDate.getText().toString(),
                btnTime.getText().toString(),
                isArrivalTime);

        i.putExtra("request",req);
        startActivity(i);
    }

    private void proposeStations(CharSequence s, int start, int before, int count) {
        searchString = s.toString();
        new StationLoaderTask().execute();
     }

    public class StationLoaderTask extends AsyncTask<Void, Void, Void> {
        private Exception fetchStationListException;

        private AutoCompleteTextView autoCompleteTV;

        @Override
        protected void onPreExecute() {
            autoCompleteTV = currentAutoCompleteTV;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            proposalStations.clear();
            try
            {
                StationList stationList = repo.findStations(searchString);

                if (stationList.getStations().size() > 0) {
                    for (Station s : stationList.getStations()) {
                        proposalStations.add(s.getName());
                    }
                }
            }
            catch(Exception e){
                this.fetchStationListException = e;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(fetchStationListException != null){
                return;
            }

            ArrayAdapter adaptorAutoComplete = new ArrayAdapter<String>(mainContext, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>(proposalStations));
            autoCompleteTV.setAdapter(adaptorAutoComplete);
            adaptorAutoComplete.notifyDataSetChanged();
        }
    }

}
