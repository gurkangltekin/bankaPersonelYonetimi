package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DigerPersonellerController implements Initializable {

    @FXML
    private Label adSoyadLabel;
    @FXML
    private CheckBox guvenlik;
    @FXML
    private CheckBox temizlikci;
    @FXML
    private CheckBox cayci;
    @FXML
    private CheckBox stajyer;
    private DosyaYazma dy;
    @FXML
    private Label hata;
    @FXML
    private Button guncelle;

    public void geri() throws Exception {
        Main.goToPage("/sample/personel.fxml");
        Main.setTitle("Şube Çalışanları");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.getAdSoyadLabel().setText(Main.getSelectedPersonel().getAdi() + " " + Main.getSelectedPersonel().getSoyadi());
        this.bilgiOku();
        if(Main.getUser().getAuth().equals("Mudur")){
            this.guncelle.setVisible(true);
        }else{
            this.guncelle.setVisible(false);
        }
    }

    public void cb1(){
        this.getGuvenlik().setSelected(true);
        this.getTemizlikci().setSelected(false);
        this.getCayci().setSelected(false);
        this.getStajyer().setSelected(false);
    }
    public void cb2(){
        this.getGuvenlik().setSelected(false);
        this.getTemizlikci().setSelected(true);
        this.getCayci().setSelected(false);
        this.getStajyer().setSelected(false);
    }
    public void cb3(){
        this.getGuvenlik().setSelected(false);
        this.getTemizlikci().setSelected(false);
        this.getCayci().setSelected(true);
        this.getStajyer().setSelected(false);
    }
    public void cb4(){
        this.getGuvenlik().setSelected(false);
        this.getTemizlikci().setSelected(false);
        this.getCayci().setSelected(false);
        this.getStajyer().setSelected(true);
    }

    public void bilgiOku() {
        try {

            List<String> lines = this.getDy().dosyadanOku("digerPersonel");
            int size = lines.size();

            for (int i = 0; i < size; i++) {

                String str = lines.get(i);

                int strsize = str.length();

                String tckimlik = "";
                String gorev = "";

                for (int j = 0; j < strsize; j++) {

                    if (str.charAt(j) == '!') {
                        j++;
                        while (str.charAt(j) != '@') {
                            tckimlik += str.charAt(j++);
                        }
                    }

                    if (String.valueOf(Main.getSelectedPersonel().getTcKimlikNo()).equals(tckimlik)) {
                        if (str.charAt(j) == '@') {
                            j++;
                            while (str.charAt(j) != '&') {
                                gorev += str.charAt(j++);
                            }
                        }
                        DigerPersoneller dp = new DigerPersoneller();
                        dp.setAdi(Main.getSelectedPersonel().getAdi());
                        dp.setSoyadi(Main.getSelectedPersonel().getAdi());
                        dp.setTcKimlikNo(Main.getSelectedPersonel().getTcKimlikNo());
                        dp.setDepartman(Main.getSelectedPersonel().getAdi());
                        dp.setEtkinlikler(Main.getSelectedPersonel().getEtkinlikler());
                        dp.setIseGiris(Main.getSelectedPersonel().getIseGiris());
                        dp.setGorev(gorev);
                        switch (gorev) {
                            case "Guvenlik":
                                this.getGuvenlik().setSelected(true);
                                break;
                            case "Temizlikci":
                                this.getTemizlikci().setSelected(true);
                                break;
                            case "Stajyer":
                                this.getStajyer().setSelected(true);
                                break;
                            case "Cayci":
                                this.getCayci().setSelected(true);
                                break;
                            default:
                                this.hata.setText("Daha önce hatalı değer seçilmiş!");
                                break;
                        }
                        i = size;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void guncelle() {
        String yazi;
        if (this.getGuvenlik().isSelected()) {
            if (this.getStajyer().isSelected() || this.getCayci().isSelected() || this.getTemizlikci().isSelected()) {
                this.hata.setText("Sadece 1 seçim yapabilirsiniz!");
            } else {
                yazi = "!" + Main.getSelectedPersonel().getTcKimlikNo() + "@Guvenlik&";
                System.out.println(1);
                this.dosyayiGuncelle(yazi);

                this.hata.setText("");
            }
        } else if (this.getTemizlikci().isSelected()) {
            if (this.getStajyer().isSelected() || this.getCayci().isSelected() || this.getGuvenlik().isSelected()) {
                this.hata.setText("Sadece 1 seçim yapabilirsiniz!");
            } else {
                yazi = "!" + Main.getSelectedPersonel().getTcKimlikNo() + "@Temizlikci&";

                this.dosyayiGuncelle(yazi);
                this.hata.setText("");
            }
        } else if (this.getCayci().isSelected()) {
            if (this.getStajyer().isSelected() || this.getGuvenlik().isSelected() || this.getTemizlikci().isSelected()) {
                this.hata.setText("Sadece 1 seçim yapabilirsiniz!");
            } else {
                yazi = "!" + Main.getSelectedPersonel().getTcKimlikNo() + "@Cayci&";

                this.dosyayiGuncelle(yazi);
                this.hata.setText("");
            }
        } else if (this.getStajyer().isSelected()) {
            if (this.getGuvenlik().isSelected() || this.getCayci().isSelected() || this.getTemizlikci().isSelected()) {
                this.hata.setText("Sadece 1 seçim yapabilirsiniz!");
            } else {
                yazi = "!" + Main.getSelectedPersonel().getTcKimlikNo() + "@Stajyer&";
                this.dosyayiGuncelle(yazi);
                this.hata.setText("");
            }
        }
    }

    public void dosyayiGuncelle(String yazi) {
        try {
            if(this.getDy().dosyaVarmi("digerPersonel")){
                List<String> lines = this.getDy().dosyadanOku("digerPersonel");
                int size = lines.size();
                boolean kontrol = false;
                for (int i = 0; i < size; i++) {
                    String str = lines.get(i);
                    int strsize = str.length();
                    for (int j = 0; j < strsize; j++) {
                        String tckimlik = "";

                        if (str.charAt(j) == '!') {
                            j++;
                            while (str.charAt(j) != '@') {
                                tckimlik += str.charAt(j++);
                            }
                        }

                        if (tckimlik.equals(String.valueOf(Main.getSelectedPersonel().getTcKimlikNo()))) {

                            lines.remove(i);

                            lines.add(yazi);

                            kontrol = true;

                            this.getDy().dosyaGuncelle("digerPersonel", lines);

                            i = size;
                        }
                    }
                }
                if(!kontrol){
                    this.getDy().setDosyayaYaz(yazi);
                    this.getDy().dosyayaYaz("digerPersonel");
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Label getAdSoyadLabel() {
        return adSoyadLabel;
    }

    public CheckBox getGuvenlik() {
        return guvenlik;
    }

    public CheckBox getTemizlikci() {
        return temizlikci;
    }

    public CheckBox getCayci() {
        return cayci;
    }

    public CheckBox getStajyer() {
        return stajyer;
    }

    public DosyaYazma getDy() {
        if (this.dy == null)
            this.dy = new DosyaYazma();
        return dy;
    }

}
