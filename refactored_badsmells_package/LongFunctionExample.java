package badsmells;

/*
 * Refactored smell: Long Function
 *
 * What changed:
 * - Extracted discount, shipping, tax, approval, and summary logic into helpers
 * - Introduced OrderRequest to group related input data
 *
 * Why better:
 * The main workflow now reads at a higher level, while detailed calculations are
 * isolated in smaller methods that are easier to understand and change.
 */
public class LongFunctionExample {

    static class OrderRequest {
        private final String customerType;
        private final int quantity;
        private final double price;
        private final boolean expressDelivery;

        OrderRequest(String customerType, int quantity, double price, boolean expressDelivery) {
            this.customerType = customerType;
            this.quantity = quantity;
            this.price = price;
            this.expressDelivery = expressDelivery;
        }
    }

    public String processOrder(String customerType, int quantity, double price, boolean expressDelivery) {
        return processOrder(new OrderRequest(customerType, quantity, price, expressDelivery));
    }

    public String processOrder(OrderRequest request) {
        double subtotal = calculateSubtotal(request);
        double discount = calculateDiscount(request.customerType, subtotal);
        double shipping = calculateShipping(request.quantity, request.expressDelivery);
        double tax = calculateTax(subtotal, discount);
        double total = subtotal - discount + shipping + tax;
        String status = determineApprovalStatus(total);

        return buildSummary(request, subtotal, discount, shipping, tax, total, status);
    }

    private double calculateSubtotal(OrderRequest request) {
        return request.quantity * request.price;
    }

    private double calculateDiscount(String customerType, double subtotal) {
        if ("STUDENT".equals(customerType)) {
            return subtotal * 0.05;
        }
        if ("VIP".equals(customerType)) {
            return subtotal * 0.12;
        }
        if ("EMPLOYEE".equals(customerType)) {
            return subtotal * 0.20;
        }
        return 0;
    }

    private double calculateShipping(int quantity, boolean expressDelivery) {
        if (expressDelivery) {
            return quantity > 10 ? 35 : 25;
        }
        return quantity > 10 ? 15 : 10;
    }

    private double calculateTax(double subtotal, double discount) {
        return (subtotal - discount) * 0.18;
    }

    private String determineApprovalStatus(double total) {
        if (total > 500) {
            return "MANAGER_APPROVAL";
        }
        if (total > 200) {
            return "FINANCE_REVIEW";
        }
        return "AUTO_APPROVED";
    }

    private String buildSummary(OrderRequest request, double subtotal, double discount,
                                double shipping, double tax, double total, String status) {
        StringBuilder summary = new StringBuilder();
        summary.append("customerType=").append(request.customerType).append('\n');
        summary.append("quantity=").append(request.quantity).append('\n');
        summary.append("price=").append(request.price).append('\n');
        summary.append("subtotal=").append(subtotal).append('\n');
        summary.append("discount=").append(discount).append('\n');
        summary.append("shipping=").append(shipping).append('\n');
        summary.append("tax=").append(tax).append('\n');
        summary.append("total=").append(total).append('\n');
        summary.append("status=").append(status);
        return summary.toString();
    }

    public void clientCode() {
        System.out.println(processOrder("VIP", 12, 30, true));
        System.out.println(processOrder("STUDENT", 2, 50, false));
    }
}
