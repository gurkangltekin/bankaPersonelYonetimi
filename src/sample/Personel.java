package sample;

import java.time.LocalDate;

public class Personel {

    private String adi;
    private String soyadi;
    private long tcKimlikNo;
    private LocalDate iseGiris;
    private int maas;
    private int etkinlikler;
    private String departman;

    public Personel() {
    }

    public Personel(String adi, String soyadi, long tcKimlikNo, LocalDate iseGiris, int maas, int etkinlikler) {
        this.adi = adi;
        this.soyadi = soyadi;
        this.tcKimlikNo = tcKimlikNo;
        this.iseGiris = iseGiris;
        this.maas = maas;
        this.etkinlikler = etkinlikler;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getSoyadi() {
        return soyadi;
    }

    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }

    public long getTcKimlikNo() {
        return tcKimlikNo;
    }

    public void setTcKimlikNo(long tcKimlikNo) {
        this.tcKimlikNo = tcKimlikNo;
    }

    public LocalDate getIseGiris() {
        if(this.iseGiris == null)
            this.iseGiris = LocalDate.now();
        return iseGiris;
    }

    public void setIseGiris(LocalDate iseGiris) {
        this.iseGiris = iseGiris;
    }

    public int getMaas() {
        return maas;
    }

    public void setMaas(int maas) {
        this.maas = maas;
    }

    public int getEtkinlikler() {
        return etkinlikler;
    }

    public void setEtkinlikler(int etkinlikler) {
        this.etkinlikler = etkinlikler;
    }

    public String getDepartman() {
        return departman;
    }

    public void setDepartman(String departman) {
        this.departman = departman;
    }
}
