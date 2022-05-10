package fit.biesp.oneplan.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganiserDto {

    public Long id;


    public OrganiserDto(){

    }

    public OrganiserDto( Long id) {
        this.id = id;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
