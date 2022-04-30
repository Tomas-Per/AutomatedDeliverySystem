package lt.vu.ads.repository;

import lt.vu.ads.models.Courier.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {
    Courier findByEmailAndPassword(String email, String password);
    Courier findByEmail(String email);
}