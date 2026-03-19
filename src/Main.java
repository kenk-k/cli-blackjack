import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static Scanner input = new Scanner(System.in);
    static void main(String[] args) {
        //Mängu alguse menüü.
        String[][] mänguSätted = {{"players", "2"}, {"start", "1000"}, {"diff", "lihtne"}};
        System.out.println("Tere tulemast kasiinosse.\n");
        label:
        while (true) {
            System.out.println("Vali tegevus:\tSätted (S)\tAbi (A)\tMängima (M)");
            String token = input.next();
            switch (token) {
                case "S":
                    mänguSätted = sätted(mänguSätted);
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
        Kaardipakk kaardipakk = new Kaardipakk(1);
        Mangija diiler = new Mangija("Diiler", Integer.MAX_VALUE);
        Mangija mina = new Mangija("Mina", Integer.parseInt(mänguSätted[1][1]));
        kaardipakk.segamine();
        Vastane diilerAI = new Diiler();
        //tegevuste jaoks mängija objekt

        //vastaste loomine ja mangijate listi tegemine.
        //Scanner vastasteKogus = new Scanner(System.in);
        int vastasteKogusInt = Integer.parseInt(mänguSätted[0][1]);
        ArrayList<Mangija> mangijad = new ArrayList<>();
        mangijad.add(diiler);
        for (int i = 1; i <= vastasteKogusInt; i++) {
            Mangija lisatav = new Mangija("Vastane " + i, Integer.parseInt(mänguSätted[1][1]));
            mangijad.add(lisatav);
        }
        mangijad.add(mina);
        Vastane minaAI = new Mina();
        Vastane vastasteAI;
        if (mänguSätted[2][1].equals("lihtne")) {
            vastasteAI = new SuvalineTampija();
        }
        else {
            vastasteAI = new Vastane();
        }


        //TODO: bet süsteem implementeerida
        mäng:
        while (true) {
            if (kaardipakk.suurus() < 27) {
                kaardipakk = new Kaardipakk(1);
            }
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
                    raund(m, vastasteAI, kaardipakk);
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
            String uusMäng = input.next();
            if (Objects.equals(uusMäng, "Y")) {
                for (Mangija mangija: mangijad) {
                    mangija.tuhjendaKasi();
                }
            }
            else if (Objects.equals(uusMäng,"N"))
                input.close();
                break mäng;
        }

    }

    //TODO: nimetada see meetod kuidagi targemalt
    public static int raund(Mangija mangija, Vastane ai, Kaardipakk kaardipakk) {
        System.out.println("Mängija " + mangija.getNimi() + " kord");
        while (Kaart.kaeVaartus(mangija.getKasi()) <= 21) {
            System.out.println(mangija.getKasi());
            char tegevus = ai.tegevus(mangija.getKasi());
            if (tegevus == 'H') {
                mangija.lisaKaart(kaardipakk.jagamine());
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
            Scanner juhisedScan = new Scanner(juhised, "utf-8");
            while (juhisedScan.hasNextLine()) {
                System.out.println(juhisedScan.nextLine());
            }
            juhisedScan.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Juhiste faili ei leitud. Parandage oma installatsioon.");
        }
    }

    public static String[][] sätted(String[][] settings) {
        System.out.println("Redigeeri sätteid\n");

        System.out.println("Edit... (Mängijad/Raha/Raskus/Tagasi)");
        label:
        while (true) {
            System.out.println("Praegused sätted\n(Mängijad): " +
                    settings[0][1]+ "\t(Raha): " +
                    settings[1][1] +"\t(Raskus): " +
                    settings[2][1]);
            String token = input.next();
            switch (token) {
                case "Mängijad" -> {
                    System.out.println("Mitme mängijaga soovite mängida?");
                    String subToken1 = input.next();
                    settings[0][1] = subToken1;
                }
                case "Raha" -> {
                    System.out.println("Kui suure rahahulgaga tahate mängida?");
                    String subToken2 = input.next();
                    settings[1][1] = subToken2;
                }
                case "Raskus" -> {
                    System.out.println("Kui raskete vastastega tahate mängida? (lihtne/raske)");
                    String subToken3 = input.next();
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
        return settings;
    }


}
