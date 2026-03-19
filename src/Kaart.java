import java.util.List;
import java.util.Objects;

public class Kaart {
    private String mast;
    //2-10, J, Q, K ,A
    private String number;
    private int vaartus;

    public Kaart(String mast, String number, int vaartus) {
        this.mast = mast;
        this.number = number;
        this.vaartus = vaartus;
    }

    public String getNumber() {
        return number;
    }

    public static int kaeVaartus(List<Kaart> kaardid) {
        int kaeSumma = 0;
        for (Kaart kaart: kaardid) {
            if (kaart.number.equals("A") && kaeSumma + 11 > 21) {
                //Ässa väärtuse varieerumine
                    kaeSumma += 1;
            }
            else {
                kaeSumma += kaart.vaartus;
            }
        }
        return kaeSumma;
    }
    @Override
    public String toString() {
        return mast +" " + number;
    }
}