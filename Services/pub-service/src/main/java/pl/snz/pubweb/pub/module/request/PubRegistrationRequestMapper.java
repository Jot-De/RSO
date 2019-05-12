package pl.snz.pubweb.pub.module.request;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import pl.snz.pubweb.commons.util.Nulls;
import pl.snz.pubweb.pub.module.common.mapper.AddressMapper;
import pl.snz.pubweb.pub.module.common.mapper.Mapper;
import pl.snz.pubweb.pub.module.request.dto.PubRegistrationRequestDto;
import pl.snz.pubweb.pub.module.request.dto.PubRegistrationRequestInfo;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationRequest;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationStatus;

import java.time.LocalDate;

@Mapper
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PubRegistrationRequestMapper {

    private final AddressMapper addressMapper;

    public PubRegistrationRequestInfo toDto(PubRegistrationRequest request) {
        return PubRegistrationRequestInfo.builder()
                .address(addressMapper.toDto(request.getAddress()))
                .added(request.getAdded())
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
        request.setUserId(userId);
        request.setName(dto.getName());
        request.setStatus(PubRegistrationStatus.PENDING);
        return request;
    }
}
