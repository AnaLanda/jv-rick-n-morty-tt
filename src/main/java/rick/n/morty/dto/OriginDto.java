package rick.n.morty.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OriginDto {
    private String name;
    private String url;

    public OriginDto(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
