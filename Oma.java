class Oma extends VerwunschenerWald implements Person {
    // Konstruktor übergibt nur Oberklasse
    public Oma(Position position){
        super(position);
    }
    public String getName() {
        return "O";
    }
    @Override
    public void sprechen(Person konversationspartner, int zaehler) {
        switch (zaehler) {
            case 2:
                System.out.println("Hallo, Rotkaeppchen");
                zaehler++;
                break;
            default:
                System.out.println("Oma default");
                return;
        }
    }
}