import java.util.Scanner;

public class Main {
    static void main() {
        Kaardipakk kaardipakk = new Kaardipakk(1);
        Mangija diiler = new Mangija("Diiler", 10000000);
        Mangija mina = new Mangija("Mina", 1000);
        Mangija[] mangijad = new Mangija[] {diiler, mina};
        kaardipakk.segamine();
        Vastane diilerAI = new Diiler();
        //tegevuste jaoks mängija objekt
        Vastane minaAI = new Mina();
        for (Mangija mangija: mangijad) {
            for (int i = 0; i < 2; i++) {
                mangija.lisaKaart(kaardipakk.jagamine());
            }
        }
        System.out.println("diiler:");
        System.out.println(diiler.getKasi().get(1));
        System.out.println(Kaart.kaeVaartus(diiler.getKasi()));
        System.out.println("mangija:");
        System.out.println(mina.getKasi());
        System.out.println(Kaart.kaeVaartus(mina.getKasi()));

        raund(mina,minaAI,kaardipakk);

        System.out.println(diiler.getKasi());
        raund(diiler,diilerAI,kaardipakk);

        //TODO: bustimine mängu
        if (Kaart.kaeVaartus(mina.getKasi()) >= Kaart.kaeVaartus(diiler.getKasi())) {
            System.out.println("You win!");
        }
        else {
            System.out.println("You lose :(");
        }


    }
    //TODO: nimetada see meetod kuidagi targemalt
    public static void raund(Mangija mangija, Vastane ai, Kaardipakk kaardipakk) {
        while (Kaart.kaeVaartus(mangija.getKasi()) <= 21) {
            String tegevus = ai.tegevus(mangija.getKasi());
            if (tegevus.equals("H")) {
                mangija.lisaKaart(kaardipakk.jagamine());
                System.out.println(mangija.getKasi());
            }
            else break;
        }
    }

}
