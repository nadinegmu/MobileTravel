package com.example.ngmuender.mobiletravel.dummy;

import android.os.Parcel;
import android.os.Parcelable;

public class ConnectionsRequest implements Parcelable {

    private String from;
    private String to;
    private String via;
    private String date;
    private String time;
    private int isArrivalTime; // 0 false, 1 true

    public ConnectionsRequest(String string, String toString, String s, String from, CharSequence text, String to, boolean b) {
        this.from = from;
        this.to = to;
    }

    public ConnectionsRequest(String from, String to, String via, String date, String time, Boolean isArrivalTime) {
        this.from = from;
        this.to = to;
        this.via = via;
        this.date = date;
        this.time = time;
        if (isArrivalTime == true) this.isArrivalTime = 1;
        else {
            this.isArrivalTime = 0;
        }
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getVia() {
        return via;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public boolean getIsArrivalTime() {
        if (this.isArrivalTime == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(from);
        dest.writeString(to);
        dest.writeString(via);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeInt(isArrivalTime);
    }

    public static final Parcelable.Creator<ConnectionsRequest> CREATOR
            = new Parcelable.Creator<ConnectionsRequest>() {
        public ConnectionsRequest createFromParcel(Parcel in) {
            return new ConnectionsRequest(in);
        }

        public ConnectionsRequest[] newArray(int size) {
            return new ConnectionsRequest[size];
        }
    };

    private ConnectionsRequest(Parcel in) {
        from = in.readString();
        to = in.readString();
        via = in.readString();
        date = in.readString();
        time = in.readString();
        isArrivalTime = in.readInt();
    }
}
