package opensource.example.subject.repository;

import opensource.example.subject.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Integer>, JpaSpecificationExecutor<Activity> {
    Optional<Activity> findByProgrmRegistNo(Integer progrmRegistNo);
}
