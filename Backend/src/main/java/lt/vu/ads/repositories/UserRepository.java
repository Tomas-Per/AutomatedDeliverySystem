package lt.vu.ads.repositories;

import lt.vu.ads.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByFirstNameAndLastNameAndPhoneNumber(String firstName, String lastName, String phoneNumber);
}