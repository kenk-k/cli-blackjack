import java.util.List;

public class Kasutaja extends Vastane {
    /**
     * Küsib kasutajalt tegevust blackjackis
     * @param kaardid Kasutaja käe seis
     * @return Kasutaja tegevus
     */
    @Override
    public char tegevus(List<Kaart> kaardid) {
        System.out.println("Sinu tegevus: (H)it, (S)tand, (D)ouble down: ");
        return Main.input.next().charAt(0);
    }
}
