import java.util.ArrayList;
import java.util.List;

public class AuditLog implements AppointmentObserver {
    private final List<String> records = new ArrayList<>();

    @Override
    public void onStatusChanged(Appointment appointment, AppointmentStatus oldStatus, AppointmentStatus newStatus) {
        String record = String.format("[ЖУРНАЛ] %s, картка №%s: %s → %s",
                appointment.getDoctor(), appointment.getPatient().getCardNumber(),
                oldStatus.getTitle(), newStatus.getTitle());
        records.add(record);
        System.out.println("    " + record);
    }

    public int size() {
        return records.size();
    }
}
