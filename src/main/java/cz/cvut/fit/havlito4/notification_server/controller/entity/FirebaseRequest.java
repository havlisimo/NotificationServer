package cz.cvut.fit.havlito4.notification_server.controller.entity;

import java.util.HashMap;
import java.util.Map;

public class FirebaseRequest {

    private String to;
    private FirebaseRequestData data;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public FirebaseRequestData getData() {
        return data;
    }

    public void setData(FirebaseRequestData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FirebaseRequest{" +
                "to='" + to + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
