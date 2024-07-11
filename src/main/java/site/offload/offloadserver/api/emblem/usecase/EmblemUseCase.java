package site.offload.offloadserver.api.emblem.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.offloadserver.api.emblem.dto.request.UpdateCurrentEmblemRequest;
import site.offload.offloadserver.api.exception.NotFoundException;
import site.offload.offloadserver.api.member.service.MemberService;
import site.offload.offloadserver.api.message.ErrorMessage;
import site.offload.offloadserver.db.emblem.entity.Emblem;
import site.offload.offloadserver.db.member.entity.Member;

@Service
@RequiredArgsConstructor
public class EmblemUseCase {

    private final MemberService memberService;

    @Transactional
    public void updateCurrentEmblem(UpdateCurrentEmblemRequest request) {
        if (isExistsEmblem(request.emblemName())) {
            Member findMember = memberService.findById(request.memberId());
            findMember.updateEmblemName(Emblem.valueOf(request.emblemName()));
        } else {
            throw new NotFoundException(ErrorMessage.EMBLEM_NOTFOUND_EXCEPTION);
        }
    }

    private boolean isExistsEmblem(String emblemName) {
        return Emblem.isExistsEmblem(Emblem.valueOf(emblemName));
    }
}
