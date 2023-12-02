package opensource.example.subject.converter;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter extends AbstractBeanField<LocalDate, String> { //  String 타입을 LocalDate 타입 으로 변환
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected LocalDate convert(String s) throws CsvDataTypeMismatchException {
        return LocalDate.parse(s, formatter);
    }
}
