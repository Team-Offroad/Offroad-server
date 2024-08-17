package site.offload.api.member.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.offload.api.character.service.CharacterService;
import site.offload.api.character.service.GainedCharacterService;
import site.offload.api.charactermotion.service.CharacterMotionService;
import site.offload.api.charactermotion.service.GainedCharacterMotionService;
import site.offload.api.emblem.service.GainedEmblemService;
import site.offload.api.exception.BadRequestException;
import site.offload.api.exception.NotFoundException;
import site.offload.api.member.dto.request.MemberAdventureInformationRequest;
import site.offload.api.member.dto.request.MemberProfileUpdateRequest;
import site.offload.api.member.dto.response.*;
import site.offload.api.member.service.MemberService;
import site.offload.api.place.service.VisitedPlaceService;
import site.offload.api.quest.service.CompleteQuestService;
import site.offload.api.util.TimeUtil;
import site.offload.db.character.entity.CharacterEntity;
import site.offload.db.charactermotion.entity.CharacterMotionEntity;
import site.offload.db.member.embeddable.Birthday;
import site.offload.db.member.entity.MemberEntity;
import site.offload.enums.place.PlaceCategory;
import site.offload.enums.response.ErrorMessage;
import site.offload.external.aws.S3Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberUseCase {

    private final MemberService memberService;
    private final CharacterService characterService;
    private final CharacterMotionService characterMotionService;
    private final GainedCharacterMotionService gainedCharacterMotionService;
    private final GainedCharacterService gainedCharacterService;
    private final S3Service s3Service;
    private final GainedEmblemService gainedEmblemService;
    private final CompleteQuestService completeQuestService;
    private final VisitedPlaceService visitedPlaceService;

    @Transactional(readOnly = true)
    public MemberAdventureInformationResponse getMemberAdventureInformation(final MemberAdventureInformationRequest request) {
        final MemberEntity findMemberEntity = memberService.findById(request.memberId());
        final String nickname = findMemberEntity.getNickName();
        final String emblemName = findMemberEntity.getCurrentEmblemName();

        if (findMemberEntity.getNickName() == null && findMemberEntity.getCurrentCharacterName() == null) {
            return MemberAdventureInformationResponse.of(null, emblemName, null, null, null);
        }

        if (findMemberEntity.getNickName() != null && findMemberEntity.getCurrentCharacterName() == null) {
            return MemberAdventureInformationResponse.of(findMemberEntity.getNickName(), emblemName, null, null, null);
        }

        final CharacterEntity findCharacterEntity = characterService.findByName(findMemberEntity.getCurrentCharacterName());
        // 사용자가 획득한 캐릭터인지 확인
        if (!gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(findMemberEntity, findCharacterEntity)) {
            throw new BadRequestException(ErrorMessage.NOT_GAINED_CHARACTER);
        }

        // 존재하는 장소 카테고리인지 확인
        if (!PlaceCategory.isExistsCategory(request.category())) {
            throw new NotFoundException(ErrorMessage.PLACE_CATEGORY_NOTFOUND_EXCEPTION);
        }

        final PlaceCategory placeCategory = PlaceCategory.valueOf(request.category().toUpperCase());
        final String motionImageUrl = getMotionImageUrl(placeCategory, findCharacterEntity, findMemberEntity);
        final String baseImageUrl = findCharacterEntity.getCharacterBaseImageUrl();
        return MemberAdventureInformationResponse.of(nickname, emblemName,
                s3Service.getPresignUrl(baseImageUrl), s3Service.getPresignUrl(motionImageUrl), findCharacterEntity.getName());
    }

    @Transactional
    public void updateMemberProfile(Long memberId, MemberProfileUpdateRequest memberProfileUpdateRequest) {
        final MemberEntity findMemberEntity = memberService.findById(memberId);
        Birthday birthday = new Birthday(memberProfileUpdateRequest.year(), memberProfileUpdateRequest.month(), memberProfileUpdateRequest.day());
        findMemberEntity.updateProfile(memberProfileUpdateRequest.nickname(), birthday, memberProfileUpdateRequest.gender());
    }

    @Transactional(readOnly = true)
    public boolean checkNickname(String nickname) {
        return memberService.isExistsByNickname(nickname);
    }

    @Transactional
    public ChooseCharacterResponse chooseCharacter(Long memberId, Integer characterId) {
        final MemberEntity findMemberEntity = memberService.findById(memberId);
        final CharacterEntity findCharacterEntity = characterService.findById(characterId);
        findMemberEntity.chooseCharacter(findCharacterEntity.getName());
        if (!gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(findMemberEntity, findCharacterEntity)) {
            gainedCharacterService.saveGainedCharacter(findMemberEntity, findCharacterEntity);
            gainedEmblemService.save(findMemberEntity, "TT000009");
        }
        return ChooseCharacterResponse.of(s3Service.getPresignUrl(findCharacterEntity.getCharacterSpotLightImageUrl()));
    }

    @Transactional(readOnly = true)
    public GainedCharactersResponse getGainedCharacters(Long memberId) {
        List<CharacterEntity> characterEntities = characterService.findAll();

        MemberEntity memberEntity = memberService.findById(memberId);

        List<GainedCharacterResponse> gainedCharacters = characterEntities.stream()
                .filter(characterEntity -> gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(memberEntity, characterEntity))
                .map(characterEntity -> GainedCharacterResponse.of(characterEntity.getId(), characterEntity.getName(), s3Service.getPresignUrl(characterEntity.getCharacterBaseImageUrl()), characterEntity.getCharacterMainColorCode(), characterEntity.getCharacterSubColorCode(), gainedCharacterService.findByMemberEntityAndCharacterEntity(memberEntity, characterEntity).isNewGained()))
                .collect(Collectors.toList());

        List<GainedCharacterResponse> notGainedCharacters = characterEntities.stream()
                .filter(characterEntity -> !gainedCharacterService.isExistsGainedCharacterByMemberAndCharacter(memberEntity, characterEntity))
                .map(characterEntity -> GainedCharacterResponse.of(characterEntity.getId(), characterEntity.getName(), s3Service.getPresignUrl(characterEntity.getNotGainedCharacterThumbnailImageUrl()), characterEntity.getCharacterMainColorCode(), characterEntity.getCharacterSubColorCode(), false))
                .collect(Collectors.toList());

        return GainedCharactersResponse.of(gainedCharacters, notGainedCharacters);
    }

    @Transactional(readOnly = true)
    public UserInfoResponse getUserInfo(final Long memberId) {
        final MemberEntity memberEntity = memberService.findById(memberId);
        final Long elapsedDays = TimeUtil.getElapsedDay(memberEntity.getCreatedAt());
        return UserInfoResponse.of(
                memberEntity.getNickName(),
                memberEntity.getCurrentEmblemName(),
                elapsedDays,
                visitedPlaceService.countByMember(memberEntity),
                completeQuestService.countByMember(memberEntity)
        );
    }

    private String getMotionImageUrl(final PlaceCategory placeCategory, final CharacterEntity characterEntity, final MemberEntity memberEntity) {

        // 카테고리가 없는 장소에 있을 경우 null 반환
        if (placeCategory.equals(PlaceCategory.NONE)) {
            return null;
        }

        // 그 외의 경우에는 특정 캐릭터, 특정 카테고리에 해당하는 모션 이미지 url 반환
        final CharacterMotionEntity findCharacterMotionEntity = characterMotionService.findByCharacterAndPlaceCategory(characterEntity, placeCategory);

        //유저가 획득한 모션인지 확인
        if (isMemberGainedMotion(findCharacterMotionEntity, memberEntity)) {
            return findCharacterMotionEntity.getMotionImageUrl();
            //아니라면 null 반환
        } else {
            return null;
        }
    }

    private boolean isMemberGainedMotion(final CharacterMotionEntity characterMotionEntity, final MemberEntity memberEntity) {
        return gainedCharacterMotionService.isExistByCharacterMotionAndMember(characterMotionEntity, memberEntity);
    }

}

