package opensource.example.subject.controller;

import opensource.example.subject.model.Activity;
import opensource.example.subject.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<String>> getAllActivityUrls(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<String> urls = activityRepository.findAll(pageable).stream()
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

    // 특정 progrmRegistNo의 Activity 데이터 반환 엔드포인트
    @GetMapping("/activity/{progrmRegistNo}")
    public ResponseEntity<Activity> getActivityByProgrmRegistNo(@PathVariable Integer progrmRegistNo) {
        Optional<Activity> activityOptional = activityRepository.findByProgrmRegistNo(progrmRegistNo);
        return activityOptional
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
