public class BillingDepartment implements AppointmentObserver {
    private double totalIncome = 0;

    @Override
    public void onStatusChanged(Appointment appointment, AppointmentStatus oldStatus, AppointmentStatus newStatus) {
        if (newStatus != AppointmentStatus.COMPLETED) {
            return;
        }
        double cost = appointment.getCost();
        totalIncome += cost;
        System.out.printf("    [БУХГАЛТЕРІЯ] рахунок %.2f грн (%s), виручка за день: %.2f грн%n",
                cost, appointment.getPaymentName(), totalIncome);
    }

    public double getTotalIncome() {
        return totalIncome;
    }
}
