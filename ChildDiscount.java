public class ChildDiscount extends AgeDiscountPayment {
    public ChildDiscount() {
        super("Дитяча пільга");
    }

    @Override
    protected boolean isEligible(int age) {
        return age < 18;
    }

    @Override
    protected double discountRate() {
        return 0.3;
    }
}
