package fit.biesp.oneplan.client;


public class LocationDto {
    private Long id;
    public String name, address, coordinates;

    public LocationDto(){

    }

    public LocationDto( String name, String address, String coordinates) {
        this.name = name;
        this.address = address;
        this.coordinates = coordinates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
