package site.offload.api.util;

import lombok.extern.slf4j.Slf4j;
import site.offload.db.member.entity.MemberEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class MemberDeleteLoggingUtil {

    public static void loggingSoftDeleteMember(final MemberEntity memberEntity) {
        log.info("회원 탈퇴 처리 완료:\n" +
                        "회원 ID: {}\n" +
                        "이름: {}\n" +
                        "이메일: {}\n" +
                        "요청일: {}",
                memberEntity.getId(),
                memberEntity.getName(),
                memberEntity.getEmail(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
