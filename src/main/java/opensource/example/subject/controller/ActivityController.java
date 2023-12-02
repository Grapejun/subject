package opensource.example.subject.controller;

import jakarta.persistence.criteria.Predicate;
import opensource.example.subject.model.Activity;
import opensource.example.subject.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }



    /*@GetMapping("/searchAi")
    public ResponseEntity<List<Activity>> searchActivities(
            @RequestParam(required = false) String gugunNm,
            @RequestParam(required = false) String sidoNm,
            @RequestParam(required = false) String srvcClCode,
            @RequestParam(required = false) String yngbgsPosblAt,
            @RequestParam(required = false) String adultPosblAt,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate possibleStartDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate possibleEndDate) {

        Specification<Activity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Adding the predicate for progrmSttusSe to be "모집중" directly
            predicates.add(cb.equal(root.get("progrmSttusSe"), "모집중"));

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

            if (possibleStartDate != null && possibleEndDate != null) {
                Predicate dateOverlap = cb.or(
                        cb.and(
                                cb.lessThanOrEqualTo(root.get("progrmBgnde"), possibleEndDate),
                                cb.greaterThanOrEqualTo(root.get("progrmEndde"), possibleStartDate)
                        ),
                        cb.and(
                                cb.lessThanOrEqualTo(root.get("progrmBgnde"), possibleStartDate),
                                cb.greaterThanOrEqualTo(root.get("progrmEndde"), possibleStartDate)
                        ),
                        cb.and(
                                cb.lessThanOrEqualTo(root.get("progrmBgnde"), possibleEndDate),
                                cb.greaterThanOrEqualTo(root.get("progrmEndde"), possibleEndDate)
                        )
                );
                predicates.add(dateOverlap);
            }

            Predicate activityDurationPredicate = cb.notEqual(
                    cb.diff(root.get("actEndTm"), root.get("actBeginTm")),
                    0
            );
            predicates.add(activityDurationPredicate);

            query.distinct(true); // 중복을 제거합니다.
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        List<Activity> result = activityRepository.findAll(spec);
        return ResponseEntity.ok(result);
    }

    // 새로운 추천 API 메소드
    @GetMapping("/recommendActivities")
    public ResponseEntity<List<Activity>> recommendActivities(
            @RequestParam(required = false) String gugunNm,
            @RequestParam(required = false) String sidoNm,
            @RequestParam(required = false) String srvcClCode,
            @RequestParam(required = false) String yngbgsPosblAt,
            @RequestParam(required = false) String adultPosblAt,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate possibleStartDate,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate possibleEndDate,
            @RequestParam(required = false) Long totalTime) {

        // searchActivities 메소드를 호출하여 조건에 맞는 활동을 검색합니다.
        List<Activity> activities = searchActivities(gugunNm, sidoNm, srvcClCode, yngbgsPosblAt, adultPosblAt, possibleStartDate, possibleEndDate).getBody();

        // totalTime에 가장 근접하는 활동의 조합을 찾습니다.
        List<Activity> recommendedActivities = findBestActivityCombination(activities, totalTime);

        // 추천된 활동들을 반환합니다.
        return ResponseEntity.ok(recommendedActivities);
    }

    private List<Activity> findBestActivityCombination(List<Activity> activities, long totalTime) {
        Collections.shuffle(activities, new Random());

        List<Activity> bestCombination = new ArrayList<>();
        int currentTotalTime = 0;

        for (Activity activity : activities) {
            int activityDuration = activity.getActDuringTm();

            // 새 활동을 추가했을 때 totalTime을 초과하지 않는 경우에만 추가합니다.
            if (currentTotalTime + activityDuration <= totalTime) {
                bestCombination.add(activity);
                currentTotalTime += activityDuration;
            }
        }

        return bestCombination;
    }
*/

    // 날짜 할당을 위한 함수 추가
    private LocalDate assignRandomDate(LocalDate start, LocalDate end) {
        long days = ChronoUnit.DAYS.between(start, end);
        LocalDate randomDate = start.plusDays(new Random().nextInt((int) days + 1));
        return randomDate;
    }

    // 시간 겹침 확인을 위한 함수 추가
    private boolean isTimeOverlap(Integer startTimeInt, Integer endTimeInt, List<Activity> assignedActivities, LocalDate assignedDate) {
        LocalTime startTime = LocalTime.of(startTimeInt, 0);
        LocalTime endTime = LocalTime.of(endTimeInt, 0);

        for (Activity activity : assignedActivities) {
            if (activity.getAssignedDate().isEqual(assignedDate)) {
                Integer existingStartTimeInt = activity.getActBeginTm();
                Integer existingEndTimeInt = activity.getActEndTm();
                LocalTime existingStartTime = LocalTime.of(existingStartTimeInt, 0);
                LocalTime existingEndTime = LocalTime.of(existingEndTimeInt, 0);

                if ((startTime.isBefore(existingEndTime) && endTime.isAfter(existingStartTime)) ||
                        startTime.equals(existingStartTime) || endTime.equals(existingEndTime)) {
                    return true;
                }
            }
        }
        return false;
    }

    @GetMapping("/searchAi")
    public ResponseEntity<List<Activity>> searchActivities(
            @RequestParam(required = false) String gugunNm,
            @RequestParam(required = false) String sidoNm,
            @RequestParam(required = false) List<String> srvcClCodes,
            @RequestParam(required = false) String yngbgsPosblAt,
            @RequestParam(required = false) String adultPosblAt,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate possibleStartDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate possibleEndDate) {

        Specification<Activity> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Adding the predicate for progrmSttusSe to be "모집중" directly
            predicates.add(cb.equal(root.get("progrmSttusSe"), "모집중"));

            if (gugunNm != null) {
                predicates.add(cb.equal(root.get("gugunNm"), gugunNm));
            }
            if (sidoNm != null) {
                predicates.add(cb.equal(root.get("sidoNm"), sidoNm));
            }
            if (srvcClCodes != null && !srvcClCodes.isEmpty()) {
                predicates.add(root.get("srvcClCode").in(srvcClCodes));
            }
            if (yngbgsPosblAt != null) {
                predicates.add(cb.equal(root.get("yngbgsPosblAt"), yngbgsPosblAt));
            }
            if (adultPosblAt != null) {
                predicates.add(cb.equal(root.get("adultPosblAt"), adultPosblAt));
            }

            if (possibleStartDate != null && possibleEndDate != null) {
                Predicate dateOverlap = cb.or(
                        cb.and(
                                cb.lessThanOrEqualTo(root.get("progrmBgnde"), possibleEndDate),
                                cb.greaterThanOrEqualTo(root.get("progrmEndde"), possibleStartDate)
                        ),
                        cb.and(
                                cb.lessThanOrEqualTo(root.get("progrmBgnde"), possibleStartDate),
                                cb.greaterThanOrEqualTo(root.get("progrmEndde"), possibleStartDate)
                        ),
                        cb.and(
                                cb.lessThanOrEqualTo(root.get("progrmBgnde"), possibleEndDate),
                                cb.greaterThanOrEqualTo(root.get("progrmEndde"), possibleEndDate)
                        )
                );
                predicates.add(dateOverlap);
            }

            Predicate activityDurationPredicate = cb.notEqual(
                    cb.diff(root.get("actEndTm"), root.get("actBeginTm")),
                    0
            );
            predicates.add(activityDurationPredicate);

            query.distinct(true); // 중복을 제거합니다.
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        List<Activity> result = activityRepository.findAll(spec);
        return ResponseEntity.ok(result);
    }

    // 새로운 추천 API 메소드
    @GetMapping("/recommendActivities")
    public ResponseEntity<List<Activity>> recommendActivities(
            @RequestParam(required = false) String gugunNm,
            @RequestParam(required = false) String sidoNm,
            @RequestParam(required = false) List<String> srvcClCodes,
            @RequestParam(required = false) String yngbgsPosblAt,
            @RequestParam(required = false) String adultPosblAt,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate possibleStartDate,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate possibleEndDate,
            @RequestParam(required = false) Long totalTime) {

        // 로직 처리...
        if (srvcClCodes != null && !srvcClCodes.isEmpty()) {
            // srvcClCodes 리스트에 대한 처리 로직
        }

        // searchActivities 메소드를 호출하여 조건에 맞는 활동을 검색합니다.
        List<Activity> activities = searchActivities(gugunNm, sidoNm, srvcClCodes, yngbgsPosblAt, adultPosblAt, possibleStartDate, possibleEndDate).getBody();

        // totalTime에 가장 근접하는 활동의 조합을 찾습니다.
        List<Activity> recommendedActivities = findBestActivityCombination(activities, totalTime, possibleStartDate, possibleEndDate);

        // 조합이 totalTime을 충족하는지 확인합니다.
        long totalDuration = recommendedActivities.stream()
                .mapToLong(Activity::getActDuringTm)
                .sum();

        if (totalDuration < totalTime) {
            // 충족할 수 있는 조합이 없음을 알리는 빈 리스트 반환
            return ResponseEntity.ok(Collections.emptyList());
        }
        /*if (totalDuration < totalTime) {
            // 충족할 수 있는 조합이 없음을 알립니다.
            return ResponseEntity.ok("해당 조건을 가지고는 총 봉사 시간을 충족할 수 있는 조합이 없습니다");
        }*/

        // 정렬 로직: assignedDate가 빠른 순, 그 다음 actBeginTm이 빠른 순
        recommendedActivities.sort(Comparator.comparing(Activity::getAssignedDate)
                .thenComparing(Activity::getActBeginTm));

        // 추천된 활동들을 반환합니다.
        return ResponseEntity.ok(recommendedActivities);
    }

    // /recommendActivities 엔드포인트의 로직 수정
    private List<Activity> findBestActivityCombination(List<Activity> activities, long totalTime, LocalDate possibleStartDate, LocalDate possibleEndDate) {
        Collections.shuffle(activities, new Random());

        List<Activity> bestCombination = new ArrayList<>();
        List<Activity> assignedActivities = new ArrayList<>();
        int currentTotalTime = 0;

        for (Activity activity : activities) {
            int activityDuration = activity.getActDuringTm();
            if (currentTotalTime + activityDuration <= totalTime) {
                LocalDate assignedDate = null;
                int attempts = 0;
                final int MAX_ATTEMPTS = 1000; // 최대 시도 횟수 설정

                do {
                    assignedDate = assignRandomDate(possibleStartDate, possibleEndDate);
                    attempts++;
                    if (attempts > MAX_ATTEMPTS) {
                        // 최대 시도 횟수 초과 시, 이 활동은 추가하지 않음
                        assignedDate = null;
                        break;
                    }
                } while (isTimeOverlap(activity.getActBeginTm(), activity.getActEndTm(), assignedActivities, assignedDate));

                if (assignedDate != null) {
                    activity.setAssignedDate(assignedDate); // 활동에 날짜 할당
                    assignedActivities.add(activity);

                    bestCombination.add(activity);
                    currentTotalTime += activityDuration;
                }
            }
        }

        return bestCombination;
    }








    @GetMapping("/search")
    public ResponseEntity<List<Activity>> searchActivities(
            @RequestParam(required = false) String gugunNm,
            @RequestParam(required = false) String sidoNm,
            @RequestParam(required = false) String srvcClCode,
            @RequestParam(required = false) String yngbgsPosblAt,
            @RequestParam(required = false) String adultPosblAt,
            @RequestParam(required = false) String progrmSttusSe,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate progrmBgnde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate progrmEndde,
            @RequestParam(required = false) String progrmSj,
            @RequestParam(defaultValue = "0") int page,  // 페이지 번호 추가
            @RequestParam(defaultValue = "10") int size) {  // 페이지 크기 (기본값 10)

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
                predicates.add(cb.greaterThanOrEqualTo(root.get("progrmBgnde"), progrmBgnde));
            }
            if (progrmEndde != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("progrmEndde"), progrmEndde));
            }

            if (progrmSj != null) {  // 문자열 포함 조건 추가
                predicates.add(cb.like(root.get("progrmSj"), "%" + progrmSj + "%"));
            }

            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        };

        Page<Activity> resultPage = activityRepository.findAll(spec, pageable);  // 결과를 페이지네이션하여 받음
        return ResponseEntity.ok(resultPage.getContent());  // 페이지의 내용을 반환
    }



}
