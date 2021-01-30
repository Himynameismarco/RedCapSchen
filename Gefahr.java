class Gefahr extends VerwunschenerWald {
    // Konstruktor übergibt nur Oberklasse
    public Gefahr(Position position){
        super(position);
        schaden = 2;
    }
    public String getName() {
        return "G";
    }
}