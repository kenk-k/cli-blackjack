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

    public int getVaartus() {
        return vaartus;
    }

    public static int kaeVaartus(List<Kaart> kaardid) {
        int kaeSumma = 0;
        for (Kaart kaart: kaardid) {
            //why tf see autocorrectib selleks mitte '=='
            if (kaart.number.equals("A")) {
                //Ässa väärtuse varieerumine
                if (kaeSumma + 11 > 21) {
                    kaeSumma += 1;
                    continue;
                } else {
                    kaeSumma += kaart.vaartus;
                }
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