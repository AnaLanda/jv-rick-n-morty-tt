package rick.n.morty.mapper;

import org.springframework.stereotype.Component;
import rick.n.morty.dto.CartoonCharacterDto;
import rick.n.morty.dto.LocationDto;
import rick.n.morty.dto.OriginDto;
import rick.n.morty.model.CartoonCharacter;
import rick.n.morty.model.Location;
import rick.n.morty.model.Origin;

@Component
public class CartoonCharacterMapper {
    public CartoonCharacter mapToCartoonCharacter(CartoonCharacterDto dto) {
        CartoonCharacter cartoonCharacter = new CartoonCharacter();
        Origin origin = new Origin(dto.getOrigin().getName(), dto.getOrigin().getUrl());
        Location location = new Location(dto.getLocation().getName(), dto.getLocation().getUrl());
        cartoonCharacter.setName(dto.getName());
        cartoonCharacter.setStatus(dto.getStatus());
        cartoonCharacter.setSpecies(dto.getSpecies());
        cartoonCharacter.setType(dto.getType());
        cartoonCharacter.setGender(dto.getGender());
        cartoonCharacter.setOrigin(origin);
        cartoonCharacter.setLocation(location);
        cartoonCharacter.setImageUrl(dto.getImage());
        cartoonCharacter.setEpisode(dto.getEpisode());
        cartoonCharacter.setUrl(dto.getUrl());
        cartoonCharacter.setCreated(dto.getCreated());
        return cartoonCharacter;
    }

    public CartoonCharacterDto mapToDto(CartoonCharacter cartoonCharacter) {
        CartoonCharacterDto dto = new CartoonCharacterDto();
        OriginDto originDto = new OriginDto(cartoonCharacter.getOrigin().getName(),
                cartoonCharacter.getOrigin().getUrl());
        LocationDto locationDto = new LocationDto(cartoonCharacter.getLocation().getName(),
                cartoonCharacter.getLocation().getUrl());
        dto.setName(cartoonCharacter.getName());
        dto.setStatus(cartoonCharacter.getStatus());
        dto.setSpecies(cartoonCharacter.getSpecies());
        dto.setType(cartoonCharacter.getType());
        dto.setGender(cartoonCharacter.getGender());
        dto.setOrigin(originDto);
        dto.setLocation(locationDto);
        dto.setImage(cartoonCharacter.getImageUrl());
        dto.setEpisode(cartoonCharacter.getEpisode());
        dto.setUrl(cartoonCharacter.getUrl());
        dto.setCreated(cartoonCharacter.getCreated());
        return dto;
    }
}
