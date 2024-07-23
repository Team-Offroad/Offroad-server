package site.offload.api.emblem.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.emblem.dto.request.UpdateCurrentEmblemRequest;
import site.offload.api.emblem.dto.response.GainedEmblemListResponse;
import site.offload.api.emblem.dto.response.GainedEmblemResponse;
import site.offload.api.emblem.service.GainedEmblemService;
import site.offload.api.exception.BadRequestException;
import site.offload.api.exception.NotFoundException;
import site.offload.api.member.service.MemberService;
import sites.offload.db.member.entity.Member;
import sites.offload.enums.Emblem;
import sites.offload.enums.ErrorMessage;

@Service
@RequiredArgsConstructor
public class EmblemUseCase {

    private final MemberService memberService;
    private final GainedEmblemService gainedEmblemService;

    @Transactional
    public void updateCurrentEmblem(final UpdateCurrentEmblemRequest request) {
        //존재하는 칭호인지 확인
        if (isExistsEmblem(request.emblemCode())) {
            final Member findMember = memberService.findById(request.memberId());
            //유저가 얻은 칭호인지 확인
            if (gainedEmblemService.isExistsByMemberAndEmblemCode(findMember, request.emblemCode())) {
                findMember.updateEmblemName(Emblem.getEmblemByCode(request.emblemCode()));
            } else {
                throw new BadRequestException(ErrorMessage.MEMBER_EMBLEM_UPDATE_EXCEPTION);
            }
        } else {
            throw new NotFoundException(ErrorMessage.EMBLEM_NOTFOUND_EXCEPTION);
        }
    }

    private boolean isExistsEmblem(String emblemCode) {
        return Emblem.isExistsEmblem(emblemCode);
    }

    @Transactional(readOnly = true)
    public GainedEmblemListResponse getGainedEmblems(Long memberId) {
        return GainedEmblemListResponse.of(gainedEmblemService.findAllByMemberId(memberId).stream().map(
                gainedEmblem -> GainedEmblemResponse.of(gainedEmblem.getEmblemCode(),
                        Emblem.getEmblemByCode(gainedEmblem.getEmblemCode()).getEmblemName())).toList());
    }
}
