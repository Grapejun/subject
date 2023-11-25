package opensource.example.subject.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "activities")  // 클래스 이름과 일치하게 변경
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;  // 대문자로 변경

    @Column(nullable = false)
    private Integer actBeginTm;

    @Column(nullable = false)
    private Integer actEndTm;

    @Column(columnDefinition = "TEXT", nullable = false)  // 대문자로 변경
    private String actPlace;

    @Column(nullable = false)
    private String adultPosblAt;  // 변경된 타입

    @Column(nullable = false)
    private Integer gugunCd;  // 정수 타입으로 변경

    @Column(nullable = false)
    private String nanmmbyNm;

    @Column(nullable = false)
    private String noticeBgnde;  // 문자열 타입으로 변경

    @Column(nullable = false)
    private String noticeEndde;  // 문자열 타입으로 변경

    @Column(nullable = false)
    private String progrmBgnde;  // 문자열 타입으로 변경

    @Column(nullable = false)
    private String progrmEndde;  // 문자열 타입으로 변경

    @Column(nullable = false)
    private Integer progrmRegistNo;

    @Column(columnDefinition = "TEXT", nullable = false)  // 대문자로 변경
    private String progrmSj;

    @Column(nullable = false)
    private Integer progrmSttusSe;

    @Column(nullable = false)
    private Integer sidoCd;  // 정수 타입으로 변경

    @Column(columnDefinition = "TEXT", nullable = false)  // 대문자로 변경
    private String srvcClCode;

    @Column(columnDefinition = "TEXT", nullable = false)  // 대문자로 변경
    private String url;

    @Column(nullable = false)
    private String yngbgsPosblAt;  // 변경된 타입

    @Column(nullable = false)
    private String gugunNm;

    @Column(nullable = false)
    private String sidoNm;

    @Column(nullable = false)
    private String progrmCn;
}