package lt.vu.ads.repositories;

import lt.vu.ads.models.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogEntry, Long> {
}
