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
    private Wolf wolf;
    private Position ziel;
    private Position startpunktr;
    private int zaehler;

    // Konstruktor mit Initialisierung des Waldes und Positionen aller Figuren innerhalb des Waldes
    public Maerchenwelt(int x, int y, int gefahrenAnzahl, int baumAnzahl) {
        this.x = x;
        this.y = y;
        // Karte erstellen (Waldstück mit einer Breite x und Höhe y)
        this.karte = new VerwunschenerWald[x][y];
        //System.out.println("Eine Karte 'Verwunschener Wald["+x+"]["+y+"]' wurde erstellt.");

        // Rotkaeppchen (und den Startpunkt fuer den Rueckweg) erstellen
        this.startpunktr = new Position(0,0);
        this.rotkaeppchen = new Rotkaeppchen(this.startpunktr);
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

        // Wolf erstellen
        int rangewx = x;
        int rangewy = y;
        int wx = (int)(Math.random() * rangewx);
        int wy = (int)(Math.random() * rangewy);
        // Sicherstellen, dass Wolf nicht an Omas oder Rotkaeppchen Position erstellt wird.
        while (!(this.karte[wx][wy] == null)) {
            System.out.println("Crazy, ein Wolf wäre fast an Omas oder Rotkaeppchens Position erstellt worden!");
            wx = (int)(Math.random() * rangewx);
            wy = (int)(Math.random() * rangewy);
        }
        Position startpunktw = new Position(wx, wy);
        System.out.println("Der Wolf... der Wolf ist da... WOOOOLFx:"+ox+" WOOOOLFy: "+oy);
        this.wolf = new Wolf(startpunktw);
        this.karte[wx][wy] = this.wolf;

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

        // Ziel erstellen
        this.ziel  = new Position(this.oma.getPosition().getX(),this.oma.getPosition().getY());

        // Bäume erstellen (ueber die Methode baeumeErstellen() auf Karte setzen) und in die Karte einfuegen!
        this.karte = this.baeumeErstellen(baumAnzahl);

        // Gefahren erstellen
        this.karte = this.gefahrenErstellen(gefahrenAnzahl);

        System.out.println("Ok, fertig: Es wurde eine Maerchenwelt erschaffen! :) \nSo sieht sie aus: ");
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
                // Sicherstellen, dass Baum nicht an bereits belegtem Element erstellt wird.
                if (!(this.karte[bx][by] == null)) {
                    //System.out.println("Ein Baum wäre fast an Omas, Rotkaeppchens oder des Wolfs Position x = " + bx + " , y = " + by + " erstellt worden!");
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

    // Meine Methode zum Gefahren erstellen (analog Baeume - meine Kommentare habe ich deswegen hier rausgenommen):
    public VerwunschenerWald[][] gefahrenErstellen(int gefahrenAnzahl) {
        int gefahrencount = 0;
        System.out.println("So viele Gefahren sollen erstellt werden: "+gefahrenAnzahl);
        for (int zaehler1 = 0; zaehler1 < gefahrenAnzahl; zaehler1++) {
            if (gefahrencount >= gefahrenAnzahl) {
                //System.out.println("Genug Gefahren erstellt!");
                break;
            }
            for (int zaehler2 = 0; zaehler2 < gefahrenAnzahl; zaehler2++) {
                int gx = (int) (Math.random() * x); //bezieht sich auf meine x Variable mit Gültigkeit über die gesamte Methode
                int gy = (int) (Math.random() * y); //bezieht sich auf meine y Variable mit Gültigkeit über die gesamte Methode
                Position positiong = new Position(gx, gy);
                // Sicherstellen, dass Baum nicht an bereits belegtem Element erstellt wird.
                if (!(this.karte[gx][gy] == null)) {
                    //System.out.println("Eine Gefahr wäre fast an Omas, Rotkaeppchens, eines Baumes oder des Wolfs Position x = " + bx + " , y = " + by + " erstellt worden!");
                    zaehler1--;
                    zaehler2--;
                } else {
                    //System.out.println("Eine Gefahr wird an Position x = " + bx + " , y = " + by + " hinzugefuegt.");
                    Gefahr gefahr = new Gefahr(positiong);
                    this.karte[gx][gy] = gefahr;
                    gefahrencount++;
                }
                if (gefahrencount >= gefahrenAnzahl) {
                    System.out.println("Genug Gefahren erstellt! Do not get tooooo dangerous, my dear Forest.");
                    break;
                }
            }
        }
        System.out.println("So viele Gefahren wurden erstellt: "+gefahrencount);
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
        ArrayList<Position> positionen = new ArrayList<Position>();
        final int anzahlRichtungen = 4; // ich denke, schraeg werden wir nicht mehr hinzufuegen, daher final.
        // Wir beginnen, den Weg zu finden.
        for (int i = 0; i < 501; i++) {
            // Wenn Rotkaeppchen nicht bei Oma gehen wir
            if (!(this.rotkaeppchen.getPosition().equals(this.ziel))) {
                int aktion = (int) (Math.random() * anzahlRichtungen); //welche Methode wird gewählt
                int rx = this.rotkaeppchen.getPosition().getX(); //X-Standpunkt von Rotkaeppchen aktuell
                int ry = this.rotkaeppchen.getPosition().getY(); //Y-Standpunkt von Rotkaeppchen aktuell
                // Wir haben haeufig Switches verwendet, daher habe ich mich dafuer entschieden
                switch (aktion) {
                    case 0:
                        //System.out.println("Rotkaeppchen soll hoch gehen.");
                        // Check, ob Rotkaeppchen aus der Karte fallen wuerde oder ob auf der Position ein Baum, eine Gefahr oder gar der Wolf ist (andere cases analog)
                        if (((ry - 1) < 0) || (this.karte[rx][ry - 1] instanceof Baum) || (this.karte[rx][ry - 1] instanceof Gefahr) || (this.karte[rx][ry - 1] instanceof Wolf)) {
                            if (((ry - 1) < 0) || (this.karte[rx][ry - 1] instanceof Baum) ) {
                                //System.out.println("Rotkaeppchen waere von der Karte gefallen oder auf einen Baum getroffen.");
                            } else {
                                System.out.println("Rotkaeppchen wollte hochgehen, doch traf auf eine(n): "+this.karte[rx][ry - 1].getName());
                                //System.out.println("this.karte[rx][ry - 1].getSchaden(): "+this.karte[rx][ry - 1].getSchaden());
                                this.rotkaeppchen.gesundheitVerringern(this.karte[rx][ry - 1].schaden);
                                System.out.println("How much Gesundheit do you have left, RedCapSchen? \nLP are at "+this.rotkaeppchen.getGesundheit()+", Commander!");
                                this.rotkaeppchen.istNochLebendig();
                            }
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
                        if (!(this.rotkaeppchen.istNochLebendig())) {
                            // Wenn Rotkaeppchen tot ist, werden einfach die Positionen zurueckgegeben
                            return positionen;
                        }
                        break;
                    case 1:
                        //System.out.println("Rotkaeppchen soll runter gehen.");
                        if (((ry + 1) > (y - 1)) || (this.karte[rx][ry + 1] instanceof Baum) || (this.karte[rx][ry + 1] instanceof Gefahr) || (this.karte[rx][ry + 1] instanceof Wolf)) {
                            if (((ry + 1) > (y - 1)) || (this.karte[rx][ry + 1] instanceof Baum) ) {
                            } else {
                                System.out.println("Rotkaeppchen wollte runtergehen, doch traf auf eine(n): "+this.karte[rx][ry + 1].getName());
                                this.rotkaeppchen.gesundheitVerringern(this.karte[rx][ry + 1].schaden);
                                System.out.println("How much Gesundheit do you have left, RedCapSchen? \nLP are at "+this.rotkaeppchen.getGesundheit()+", Commander!");
                                this.rotkaeppchen.istNochLebendig();
                            }
                        }else {
                            System.out.println("Rotkaeppchen geht runter.");
                            this.karte[this.rotkaeppchen.getPosition().getX()][this.rotkaeppchen.getPosition().getY()] = null;
                            this.rotkaeppchen.geheRunter();
                            this.karte[this.rotkaeppchen.getPosition().getX()][this.rotkaeppchen.getPosition().getY()] = this.rotkaeppchen;
                        }
                        positionen.add(this.rotkaeppchen.getPosition());
                        if (!(this.rotkaeppchen.istNochLebendig())) {
                            // Wenn Rotkaeppchen tot ist, werden einfach die Positionen zurueckgegeben
                            return positionen;
                        }
                        break;
                    case 2:
                        //System.out.println("Rotkaeppchen soll links gehen.");
                        if (((rx - 1) < 0) || (this.karte[rx - 1][ry] instanceof Baum) || (this.karte[rx - 1][ry] instanceof Gefahr) || (this.karte[rx - 1][ry] instanceof Wolf)) {
                            if (((rx - 1) < 0) || (this.karte[rx - 1][ry] instanceof Baum) ) {
                                //System.out.println("Rotkaeppchen waere von der Karte gefallen oder auf einen Baum getroffen.");
                            } else {
                                System.out.println("Rotkaeppchen wollte links gehen, doch traf auf eine(n): "+this.karte[rx - 1][ry].getName());
                                //System.out.println("this.karte[rx][ry - 1].getSchaden(): "+this.karte[rx][ry - 1].getSchaden());
                                this.rotkaeppchen.gesundheitVerringern(this.karte[rx - 1][ry].schaden);
                                System.out.println("How much Gesundheit do you have left, RedCapSchen? \nLP are at "+this.rotkaeppchen.getGesundheit()+", Commander!");
                                this.rotkaeppchen.istNochLebendig();
                            }
                        } else {
                            System.out.println("Rotkaeppchen geht links.");
                            this.karte[this.rotkaeppchen.getPosition().getX()][this.rotkaeppchen.getPosition().getY()] = null;
                            this.rotkaeppchen.geheLinks();
                            //System.out.println("Rotkaeppchen X: "+this.rotkaeppchen.getPosition().getX()+" Y: "+this.rotkaeppchen.getPosition().getY());
                            this.karte[this.rotkaeppchen.getPosition().getX()][this.rotkaeppchen.getPosition().getY()] = this.rotkaeppchen;
                        }
                        positionen.add(this.rotkaeppchen.getPosition());
                        if (!(this.rotkaeppchen.istNochLebendig())) {
                            // Wenn Rotkaeppchen tot ist, werden einfach die Positionen zurueckgegeben
                            return positionen;
                        }
                        break;
                    case 3:
                        //System.out.println("Rotkaeppchen soll rechts gehen.");
                        if (((rx + 1) > (x - 1)) || (this.karte[rx + 1][ry] instanceof Baum) || (this.karte[rx + 1][ry] instanceof Gefahr) || (this.karte[rx + 1][ry] instanceof Wolf)) {
                            if (((rx + 1) > (x - 1)) || (this.karte[rx + 1][ry] instanceof Baum) ) {
                                // Nichts.
                                //System.out.println("Rotkaeppchen waere von der Karte gefallen oder auf einen Baum getroffen.");
                            } else {
                                System.out.println("Rotkaeppchen wollte runtergehen, doch traf auf eine(n): "+this.karte[rx + 1][ry].getName());
                                //System.out.println("this.karte[rx][ry - 1].getSchaden(): "+this.karte[rx][ry - 1].getSchaden());
                                this.rotkaeppchen.gesundheitVerringern(this.karte[rx + 1][ry].schaden);
                                System.out.println("How much Gesundheit do you have left, RedCapSchen? \nLP are at "+this.rotkaeppchen.getGesundheit()+", Commander!");
                                this.rotkaeppchen.istNochLebendig();
                            }
                        } else {
                            System.out.println("Rotkaeppchen geht rechts.");
                            this.karte[this.rotkaeppchen.getPosition().getX()][this.rotkaeppchen.getPosition().getY()] = null;
                            this.rotkaeppchen.geheRechts();
                            //System.out.println("Rotkaeppchen X: "+this.rotkaeppchen.getPosition().getX()+" Y: "+this.rotkaeppchen.getPosition().getY());
                            this.karte[this.rotkaeppchen.getPosition().getX()][this.rotkaeppchen.getPosition().getY()] = this.rotkaeppchen;
                        }
                        positionen.add(this.rotkaeppchen.getPosition());
                        if (!(this.rotkaeppchen.istNochLebendig())) {
                            // Wenn Rotkaeppchen tot ist, werden einfach die Positionen zurueckgegeben
                            return positionen;
                        }
                        break;
                    default:
                        System.out.println("Sollte nie geprinted werden. Default statement in Methode wegFinden() aus Klasse Maerchenwelt.");
                        break;
                }
            } else {
                // Rotkaeppchen ist bei Oma angekommen.
                //System.out.println("Rotkaeppchen ist bei Oma angekommen! :) ");
                break;
            }

        }
        //this.printWald();
        //System.out.println(positionen);
        return positionen;
    }

    public void start() {
        // Karte ausgeben
        this.printWald();

        System.out.println("Und jetzt machen wir uns mal auf den Weg: ");

        // Weg finden
        ArrayList<Position> hinwegPositionen = this.wegFinden(this.ziel);

        if (hinwegPositionen.get(hinwegPositionen.size()-1).equals(this.oma.getPosition())) {
            this.rotkaeppchen.sprechen(this.oma, 1);
            this.oma.sprechen(this.rotkaeppchen, 2);
            System.out.println("Rotkaeppchen ist bei Oma angekommen.");
            this.rotkaeppchen.setGesundheit(100);
            System.out.println("Rotkaeppchen hat sich bei Oma erholt und hat jetzt wieder eine Gesundheit von: "+this.rotkaeppchen.getGesundheit());
        } else {
            // Wenn Rotkaeppchen nicht bei Oma ist, aber noch lebendig
            if (this.rotkaeppchen.istNochLebendig()) {
                System.out.println("Rotkaeppchen hat sich auf dem Weg zur Oma verlaufen");
            } else {
                System.out.println("Rotkaeppchen ist tot... also: ");
                System.out.println("Rotkaeppchen ist nicht bei der Oma angekommen.");
            }
        }

        // Auf den Rueckweg machen wir uns (wenn ich die Angabe richtig verstehe), nur wenn Rotkaeppchen noch lebendig ist und bereits bei Oma angekommen ist.
        if (this.rotkaeppchen.istNochLebendig() && hinwegPositionen.get(hinwegPositionen.size()-1).equals(this.oma.getPosition())) {
            System.out.println("Gut, wir sind bei Oma angekommen, haben uns erholt und jetzt brechen wir wieder auf und sagen: ");
            this.rotkaeppchen.sprechen(this.oma, 3);
            this.ziel.setX(0);
            this.ziel.setY(0);
            System.out.println("Neues Ziel fuer Rotkaeppchen: Der Heimweg! Ziel X: "+this.ziel.getX()+" Y: "+this.ziel.getY());
            System.out.println("Nochmal zur Erinnerung. Die Karte: ");
            this.printWald();
            ArrayList<Position> rueckwegPositionen = this.wegFinden(this.ziel);

            if (rueckwegPositionen.get(rueckwegPositionen.size()-1).equals(this.ziel)) {
                System.out.println("Rotkaeppchen ist wieder zu Hause angekommen.");
            } else {
                // Wenn Rotkaeppchen nicht bei Oma ist, aber noch lebendig
                if (this.rotkaeppchen.istNochLebendig()) {
                    System.out.println("Rotkaeppchen hat sich auf dem Heimweg verlaufen");
                } else {
                    System.out.println("Rotkaeppchen ist nich wieder zu Hause angekommen.");
                }
            }

            //Neue Liste mit allen Positionen, falls es einen Rueckweg gab.
            ArrayList<Position> hinUrueckwegPositionen = new ArrayList<Position>();
            hinUrueckwegPositionen.addAll(hinwegPositionen);
            hinUrueckwegPositionen.addAll(rueckwegPositionen);
        }

        //System.out.println(hinUrueckwegPositionen);
    }
}
