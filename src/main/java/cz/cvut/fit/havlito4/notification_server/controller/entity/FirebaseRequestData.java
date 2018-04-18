package cz.cvut.fit.havlito4.notification_server.controller.entity;

public class FirebaseRequestData {
    private String classification_notification_id;
    private String classification_notification_type;

    public FirebaseRequestData(String notificationID, String notificationType) {
        this.classification_notification_id = notificationID;
        this.classification_notification_type = notificationType;
    }

    public String getClassification_notification_id() {
        return classification_notification_id;
    }

    public void setClassification_notification_id(String classification_notification_id) {
        this.classification_notification_id = classification_notification_id;
    }

    public String getClassification_notification_type() {
        return classification_notification_type;
    }

    public void setClassification_notification_type(String classification_notification_type) {
        this.classification_notification_type = classification_notification_type;
    }

    @Override
    public String toString() {
        return "FirebaseRequestData{" +
                "classification_notification_id='" + classification_notification_id + '\'' +
                ", classification_notification_type='" + classification_notification_type + '\'' +
                '}';
    }
}
