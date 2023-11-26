package opensource.example.subject.repository;

import opensource.example.subject.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    Optional<Activity> findByProgrmRegistNo(Integer progrmRegistNo);
}
