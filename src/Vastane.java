import java.util.List;

public class Vastane {
    public char tegevus(List<Kaart> kasi) {
        int vaartus = Kaart.kaeVaartus(kasi);

        if (vaartus < 17)
            return 'H';
        else if (vaartus > 17)
            return 'S';
        else {
            for (Kaart kaart : kasi) {
                if (kaart.getNumber().equals("A"))
                    return 'H';
            }
            return 'S';
        }
    }
}
