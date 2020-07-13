package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;


public class PersonelController implements Initializable {
    @FXML
    private Label hata;
    @FXML
    private TextField adi;
    @FXML
    private TextField soyadi;
    @FXML
    private TextField tcKimlik;
    @FXML
    private TextField maas;
    @FXML
    private TextField etkinlikSayisi;
    @FXML
    private TextField departman;
    @FXML
    private DatePicker iseGiris;
    @FXML
    private TableView<Personel> personelTablosu;
    @FXML
    private TableColumn<Personel, String> tabloAdi;
    @FXML
    private TableColumn<Personel, String> tabloSoyadi;
    @FXML
    private TableColumn<Personel, Date> tabloIseGiris;
    @FXML
    private Button formTemizle;
    @FXML
    private Button personelYonetim;
    @FXML
    private Button istenCikar;
    @FXML
    private Button digerVeriler;

    private final ObservableList<Personel> list = FXCollections.observableArrayList();
    private final DosyaYazma dy = new DosyaYazma();
    private String yazi;
    private Personel p;


    public void geri() throws Exception {
        Main.goToPage("/sample/anasayfa.fxml");
        Main.setTitle("Anasayfa");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dosyadanOku();
        this.getDigerVeriler().setVisible(false);
        this.getFormTemizle().setVisible(false);
        this.getIstenCikar().setVisible(false);
        this.getIseGiris().setValue(LocalDate.now());
        if (Main.getUser().getAuth().equals("Mudur")) {
            this.getPersonelYonetim().setText("Yeni Personel Ekle");
        } else {
            this.getPersonelYonetim().setVisible(false);
        }
    }

    public void dosyadanOku() {

        this.getPersonelTablosu().getItems().clear();
        this.getList().clear();
        try {
            List<String> lines = this.getDy().dosyadanOku("PersonelListesi");

            for (String str : lines) {
                int strsize = str.length();

                StringBuilder ad = new StringBuilder();
                StringBuilder soyad = new StringBuilder();
                StringBuilder tckimlik = new StringBuilder();
                StringBuilder maas = new StringBuilder();
                StringBuilder departman = new StringBuilder();
                StringBuilder isegiris = new StringBuilder();
                StringBuilder etkinlik = new StringBuilder();

                for (int j = 0; j < strsize; j++) {

                    if (str.charAt(j) == '!') {
                        j++;
                        while (str.charAt(j) != '@') {
                            tckimlik.append(str.charAt(j++));
                        }
                    }
                    if (str.charAt(j) == '@') {
                        j++;
                        while (str.charAt(j) != '#') {
                            ad.append(str.charAt(j++));
                        }
                    }
                    if (str.charAt(j) == '#') {
                        j++;
                        while (str.charAt(j) != '%') {
                            soyad.append(str.charAt(j++));
                        }
                    }
                    if (str.charAt(j) == '%') {
                        j++;
                        while (str.charAt(j) != '&') {
                            departman.append(str.charAt(j++));
                        }
                    }
                    if (str.charAt(j) == '&') {
                        j++;
                        while (str.charAt(j) != '/') {
                            etkinlik.append(str.charAt(j++));
                        }
                    }
                    if (str.charAt(j) == '/') {
                        j++;
                        while (str.charAt(j) != '{') {
                            isegiris.append(str.charAt(j++));
                        }
                    }
                    if (str.charAt(j) == '{') {
                        j++;
                        while (str.charAt(j) != '=') {
                            maas.append(str.charAt(j++));
                        }
                    }
                }

                p = new Personel();
                p.setAdi(ad.toString());
                p.setSoyadi(soyad.toString());
                p.setDepartman(departman.toString());
                p.setMaas(parseInt(maas.toString()));
                p.setTcKimlikNo(Long.parseLong(tckimlik.toString()));
                p.setIseGiris(LocalDate.parse(isegiris.toString()));
                p.setEtkinlikler(Integer.parseInt(etkinlik.toString()));
                this.getList().add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.tabloyaYukle();
    }

    public void tabloyaYukle() {

        this.getPersonelTablosu().setItems(this.getList());

        this.getTabloAdi().setCellValueFactory(new PropertyValueFactory<>("adi"));

        this.getTabloSoyadi().setCellValueFactory(new PropertyValueFactory<>("soyadi"));

        this.getTabloIseGiris().setCellValueFactory(new PropertyValueFactory<>("iseGiris"));
    }

    public void personelTanimla() {
        p = new Personel();
        p.setAdi(this.getAdi().getText());
        p.setSoyadi(this.getSoyadi().getText());
        p.setTcKimlikNo(Long.parseLong(this.getTcKimlik().getText()));
        p.setMaas(Integer.parseInt(this.getMaas().getText()));
        p.setIseGiris(this.getIseGiris().getValue());
        p.setEtkinlikler(Integer.parseInt(this.getEtkinlikSayisi().getText()));
        p.setDepartman(this.getDepartman().getText());
        this.getList().add(p);
    }

    public void yaziOlustur() {
        this.setYazi("!" + p.getTcKimlikNo() + "@" + p.getAdi() + "#" + p.getSoyadi() + "%" + p.getDepartman() + "&" +
                p.getEtkinlikler() + "/" + p.getIseGiris().toString() + "{" + p.getMaas() + "=");
    }

    public void personelIslemleri() {
        if (this.getPersonelYonetim().getText().equals("Yeni Personel Ekle")) {
            switch (this.getDepartman().getText()) {
                case "Bireysel Musteri Temsilcisi":
                case "Diger Personeller":
                case "Gise Personeli":
                case "Mudur":
                case "Operasyon Personeli":
                    this.personelEkle();
                    break;
                default:
                    this.getHata().setText("Yanlış personel departmanı girdiniz!");
                    break;
            }
        } else {
            if (this.getDepartman().getText().equals("Bireysel Musteri Temsilcisi") ||
                    this.getDepartman().getText().equals("Diger Personeller") ||
                    this.getDepartman().getText().equals("Gise Personeli") ||
                    this.getDepartman().getText().equals("Mudur") ||
                    this.getDepartman().getText().equals("Operasyon Personeli")) {

                this.personelTanimla();

                this.yaziOlustur();

                try {
                    List<String> lines = this.getDy().dosyadanOku("PersonelListesi");
                    int size = lines.size();
                    for (int i = 0; i < size; i++) {
                        String line = lines.get(i);
                        int strsize = line.length();
                        for (int j = 0; j < strsize; j++) {
                            StringBuilder tckimlik = new StringBuilder();

                            if (line.charAt(j) == '!') {
                                j++;
                                while (line.charAt(j) != '@') {
                                    tckimlik.append(line.charAt(j++));
                                }
                            }

                            if (tckimlik.toString().equals(this.getTcKimlik().getText())) {

                                lines.remove(i);

                                lines.add(this.getYazi());

                                this.getDy().dosyaGuncelle("PersonelListesi", lines);

                                i = size;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                this.formTemizle();
                this.dosyadanOku();
            } else {
                this.getHata().setText("Yanlış personel departmanı girdiniz!");
            }
        }
    }

    public void personelEkle(){
        this.personelTanimla();
        this.yaziOlustur();

        this.getDy().setDosyayaYaz(this.getYazi());
        try {
            this.getDy().dosyayaYaz("PersonelListesi");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        this.formTemizle();
        this.getHata().setText("");


        this.tabloyaYukle();
        this.formTemizle();
    }

    public void personelDetay() {

        TableView.TableViewSelectionModel<Personel> selectionModel = this.getPersonelTablosu().getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        Main.setSelectedPersonel(selectionModel.getSelectedItem());

        this.getTcKimlik().setText(String.valueOf(Main.getSelectedPersonel().getTcKimlikNo()));
        this.getAdi().setText(Main.getSelectedPersonel().getAdi());
        this.getSoyadi().setText(Main.getSelectedPersonel().getSoyadi());
        this.getMaas().setText(String.valueOf(Main.getSelectedPersonel().getMaas()));
        this.getIseGiris().setValue(Main.getSelectedPersonel().getIseGiris());
        this.getEtkinlikSayisi().setText(String.valueOf(Main.getSelectedPersonel().getEtkinlikler()));
        this.getDepartman().setText(Main.getSelectedPersonel().getDepartman());


        if (Main.getUser().getAuth().equals("Mudur")) {
            this.getFormTemizle().setVisible(true);
            this.getIstenCikar().setVisible(true);
            this.getPersonelYonetim().setText("Personel Güncelle");
        }
        this.getDigerVeriler().setVisible(true);

    }

    public void formTemizle() {
        this.getTcKimlik().setText("");
        this.getAdi().setText("");
        this.getSoyadi().setText("");
        this.getEtkinlikSayisi().setText("");
        this.getMaas().setText("");
        this.getDepartman().setText("");
        this.getIseGiris().setValue(LocalDate.now());
        this.getPersonelYonetim().setText("Yeni Personel Ekle");
        this.getFormTemizle().setVisible(false);
        this.getDigerVeriler().setVisible(false);
        this.getIstenCikar().setVisible(false);
        this.getPersonelTablosu().getSelectionModel().clearSelection();
        Main.setSelectedPersonel(null);
    }

    public void veriDetay() throws Exception {
        switch (this.getDepartman().getText()) {
            case "Bireysel Musteri Temsilcisi":
                Main.goToPage("/sample/bireyselMusteriTemsilcisi.fxml");
                Main.setTitle("Bireysel Müşteri Temsilcisi");
                break;
            case "Diger Personeller":
                Main.goToPage("/sample/digerPersoneller.fxml");
                Main.setTitle("Diger Personeller");
                break;
            case "Gise Personeli":
                Main.goToPage("/sample/gisePersoneli.fxml");
                Main.setTitle("Gise Personeli");
                break;
            case "Mudur":
                Main.goToPage("/sample/mudur.fxml");
                Main.setTitle("Mudur");
                break;
            case "Operasyon Personeli":
                Main.goToPage("/sample/operasyonPersoneli.fxml");
                Main.setTitle("Operasyon Personeli");
                break;
            default:
                this.getHata().setText("Yanlış personel departmanı girdiniz!");
                break;
        }
    }

    public void istenCikar() {
        switch (this.getDepartman().getText()) {
            case "Bireysel Musteri Temsilcisi":
                this.istenCikar("bireyselMusteriTemsilcisi");
                break;
            case "Diger Personeller":
                this.istenCikar("digerPersonel");
                break;
            case "Gise Personeli":
                this.istenCikar("gisePersoneli");
                break;
            case "Mudur":
                this.istenCikar("mudur");
                break;
            case "Operasyon Personeli":
                this.istenCikar("operasyonPersoneli");
                break;
            default:
                this.getHata().setText("Yanlış personel departmanı girdiniz!");
                break;
        }
        this.istenCikar("PersonelListesi");
        this.dosyadanOku();
        this.formTemizle();
    }

    public void istenCikar(String string) {
        try {
            List<String> lines = this.getDy().dosyadanOku(string);
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
                    if (tckimlik.toString().equals(this.getTcKimlik().getText())) {
                        lines.remove(i);
                        this.getDy().dosyaGuncelle(string, lines);
                        i = size;
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public Label getHata() {
        return hata;
    }

    public TextField getAdi() {
        return adi;
    }

    public TextField getSoyadi() {
        return soyadi;
    }

    public TextField getTcKimlik() {
        return tcKimlik;
    }

    public TextField getMaas() {
        return maas;
    }

    public TextField getEtkinlikSayisi() {
        return etkinlikSayisi;
    }

    public DatePicker getIseGiris() {
        return iseGiris;
    }

    public TableView<Personel> getPersonelTablosu() {
        return personelTablosu;
    }

    public TableColumn<Personel, String> getTabloAdi() {
        return tabloAdi;
    }

    public TableColumn<Personel, String> getTabloSoyadi() {
        return tabloSoyadi;
    }

    public TableColumn<Personel, Date> getTabloIseGiris() {
        return tabloIseGiris;
    }

    public Button getFormTemizle() {
        return formTemizle;
    }

    public Button getPersonelYonetim() {
        return personelYonetim;
    }

    public Button getDigerVeriler() {
        return digerVeriler;
    }

    public ObservableList<Personel> getList() {
        return list;
    }

    public TextField getDepartman() {
        return departman;
    }

    public DosyaYazma getDy() {
        return dy;
    }

    public Button getIstenCikar() {
        return istenCikar;
    }

    public String getYazi() {
        return yazi;
    }

    public void setYazi(String yazi) {
        this.yazi = yazi;
    }
}
