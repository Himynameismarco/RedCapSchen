public class Maerchen {
    public static void main(String[] args) {

        Maerchenwelt mw = new Maerchenwelt(40,10,0,20);
        mw.start();


        // ALTE TESTS (weil ich zuvor schon immer main-Methoden gemacht hatte, um meinen Code zu testen).

        // TEST POSITION
        //Position einseins = new Position(1, 1);
        //Position zweizwei = new Position(2, 2);
        //Position zweiteseinseins = new Position(1, 1);

        // TEST EQUALS
        //System.out.println(einseins);
        //System.out.println(zweizwei);
        //boolean tf = zweiteseinseins.equals(einseins);
        //System.out.println("Equals-Test: "+tf);

        // TEST ROTKAEPPCHEN
        //Rotkaeppchen r = new Rotkaeppchen(zweizwei);
        //System.out.println("Rotkaeppchens Position:");
        //r.getPosition();
        //System.out.println("OK, Kaeppchen, dann geh mal zweimal hoch und zweimal rechts:");
        //r.geheHoch();
        //r.geheHoch();
        //r.geheRechts();
        //r.geheRechts();
        //System.out.println("Rotkaeppchens Position:");
        //r.getPosition();
        //r.istNochLebendig();
        //r.gesundheitVerringern(12);
        //r.istNochLebendig();

        // TEST MAERCHENWELT
        //Maerchenwelt mw = new Maerchenwelt(15,15,40,20);

        // TEST BEWEGUNGEN
        //Position ziel = new Position(5,5);
        //mw.wegFinden(ziel);
        //mw.printKarte();

        //Ich will eine Methode zum Printen der Karte bauen - aber ich kriegs nicht hin... wann anders.
        //public void printKarte(VerwunschenerWald[][] mw) {}
    }
}