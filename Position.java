class Position {
    private int x;
    private int y;

    //Konstruktor
    public Position (int x, int y) {
        this.x = x;
        this.y = y;
        //System.out.println("Wir haben ein Positionselement erstellt! \nx: "+x+" \ny: "+y+"\nYeah!\nUnd zwar: "+this);
    }

    //Getter
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    //Setter
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    //http://www.tutego.de/javabuch/Java-ist-auch-eine-Insel/10/javainsel_08_003.html#dodtp848b6b5d-0c2d-464d-ac8e-4c765d194c9e
    @Override
    public boolean equals(Object o) {
        // Immer falsch zurückgeben, wenn Object ist null
        if (o == null) {
            return false;
        }
        // Wenn das Objekt wirklich einfach auf sich selbst verweise (es den selben Speicherplatz auf dem Stack verwendet wie das zu vergleichende Objekt) / Identitätscheck
        if (o == this) {
            return true;
        }
        // Jetzt Check: Ist zu vergleichende Objekt vom richtigen Typ?
        if (!(o instanceof Position)) {
            return false;
        } // Allerdings gibt es das Problem, dass instanceof auch Unterklassen testet, daher sollte theoretisch
        // if ( ! o.getClass().equals(getClass()) )
        //      return false;
        // gemacht werden.

        // Hier erstellen wir unser Positionsobjekt für die Methode aus dem eingegebenen
        Position that = (Position) o; //casten
        // Jetzt: ist x wirklich x und y wirklich y?
        return this.getX() == that.getX()
                   && this.getY() == that.getY();
        }
        // Hier haben wir jetzt genauer verglichen, ob das erste int in dem einen Positionselement, dem ersten int im anderen Positionselement entspricht. Yeah!
}