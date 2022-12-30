package api;

import lombok.Data;

@Data
public class DeliveryAdressDto {
    private String city;
    private String street;
    private String homeNumber;
    private String roomNumber;

    @Override
    public String toString() {
        return "DeliveryAdressDto{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", homeNumber='" + homeNumber + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                '}';
    }
}
