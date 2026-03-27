import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kaardipakk {
    private List<Kaart> kaardid;

    public Kaardipakk(int mituPakki) {
        this.kaardid = new ArrayList<>();
        String[] mastid = new String[] {"Poti","Ärtu", "Risti", "Ruutu"};
        String[] pildid = new String[] {"J", "Q", "K"};
        for (int i = 0; i < mituPakki; i++) {
            //Genereerib ühe kaardipaki
            for (String mast : mastid) {
                for (int j = 2; j < 11; j++) {
                    kaardid.add(new Kaart(mast, Integer.toString(j), j));
                }
                for (String pilt : pildid) {
                    kaardid.add(new Kaart(mast, pilt, 10));
                }
                kaardid.add(new Kaart(mast, "A", 11));
            }
        }
    }

    public void segamine() {
        Collections.shuffle(kaardid);
    }

    public Kaart jagamine() {
        return kaardid.removeFirst();
    }

    public int suurus() {
        return kaardid.size();
    }
}
