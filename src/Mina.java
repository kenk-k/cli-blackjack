import java.util.List;
import java.util.Scanner;

public class Mina extends Vastane {
    public char tegevus(List<Kaart> kaardid) {
        Scanner scan = new Scanner(System.in);
        return scan.next().charAt(0);
    }
}
