class Baum extends VerwunschenerWald {
    // Konstruktor übergibt nur Oberklasse
    public Baum(Position position){
        super(position);
    }
    public String getName() {
        return "B";
    }
}