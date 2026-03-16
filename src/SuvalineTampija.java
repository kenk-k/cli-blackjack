import java.util.List;

public class SuvalineTampija extends Vastane {
    @Override
    public char tegevus(List<Kaart> kasi) {
        int suvalineBit = (int) Math.round(Math.random());
        if (suvalineBit==1)
            return 'H';
        else return 'S';
    }
}
