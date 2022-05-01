package fit.biesp.oneplan.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDto {


    public String nickname;

    public String email;

    public String password;

    public String id;

    public UserDto(){

    }

    public UserDto(String nickname, String email, String password, String id) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        id = id;
    }
}
