package rick.n.morty.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rick.n.morty.dto.CartoonCharacterDto;
import rick.n.morty.mapper.CartoonCharacterMapper;
import rick.n.morty.service.CartoonCharacterService;
import rick.n.morty.service.HttpService;

@RestController
@RequestMapping("/characters")
public class CartoonCharacterController {
    private final HttpService httpService;
    private final CartoonCharacterMapper mapper;
    private final CartoonCharacterService service;

    public CartoonCharacterController(HttpService httpService,
                                      CartoonCharacterMapper mapper,
                                      CartoonCharacterService service) {
        this.httpService = httpService;
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping("/random")
    public CartoonCharacterDto getRandomCharacter() {
        return mapper.mapToDto(service.getRandom());
    }

    @GetMapping(value = "/{fragment}")
    public List<CartoonCharacterDto> getRandomCharacter(@PathVariable String fragment) {
        return service.getByNameFragment(fragment).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
}
