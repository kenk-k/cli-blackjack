import java.util.ArrayList;
import java.util.List;

public class Mangija {

    private List<Kaart> kasi;
    private String nimi;
    private double rahahulk;
    private List<Kaart> kasiSplit;

    public Mangija(String nimi, double rahahulk) {
        this.nimi = nimi;
        this.rahahulk = rahahulk;
        this.kasi = new ArrayList<>();
        this.kasiSplit = new ArrayList<>();
    }

    public String getNimi() {
        return nimi;
    }

    public double getRahahulk() {
        return rahahulk;
    }

    public void setRahahulk(double rahahulk) { this.rahahulk = rahahulk; }

    public List<Kaart> getKasi() {
        return kasi;
    }

    public boolean panusta(double panus) {
        if (rahahulk - panus < 0)
            return false;
        else {
            rahahulk -= panus;
            return true;
        }
    }

    public void lisaKaart(Kaart kaart) {
        kasi.add(kaart);
    }

    public void lisaKaartSplit(Kaart kaart) {
        kasiSplit.add(kaart);
    }

    public void tuhjendaKasi() {
        kasi.clear();
    }
}
