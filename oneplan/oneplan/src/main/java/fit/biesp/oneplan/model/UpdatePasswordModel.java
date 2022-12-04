package fit.biesp.oneplan.model;

public class UpdatePasswordModel {
    private String password, rPassword;

    public UpdatePasswordModel(String password, String rPassword) {
        this.password = password;
        this.rPassword = rPassword;
    }

    public UpdatePasswordModel() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getrPassword() {
        return rPassword;
    }

    public void setrPassword(String rPassword) {
        this.rPassword = rPassword;
    }
}
