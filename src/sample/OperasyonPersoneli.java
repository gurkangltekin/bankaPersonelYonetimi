package sample;

public class OperasyonPersoneli extends Personel{

    private int toplamGelenEvrakSayisi;
    private int toplamGidenEvrakSayisi;

    public OperasyonPersoneli() {
    }

    public int getToplamGelenEvrakSayisi() {
        return toplamGelenEvrakSayisi;
    }

    public void setToplamGelenEvrakSayisi(int toplamGelenEvrakSayisi) {
        this.toplamGelenEvrakSayisi = toplamGelenEvrakSayisi;
    }

    public int getToplamGidenEvrakSayisi() {
        return toplamGidenEvrakSayisi;
    }

    public void setToplamGidenEvrakSayisi(int toplamGidenEvrakSayisi) {
        this.toplamGidenEvrakSayisi = toplamGidenEvrakSayisi;
    }
}
