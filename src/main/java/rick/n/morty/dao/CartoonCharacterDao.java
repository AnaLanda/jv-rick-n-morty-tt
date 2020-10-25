package rick.n.morty.dao;

import java.util.List;
import rick.n.morty.model.CartoonCharacter;

public interface CartoonCharacterDao {
    CartoonCharacter add(CartoonCharacter cartoonCharacter);

    List<CartoonCharacter> addAll(List<CartoonCharacter> cartoonCharacters);

    CartoonCharacter getRandom();

    List<CartoonCharacter> getAll();

    List<CartoonCharacter> getByNameFragment(String fragment);

    void remove(CartoonCharacter cartoonCharacter);
}
