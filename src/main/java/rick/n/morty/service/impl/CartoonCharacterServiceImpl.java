package rick.n.morty.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import rick.n.morty.dao.CartoonCharacterDao;
import rick.n.morty.mapper.CartoonCharacterMapper;
import rick.n.morty.model.CartoonCharacter;
import rick.n.morty.service.CartoonCharacterService;
import rick.n.morty.service.HttpService;

@Service
public class CartoonCharacterServiceImpl implements CartoonCharacterService {
    private final CartoonCharacterDao cartoonCharacterDao;
    private final CartoonCharacterMapper mapper;
    private final HttpService httpService;

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
}
