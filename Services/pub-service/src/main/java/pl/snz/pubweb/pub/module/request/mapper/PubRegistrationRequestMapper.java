package pl.snz.pubweb.pub.module.request.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import pl.snz.pubweb.commons.util.Mappers;
import pl.snz.pubweb.commons.util.Nulls;
import pl.snz.pubweb.pub.module.common.mapper.AddressMapper;
import pl.snz.pubweb.pub.module.common.mapper.Mapper;
import pl.snz.pubweb.pub.module.request.dto.PubRegistrationRequestDto;
import pl.snz.pubweb.pub.module.request.dto.PubRegistrationRequestInfo;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationRequest;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationStatus;
import pl.snz.pubweb.pub.module.tag.TagRepository;

import java.time.LocalDate;
import java.util.function.Function;

@Mapper
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PubRegistrationRequestMapper {

    private final AddressMapper addressMapper;
    private final TagRepository tagRepository;

    public PubRegistrationRequestInfo toDto(PubRegistrationRequest request) {
        return PubRegistrationRequestInfo.builder()
                .address(addressMapper.toDto(request.getAddress()))
                .added(request.getAdded())
                .description(request.getDescription())
                .id(request.getId())
                .requesterId(request.getUserId())
                .name(request.getName())
                .status(request.getStatus())
                .pubId(Nulls.npeToNull((r) -> r.getPub().getId(), request))
                .build();
    }

    public PubRegistrationRequest toEntity(PubRegistrationRequestDto dto, Long userId) {
        PubRegistrationRequest request = new PubRegistrationRequest();

        request.setAdded(LocalDate.now());
        request.setAddress(addressMapper.toEntity(dto.getAddress()));
        request.setDescription(dto.getDescription());
        request.setUserId(userId);
        request.setName(dto.getName());
        request.setStatus(PubRegistrationStatus.PENDING);
        if(dto.getTags() != null && !dto.getTags().isEmpty())
            request.setTags(Mappers.set(Function.identity(), tagRepository.findAllById(dto.getTags())));

        return request;
    }
}
