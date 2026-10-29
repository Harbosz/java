public enum AppointmentStatus {
    SCHEDULED("Записаний"),
    IN_PROGRESS("На прийомі"),
    COMPLETED("Завершений"),
    CANCELLED("Скасований");

    private final String title;

    AppointmentStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
