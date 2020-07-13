package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label hata;

    private DosyaYazma dy;

    public void login(){

        User user = new User();
        user.setUsername(this.getUsername().getText());
        user.setPassword(this.getPassword().getText());

        try {
            List<String> users = this.getDy().dosyadanOku("users");
            int size = users.size();

            for (int i = 0; i < size; i++) {

                String str = users.get(i);

                int strsize = str.length();

                String username = "";
                String password = "";
                String auth = "";

                for (int j = 0; j < strsize; j++) {

                    if (str.charAt(j) == '!') {
                        j++;
                        while (str.charAt(j) != '@') {
                            username += str.charAt(j++);
                        }
                    }

                    if(user.getUsername().equals(username)){
                        if (str.charAt(j) == '@') {
                            j++;
                            while (str.charAt(j) != '%') {
                                password += str.charAt(j++);
                            }
                        }
                        if(user.getPassword().equals(password)){
                            if (str.charAt(j) == '%') {
                                j++;
                                while (str.charAt(j) != '&') {
                                    auth += str.charAt(j++);
                                }
                            }
                            Main.getUser().setUsername(user.getUsername());
                            Main.getUser().setPassword(user.getPassword());
                            Main.getUser().setAuth(auth);
                            Main.goToPage("/sample/anasayfa.fxml");
                            Main.setTitle("Anasayfa");
                            i = size;
                        }else{
                            this.getHata().setText("Yanlış kullanıcı adı veya parola.");
                        }
                    }else{
                        this.getHata().setText("Yanlış kullanıcı adı veya parola.");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public TextField getUsername() {
        return username;
    }

    public PasswordField getPassword() {
        return password;
    }

    public DosyaYazma getDy() {
        if(this.dy == null)
            this.dy = new DosyaYazma();
        return dy;
    }

    public Label getHata() {
        return hata;
    }
}
