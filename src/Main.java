import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    //Üks scanner terve programmi jaoks
    public static Scanner input = new Scanner(System.in);

    static void main(String[] args) throws InterruptedException {
        //Sätete massiiv: vastaste arv, algne rahasumma igal mängijal, vastaste raskusaste,
        // kaardipakkide arv
        String[] mänguSätted = {"2", "1000", "lihtne","3"};
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
        Mangija kasutaja = new Mangija("Kasutaja", Integer.parseInt(mänguSätted[1]));
        kaardipakk.segamine();
        Vastane diilerAI = new Diiler();
        //tegevuste jaoks mängija objekt

        //vastaste loomine ja mangijate listi tegemine.
        int vastasteKogusInt = Integer.parseInt(mänguSätted[0]);
        ArrayList<Mangija> mangijad = new ArrayList<>();
        mangijad.add(diiler);
        //Tekitab soovitud arvu mängijaid ning lisab need massiivi
        for (int i = 1; i <= vastasteKogusInt; i++) {
            Mangija lisatav = new Mangija("Vastane " + i, Integer.parseInt(mänguSätted[1]));
            mangijad.add(lisatav);
        }
        mangijad.add(kasutaja);
        Vastane Kasutaja = new Kasutaja();
        Vastane vastasteAI;
        //lisab vastase loogika vastavalt kasutaja valikule
        if (mänguSätted[2].equals("lihtne")) {
            vastasteAI = new LihtneVastane();
        }
        else {
            vastasteAI = new Vastane();
        }


        while (true) {
            //Küsib panust ja koostab panuste HashMapi
            System.out.println("Kui suure raha peale mängid?");
            double panuseSuurus = input.nextDouble();
            if (!kasutaja.panusta(panuseSuurus)) {
                System.out.println("Oled liiga vaene, et sellise raha peale mängida.");
                continue;
            }
            HashMap<Mangija, Double> panused = panusteLoomine(mangijad, panuseSuurus);


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
                    raund(m, Kasutaja, kaardipakk,panused);
                } else {
                    raund(m, vastasteAI, kaardipakk,panused);
                }
            }
            //Diiler mängib viimasena
            raund(diiler, diilerAI, kaardipakk,panused);

            int diileriKaevaartus = Kaart.kaeVaartus(diiler.getKasi());
            //Muudab raha panuste suurusele ja raundi tulemusele vastavalt.
            panusteRealiseerimine(panused, diileriKaevaartus);

            System.out.println("Mängijate rahahulgad:");
            for (Mangija mangija: mangijad) {
                if (!mangija.getNimi().equals("Diiler"))
                    System.out.println(mangija.getNimi() + ": " + mangija.getRahahulk());
            }
            if (kasutaja.getRahahulk() == 0) {
                System.out.println("Oled vaene, pead kasiinost lahkuma");
                input.close();
                break;
            }
            System.out.println("uus mäng(Y/N)? ");

            String uusMäng = input.next();

            if (Objects.equals(uusMäng, "Y")) {
                for (Mangija mangija: mangijad) {
                    mangija.tuhjendaKasi();

                }
            }
            else if (Objects.equals(uusMäng,"N")) {
                input.close();
                break;
            }
        }

    }


    /**
     *
     * @param mangija Mängija, kes mängib raundi (vastane või kasutaja)
     * @param ai Vastase loogika/kasutaja tegevused
     * @param kaardipakk Kaardipakk, kust kaardid võetakse
     */
    public static void raund(Mangija mangija, Vastane ai,
                             Kaardipakk kaardipakk,
                             HashMap<Mangija, Double> panused) throws InterruptedException {
        System.out.println("Mängija " + mangija.getNimi() + " kord");
        Thread.sleep(1000);
        while (Kaart.kaeVaartus(mangija.getKasi()) <= 21) {
            System.out.println(mangija.getKasi());
            char tegevus = ai.tegevus(mangija.getKasi());
            if (tegevus == 'H') {
                mangija.lisaKaart(kaardipakk.jagamine());
            } else if (tegevus =='D') {
                if (mangija.panusta(panused.get(mangija))) {
                    panused.put(mangija, panused.get(mangija) * 2);
                    mangija.lisaKaart(kaardipakk.jagamine());
                    break;
                } else {
                    System.out.println("Oled liiga vaene, tee teine liigutus");
                }
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
     * @param algsätted Algsete sätete massiiv
     * @return Muudetud sätete massiiv
     */
    public static String[] sätted(String[] algsätted) {
        System.out.println("Redigeeri sätteid\n");

        System.out.println("Edit... (Mängijad/Raha/Raskus/Tagasi)");
        säteteLoop:
        while (true) {
            System.out.println("Praegused sätted\n(Mängijad): " +
                    algsätted[0]+ "\t(Raha): " +
                    algsätted[1] +"\t(Raskus): " +
                    algsätted[2] + "\t (Kaardipakid): " +
                    algsätted[3]);
            String token = input.next();
            switch (token) {
                case "Mängijad" -> {
                    System.out.println("Mitme mängijaga soovite mängida?");
                    String subToken = input.next();
                    algsätted[0] = subToken;
                }
                case "Raha" -> {
                    System.out.println("Kui suure rahahulgaga tahate mängida?");
                    String subToken = input.next();
                    algsätted[1] = subToken;
                }
                case "Raskus" -> {
                    System.out.println("Kui raskete vastastega tahate mängida? (lihtne/raske)");
                    String subToken = input.next();
                    algsätted[2] = subToken;
                }
                case "Kaardipakid" -> {
                    System.out.println("Mitme kaardipakiga tahate mängida?");
                    String subToken = input.next();
                    algsätted[3] = subToken;
                }

                case "Tagasi" -> {
                    break säteteLoop;
                }
                default -> {
                    System.out.println("Sisesta normaalne vastus.");
                }
            }
        }
        return algsätted;
    }

    /**
     * Loob panuse põhjal HashMapi ning võtab kõikidelt mängijatelt selle summa ära.
      * @param mangijad Mängus olevad mängijad
     * @param panus Panuse suurus
     * @return HashMap mängijatest ja nende panustest.
     */
    public static HashMap<Mangija, Double> panusteLoomine(ArrayList<Mangija> mangijad, double panus) {
        HashMap<Mangija, Double> panused = new HashMap<>();
        List<Mangija> eemaldatavad = new ArrayList<>();
        for (Mangija m : mangijad) {
            if (!m.getNimi().equals("Kasutaja")) {
                if (m.panusta(panus))
                    panused.put(m, panus);
                else {
                    //Eemaldab mängust need, kes panustada ei saa.
                    System.out.println(m.getNimi() + " ei ole rahaliselt võimekas, mängust välja langenud.");
                    eemaldatavad.add(m);
                }
            }
            else {
                panused.put(m, panus);
            }
        }
        for (Mangija m: eemaldatavad) {
            for (Mangija m2: mangijad) {
                if (m.equals(m2)) {
                    mangijad.remove(m);
                }
            }
        }
        return panused;
    }

    /**
     * Kontrollib iga mängija kätt ja jagab panused tagasi sõltuvalt mängija käest
     * @param mangijad Mängijate HashMap
     * @param diileriKasi Diileri käe väärtus
     */
    public static void panusteRealiseerimine(HashMap<Mangija, Double> mangijad, int diileriKasi) {
        for (Map.Entry<Mangija, Double> panustamine : mangijad.entrySet()) {
            Mangija m = panustamine.getKey();
            double panus = panustamine.getValue();

            if (Objects.equals(m.getNimi(), "Diiler")) continue;

            boolean onKasutaja = Objects.equals(m.getNimi(), "Kasutaja");

            //Kontrollib iga mängija käe ja annab raha vastavalt.
            int praeguneKasi = Kaart.kaeVaartus(m.getKasi());
            if (praeguneKasi > diileriKasi && praeguneKasi == 21 && m.getKasi().size() == 2) {
                m.setRahahulk(m.getRahahulk() + panus * 2.5);
                if (onKasutaja)
                    System.out.println("BLACKJACK!!! Said 2.5 korda panuse tagasi!");
            } else if (praeguneKasi > diileriKasi && praeguneKasi <= 21) {
                m.setRahahulk(m.getRahahulk() + panus * 2);
                if (onKasutaja)
                    System.out.println("Võitsid! Said 2 korda panuse tagasi");
            } else if (praeguneKasi == diileriKasi && praeguneKasi <= 21) {
                m.setRahahulk(m.getRahahulk() + panus);
                if (onKasutaja)
                    System.out.println("Viik. Said panuse tagasi.");
            } else if (praeguneKasi <= 21 && diileriKasi > 21) {
                m.setRahahulk(m.getRahahulk() + panus * 2);
                if (onKasutaja)
                    System.out.println("Diiler bustis. Sa võitsid! Said 2 korda panuse tagasi");
            } else {
                if (onKasutaja)
                    System.out.println("Kaotasid. Jäid panusest ilma.");
            }
        }
    }
}

