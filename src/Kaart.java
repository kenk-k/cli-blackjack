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
        int ässadeArv = 0;
        for (Kaart kaart: kaardid) {
            if (kaart.number.equals("A")) {
                //Ässa väärtuse varieerumine
                    ässadeArv++;
                    kaeSumma += kaart.vaartus;
            }
            else {
                kaeSumma += kaart.vaartus;
            }
            if (kaeSumma>21) {
                while (ässadeArv > 0) {
                    kaeSumma -= 10;
                    ässadeArv--;
                }
            }
        }
        return kaeSumma;
    }
    @Override
    public String toString() {
        return mast +" " + number;
    }
}