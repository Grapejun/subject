package opensource.example.subject.controller;

import jakarta.persistence.criteria.Predicate;
import opensource.example.subject.model.Activity;
import opensource.example.subject.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
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

    // Constructor omitted for brevity

    /*
    @GetMapping("/search")
    public ResponseEntity<List<Activity>> searchActivities(
            @RequestParam(required = false) String gugunNm,
            @RequestParam(required = false) String sidoNm,
            @RequestParam(required = false) String srvcClCode,
            @RequestParam(required = false) String yngbgsPosblAt,
            @RequestParam(required = false) String adultPosblAt,
            @RequestParam(required = false) String progrmSttusSe,
            @RequestParam(required = false) Date progrmBgnde,
            @RequestParam(required = false) Date progrmEndde,
            @RequestParam(required = false) String progrmSj) {

        Specification<Activity> spec = (root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if (gugunNm != null) {
                predicates.add(cb.equal(root.get("gugunNm"), gugunNm));
            }
            if (sidoNm != null) {
                predicates.add(cb.equal(root.get("sidoNm"), sidoNm));
            }
            if (srvcClCode != null) {
                predicates.add(cb.equal(root.get("srvcClCode"), srvcClCode));
            }
            if (yngbgsPosblAt != null) {
                predicates.add(cb.equal(root.get("yngbgsPosblAt"), yngbgsPosblAt));
            }
            if (adultPosblAt != null) {
                predicates.add(cb.equal(root.get("adultPosblAt"), adultPosblAt));
            }
            if (progrmSttusSe != null) {
                predicates.add(cb.equal(root.get("progrmSttusSe"), progrmSttusSe));
            }
            if (progrmBgnde != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("actBeginTm"), progrmBgnde));
            }
            if (progrmEndde != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("actEndTm"), progrmBgnde));
            }
            if (progrmSj != null) {
                predicates.add(cb.like(root.get("progrmSj"), "%" + progrmSj + "%"));
            }

            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };

        List<Activity> result = activityRepository.findAll(spec);
        return ResponseEntity.ok(result);
    }
*/

    @GetMapping("/search")
    public ResponseEntity<List<Activity>> searchActivities(
            @RequestParam(required = false) String gugunNm,
            @RequestParam(required = false) String sidoNm,
            @RequestParam(required = false) String srvcClCode,
            @RequestParam(required = false) String yngbgsPosblAt,
            @RequestParam(required = false) String adultPosblAt,
            @RequestParam(required = false) String progrmSttusSe,
            @RequestParam(required = false) Date progrmBgnde,
            @RequestParam(required = false) Date progrmEndde,
            @RequestParam(required = false) String progrmSj,
            @RequestParam(defaultValue = "0") int page,  // 페이지 번호
            @RequestParam(defaultValue = "10") int size) {  // 페이지 크기

        Pageable pageable = PageRequest.of(page, size);  // Pageable 객체 생성

        Specification<Activity> spec = (root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if (gugunNm != null) {
                predicates.add(cb.equal(root.get("gugunNm"), gugunNm));
            }
            if (sidoNm != null) {
                predicates.add(cb.equal(root.get("sidoNm"), sidoNm));
            }
            if (srvcClCode != null) {
                predicates.add(cb.equal(root.get("srvcClCode"), srvcClCode));
            }
            if (yngbgsPosblAt != null) {
                predicates.add(cb.equal(root.get("yngbgsPosblAt"), yngbgsPosblAt));
            }
            if (adultPosblAt != null) {
                predicates.add(cb.equal(root.get("adultPosblAt"), adultPosblAt));
            }
            if (progrmSttusSe != null) {
                predicates.add(cb.equal(root.get("progrmSttusSe"), progrmSttusSe));
            }
            if (progrmBgnde != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("actBeginTm"), progrmBgnde));
            }
            if (progrmEndde != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("actEndTm"), progrmBgnde));
            }
            if (progrmSj != null) {
                predicates.add(cb.like(root.get("progrmSj"), "%" + progrmSj + "%"));
            }

            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };

        Page<Activity> resultPage = activityRepository.findAll(spec, pageable);  // 결과를 페이지네이션하여 받음
        return ResponseEntity.ok(resultPage.getContent());  // 페이지의 내용을 반환
    }




}
