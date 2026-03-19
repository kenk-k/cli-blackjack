import java.util.List;

public class Diiler extends Vastane {
    /**
     * Diileri loogika, hitib, kui käsi on väiksem kui 17, kui on suurem või võrdne ilma ässata, standib
     * @param kasi Diileri käsi, mille pealt tegevuse otsustab
     * @return Tegevus blackjacki mängus
     */
    @Override
    public char tegevus(List<Kaart> kasi) {
        int vaartus = Kaart.kaeVaartus(kasi);

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
            System.out.println("Stand");
            return 'S';
        }
    }
}
