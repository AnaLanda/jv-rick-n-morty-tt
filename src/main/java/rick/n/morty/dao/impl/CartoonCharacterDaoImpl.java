package rick.n.morty.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import rick.n.morty.dao.CartoonCharacterDao;
import rick.n.morty.model.CartoonCharacter;

@Repository
public class CartoonCharacterDaoImpl implements CartoonCharacterDao {
    private static final Logger log = Logger.getLogger(CartoonCharacterDaoImpl.class);
    private final SessionFactory sessionFactory;

    public CartoonCharacterDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CartoonCharacter add(CartoonCharacter cartoonCharacter) {
        Transaction transaction = null;
        Session session = null;
        log.info("Trying to add the character " + cartoonCharacter + " to the DB.");
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(cartoonCharacter);
            transaction.commit();
            log.info("Successfully added the cartoon character "
                    + cartoonCharacter + " to the DB.");
            return cartoonCharacter;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to add the cartoon character "
                    + cartoonCharacter + " to the DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<CartoonCharacter> addAll(List<CartoonCharacter> cartoonCharacters) {
        log.info("Trying to add characters to the DB.");
        List<CartoonCharacter> addedCartoonCharacters = new ArrayList<>();
        for (CartoonCharacter cartoonCharacter : cartoonCharacters) {
            addedCartoonCharacters.add(add(cartoonCharacter));
        }
        log.info("Successfully added all characters to the DB.");
        return addedCartoonCharacters;
    }

    @Override
    public CartoonCharacter getRandom() {
        log.info("Trying to get a random character");
        try (Session session = sessionFactory.openSession()) {
            Query<CartoonCharacter> query = session.createQuery(
                    "SELECT DISTINCT c FROM CartoonCharacter c "
                    + "JOIN FETCH  c.location "
                    + "JOIN FETCH c.origin "
                    + "JOIN FETCH c.episodes "
                    + "ORDER BY RAND()", CartoonCharacter.class);
            return query.setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get a random character");
        }
    }

    @Override
    public List<CartoonCharacter> getAll() {
        log.info("Trying to get all characters...");
        try (Session session = sessionFactory.openSession()) {
            Query<CartoonCharacter> getAllCharactersQuery = session.createQuery(
                    "SELECT DISTINCT c FROM CartoonCharacter c "
                    + "JOIN FETCH c.location "
                    + "JOIN FETCH c.origin "
                    + "JOIN FETCH c.episodes", CartoonCharacter.class);
            return getAllCharactersQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all characters from the DB.", e);
        }
    }

    @Override
    public List<CartoonCharacter> getByNameFragment(String fragment) {
        log.info("Trying to get characters whose name contains " + fragment);
        try (Session session = sessionFactory.openSession()) {
            Query<CartoonCharacter> query = session.createQuery(
                    "SELECT DISTINCT c FROM CartoonCharacter c "
                    + "JOIN FETCH c.location "
                    + "JOIN FETCH  c.origin "
                    + "JOIN FETCH c.episodes "
                    + "WHERE c.name LIKE :name", CartoonCharacter.class);
            query.setParameter("name", "%" + fragment + "%");
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get any characters whose name contains "
                    + fragment, e);
        }
    }

    @Override
    public void remove(CartoonCharacter cartoonCharacter) {
        Transaction transaction = null;
        Session session = null;
        log.info("Trying to remove the character " + cartoonCharacter + " to the DB.");
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.remove(cartoonCharacter);
            transaction.commit();
            log.info("Successfully removed the cartoon character "
                    + cartoonCharacter + " to the DB.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to remove the cartoon character "
                    + cartoonCharacter + " to the DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
