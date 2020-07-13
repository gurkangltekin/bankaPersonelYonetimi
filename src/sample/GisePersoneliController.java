package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GisePersoneliController extends MudurController implements Initializable {
    @FXML
    private Label adSoyadLabel;
    @FXML
    private TextField kasadakiPara;
    @FXML
    private TextField aylikSatisHedefi;
    @FXML
    private TextField toplamSatis;
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

    @Override
    public void bilgileriYaz(){
        String yazi = "!" + Main.getSelectedPersonel().getTcKimlikNo() + "@" + this.getAylikSatisHedefi().getText() +
                "%" + this.getToplamSatis().getText() + "&" + this.getKasadakiPara().getText() + "=" ;

        try {
            if(this.getDy().dosyaVarmi("gisePersoneli")) {
                boolean kontrol = false;
                List<String> lines = this.getDy().dosyadanOku("gisePersoneli");
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

                            this.getDy().dosyaGuncelle("gisePersoneli", lines);

                            i = size;
                        }
                    }
                }
                if(!kontrol){
                    this.getDy().setDosyayaYaz(yazi);
                    this.getDy().dosyayaYaz("gisePersoneli");
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void bilgileriOku(){
        try {

            List<String> lines = this.getDy().dosyadanOku("gisePersoneli");
            int size = lines.size();

            for (int i = 0; i < size; i++) {

                String str = lines.get(i);

                int strsize = str.length();

                String tckimlik = "";
                String toplamMusteriSayisi = "";
                String aylikSatisHedefi = "";
                String yapilanToplamSatis = "";

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
                                aylikSatisHedefi += str.charAt(j++);
                            }
                        }
                        if (str.charAt(j) == '%') {
                            j++;
                            while (str.charAt(j) != '&') {
                                yapilanToplamSatis += str.charAt(j++);
                            }
                        }
                        if (str.charAt(j) == '&') {
                            j++;
                            while (str.charAt(j) != '=') {
                                toplamMusteriSayisi += str.charAt(j++);
                            }
                        }
                        BireyselMusteriTemsilcisi bmt = new BireyselMusteriTemsilcisi();
                        bmt.setAdi(Main.getSelectedPersonel().getAdi());
                        bmt.setSoyadi(Main.getSelectedPersonel().getAdi());
                        bmt.setTcKimlikNo(Main.getSelectedPersonel().getTcKimlikNo());
                        bmt.setDepartman(Main.getSelectedPersonel().getAdi());
                        bmt.setEtkinlikler(Main.getSelectedPersonel().getEtkinlikler());
                        bmt.setIseGiris(Main.getSelectedPersonel().getIseGiris());
                        bmt.setAylikSatisHedefi(Integer.parseInt(aylikSatisHedefi));
                        bmt.setToplamMusteriSayisi(Integer.parseInt(toplamMusteriSayisi));
                        bmt.setYapilanToplamSatis(Integer.parseInt(yapilanToplamSatis));
                        this.getKasadakiPara().setText(String.valueOf(bmt.getToplamMusteriSayisi()));
                        this.getToplamSatis().setText(String.valueOf(bmt.getYapilanToplamSatis()));
                        this.getAylikSatisHedefi().setText(String.valueOf(bmt.getAylikSatisHedefi()));
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

    public TextField getKasadakiPara() {
        return kasadakiPara;
    }

    public TextField getAylikSatisHedefi() {
        return aylikSatisHedefi;
    }

    public TextField getToplamSatis() {
        return toplamSatis;
    }

    public DosyaYazma getDy() {
        if(this.dy == null)
            this.dy = new DosyaYazma();
        return dy;
    }
}
