public class Main {
    static void main() {
        Kaardipakk kaardipakk = new Kaardipakk();
        String[] mastid = new String[] {"Poti","Ärtu", "Risti", "Ruutu"};
        for (String mast: mastid) {
            for (int j = 2; j < 11; j++) {
                kaardipakk.lisamine(new Kaart(mast, Integer.toString(j),j));
            }

        }
    }

}
