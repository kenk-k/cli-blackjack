import java.util.List;

//kas siin ei peaks olema "extends"? või ma saan mingist loogikast valesti aru?
public class Diiler extends Vastane {
    public char tegevus(List<Kaart> kasi) {
        int vaartus = Kaart.kaeVaartus(kasi);

        if (vaartus < 17)
            return 'H';
        else if (vaartus > 17)
            return 'S';
        else {
            for (Kaart kaart : kasi) {
                if (kaart.getNumber().equals("A"))
                    return 'S';
            }
            return 'S';
        }
    }
}
