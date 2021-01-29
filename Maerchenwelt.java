import java.util.Random;
import java.util.ArrayList;

class Maerchenwelt {
    private int x; //Breite Waldstück
    private int y; //Höhe Waldstück
    // Das heißt 'karte' ist ein zweidimensionaler Array des Typs VerwunschenerWald (enthält also nur solche Werte) - wird aber noch nicht initialisiert
    private VerwunschenerWald[][] karte;
    // würden wir jetzt schreiben karte = new VerwunschenerWald[2][4], dann gäbe es zwei Verwunschener Wald Werte
    // in der ersten Dimension und vier in der zweiten.
    private Oma oma;
    private Rotkaeppchen rotkaeppchen;

    // Konstruktor mit Initialisierung des Waldes und Positionen aller Figuren innerhalb des Waldes
    public Maerchenwelt(int x, int y, int gefahrenAnzahl, int baumAnzahl) {
        this.x = x;
        this.y = y;
        // Karte erstellen (Waldstück mit einer Breite x und Höhe y)
        this.karte = new VerwunschenerWald[x][y];
        //System.out.println("Eine Karte 'Verwunschener Wald["+x+"]["+y+"]' wurde erstellt.");
        // Exceptions
        // Zu gefährlich:
        int summeGefahrenBaeume = gefahrenAnzahl + baumAnzahl;
        int felderAnzahl = x * y;
        if (summeGefahrenBaeume > (felderAnzahl - 3)) {
            throw new IllegalArgumentException("Reduzieren Sie die Anzahl der Gefahren und Baeume.");
        }
        // Wald zu klein
        if (x < 10 || y < 10) {
            throw new IllegalArgumentException("Vergroessern Sie den verwunschenen Wald.");
        }

        // Rotkaeppchen erstellen
        Position startpunktr = new Position(0,0);
        this.rotkaeppchen = new Rotkaeppchen(startpunktr);
        this.karte[0][0] = this.rotkaeppchen; // Rotkäppchen wird auf die Karte gesetzt.

        // Oma erstellen
        // Erst: Zufallsstandpunkt von Oma festlegen
        int max = 8;
        int min = 1;
        int rangeo = (max-min) + 1; // Weil nicht bei 0 beginnend
        int ox = (int)(Math.random() * rangeo) + min;
        int oy = (int)(Math.random() * rangeo) + min;
        Position startpunkto = new Position(ox, oy);
        System.out.println("Oma lebt in der Maerchenwelt: Sie chillt at: "+ox+" "+oy);
        this.oma = new Oma(startpunkto);
        this.karte[ox][oy] = this.oma;

        // Ziel erstellen
        Position ziel = new Position(this.oma.getPosition().getX(),this.oma.getPosition().getY());

        // Bäume erstellen (über die Methode baeumeErstellen() auf Karte setzen) und in die Karte einfuegen!
        this.karte = this.baeumeErstellen(baumAnzahl);

        System.out.println("Ok, fertig: Es wurde eine Maerchenwelt erschaffen! :) \nKarte: ");
        // Karte ausgeben
        this.printWald();

        // Weg finden
        this.wegFinden(ziel);

    } // Ende Riesenkonstruktor Maerchenwelt

    // Meine Methode zum Baeume erstellen:
    public VerwunschenerWald[][] baeumeErstellen(int baumAnzahl) {
        // Diese Funktion wird angewendet auf ein VerwunschenerWald[][] Objekt (karte) und wird ein verändertes Objekt des Typs zurückgeben.
        //public VerwunschenerWald[][] baeumeErstellen() {
        int baumcount = 0; // als Test, um meine Bäume zu zählen --> So bin ich drauf gekommen, dass ich immer baumAnzahlxbaumAnzahl und nicht baumAnzahl Bäume erstellt habe.
        System.out.println("So viele Bäume sollen erstellt werden: "+baumAnzahl);
        // Nested for-loop um auf Arrays zuzugreifen
        for (int zaehler1 = 0; zaehler1 < baumAnzahl; zaehler1++) {
            if (baumcount >= baumAnzahl) {
                //System.out.println("Genug Bäume erstellt!");
                break;
            }
            for (int zaehler2 = 0; zaehler2 < baumAnzahl; zaehler2++) {
                // Hier sollen jetzt meine x und y Werte für den Baum erstellt werden.
                int bx = (int) (Math.random() * x); //bezieht sich auf meine x Variable mit Gültigkeit über die gesamte Methode
                int by = (int) (Math.random() * y); //bezieht sich auf meine y Variable mit Gültigkeit über die gesamte Methode
                // Daraus ergibt sich dann die Position.
                Position positionb = new Position(bx, by);
                //System.out.println("Baumcount: " + baumcount);
                if (!(this.karte[bx][by] == null)) {
                    //System.out.println("Ein Baum wäre fast an Omas oder Rotkaeppchens oder die Ziel Position x = " + bx + " , y = " + by + " erstellt worden!");
                    zaehler1--;
                    zaehler2--;
                } else {
                    //System.out.println("Ein Baum wird an Position x = " + bx + " , y = " + by + " hinzugefuegt.");
                    Baum baum = new Baum(positionb);
                    this.karte[bx][by] = baum;
                    baumcount++;
                }
                if (baumcount >= baumAnzahl) {
                    //System.out.println("Genug Bäume erstellt!");
                    break;
                }
            }
        }
        System.out.println("So viele Bäume wurden erstellt: "+baumcount);
        return this.karte;
    }

    // Getter
    public VerwunschenerWald[][] getKarte() {
        return this.karte;
    }
    public Rotkaeppchen getRotkaeppchen() {
        return this.rotkaeppchen;
    }
    public Oma getOma() {
        return this.oma;
    }

    // Meine selbstgebaute printMethode.
    public void printKarte() {
        for (int zaehler1 = 0; zaehler1 < x; zaehler1++) {
            for (int zaehler2 = 0; zaehler2 < y; zaehler2++) {
                if (!(this.karte[zaehler1][zaehler2] == null)) {
                    System.out.printf("%5s ", this.karte[zaehler1][zaehler2].getName());
                } else {
                    System.out.printf("%5s ", this.karte[zaehler1][zaehler2]);
                }
            }
            System.out.println();
        }
    }

    // Die Printmethode aus dem Anhang
    public void printWald(){
    // Rahmen: linke obere Ecke
        System.out.print("+");
    // Rahmen: erste Zeile
        for(int i = 0; i < x; i++){
            System.out.print("-");
        }
    // Rahmen: rechte obere Ecke
        System.out.println("+");
        for(int j = 0; j < y; j++) {
    // Rahmen: linker Rand
            System.out.print("|");
    // Die eigentliche Karte
            for(int i = 0; i < x; i++) {
                if (karte[i][j] != null ) {
                    System.out.print(karte[i][j].getName());
                }else{
                    System.out.print(" ");
                }
            }
    // Rahmen: rechter Rand
            System.out.println("|");
        }
    // Rahmen: linke untere Ecke
        System.out.print("+");
    // Rahmen: letzte Zeile
        for(int i = 0; i < x; i++){
            System.out.print("-");
        }
    // Rahmen: rechte untere Ecke
        System.out.println("+");
    }


    // Bewegungen
    public ArrayList<Position> wegFinden(Position ziel) {
        // Ist Oma die Oma von oben?
        //System.out.println("Oma x: "+oma.getPosition().getX()+", Oma y: "+oma.getPosition().getY());
        // Warum ist meine Karte leer? // Was ist bei der Karte anders als bei Rotkaeppchen und Oma?
        //System.out.println("Karte in wegFinden Methode: "+karte);
        //this.printKarte();
        ArrayList<Position> positionen = new ArrayList<Position>();
        final int anzahlRichtungen = 4; // ich denke, schraeg werden wir nicht mehr hinzufuegen, daher final.
        //TODO Bedingung auf 501 setzen
        for (int i = 0; i < 501; i++) {
            // Wenn Rotkaeppchen nicht bei Oma
            if (!(this.rotkaeppchen.getPosition().equals(this.oma.getPosition()))) {
                int aktion = (int) (Math.random() * anzahlRichtungen); //welche Methode wird gewählt
                int rx = this.rotkaeppchen.getPosition().getX(); //X-Standpunkt von Rotkaeppchen aktuell
                int ry = this.rotkaeppchen.getPosition().getY(); //Y-Standpunkt von Rotkaeppchen aktuell
                // Wir haben haeufig Switches verwendet, daher habe ich mich dafuer entschieden
                switch (aktion) {
                    case 0:
                        //System.out.println("Rotkaeppchen soll hoch gehen.");
                        // Check, ob Rotkaeppchen aus der Karte fallen wuerde oder ob auf der Position ein Baum ist
                        if (((ry - 1) < 0) || (this.karte[rx][ry - 1] instanceof Baum)) {
                            //System.out.println("Rotkaeppchen waere von der Karte gefallen oder auf einen Baum gestossen, wenn es hoch gegangen waere.");
                        } else {
                            System.out.println("Rotkaeppchen geht hoch.");
                            // Damit Rotkaeppchen nicht zweimal auf der Karte auftaucht, setzen wir seine aktuelle Position auf null
                            this.karte[this.rotkaeppchen.getPosition().getX()][this.rotkaeppchen.getPosition().getY()] = null;
                            // Dann macht Rotkaeppchen seine Bewegung
                            this.rotkaeppchen.geheHoch();
                            //System.out.println("Rotkaeppchen X: "+this.rotkaeppchen.getPosition().getX()+" Y: "+this.rotkaeppchen.getPosition().getY());
                            // Jetzt setzen wir Rotkaeppchen auf die Position auf der Karte (ich denke, es waere besser, das direkt ueber
                            // Rotkaeppchens Bewegung zu loesen, aber leider schaffe ich das gerade noch nicht.)
                            this.karte[this.rotkaeppchen.getPosition().getX()][this.rotkaeppchen.getPosition().getY()] = this.rotkaeppchen;
                            // Andere Cases analog
                        }
                        positionen.add(this.rotkaeppchen.getPosition());
                        break;
                    case 1:
                        //System.out.println("Rotkaeppchen soll runter gehen.");
                        if (((ry + 1) > y - 1) || (this.karte[rx][ry + 1] instanceof Baum)) {
                            //System.out.println("Rotkaeppchen waere von der Karte gefallen oder auf einen Baum gestossen, wenn es runter gegangen waere.");
                        } else {
                            System.out.println("Rotkaeppchen geht runter.");
                            this.karte[this.rotkaeppchen.getPosition().getX()][this.rotkaeppchen.getPosition().getY()] = null;
                            this.rotkaeppchen.geheRunter();
                            //System.out.println("Rotkaeppchen X: "+this.rotkaeppchen.getPosition().getX()+" Y: "+this.rotkaeppchen.getPosition().getY());
                            this.karte[this.rotkaeppchen.getPosition().getX()][this.rotkaeppchen.getPosition().getY()] = this.rotkaeppchen;
                        }
                        positionen.add(this.rotkaeppchen.getPosition());
                        break;
                    case 2:
                        //System.out.println("Rotkaeppchen soll links gehen.");
                        if (((rx - 1) < 0) || (this.karte[rx - 1][ry] instanceof Baum)) {
                            //System.out.println("Rotkaeppchen waere von der Karte gefallen oder auf einen Baum gestossen, wenn es links gegangen waere.");
                        } else {
                            System.out.println("Rotkaeppchen geht links.");
                            this.karte[this.rotkaeppchen.getPosition().getX()][this.rotkaeppchen.getPosition().getY()] = null;
                            this.rotkaeppchen.geheLinks();
                            //System.out.println("Rotkaeppchen X: "+this.rotkaeppchen.getPosition().getX()+" Y: "+this.rotkaeppchen.getPosition().getY());
                            this.karte[this.rotkaeppchen.getPosition().getX()][this.rotkaeppchen.getPosition().getY()] = this.rotkaeppchen;
                        }
                        positionen.add(this.rotkaeppchen.getPosition());
                        break;
                    case 3:
                        //System.out.println("Rotkaeppchen soll rechts gehen.");
                        if (((rx + 1) > x - 1) || (this.karte[rx + 1][ry] instanceof Baum)) {
                            //System.out.println("Rotkaeppchen waere von der Karte gefallen oder auf einen Baum gestossen, wenn es rechts gegangen waere.");
                        } else {
                            System.out.println("Rotkaeppchen geht rechts.");
                            this.karte[this.rotkaeppchen.getPosition().getX()][this.rotkaeppchen.getPosition().getY()] = null;
                            this.rotkaeppchen.geheRechts();
                            //System.out.println("Rotkaeppchen X: "+this.rotkaeppchen.getPosition().getX()+" Y: "+this.rotkaeppchen.getPosition().getY());
                            this.karte[this.rotkaeppchen.getPosition().getX()][this.rotkaeppchen.getPosition().getY()] = this.rotkaeppchen;
                        }
                        positionen.add(this.rotkaeppchen.getPosition());
                        break;
                    default:
                        System.out.println("Sollte nie geprinted werden. Default statement in Methode wegFinden() aus Klasse Maerchenwelt.");
                        break;
                }
            } else {
                System.out.println("Rotkaeppchen ist bei Oma angekommen! :) ");
                break;
            }

        }
        this.printWald();
        System.out.println(positionen);
        return positionen;
    }

    public static void main(String[] args) {

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
        Maerchenwelt mw = new Maerchenwelt(15,15,40,20);

        // TEST BEWEGUNGEN
        //Position ziel = new Position(5,5);
        //mw.wegFinden(ziel);
        //mw.printKarte();

        //Ich will eine Methode zum Printen der Karte bauen - aber ich kriegs nicht hin... wann anders.
        //public void printKarte(VerwunschenerWald[][] mw) {}
    }
}
