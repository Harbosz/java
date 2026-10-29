public class PensionerDiscount extends AgeDiscountPayment {
    public PensionerDiscount() {
        super("Пільга пенсіонера");
    }

    @Override
    protected boolean isEligible(int age) {
        return age >= 60;
    }

    @Override
    protected double discountRate() {
        return 0.5;
    }
}
