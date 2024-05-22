package com.capstone.uniculture.entity.Notification;

import com.capstone.uniculture.entity.Member.Member;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * [ 알림 Entity ]
 * WHERE 절에 자주 검색되는 member_id 를 인덱싱 하였다
 * -> 다만, 추가-삭제도 자주 이루어지는 Table 인 만큼 모니터링이 필요하다.
 **/
@Entity @Getter @Setter
@Table(indexes = { @Index(name = "idx_member_id", columnList = "member_id") })
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 알림의 타입 - 친구신청, 게시글 알림, 댓글 알림 - 추후 추가 가능성이 있다. **/
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    /** Type 의 종류에 따라 연관된 Entity 의 ID 가 들어간다. (회원 번호, 게시물 번호) **/
    @Column(nullable = false)
    private long relatedNum;

    @Column(nullable = false)
    private String content;

    /** batch deletion 을 통해 효율성을 높이기 위해 바로 제거하지 않기 위해 사용 **/
    @Column(nullable = false)
    private boolean isCheck;

    /* JPA 가 Entity 객체를 생성할때 리플렉션 기능을 사용하기 위한 기본생성자 */
    public Notification() {
    }

    public Notification(NotificationType notificationType, Member member, Long relatedNum, String content){
        this.notificationType = notificationType;
        this.member = member;
        this.relatedNum = relatedNum;
        this.content = content;
    }
    @Builder
    public Notification(Long id, NotificationType notificationType, Member member, Long relatedNum, String content, Boolean isCheck) {
        this.id = id;
        this.notificationType = notificationType;
        this.member = member;
        this.relatedNum = relatedNum;
        this.content = content;
        this.isCheck = isCheck;
    }

    public void check() {
        this.isCheck = true;
    }
}
