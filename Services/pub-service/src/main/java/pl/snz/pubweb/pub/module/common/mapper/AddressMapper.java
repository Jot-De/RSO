package pl.snz.pubweb.pub.module.common.mapper;

import pl.snz.pubweb.pub.module.common.data.Address;
import pl.snz.pubweb.pub.module.common.dto.AddressDto;

@Mapper
public class AddressMapper {

    public AddressDto toDto(Address address) {
        return AddressDto.builder()
                .city(address.getCity())
                .number(address.getNumber())
                .street(address.getStreet())
                .build();
    }

    public Address toEntity(AddressDto dto) {
        Address address = new Address();
        address.setCity(dto.getCity());
        address.setNumber(dto.getNumber());
        address.setStreet(dto.getStreet());
        return address;
    }
}
