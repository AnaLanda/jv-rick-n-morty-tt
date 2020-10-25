package rick.n.morty.service;

import java.util.List;
import rick.n.morty.model.CartoonCharacter;

public interface CartoonCharacterService {
    CartoonCharacter getRandom();

    List<CartoonCharacter> getByNameFragment(String fragment);

    List<CartoonCharacter> getAllFromApi(String url);

    List<CartoonCharacter> addAll(List<CartoonCharacter> cartoonCharacters);

    List<CartoonCharacter> updateAll(List<CartoonCharacter> cartoonCharacters);
}
