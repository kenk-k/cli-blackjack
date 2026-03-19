import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.lang.Thread;

public class Main {
    public static Scanner input = new Scanner(System.in);
    static void main(String[] args) throws InterruptedException {
        //Sätete massiiv
        String[][] mänguSätted = {{"players", "2"}, {"start", "1000"}, {"diff", "lihtne"}};
        System.out.println("Tere tulemast kasiinosse.\n");
        //Mängu menüü loop
        label:
        while (true) {
            System.out.println("Vali tegevus:\tSätted (S)\tAbi (A)\tMängima (M)");
            String token = input.next();
            switch (token) {
                case "S":
                    //Viib sätete meetodisse, kus saab sätteid muuta
                    mänguSätted = sätted(mänguSätted);
                    break;
                case "A":
                    //Väljastab juhiste teksti
                    abi();
                    break;
                case "M":
                    //breakib menüü loopi ja läheb edasi mängu
                    break label;
                default:
                    System.out.println("Sisesta sobiv valik.");
                    break;
            }
        }
        Kaardipakk kaardipakk = new Kaardipakk(3);
        int kaardipakkAlgSuurus = kaardipakk.suurus();
        Mangija diiler = new Mangija("Diiler", Integer.MAX_VALUE);
        Mangija kasutaja = new Mangija("Kasutaja", Integer.parseInt(mänguSätted[1][1]));
        kaardipakk.segamine();
        Vastane diilerAI = new Diiler();
        //tegevuste jaoks mängija objekt

        //vastaste loomine ja mangijate listi tegemine.
        int vastasteKogusInt = Integer.parseInt(mänguSätted[0][1]);
        ArrayList<Mangija> mangijad = new ArrayList<>();
        mangijad.add(diiler);
        //Tekitab soovitud arvu mängijaid ning lisab need massiivi
        for (int i = 1; i <= vastasteKogusInt; i++) {
            Mangija lisatav = new Mangija("Vastane " + i, Integer.parseInt(mänguSätted[1][1]));
            mangijad.add(lisatav);
        }
        mangijad.add(kasutaja);
        Vastane Kasutaja = new Kasutaja();
        Vastane vastasteAI;
        //lisab vastase loogika vastavalt kasutaja valikule
        if (mänguSätted[2][1].equals("lihtne")) {
            vastasteAI = new LihtneVastane();
        }
        else {
            vastasteAI = new Vastane();
        }


        //TODO: bet süsteem implementeerida
        while (true) {
            //kui kaardipakk on rohkem, kui pooltühi, segatakse kaarte
            if (kaardipakk.suurus() < (kaardipakkAlgSuurus / 2)) {
                kaardipakk = new Kaardipakk(3);
                System.out.println("Kaardipakki segatakse...");
                Thread.sleep(3000);
                kaardipakk.segamine();
            }
            jagamised(mangijad, kaardipakk);
            //Käiakse kõik mängijad läbi
            for (Mangija m : mangijad) {
                //Kui mängija diiler, näidatakse ainult pealmist kaarti, muidu näidatakse käsi
                //ette ning mängitakse raund ära
                if (m == diiler) {
                    System.out.println("diiler:");
                    System.out.println(diiler.getKasi().get(1));
                    Thread.sleep(200);
                } else if (m == kasutaja) {
                    raund(m, Kasutaja, kaardipakk);
                } else {
                    raund(m, vastasteAI, kaardipakk);
                }
            }
            //Diiler mängib viimasena
            raund(diiler, diilerAI, kaardipakk);

            int minuKaevaartus = Kaart.kaeVaartus(kasutaja.getKasi());
            //Kui kasutaja sai suurema skoori, kui diiler, võitis kasutaja
            if (21 >= minuKaevaartus && minuKaevaartus >= Kaart.kaeVaartus(diiler.getKasi())) {
                System.out.println("Võitsid!");
            } else {
                System.out.println("Kaotasid :(");
            }
            System.out.println("uus mäng(Y/N)? ");
            String uusMäng = input.next();
            if (Objects.equals(uusMäng, "Y")) {
                for (Mangija mangija: mangijad) {
                    mangija.tuhjendaKasi();
                    continue;
                }
            }
            else if (Objects.equals(uusMäng,"N"))
                input.close();
                break;
        }

    }


    /**
     *
     * @param mangija Mängija, kes mängib raundi (vastane või kasutaja)
     * @param ai Vastase loogika/kasutaja tegevused
     * @param kaardipakk Kaardipakk, kust kaardid võetakse
     */
    public static void raund(Mangija mangija, Vastane ai, Kaardipakk kaardipakk) throws InterruptedException {
        System.out.println("Mängija " + mangija.getNimi() + " kord");
        Thread.sleep(1000);
        while (Kaart.kaeVaartus(mangija.getKasi()) <= 21) {
            System.out.println(mangija.getKasi());
            char tegevus = ai.tegevus(mangija.getKasi());
            if (tegevus == 'H') {
                mangija.lisaKaart(kaardipakk.jagamine());
            } else if (tegevus =='D') {
                //TODO: siia panused
                mangija.lisaKaart(kaardipakk.jagamine());
                break;
            } else {
                break;
            }
        }
        System.out.println(mangija.getNimi() + " käsi:\n"+
                mangija.getKasi()+"\nMängija punktid: "+
                Kaart.kaeVaartus(mangija.getKasi()));
    }
    /**
     * Jagab kõigile mängijatele kätte 2 kaarti.
     */
    public static void jagamised(ArrayList<Mangija> md, Kaardipakk k) {
        for (Mangija m : md)
            for (int i = 0; i < 2; i++)
                m.lisaKaart(k.jagamine());
    }

    /**
     * Väljastab ekraanile abistava teksti.
     */
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

    /**
     * Küsib kasutajalt soovitud sätteid ning muudab neid.
     * @param settings Algsete sätete massiiv
     * @return Muudetud sätete massiiv
     */
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
