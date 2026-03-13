import java.util.List;

class Kaart {
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

    public String getMast() {
        return mast;
    }

    public int getVaartus() {
        return vaartus;
    }

    public static int kaeVaartus(List<Kaart> kaardid) {
        int kaeSumma = 0;
        for (Kaart kaart: kaardid) {
            kaeSumma += kaart.vaartus;
        }
        return kaeSumma;
    }
}