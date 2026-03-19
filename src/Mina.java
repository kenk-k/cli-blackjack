import java.util.List;
import java.util.Scanner;

public class Mina extends Vastane {
    public char tegevus(List<Kaart> kaardid, Scanner input) {
        return input.next().charAt(0);
    }
}
