import java.util.List;

public class Diiler implements Vastane {
    public String tegevus(List<Kaart> kasi) {
        if (Kaart.kaeVaartus(kasi) < 17)
            return "H";
        else if (Kaart.kaeVaartus(kasi) > 17)
            return "S";
        else {
            for (Kaart kaart : kasi) {
                if (kaart.getNumber().equals("A"))
                    return "H";
            }
            return "S";
        }
    }
}
