public class Main {

    public static void main(String[] args) {

        Position einseins = new Position(1, 1);
        Position zweizwei = new Position(2, 2);
        Position zweiteseinseins = new Position(1, 1);

        //System.out.println(einseins);
        //System.out.println(zweizwei);
        //boolean tf = zweiteseinseins.equals(einseins);
        //System.out.println("Equals-Test: "+tf);

        Rotkaeppchen r = new Rotkaeppchen(zweizwei);
        System.out.println("Rotkaeppchens Position:");
        r.getPosition();
        System.out.println("OK, Kaeppchen, dann geh mal zweimal hoch und zweimal rechts:");
        r.geheHoch();
        r.geheHoch();
        r.geheRechts();
        r.geheRechts();
        System.out.println("Rotkaeppchens Position:");
        r.getPosition();
        r.istNochLebendig();
        r.gesundheitVerringern(12);
        r.istNochLebendig();
    }

}