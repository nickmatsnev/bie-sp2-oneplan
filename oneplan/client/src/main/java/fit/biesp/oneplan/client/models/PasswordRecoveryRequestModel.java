package fit.biesp.oneplan.client.models;

public class PasswordRecoveryRequestModel {
    private String email;

    public PasswordRecoveryRequestModel(String email) {
        this.email = email;
    }

    public PasswordRecoveryRequestModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
