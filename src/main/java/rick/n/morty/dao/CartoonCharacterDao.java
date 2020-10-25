package rick.n.morty.dao;

import java.util.List;
import rick.n.morty.model.CartoonCharacter;

public interface CartoonCharacterDao {
    CartoonCharacter addCartoonCharacter(CartoonCharacter cartoonCharacter);

    List<CartoonCharacter> addAll(List<CartoonCharacter> cartoonCharacters);

    CartoonCharacter getRandom();

    List<CartoonCharacter> getByNameFragment(String fragment);
}
