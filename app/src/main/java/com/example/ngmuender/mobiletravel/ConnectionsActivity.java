package com.example.ngmuender.mobiletravel;

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
import android.widget.ListView;
import android.widget.TextView;

import com.example.ngmuender.mobiletravel.dummy.ConnectionsRequest;

import ch.schoeb.opendatatransport.IOpenTransportRepository;
import ch.schoeb.opendatatransport.OpenTransportRepositoryFactory;
import ch.schoeb.opendatatransport.model.ConnectionList;

public class ConnectionsActivity extends AppCompatActivity {
    private CoordinatorLayout coordinatorLayout;
    private ConnectionsRequest req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        req = (ConnectionsRequest) i.getParcelableExtra("request");

        TextView tvFromStation = (TextView) findViewById(R.id.textViewFromStation);
        tvFromStation.setText(req.getFrom());

        TextView tvToStation = (TextView) findViewById(R.id.textViewToStation);
        tvToStation.setText(req.getTo());

        LoadConnections();
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

    private void LoadConnections() {
        new LoaderTask().execute();
    }

    private class LoaderTask extends AsyncTask<Void, Void, ConnectionList> {

        protected String from;
        protected String to;
        private String via;
        private String date;
        private String time;
        private Boolean isArrivalTime;

        @Override
        protected void onPreExecute() {
            from = req.getFrom();
            to = req.getTo();
            via = req.getVia();
            date = req.getDate();
            time = req.getTime();
            isArrivalTime = req.getIsArrivalTime();
        }

        @Override
        protected ConnectionList doInBackground(Void... params) {
            // Get Repository
            IOpenTransportRepository repo = OpenTransportRepositoryFactory.CreateOnlineOpenTransportRepository();

            ConnectionList connectionList = repo.searchConnections(from,to,via,date,time,isArrivalTime);
            return connectionList;
        }


        @Override
        protected void onPostExecute(ConnectionList connectionList) {
            Log.d("ConnectionList", connectionList.toString());
            ConnectionAdapter adapter = new ConnectionAdapter(ConnectionsActivity.this,connectionList.getConnections());

            ListView lv = (ListView) findViewById(R.id.listView);
            lv.setAdapter(adapter);


        }
    }
}
