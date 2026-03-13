import java.util.ArrayList;
import java.util.List;

public class Kaardipakk {
    private List<Kaart> kaardid;

    public Kaardipakk(List<Kaart> kaardid) {
        this.kaardid = kaardid;
    }

    public List<Kaart> getKaardid() {
        return kaardid;
    }

    public void setKaardid(List<Kaart> kaardid) {
        this.kaardid = kaardid;
    }

    public void segamine() {
        List<Kaart> uuedKaardid = new ArrayList<>();
        for (int i = 52; i >= 0; i--) {
            //Lisab uude List-i kaardi ja eemaldab eelmisest
            Kaart praeguneKaart = kaardid.get((int)(Math.random() * i));
            uuedKaardid.add(praeguneKaart);
            kaardid.remove(praeguneKaart);
        }
        setKaardid(uuedKaardid);
    }

    public Kaart jagamine() {
        return kaardid.removeFirst();
    }

    public void lisamine(Kaart lisatav) {
        kaardid.add(lisatav);
    }
}
