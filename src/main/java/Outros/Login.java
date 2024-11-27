package Outros;

public class Login{
    static String emailLogin = "admin@admin.com";
    static String passwordLogin = "admin";
    private String email;
    private String password;

    public Login() {
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

    public boolean checkLogin() {
        return email.equals(emailLogin) && password.equals(passwordLogin);
    }
}
