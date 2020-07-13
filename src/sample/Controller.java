package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private Main main = new Main();
    @FXML
    private Button kullanicilar;
    @FXML
    private Button cikis;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(!Main.getUser().getAuth().equals("Mudur")){
            this.getKullanicilar().setVisible(false);
        }

    }

    public void tumPersoneller(ActionEvent e) throws Exception{
        Main.goToPage("/sample/personel.fxml");
        Main.setTitle("Şube Çalışanları");
    }

    public void users(ActionEvent e) throws Exception{
        Main.goToPage("/sample/user.fxml");
        Main.setTitle("Kullanıcılar");
    }

    public void logout(ActionEvent e) throws Exception{
        Main.goToPage("/sample/login.fxml");
        Main.setTitle("Giriş Yap");
        Main.setUser(null);
    }

    public Button getKullanicilar() {
        return kullanicilar;
    }

    public Button getCikis() {
        return cikis;
    }
}
