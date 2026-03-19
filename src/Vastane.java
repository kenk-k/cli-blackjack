import java.util.List;

public class Vastane {
    /**
     * Vastase loogika, vt. diileri loogikat
     * @param kasi Vastase käe seis
     * @return Vastase tegevus
     */
    public char tegevus(List<Kaart> kasi) throws InterruptedException {
        int vaartus = Kaart.kaeVaartus(kasi);
        Thread.sleep(1000);

        if (vaartus < 17) {
            System.out.println("Hit!");
            return 'H';
        }
        else if (vaartus > 17) {
            System.out.println("Stand!");

            return 'S';
        }
        else {
            for (Kaart kaart : kasi) {
                if (kaart.getNumber().equals("A")) {
                    System.out.println("Hit!");
                    return 'H';
                }
            }
            System.out.println("Stand!");
            return 'S';
        }
    }
}
