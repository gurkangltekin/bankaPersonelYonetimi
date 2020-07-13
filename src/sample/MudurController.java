package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MudurController implements Initializable {

    @FXML
    private Label adSoyadLabel;
    @FXML
    private TextField toplantiSayisi;
    @FXML
    private TextField kidem;

    private DosyaYazma dy;
    @FXML
    private Button guncelle;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.getAdSoyadLabel().setText(Main.getSelectedPersonel().getAdi() + " " + Main.getSelectedPersonel().getSoyadi());
        this.bilgileriOku();
        if(Main.getUser().getAuth().equals("Mudur")){
            this.guncelle.setVisible(true);
        }else{
            this.guncelle.setVisible(false);
        }
    }

    public void bilgileriYaz(){
        String yazi = "!" + Main.getSelectedPersonel().getTcKimlikNo() + "@" + this.getToplantiSayisi().getText() +
                "%" + this.getKidem().getText() + "&" ;

        try {
            if(this.getDy().dosyaVarmi("mudur")) {
                boolean kontrol = false;
                List<String> lines = this.getDy().dosyadanOku("mudur");
                int size = lines.size();
                for (int i = 0; i < size; i++) {
                    String str = lines.get(i);
                    int strsize = str.length();
                    for (int j = 0; j < strsize; j++) {
                        StringBuilder tckimlik = new StringBuilder();

                        if (str.charAt(j) == '!') {
                            j++;
                            while (str.charAt(j) != '@') {
                                tckimlik.append(str.charAt(j++));
                            }
                        }

                        if (tckimlik.toString().equals(String.valueOf(Main.getSelectedPersonel().getTcKimlikNo()))) {

                            lines.remove(i);

                            lines.add(yazi);

                            kontrol = true;

                            this.getDy().dosyaGuncelle("mudur", lines);

                            i = size;
                        }
                    }
                }
                if(!kontrol){
                    this.getDy().setDosyayaYaz(yazi);
                    this.getDy().dosyayaYaz("mudur");
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void bilgileriOku(){
        try {

            List<String> lines = this.getDy().dosyadanOku("mudur");
            int size = lines.size();

            for (int i = 0; i < size; i++) {

                String str = lines.get(i);

                int strsize = str.length();

                String tckimlik = "";
                String toplantiSayisi = "";
                String kidem = "";

                for (int j = 0; j < strsize; j++) {

                    if (str.charAt(j) == '!') {
                        j++;
                        while (str.charAt(j) != '@') {
                            tckimlik += str.charAt(j++);
                        }
                    }

                    if(String.valueOf(Main.getSelectedPersonel().getTcKimlikNo()).equals(tckimlik)){
                        if (str.charAt(j) == '@') {
                            j++;
                            while (str.charAt(j) != '%') {
                                toplantiSayisi += str.charAt(j++);
                            }
                        }
                        if (str.charAt(j) == '%') {
                            j++;
                            while (str.charAt(j) != '&') {
                                kidem += str.charAt(j++);
                            }
                        }
                        Mudur mdr = new Mudur();
                        mdr.setAdi(Main.getSelectedPersonel().getAdi());
                        mdr.setSoyadi(Main.getSelectedPersonel().getAdi());
                        mdr.setTcKimlikNo(Main.getSelectedPersonel().getTcKimlikNo());
                        mdr.setDepartman(Main.getSelectedPersonel().getAdi());
                        mdr.setEtkinlikler(Main.getSelectedPersonel().getEtkinlikler());
                        mdr.setIseGiris(Main.getSelectedPersonel().getIseGiris());
                        mdr.setSeviye(Integer.parseInt(kidem));
                        mdr.setToplantiSayisi(Integer.parseInt(toplantiSayisi));
                        this.getToplantiSayisi().setText(String.valueOf(mdr.getToplantiSayisi()));
                        this.getKidem().setText(String.valueOf(mdr.getSeviye()));
                        i = size;
                    }
                }
            }
        } catch (Exception e) {
            //olasi bir hata durumunda karsilasilan hatayi konsol ekranina yazma islemi gerceklesecek
            e.printStackTrace();
        }
    }

    public void geri() throws Exception {
        Main.goToPage("/sample/personel.fxml");
        Main.setTitle("Şube Çalışanları");
    }

    public Label getAdSoyadLabel() {
        return adSoyadLabel;
    }

    public TextField getToplantiSayisi() {
        return toplantiSayisi;
    }

    public TextField getKidem() {
        return kidem;
    }

    public DosyaYazma getDy() {
        if(this.dy == null)
            this.dy = new DosyaYazma();
        return dy;
    }
}
