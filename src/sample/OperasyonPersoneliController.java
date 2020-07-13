package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OperasyonPersoneliController implements Initializable {

    @FXML
    private Label adSoyadLabel;
    @FXML
    private TextField gelenEvrakSayisi;
    @FXML
    private TextField gidenEvrakSayisi;

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
        String yazi = "!" + Main.getSelectedPersonel().getTcKimlikNo() + "@" + this.getGelenEvrakSayisi().getText() +
                "%" + this.getGidenEvrakSayisi().getText() + "&" ;

        try {
            if(this.getDy().dosyaVarmi("operasyonPersoneli")) {
                boolean kontrol = false;
                List<String> lines = this.getDy().dosyadanOku("operasyonPersoneli");
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

                            this.getDy().dosyaGuncelle("operasyonPersoneli", lines);

                            i = size;
                        }
                    }
                }
                if(!kontrol){
                    this.getDy().setDosyayaYaz(yazi);
                    this.getDy().dosyayaYaz("operasyonPersoneli");
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void bilgileriOku(){
        try {

            List<String> lines = this.getDy().dosyadanOku("operasyonPersoneli");
            int size = lines.size();

            for (int i = 0; i < size; i++) {

                String str = lines.get(i);

                int strsize = str.length();

                String tckimlik = "";
                String gelenEvrakSayisi = "";
                String gidenEvrakSayisi = "";

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
                                gidenEvrakSayisi += str.charAt(j++);
                            }
                        }
                        if (str.charAt(j) == '%') {
                            j++;
                            while (str.charAt(j) != '&') {
                                gelenEvrakSayisi += str.charAt(j++);
                            }
                        }
                        OperasyonPersoneli op = new OperasyonPersoneli();
                        op.setAdi(Main.getSelectedPersonel().getAdi());
                        op.setSoyadi(Main.getSelectedPersonel().getAdi());
                        op.setTcKimlikNo(Main.getSelectedPersonel().getTcKimlikNo());
                        op.setDepartman(Main.getSelectedPersonel().getAdi());
                        op.setEtkinlikler(Main.getSelectedPersonel().getEtkinlikler());
                        op.setIseGiris(Main.getSelectedPersonel().getIseGiris());
                        op.setToplamGidenEvrakSayisi(Integer.parseInt(gidenEvrakSayisi));
                        op.setToplamGelenEvrakSayisi(Integer.parseInt(gelenEvrakSayisi));
                        this.getGidenEvrakSayisi().setText(String.valueOf(op.getToplamGidenEvrakSayisi()));
                        this.getGelenEvrakSayisi().setText(String.valueOf(op.getToplamGelenEvrakSayisi()));
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

    public TextField getGelenEvrakSayisi() {
        return gelenEvrakSayisi;
    }

    public TextField getGidenEvrakSayisi() {
        return gidenEvrakSayisi;
    }

    public DosyaYazma getDy() {
        if(this.dy == null)
            this.dy = new DosyaYazma();
        return dy;
    }
}
