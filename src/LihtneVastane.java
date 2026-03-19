import java.util.List;

public class LihtneVastane extends Vastane {
    /**
     * Lihtsa vastase loogika: valib suvaliseslt, kas ta hitib või standib
     * @param kasi Vastase käe seis
     * @return Vastase tegevus
     */
    @Override
    public char tegevus(List<Kaart> kasi) throws InterruptedException {
        Thread.sleep(1000);
        int suvalineBit = (int) Math.round(Math.random());
        if (suvalineBit==1) {
            System.out.println("Hit!");
            return 'H';
        }
        else {
            System.out.println("Stand");
            return 'S';
        }
    }
}
