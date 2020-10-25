package rick.n.morty.dto;

import java.util.List;
import lombok.Data;

@Data
public class ApiDto {
    private ApiInfoDto info;
    private List<CartoonCharacterDto> results;
}
