package rick.n.morty.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import rick.n.morty.dto.CartoonCharacterDto;
import rick.n.morty.dto.LocationDto;
import rick.n.morty.dto.OriginDto;
import rick.n.morty.model.CartoonCharacter;
import rick.n.morty.model.Episode;
import rick.n.morty.model.Location;
import rick.n.morty.model.Origin;

@Component
public class CartoonCharacterMapper {
    public CartoonCharacter mapToCartoonCharacter(CartoonCharacterDto dto) {
        CartoonCharacter cartoonCharacter = new CartoonCharacter();
        cartoonCharacter.setName(dto.getName());
        cartoonCharacter.setStatus(dto.getStatus());
        cartoonCharacter.setSpecies(dto.getSpecies());
        cartoonCharacter.setType(dto.getType());
        cartoonCharacter.setGender(dto.getGender());
        Origin origin = new Origin(dto.getOrigin().getName(), dto.getOrigin().getUrl());
        cartoonCharacter.setOrigin(origin);
        Location location = new Location(dto.getLocation().getName(), dto.getLocation().getUrl());
        cartoonCharacter.setLocation(location);
        cartoonCharacter.setImageUrl(dto.getImage());
        List<Episode> episodes = new ArrayList<>();
        for (String episodeUrl : dto.getEpisode()) {
            Episode episode = new Episode(episodeUrl);
            episodes.add(episode);
        }
        cartoonCharacter.setEpisodes(episodes);
        cartoonCharacter.setUrl(dto.getUrl());
        cartoonCharacter.setCreated(dto.getCreated());
        return cartoonCharacter;
    }

    public CartoonCharacterDto mapToDto(CartoonCharacter cartoonCharacter) {
        CartoonCharacterDto dto = new CartoonCharacterDto();
        dto.setName(cartoonCharacter.getName());
        dto.setStatus(cartoonCharacter.getStatus());
        dto.setSpecies(cartoonCharacter.getSpecies());
        dto.setType(cartoonCharacter.getType());
        dto.setGender(cartoonCharacter.getGender());
        OriginDto originDto = new OriginDto(cartoonCharacter.getOrigin().getName(),
                cartoonCharacter.getOrigin().getUrl());
        dto.setOrigin(originDto);
        LocationDto locationDto = new LocationDto(cartoonCharacter.getLocation().getName(),
                cartoonCharacter.getLocation().getUrl());
        dto.setLocation(locationDto);
        dto.setImage(cartoonCharacter.getImageUrl());
        List<String> episodes = new ArrayList<>();
        for (Episode episode : cartoonCharacter.getEpisodes()) {
            episodes.add(episode.getUrl());
        }
        dto.setEpisode(episodes);
        dto.setUrl(cartoonCharacter.getUrl());
        dto.setCreated(cartoonCharacter.getCreated());
        return dto;
    }
}
