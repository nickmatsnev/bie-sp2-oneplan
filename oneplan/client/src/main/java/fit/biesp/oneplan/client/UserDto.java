package fit.biesp.oneplan.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDto {

    public Long id;
    public String nickname;

    public String email;



    public UserDto(){

    }

    public UserDto(String nickname, String email, Long id) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
