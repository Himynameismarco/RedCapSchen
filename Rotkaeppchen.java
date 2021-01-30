class Rotkaeppchen extends VerwunschenerWald implements Person {
    private int gesundheit = 100; //Inititalwert

    public Rotkaeppchen(Position position) {
        super(position); //Ich glaube, hier wird jetzt einfach VerwunschenerWald aufgerufen!
        // Das heißt auch, wir brauchen keine Getter hier.
        //System.out.println("Rotkaeppchen wurde geboren! :)");
    }
    public void geheHoch() {
        int ry = this.getPosition().getY();
        this.getPosition().setY(ry - 1);
    }
    public void geheRunter() {
        int ry = this.getPosition().getY();
        this.getPosition().setY(ry + 1);
    }
    public void geheLinks() {
        int rx = this.getPosition().getX();
        this.getPosition().setX(rx - 1);
    }
    public void geheRechts() {
        int rx = this.getPosition().getX();
        this.getPosition().setX(rx + 1);
    }
    public void gesundheitVerringern(int wert) {
        if (gesundheit - wert > 0) {
            gesundheit = gesundheit - wert;
        }
        else {
            System.out.println("RedCapSchen died :'( ");
            gesundheit = 0;
        }
    }
    public boolean istNochLebendig() {
        if (gesundheit > 0) {
            //System.out.println("Still alive Biatch! Gesundheitsstatus: "+gesundheit);
            return true;
        }
        else {
            System.out.println("\n \n :'( *wolgaliedspiel* :'(\n \n ");
            return false;
        }
    }
    public String getName() {
        return "R";
    }

    public int getGesundheit() {
        return gesundheit;
    }

    public void setGesundheit(int gesundheit) {
        this.gesundheit = gesundheit;
    }

    @Override
    public void sprechen(Person konversationspartner, int zaehler) {
        switch (zaehler) {
            case 1:
                System.out.println("Hallo, Oma");
                zaehler++;
                break;
            case 3:
                System.out.println("Tschuess, Oma");
                zaehler++;
                break;
            default:
                System.out.println("Rotkaeppchen default");
                return;
        }
    }

}