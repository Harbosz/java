public interface AppointmentObserver {
    void onStatusChanged(Appointment appointment, AppointmentStatus oldStatus, AppointmentStatus newStatus);
}
