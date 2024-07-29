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
import site.offload.db.member.entity.MemberEntity;
import site.offload.enums.emblem.Emblem;
import site.offload.enums.response.ErrorMessage;

@Service
@RequiredArgsConstructor
public class EmblemUseCase {

    private final MemberService memberService;
    private final GainedEmblemService gainedEmblemService;

    @Transactional
    public void updateCurrentEmblem(final UpdateCurrentEmblemRequest request) {
        //존재하는 칭호인지 확인
        if (isExistsEmblem(request.emblemCode())) {
            final MemberEntity findMemberEntity = memberService.findById(request.memberId());
            //유저가 얻은 칭호인지 확인
            if (gainedEmblemService.isExistsByMemberAndEmblemCode(findMemberEntity, request.emblemCode())) {
                findMemberEntity.updateEmblemName(Emblem.getEmblemByCode(request.emblemCode()));
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
