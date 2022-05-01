package lt.vu.ads.repositories;

import lt.vu.ads.models.Courier.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {
    Courier findByEmail(String email);
}