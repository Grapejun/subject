package opensource.example.subject.controller;

import opensource.example.subject.model.Activity;
import opensource.example.subject.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @GetMapping("/actBeginTm")
    public ResponseEntity<List<Integer>> getAllActBeginTm() {
        List<Integer> actBeginTmList = activityRepository.findAll().stream()
                .map(Activity::getActBeginTm)
                .collect(Collectors.toList());

        return ResponseEntity.ok(actBeginTmList);
    }

    @GetMapping("/urls")
    public ResponseEntity<List<String>> getAllActivityUrls() {
        List<String> urls = activityRepository.findAll().stream()
                .map(Activity::getUrl)
                .collect(Collectors.toList());
        return ResponseEntity.ok(urls);
    }

    // 특정 ID의 Activity 데이터 반환 엔드포인트
    @GetMapping("/{id}")
    public ResponseEntity<Activity> getActivityById(@PathVariable Integer id) {
        Optional<Activity> activityOptional = activityRepository.findById(id);
        if (activityOptional.isPresent()) {
            return ResponseEntity.ok(activityOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
