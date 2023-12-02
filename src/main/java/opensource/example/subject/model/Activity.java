package opensource.example.subject.model;
import java.time.LocalDate;

import jakarta.persistence.*;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;

import lombok.Getter;
import lombok.Setter;
import opensource.example.subject.converter.LocalDateConverter;

@Getter
@Setter
@Entity
@Table(name = "activities")  // 클래스 이름과 일치하게 변경
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;  // 대문자로 변경

    @Column(nullable = false)
    @CsvBindByName
    private Integer actBeginTm;

    @Column(nullable = false)
    @CsvBindByName
    private Integer actEndTm;
/*
    @Column(nullable = false)
    @CsvBindByName
    private Integer actDuringTm;
*/
    @Column(columnDefinition = "TEXT", nullable = false)  // 대문자로 변경
    @CsvBindByName
    private String actPlace;

    @Column(nullable = false)
    @CsvBindByName
    private String adultPosblAt;  // 변경된 타입

    @Column(nullable = false)
    @CsvBindByName
    private Integer gugunCd;  // 정수 타입으로 변경

    @Column(nullable = false)
    @CsvBindByName
    private String nanmmbyNm;

    @CsvCustomBindByName(column = "noticeBgnde", converter = LocalDateConverter.class)
    @Column(nullable = false)
    private LocalDate noticeBgnde;

    @CsvCustomBindByName(column = "noticeEndde", converter = LocalDateConverter.class)
    @Column(nullable = false)
    private LocalDate noticeEndde;

    @CsvCustomBindByName(column = "progrmBgnde", converter = LocalDateConverter.class)
    @Column(nullable = false)
    private LocalDate progrmBgnde;

    @CsvCustomBindByName(column = "progrmEndde", converter = LocalDateConverter.class)
    @Column(nullable = false)
    private LocalDate progrmEndde;

    @Column(nullable = false)
    @CsvBindByName
    private Integer progrmRegistNo;

    @Column(columnDefinition = "TEXT", nullable = false)  // 대문자로 변경
    @CsvBindByName
    private String progrmSj;

    @Column(nullable = false)
    @CsvBindByName
    private String progrmSttusSe;

    @Column(nullable = false)
    @CsvBindByName
    private Integer sidoCd;  // 정수 타입으로 변경

    @Column(columnDefinition = "TEXT", nullable = false)  // 대문자로 변경
    @CsvBindByName
    private String srvcClCode;

    @Column(columnDefinition = "TEXT", nullable = false)  // 대문자로 변경
    @CsvBindByName
    private String url;

    @Column(nullable = false)
    @CsvBindByName
    private String yngbgsPosblAt;  // 변경된 타입

    @Column(nullable = false)
    @CsvBindByName
    private String gugunNm;

    @Column(nullable = false)
    @CsvBindByName
    private String sidoNm;

    @Column(nullable = false)
    @CsvBindByName
    private String progrmCn;
}