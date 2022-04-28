package fit.biesp.oneplan.client;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class EventDto {

    private Long id;

    private UserDto organiser;

    private String name;

    private String description;

    private Date date;

    private Time time;

    private int capacity;

    public EventDto(Long id, UserDto organiser, String name, String description, Date date, Time time, int capacity) {
        this.id = id;
        this.organiser = organiser;
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
        this.capacity = capacity;
    }
}