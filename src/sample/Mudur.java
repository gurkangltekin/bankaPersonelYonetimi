package sample;

import java.time.LocalDate;
import java.util.List;

public class Mudur extends Personel{

    private int toplantiSayisi;
    private int seviye;

    public Mudur() {
    }

    public int getToplantiSayisi() {
        return toplantiSayisi;
    }

    public void setToplantiSayisi(int toplantiSayisi) {
        this.toplantiSayisi = toplantiSayisi;
    }

    public int getSeviye() {
        return seviye;
    }

    public void setSeviye(int seviye) {
        this.seviye = seviye;
    }
}
