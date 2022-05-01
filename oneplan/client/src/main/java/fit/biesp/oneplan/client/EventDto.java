package fit.biesp.oneplan.client;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class EventDto {
    private Long id;
    public LocationDto location;
    public UserDto organiser;
    public List<PersonDto> attendees;
    public String name, description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date date;
    public Time time;
    public int capacity;

    public EventDto(){

    }

    public EventDto(LocationDto location, UserDto organiser, List<PersonDto> attendees, String name, String description, Date date, Time time, int capacity) {
        this.location = location;
        this.organiser = organiser;
        this.attendees = attendees;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.capacity = capacity;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public UserDto getOrganiser() {
        return organiser;
    }

    public void setOrganiser(UserDto organiser) {
        this.organiser = organiser;
    }

    public List<PersonDto> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<PersonDto> attendees) {
        this.attendees = attendees;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
