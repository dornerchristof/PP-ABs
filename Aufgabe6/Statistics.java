
@Annotations.Responsible(Annotations.names.Patrick)
public class Statistics {
    private int ZbyV = 0;
    private int YbyV = 0;
    private int XbyV = 0;
    private int ZbyU = 0;
    private int YbyU = 0;
    private int XbyU = 0;
    private int ZbyW = 0;
    private int YbyW = 0;
    private int XbyW = 0;

    private int WviZ = 0;
    private int WviY = 0;
    private int WviX = 0;
    private int VviZ = 0;
    private int VviY = 0;
    private int VviX = 0;
    private int UviZ = 0;
    private int UviY = 0;
    private int UviX = 0;

    public Statistics() {
    }

    public void collectData(U bee){
        ZbyU += bee.collectedFromZ();
        YbyU += bee.collectedFromY();
        XbyU += bee.collectedFromX();
    }
    public void collectData(V bee){
        ZbyV += bee.collectedFromZ();
        YbyV += bee.collectedFromY();
        XbyV += bee.collectedFromX();
    }
    public void collectData(W bee){
        ZbyW += bee.collectedFromZ();
        YbyW += bee.collectedFromY();
        XbyW += bee.collectedFromX();
    }
    public void collectData(X flower){
        WviX += flower.visitedByW();
        VviX += flower.visitedByV();
        UviX += flower.visitedByU();
    }
    public void collectData(Y flower){
        WviY += flower.visitedByW();
        VviY += flower.visitedByV();
        UviY += flower.visitedByU();
    }
    public void collectData(Z flower){
        WviZ += flower.visitedByW();
        VviZ += flower.visitedByV();
        UviZ += flower.visitedByU();
    }

    public void print() {
        System.out.println("\nSTATISTIK: GESAMMELTER NEKTAR (Bienen-Sicht)");
        System.out.println("+-------+-------+-------+-------+-------+");
        System.out.println("| Biene |   X   |   Y   |   Z   | Gesamt|");
        System.out.println("+-------+-------+-------+-------+-------+");
        System.out.printf("|   U   | %-5d | %-5d | %-5d | %-5d |\n", XbyU, YbyU, ZbyU, XbyU + YbyU + ZbyU);
        System.out.printf("|   V   | %-5d | %-5d | %-5d | %-5d |\n", XbyV, YbyV, ZbyV, XbyV + YbyV + ZbyV);
        System.out.printf("|   W   | %-5d | %-5d | %-5d | %-5d |\n", XbyW, YbyW, ZbyW, XbyW + YbyW + ZbyW);
        System.out.println("+-------+-------+-------+-------+-------+");

        System.out.println("\nSTATISTIK: ERHALTENE BESUCHE (Blumen-Sicht)");
        System.out.println("+-------+-------+-------+-------+-------+");
        System.out.println("| Blume |   U   |   V   |   W   | Gesamt|");
        System.out.println("+-------+-------+-------+-------+-------+");
        System.out.printf("|   X   | %-5d | %-5d | %-5d | %-5d |\n", UviX, VviX, WviX, UviX + VviX + WviX);
        System.out.printf("|   Y   | %-5d | %-5d | %-5d | %-5d |\n", UviY, VviY, WviY, UviY + VviY + WviY);
        System.out.printf("|   Z   | %-5d | %-5d | %-5d | %-5d |\n", UviZ, VviZ, WviZ, UviZ + VviZ + WviZ);
        System.out.println("+-------+-------+-------+-------+-------+");
    }
}
