package site.offload.offloadserver.api.emblem.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.offloadserver.api.emblem.dto.request.UpdateCurrentEmblemRequest;
import site.offload.offloadserver.api.emblem.dto.response.GainedEmblemListResponse;
import site.offload.offloadserver.api.emblem.dto.response.GainedEmblemResponse;
import site.offload.offloadserver.api.emblem.service.GainedEmblemService;
import site.offload.offloadserver.api.exception.BadRequestException;
import site.offload.offloadserver.api.exception.NotFoundException;
import site.offload.offloadserver.api.member.service.MemberService;
import site.offload.offloadserver.api.message.ErrorMessage;
import site.offload.offloadserver.db.emblem.entity.Emblem;
import site.offload.offloadserver.db.member.entity.Member;

@Service
@RequiredArgsConstructor
public class EmblemUseCase {

    private final MemberService memberService;
    private final GainedEmblemService gainedEmblemService;

    @Transactional
    public void updateCurrentEmblem(final UpdateCurrentEmblemRequest request) {
        //존재하는 칭호인지 확인
        if (isExistsEmblem(request.emblemName())) {
            final Member findMember = memberService.findById(request.memberId());
            //유저가 얻은 칭호인지 확인
            if (gainedEmblemService.isExistsByMemberAndEmblem(findMember, Emblem.valueOf(request.emblemName()))) {
                findMember.updateEmblemName(Emblem.valueOf(request.emblemName()));
            } else {
                throw new BadRequestException(ErrorMessage.MEMBER_EMBLEM_UPDATE_EXCEPTION);
            }
        } else {
            throw new NotFoundException(ErrorMessage.EMBLEM_NOTFOUND_EXCEPTION);
        }
    }

    private boolean isExistsEmblem(String emblemName) {
        return Emblem.isExistsEmblem(Emblem.valueOf(emblemName));
    }

    @Transactional(readOnly = true)
    public GainedEmblemListResponse getGainedEmblems(Long memberId) {
        return GainedEmblemListResponse.of(gainedEmblemService.findAllByMemberId(memberId).stream().map(
                gainedEmblem -> GainedEmblemResponse.of(gainedEmblem.getEmblemName().getEmblemCodeName(),
                        gainedEmblem.getEmblemName().getEmblemName())).toList());
    }
}
