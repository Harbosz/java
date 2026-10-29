public class PatientNotifier implements AppointmentObserver {
    @Override
    public void onStatusChanged(Appointment appointment, AppointmentStatus oldStatus, AppointmentStatus newStatus) {
        String text;
        switch (newStatus) {
            case IN_PROGRESS: text = "лікар чекає на вас у кабінеті"; break;
            case COMPLETED:   text = String.format("прийом завершено, до сплати %.2f грн", appointment.getCost()); break;
            case CANCELLED:   text = "ваш прийом скасовано, оберіть інший час"; break;
            default:          text = "статус прийому: " + newStatus.getTitle();
        }
        System.out.println("    [SMS → " + appointment.getPatient().getFullName() + "] " + text);
    }
}
