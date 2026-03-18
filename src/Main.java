import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        String[][] defSätted = {{"players", "2"},{"start", "1000"},{"diff", "noob"}};
        while (true) {
            System.out.println("Tere tulemast kasiinosse.\n\n" +
                    "Vali tegevus:\tSätted (S)\tAbi (A)\tMängima (M)");
            Scanner valik = new Scanner (System.in);
            if (valik.toString().equals("S"))
                sätted(defSätted);
            else if (valik.toString().equals("A"))
                abi();
            else if (valik.toString().equals("M"))
                break;
            else
                System.out.println("Sisesta sobiv valik.");
        }

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
            }
            else {
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
        System.out.println("Edit... (Mängijad/Raha/Raskus/Tagasi");
        Scanner edit = new Scanner(System.in);
        if (edit.toString().equals("Mängijad"))

    }


}
