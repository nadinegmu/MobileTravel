package com.example.ngmuender.mobiletravel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ch.schoeb.opendatatransport.model.Connection;


public class ConnectionAdapter extends BaseAdapter {

    private Context context;
    private List<Connection> connections;

    public ConnectionAdapter(Context context, List<Connection> connections) {
        this.context = context;
        this.connections = connections;
    }

    @Override
    public int getCount() {
        return connections.size();
    }

    @Override
    public Object getItem(int position) {
        return connections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.connection_list_item, parent, false);
        }

        Connection connection = (Connection) getItem(position);

        TextView connectionFromTextView = (TextView) convertView.findViewById(R.id.connection_from);
        connectionFromTextView.setText(connection.getFrom().getStation().getName() +"");

        TextView connectionToTextView = (TextView) convertView.findViewById(R.id.connection_to);
        connectionToTextView.setText(connection.getTo().getStation().getName() +"");

        TextView connectionDurationTextView = (TextView) convertView.findViewById(R.id.connection_duration);
        connectionDurationTextView.setText(connection.getDuration() +"");

        TextView connectionTransfersTextView = (TextView) convertView.findViewById(R.id.connection_transfers);
        connectionTransfersTextView.setText(connection.getTransfers() +"");

        TextView connectionDepartureTextView = (TextView) convertView.findViewById(R.id.connection_departure);
        connectionTransfersTextView.setText(connection.getFrom().getDeparture() +"");

        TextView connectionArrivalTextView = (TextView) convertView.findViewById(R.id.connection_arrival);
        connectionTransfersTextView.setText(connection.getTo().getArrival() +"");        


        return convertView;
    }




}
