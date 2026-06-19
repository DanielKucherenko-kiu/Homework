package badsmells;

/*
 * Refactored smell: Mysterious Name
 *
 * What changed:
 * - Renamed the method and variables to reveal intent
 * - Extracted intermediate steps into named helpers
 *
 * Why better:
 * The calculation can now be read instead of reverse-engineered. Clear naming
 * reduces cognitive load and makes future changes safer.
 */
public class MysteriousNameExample {

    public int calculateAdjustedHalfProduct(int multiplicand, int multiplier, int deduction) {
        int product = multiply(multiplicand, multiplier);
        int adjustedValue = subtractDeduction(product, deduction);
        return divideByTwo(adjustedValue);
    }

    private int multiply(int multiplicand, int multiplier) {
        return multiplicand * multiplier;
    }

    private int subtractDeduction(int value, int deduction) {
        return value - deduction;
    }

    private int divideByTwo(int value) {
        return value / 2;
    }

    public void clientCode() {
        int result = calculateAdjustedHalfProduct(8, 4, 6);
        System.out.println(result);
    }
}
