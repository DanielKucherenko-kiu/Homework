package badsmells;

/*
 * Refactored smell: Duplicated Code
 *
 * What changed:
 * - Extracted shared invoice calculation into one method
 * - Kept only the seasonal discount rule separate
 *
 * Why better:
 * Tax and shipping logic now live in one place, so pricing changes are less
 * error-prone and easier to maintain.
 */
public class DuplicatedCodeExample {

    public double summerInvoice(double subtotal) {
        return calculateInvoice(subtotal, summerDiscount(subtotal));
    }

    public double winterInvoice(double subtotal) {
        return calculateInvoice(subtotal, winterDiscount(subtotal));
    }

    private double calculateInvoice(double subtotal, double discount) {
        double tax = subtotal * 0.18;
        double shipping = subtotal > 100 ? 0 : 15;
        return subtotal + tax + shipping - discount;
    }

    private double summerDiscount(double subtotal) {
        return subtotal > 200 ? subtotal * 0.10 : 0;
    }

    private double winterDiscount(double subtotal) {
        return subtotal > 200 ? subtotal * 0.20 : 50;
    }

    public void clientCode() {
        System.out.println(summerInvoice(240));
        System.out.println(winterInvoice(240));
    }
}
