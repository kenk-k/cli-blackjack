import java.util.ArrayList;
import java.util.List;

public class Kaardipakk {
    private List<Kaart> kaardid;

    public Kaardipakk() {
        this.kaardid = new ArrayList<>();
    }

    //see paneb mind silmi välja kiskuma tahtma
    public Kaardipakk(int mituPakki) {
        this.kaardid = new ArrayList<>();
        String[] mastid = new String[] {"Poti","Ärtu", "Risti", "Ruutu"};
        String[] pildid = new String[] {"J", "Q", "K"};
        for (int i = 0; i < mituPakki; i++) {
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

    public void setKaardid(List<Kaart> kaardid) {
        this.kaardid = kaardid;
    }

    public void segamine() {
        List<Kaart> uuedKaardid = new ArrayList<>();
        for (int i = kaardid.size(); i > 0; i--) {
            //Lisab uude List-i kaardi ja eemaldab eelmisest
            Kaart praeguneKaart = kaardid.get((int)Math.round((Math.random() * (i-1))));
            uuedKaardid.add(praeguneKaart);
            kaardid.remove(praeguneKaart);
        }
        setKaardid(uuedKaardid);
    }

    public Kaart jagamine() {
        return kaardid.removeFirst();
    }

    public int suurus() {
        return kaardid.size();
    }

    public void lisamine(Kaart lisatav) {
        kaardid.add(lisatav);
    }
    //Mõttetu toString tegelikult
    @Override
    public String toString() {
        return "Kaardipakk{" +
                "kaardid=" + kaardid +
                '}';
    }

}
