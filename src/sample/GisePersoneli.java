package sample;

import java.time.LocalDate;

public class GisePersoneli extends Personel{

    private int kasadakiPara;
    private int aylikSatisHedefi;
    private int toplamYapilanSatis;

    public GisePersoneli() {
    }

    public int getKasadakiPara() {
        return kasadakiPara;
    }

    public void setKasadakiPara(int kasadakiPara) {
        this.kasadakiPara = kasadakiPara;
    }

    public int getAylikSatisHedefi() {
        return aylikSatisHedefi;
    }

    public void setAylikSatisHedefi(int aylikSatisHedefi) {
        this.aylikSatisHedefi = aylikSatisHedefi;
    }

    public int getToplamYapilanSatis() {
        return toplamYapilanSatis;
    }

    public void setToplamYapilanSatis(int toplamYapilanSatis) {
        this.toplamYapilanSatis = toplamYapilanSatis;
    }
}
