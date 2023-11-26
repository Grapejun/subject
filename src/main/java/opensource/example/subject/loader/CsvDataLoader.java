package opensource.example.subject.loader;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import opensource.example.subject.model.Activity;
import opensource.example.subject.repository.ActivityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// @Component
public class CsvDataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CsvDataLoader.class);

    private final ActivityRepository activityRepository;

    public CsvDataLoader(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource("data/dataset.csv").getInputStream(), "CP949"));

        CsvToBean<Activity> csvToBean = new CsvToBeanBuilder<Activity>(reader)
                .withType(Activity.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        List<Activity> activities = csvToBean.parse();

        // 로딩된 각 Activity 객체를 로깅합니다.
        activities.forEach(activity -> logger.info("Loaded activity: {}", activity));

        activityRepository.saveAll(activities);
    }
}

