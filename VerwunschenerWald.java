abstract class VerwunschenerWald {
    protected Position position;
    protected int schaden = 0; //Initialwert

    // Konstruktor
    public VerwunschenerWald(Position position) {
        this.position = position;
        //System.out.println("Wir haben einen verwunschenen Wald erstellt O.o!");
        //System.out.println("Name der Instanz: "+this.getName());
    }

    // Getter
    public Position getPosition() {
        //System.out.println("Positionsabruf von Objekt '"+position+"': \nx: "+position.getX()+"\ny: "+position.getY());
        return position;
    }
    public int getSchaden() {
        return schaden;
        }
    // Jedes Element, das von der Oberklasse VerwunschenerWald erbt, muss also einen Namen bekommen (diese Methode implementieren).
    public abstract String getName();
}