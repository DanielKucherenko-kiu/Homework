package badsmells;

/*
 * Refactored smell: Comments
 *
 * What changed:
 * - Replaced explanatory comments with intention-revealing methods
 * - Renamed the main method to describe the business behavior
 *
 * Why better:
 * The code explains itself through structure and naming, so it is easier to read
 * and less likely to become misleading when the implementation changes.
 */
public class CommentsExample {

    public double calculateFinalPrice(double basePrice, boolean vipCustomer, int quantity) {
        double discountedPrice = applyVipDiscount(basePrice, vipCustomer);
        discountedPrice = applyBulkDiscount(discountedPrice, quantity);
        return addTax(discountedPrice);
    }

    private double applyVipDiscount(double price, boolean vipCustomer) {
        return vipCustomer ? price - price * 0.10 : price;
    }

    private double applyBulkDiscount(double price, int quantity) {
        return quantity > 20 ? price - price * 0.05 : price;
    }

    private double addTax(double price) {
        return price + price * 0.18;
    }

    public void clientCode() {
        double vipOrder = calculateFinalPrice(120, true, 25);
        double regularOrder = calculateFinalPrice(120, false, 5);
        System.out.println(vipOrder);
        System.out.println(regularOrder);
    }
}
