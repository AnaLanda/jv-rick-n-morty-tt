package rick.n.morty.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationDto {
    private String name;
    private String url;

    public LocationDto(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
