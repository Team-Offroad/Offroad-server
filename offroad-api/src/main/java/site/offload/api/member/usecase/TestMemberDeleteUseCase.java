package site.offload.api.member.usecase;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.character.service.GainedCharacterService;
import site.offload.api.charactermotion.service.GainedCharacterMotionService;
import site.offload.api.coupon.service.GainedCouponService;
import site.offload.api.emblem.service.GainedEmblemService;
import site.offload.api.member.dto.request.TestMemberDeleteRequest;
import site.offload.api.member.service.MemberService;
import site.offload.api.member.service.TestMemberService;
import site.offload.api.place.service.VisitedPlaceService;
import site.offload.api.quest.service.CompleteQuestService;
import site.offload.api.quest.service.ProceedingQuestService;
import site.offload.db.member.entity.MemberEntity;
import site.offload.external.discord.DiscordService;
import site.offload.external.discord.DiscordWebhookRequest;
import site.offload.external.discord.Embed;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestMemberDeleteUseCase {

    private final CompleteQuestService completeQuestService;
    private final GainedCharacterService gainedCharacterService;
    private final GainedCharacterMotionService gainedCharacterMotionService;
    private final GainedEmblemService gainedEmblemService;
    private final TestMemberService testMemberService;
    private final MemberService memberService;
    private final ProceedingQuestService proceedingQuestService;
    private final VisitedPlaceService visitedPlaceService;
    private final GainedCouponService gainedCouponService;
    private final DiscordService discordService;

    @Transactional
    public void deleteTestMember(final long memberId, final TestMemberDeleteRequest request) {
        final MemberEntity foundMember = memberService.findById(memberId);
        discordService.sendMessage(
                new DiscordWebhookRequest(
                        String.format("%s님이 회원탈퇴 요청을 보냈습니다.", request.name()),
                        List.of(
                                new Embed(
                                        "탈퇴 요청한 회원",
                                        String.format("회원 ID: %d\n회원 이름: %s", memberId, foundMember.getNickName())
                                )
                        )
                )
        );
        completeQuestService.deleteAllByMemberId(memberId);
        gainedCharacterService.deleteAllByMemberId(memberId);
        gainedCharacterMotionService.deleteAllByMemberId(memberId);
        proceedingQuestService.deleteAllByMemberId(memberId);
        visitedPlaceService.deleteAllByMemberId(memberId);
        gainedCouponService.deleteAllByMemberId(memberId);
        gainedEmblemService.deleteAllByMemberId(memberId);
        testMemberService.deleteTestMember(memberId);
    }
}
