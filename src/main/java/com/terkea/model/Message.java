package com.terkea.model;

public class Message {
    private String userName;
    private String message;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public static Message fromString(String fromString){
        Message message = new Message();
        String withoutDataType = fromString.replaceAll("Message", "");
        String userNameFs = withoutDataType.substring(0, withoutDataType.indexOf("',"));
        String userNameSs = userNameFs.substring(userNameFs.indexOf("='")).replaceAll("='", "");
        String text = withoutDataType.substring(withoutDataType.indexOf(", message='")).replaceAll(", message='", "").replaceAll("'}", "");
        message.setUserName(userNameSs);
        message.setMessage(text);
        return message;
    }
}
