class Rotkaeppchen extends VerwunschenerWald {
    private int gesundheit = 100; //Inititalwert

    public Rotkaeppchen(Position position) {
        super(position); //Ich glaube, hier wird jetzt einfach VerwunschenerWald aufgerufen!
        // Das heißt auch, wir brauchen keine Getter hier.
        System.out.println("Rotkaeppchen wurde geboren!");
    }
    public void geheHoch() {
        Position pr = this.getPosition();
        int x = pr.getX();
        int y = pr.getY();
        //System.out.println("y vor dem Hochgehen: "+pr.getY());
        pr.setY(y - 1);
        //System.out.println("y nach dem Hochgehen: "+pr.getY());
    }
    public void geheRunter() {
        Position pr = this.getPosition();
        int x = pr.getX();
        int y = pr.getY();
        pr.setY(y + 1);
    }
    public void geheLinks() {
        Position pr = this.getPosition();
        int x = pr.getX();
        int y = pr.getY();
        pr.setX(x - 1);
    }
    public void geheRechts() {
        Position pr = this.getPosition();
        int x = pr.getX();
        int y = pr.getY();
        pr.setX(x + 1);
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
            System.out.println("Ja, Gesundheitsstatus: "+gesundheit);
            return true;
        }
        else {
            return false;
        }
    }
    public String getName() {
        return "R";
    }

}