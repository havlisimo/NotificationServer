package cz.cvut.fit.havlito4.notification_server.controller.entity;

public class NotificationRequest {

    private String notidficationId;
    private String receiverId;
    private String type;

    public String getNotidficationId() {
        return notidficationId;
    }

    public void setNotidficationId(String notidficationId) {
        this.notidficationId = notidficationId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "NotificationRequest{" +
                "notidficationId='" + notidficationId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
