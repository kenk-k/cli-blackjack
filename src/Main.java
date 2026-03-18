import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        //Mängu alguse menüü.
        String[][] defSätted = {{"players", "2"}, {"start", "1000"}, {"diff", "noob"}};
        System.out.println("Tere tulemast kasiinosse.\n\n" +
                "Vali tegevus:\tSätted (S)\tAbi (A)\tMängima (M)");
        Scanner valik;
        label:
        while (true) {
            valik = new Scanner(System.in);
            String token = valik.next();
            switch (token) {
                case "S":
                    defSätted = sätted(defSätted);
                    break;
                case "A":
                    abi();
                    break;
                case "M":
                    break label;
                default:
                    System.out.println("Sisesta sobiv valik.");
                    break;
            }
        }
        valik.close();

        Kaardipakk kaardipakk = new Kaardipakk(1);
        Mangija diiler = new Mangija("Diiler", 10000000);
        Mangija mina = new Mangija("Mina", 1000);
        kaardipakk.segamine();
        Vastane diilerAI = new Diiler();
        //tegevuste jaoks mängija objekt

        //vastaste loomine ja mangijate listi tegemine.
        System.out.print("Mitme vastasega soovid mängida?(number) ");
        Scanner vastasteKogus = new Scanner(System.in);
        int vastasteKogusInt = vastasteKogus.nextInt();
        ArrayList<Mangija> mangijad = new ArrayList<>();
        mangijad.add(diiler);
        for (int i = 0; i < vastasteKogusInt; i++) {
            Mangija lisatav = new Mangija("Tauno Maksimus", 1000);
            mangijad.add(lisatav);
        }
        mangijad.add(mina);
        Vastane minaAI = new Mina();

        //TODO: bet süsteem implementeerida
        while (true) {
            jagamised(mangijad, kaardipakk);
            //TODO: raunde AI vastaste ja diileri vahel saaks targemalt teha
            for (Mangija m : mangijad) {
                if (m == diiler) {
                    System.out.println("diiler:");
                    System.out.println(diiler.getKasi().get(1));
                    System.out.println(Kaart.kaeVaartus(diiler.getKasi()));
                } else if (m == mina) {
                    raund(m, minaAI, kaardipakk);
                } else {
                    raund(m, diilerAI, kaardipakk);
                }
            }
            raund(diiler, diilerAI, kaardipakk);

            int minuKaevaartus = Kaart.kaeVaartus(mina.getKasi());
            if (21 >= minuKaevaartus && minuKaevaartus >= Kaart.kaeVaartus(diiler.getKasi())) {
                System.out.println("You win!");
            } else {
                System.out.println("You lose :(");
            }
            System.out.println("uus mäng(Y/N)? ");
            Scanner kordus = new Scanner(System.in);
            if (Objects.equals(kordus.next(), "N"))
                break;
        }

    }

    //TODO: nimetada see meetod kuidagi targemalt
    public static int raund(Mangija mangija, Vastane ai, Kaardipakk kaardipakk) {
        while (Kaart.kaeVaartus(mangija.getKasi()) <= 21) {
            char tegevus = ai.tegevus(mangija.getKasi());
            if (tegevus == 'H') {
                mangija.lisaKaart(kaardipakk.jagamine());
                System.out.println(mangija.getKasi());
            } else break;
        }
        return Kaart.kaeVaartus(mangija.getKasi());
    }

    public static void jagamised(ArrayList<Mangija> md, Kaardipakk k) {
        for (Mangija m : md)
            for (int i = 0; i < 2; i++)
                m.lisaKaart(k.jagamine());
    }

    public static void abi() {
        File juhised = new File("Juhised.txt");
        try {
            Scanner scan = new Scanner(juhised, "utf-8");
            while (scan.hasNextLine()) {
                System.out.println(scan.nextLine());
            }
            scan.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Juhiste faili ei leitud. Parandage oma installatsioon.");
        }
    }

    public static String[][] sätted(String[][] settings) {
        System.out.println("Redigeeri sätteid\n\n" +
                "Praegused sätted\nMängijaid: 2\tRaha: 1000\tRaskustase: noob");
        Scanner edit;
        Scanner mKogus = null;
        Scanner rKogus = null;
        Scanner dKogus = null;
        label:
        while (true) {
            System.out.println("Edit... (Mängijad/Raha/Raskus/Tagasi)");
            edit = new Scanner(System.in);
            String token = edit.next();
            switch (token) {
                case "Mängijad" -> {
                    mKogus = new Scanner(System.in);
                    String subToken1 = mKogus.next();
                    settings[0][1] = subToken1;
                }
                case "Raha" -> {
                    rKogus = new Scanner(System.in);
                    String subToken2 = rKogus.next();
                    settings[1][1] = subToken2;
                }
                case "Raskus" -> {
                    dKogus = new Scanner(System.in);
                    String subToken3 = dKogus.next();
                    settings[2][1] = subToken3;
                }
                case "Tagasi" -> {
                    break label;
                }
                default -> {
                    System.out.println("Sisesta normaalne vastus.");
                }
            }
        }
        edit.close();
        assert mKogus != null;
        mKogus.close();
        assert rKogus != null;
        rKogus.close();
        assert dKogus != null;
        dKogus.close();
        return settings;
    }
}