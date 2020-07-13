package sample;

public class BireyselMusteriTemsilcisi extends Personel{

    private int toplamMusteriSayisi;
    private int aylikSatisHedefi;
    private int yapilanToplamSatis;

    public BireyselMusteriTemsilcisi() {
    }

    public int getToplamMusteriSayisi() {
        return toplamMusteriSayisi;
    }

    public void setToplamMusteriSayisi(int toplamMusteriSayisi) {
        this.toplamMusteriSayisi = toplamMusteriSayisi;
    }

    public int getAylikSatisHedefi() {
        return aylikSatisHedefi;
    }

    public void setAylikSatisHedefi(int aylikSatisHedefi) {
        this.aylikSatisHedefi = aylikSatisHedefi;
    }

    public int getYapilanToplamSatis() {
        return yapilanToplamSatis;
    }

    public void setYapilanToplamSatis(int yapilanToplamSatis) {
        this.yapilanToplamSatis = yapilanToplamSatis;
    }
}
