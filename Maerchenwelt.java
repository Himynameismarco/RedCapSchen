import java.util.Random;

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
        // Hier erstellen wir jetzt die Karte (das Waldstück) mit einer Breite x und Höhe y
        VerwunschenerWald[][] karte = new VerwunschenerWald[x][y];
        System.out.println("Eine Karte 'Verwunschener Wald["+x+"]["+y+"]' wurde erstellt.");
        Position startpunktr = new Position(0,0);
        rotkaeppchen = new Rotkaeppchen(startpunktr);
        karte[0][0] = rotkaeppchen; // Rotkäppchen wird auf die Karte gesetzt.
        //System.out.println("Karte:"+karte);
        // Jetzt: Zufallsstandpunkt von Oma festlegen
        int max = 8;
        int min = 1;
        int rangeo = (max-min) + 1; // Weil nicht bei 0 beginnend
        int ox = (int)(Math.random() * rangeo) + min;
        int oy = (int)(Math.random() * rangeo) + min;
        Position startpunkto = new Position(ox, oy);
        System.out.println("Oma Startpunkt: "+ox+" "+oy);
        oma = new Oma(startpunkto);
        karte[ox][oy] = oma;
        // Jetzt auch Bäume erstellen, diesmal über einen nested for-loop auf Karte setzen:
        // Also nochmal: Karte ist VerwunschenerWald[x][y]
        // Mal schauen, ob das klappt.
        int baumcount = 0; // als Test, um meine Bäume zu zählen --> So bin ich drauf gekommen, dass ich immer baumAnzahlxbaumAnzahl und nicht baumAnzahl Bäume erstellt habe.
        System.out.println("So viele Bäume sollen erstellt werden: "+baumAnzahl);
        for (int zaehler1 = 0; zaehler1 < baumAnzahl; zaehler1++) {
            if (baumcount >= baumAnzahl) {
                System.out.println("Genug Bäume erstellt!");
                break;
            }
            for (int zaehler2 = 0; zaehler2 < baumAnzahl; zaehler2++) {
                // Hier sollen jetzt meine x und y Werte für den Baum erstellt werden.
                int bx = (int) (Math.random() * x); //bezieht sich auf meine x Variable mit Gültigkeit über die gesamte Methode
                int by = (int) (Math.random() * y); //bezieht sich auf meine y Variable mit Gültigkeit über die gesamte Methode
                // Daraus ergibt sich dann die Position.
                Position positionb = new Position(bx, by);
                System.out.println("Baumcount: " + baumcount);
                if (!(karte[bx][by] == null)) {
                    System.out.println("Ein Baum wäre fast an Omas oder Rotkaeppchens Position x = " + bx + " , y = " + by + " erstellt worden!");
                    zaehler1--;
                    zaehler2--;
                }
                if (baumcount >= baumAnzahl) {
                    System.out.println("Genug Bäume erstellt!");
                    break;
                } else {
                    System.out.println("Ein Baum wird an Position x = " + bx + " , y = " + by + " hinzugefuegt.");
                    Baum baum = new Baum(positionb);
                    karte[bx][by] = baum;
                    baumcount++;
                }
            }
        }
        System.out.println("So viele Bäume wurden erstellt: "+baumcount);
        //Ich will eine Methode zum Printen der Karte bauen
        for (int zaehler1 = 0; zaehler1 < x; zaehler1++) {
            for (int zaehler2 = 0; zaehler2 < y; zaehler2++) {
                if (!(karte[zaehler1][zaehler2] == null)) {
                    System.out.printf("%5s ", karte[zaehler1][zaehler2].getName());
                }
                else {
                    System.out.printf("%5s ", karte[zaehler1][zaehler2]);
                }
            }
            System.out.println();
        }
        // an dieser Stelle habe ich gelernt: Wenn ein Baum an der Stelle erstellt wird, an der Oma erstellt wurde, überschreibt er Oma!!
        System.out.println("Ok, fertig: Es wurde eine Maerchenwelt erschaffen! :) ");
    }

    // Getter
    public VerwunschenerWald[][] getKarte() {
        return karte;
    }

    public Rotkaeppchen getRotkaeppchen() {
        return rotkaeppchen;
    }
    public Oma getOma() {
        return oma;
    }
}