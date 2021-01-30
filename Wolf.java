class Wolf extends VerwunschenerWald {
    // Konstruktor übergibt nur Oberklasse
    public Wolf(Position position){
        super(position);
        schaden = 5;
    }
    public String getName() {
        return "W";
    }
}