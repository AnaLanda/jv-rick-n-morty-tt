package rick.n.morty.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import rick.n.morty.dao.CartoonCharacterDao;
import rick.n.morty.dto.ApiDto;
import rick.n.morty.dto.ApiInfoDto;
import rick.n.morty.dto.CartoonCharacterDto;
import rick.n.morty.mapper.CartoonCharacterMapper;
import rick.n.morty.model.CartoonCharacter;
import rick.n.morty.service.CartoonCharacterService;
import rick.n.morty.service.HttpService;

@Service
public class CartoonCharacterServiceImpl implements CartoonCharacterService {
    CartoonCharacterDao cartoonCharacterDao;
    CartoonCharacterMapper mapper;
    HttpService httpService;

    public CartoonCharacterServiceImpl(CartoonCharacterDao cartoonCharacterDao,
                                       HttpService httpService, CartoonCharacterMapper mapper) {
        this.cartoonCharacterDao = cartoonCharacterDao;
        this.httpService = httpService;
        this.mapper = mapper;
    }

    @Override
    public CartoonCharacter getRandom() {
        return cartoonCharacterDao.getRandom();
    }

    @Override
    public List<CartoonCharacter> getByNameFragment(String fragment) {
        return cartoonCharacterDao.getByNameFragment(fragment);
    }

    @Override
    public List<CartoonCharacter> addAll(List<CartoonCharacter> cartoonCharacters) {
        return cartoonCharacterDao.addAll(cartoonCharacters);
    }

    @Override
    public List<CartoonCharacter> updateAll(List<CartoonCharacter> cartoonCharacters) {
        return null;
    }

    public List<CartoonCharacter> getAllFromApi(String url) {
        ApiDto apiDto = httpService.getData(url, ApiDto.class);
        ApiInfoDto apiInfoDto = apiDto.getInfo();
        List<CartoonCharacterDto> cartoonCharacterDtos = new ArrayList<>();
        int pages = apiInfoDto.getPages();
        int currentPage = 1;
        while (currentPage <= pages) {
            String nextPageUrl = apiInfoDto.getNext();
            cartoonCharacterDtos.addAll(apiDto.getResults());
            if (currentPage == pages) {
                break;
            }
            apiDto = httpService.getData(nextPageUrl, ApiDto.class);
            apiInfoDto = apiDto.getInfo();
            currentPage++;
        }
        return cartoonCharacterDtos.stream()
                .map(mapper::mapToCartoonCharacter)
                .collect(Collectors.toList());
    }
}
