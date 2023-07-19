package poly.mobile.findfriends;

public class Friend {
    public String firstName, secondName,phoneNumber,longitude,latitude,uri;

    public Friend(){
    }

    public Friend(String firstName, String secondName, String phoneNumber, String longitude, String latitude, String uri) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
        this.longitude = longitude;
        this.latitude = latitude;
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
