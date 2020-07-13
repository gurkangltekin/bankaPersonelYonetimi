package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ChoiceBox<String> yetkiKutusu;
    @FXML
    private Button olustur_Guncelle;
    @FXML
    private Button sil;
    @FXML
    private Button formTemizle;
    @FXML
    private ListView<User> users;

    private DosyaYazma dy;

    private final User selectedUser = new User();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.getSil().setVisible(false);
        this.getOlustur_Guncelle().setText("Oluştur");
        this.getYetkiKutusu().getItems().addAll("Mudur", "Operasyon Personeli");
        this.getFormTemizle().setVisible(false);
        this.kullanicilar();
    }

    public void geri() throws Exception {
        Main.goToPage("/sample/anasayfa.fxml");
        Main.setTitle("Anasayfa");
    }

    public void kullanicilar() {
        this.getUsers().getItems().clear();
        try {
            List<String> users = this.getDy().dosyadanOku("users");

            for (String str : users) {

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
                    if (str.charAt(j) == '@') {
                        j++;
                        while (str.charAt(j) != '%') {
                            password += str.charAt(j++);
                        }
                    }
                    if (str.charAt(j) == '%') {
                        j++;
                        while (str.charAt(j) != '&') {
                            auth += str.charAt(j++);
                        }
                    }
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setAuth(auth);
                    this.getUsers().getItems().add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void kullaniciDetay(){
        this.selectedUser.setUsername(this.getUsers().getSelectionModel().getSelectedItem().getUsername());
        this.selectedUser.setPassword(this.getUsers().getSelectionModel().getSelectedItem().getPassword());
        this.selectedUser.setAuth(this.getUsers().getSelectionModel().getSelectedItem().getAuth());
        this.getUsername().setText(this.selectedUser.getUsername());
        this.getYetkiKutusu().getSelectionModel().select(this.selectedUser.getAuth());
        this.getOlustur_Guncelle().setText("Güncelle");
        this.getFormTemizle().setVisible(true);
        this.getSil().setVisible(true);
    }

    public void olustur_guncelle() {
        String yazi;
        if(this.getOlustur_Guncelle().getText().equals("Oluştur")){
            yazi = "!" + this.getUsername().getText() + "@" + this.getPassword().getText() + "%" + this.getYetkiKutusu().getSelectionModel().getSelectedItem() + "&";
            this.getDy().setDosyayaYaz(yazi);
            try {
                this.getDy().dosyayaYaz("users");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            yazi = "!" + this.getUsername().getText() + "@" + this.getPassword().getText() + "%" + this.getYetkiKutusu().getSelectionModel().getSelectedItem() + "&";


            try {

                if(this.getDy().dosyaVarmi("users")) {
                    boolean kontrol = false;
                    List<String> lines = this.getDy().dosyadanOku("users");
                    int size = lines.size();
                    for (int i = 0; i < size; i++) {
                        String str = lines.get(i);
                        int strsize = str.length();
                        for (int j = 0; j < strsize; j++) {
                            StringBuilder username = new StringBuilder();

                            if (str.charAt(j) == '!') {
                                j++;
                                while (str.charAt(j) != '@') {
                                    username.append(str.charAt(j++));
                                }
                            }

                            if (username.toString().equals(this.selectedUser.getUsername())) {

                                lines.remove(i);

                                lines.add(yazi);

                                kontrol = true;

                                this.getDy().dosyaGuncelle("users", lines);

                                i = size;
                            }
                        }
                    }
                    if(!kontrol){
                        this.getDy().setDosyayaYaz(yazi);
                        this.getDy().dosyayaYaz("users");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        this.formTemizle();
        this.kullanicilar();
    }

    public void kullaniciSil() {

        try {
            if(this.getDy().dosyaVarmi("users")) {
                List<String> lines = this.getDy().dosyadanOku("users");
                int size = lines.size();
                for (int i = 0; i < size; i++) {
                    String str = lines.get(i);
                    int strsize = str.length();
                    for (int j = 0; j < strsize; j++) {
                        StringBuilder username = new StringBuilder();

                        if (str.charAt(j) == '!') {
                            j++;
                            while (str.charAt(j) != '@') {
                                username.append(str.charAt(j++));
                            }
                        }

                        if (username.toString().equals(this.selectedUser.getUsername())) {

                            lines.remove(i);

                            this.getDy().dosyaGuncelle("users", lines);

                            i = size;
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        this.formTemizle();
        this.kullanicilar();

    }

    public void formTemizle(){
        this.getOlustur_Guncelle().setText("Oluştur");
        this.getYetkiKutusu().getSelectionModel().clearSelection();
        this.getUsername().setText("");
        this.getUsers().getSelectionModel().clearSelection();
        this.getSil().setVisible(false);
        this.getFormTemizle().setVisible(false);
        this.getPassword().setText("");
    }

    public TextField getUsername() {
        return username;
    }

    public PasswordField getPassword() {
        return password;
    }

    public ChoiceBox<String> getYetkiKutusu() {
        return yetkiKutusu;
    }

    public DosyaYazma getDy() {
        if(this.dy == null)
            this.dy = new DosyaYazma();
        return dy;
    }

    public Button getOlustur_Guncelle() {
        return olustur_Guncelle;
    }

    public Button getSil() {
        return sil;
    }

    public ListView<User> getUsers() {
        return users;
    }

    public Button getFormTemizle() {
        return formTemizle;
    }
}
