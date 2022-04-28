package fit.biesp.oneplan.client;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class EventDto {

    private Long id;

    private UserDto organiser;


    /* TODO:
    @OneToMany
    private List<MoneyTransferEntity> moneyTransfer;

    @OneToMany
    private List<InvitationEntity> invitation; */


    private String name;

    private String description;

    private Date date;

    private Time time;

    private int capacity;

}