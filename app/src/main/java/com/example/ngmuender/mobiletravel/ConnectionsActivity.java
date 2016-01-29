package com.example.ngmuender.mobiletravel;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.ngmuender.mobiletravel.dummy.ConnectionsRequest;

import ch.schoeb.opendatatransport.IOpenTransportRepository;
import ch.schoeb.opendatatransport.OpenTransportRepositoryFactory;
import ch.schoeb.opendatatransport.model.ConnectionList;

public class ConnectionsActivity extends AppCompatActivity {

    private ConnectionsRequest req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connections);

        Intent i = getIntent();
        req = (ConnectionsRequest) i.getParcelableExtra("request");
        LoadConnections();
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
