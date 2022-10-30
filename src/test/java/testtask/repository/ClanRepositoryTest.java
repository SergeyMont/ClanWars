package testtask.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testtask.model.Clan;
import testtask.model.Gold;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ClanRepositoryTest {
    private final ClanRepository repository = new ClanRepositoryImpl();
    private static Clan clan = new Clan();

    @BeforeAll
    static void setup() {
        clan.setName("some");
        clan.setGold(new Gold());
        clan.getGold().change(0);
    }

    @BeforeEach
    void create() {
        repository.createTableClan();
    }

    @Test
    void createClan() {
        Clan result = repository.createClan(clan);
        assertEquals(1, result.getId());
    }

    @Test
    void deleteClan() {
        Clan result = repository.createClan(clan);
        repository.deleteClan(result.getId());
        repository.getClan(result.getId());
    }

    @Test
    void changeClan() {
        repository.createClan(clan);
        clan.setName("another");
        Clan result = repository.changeClan(clan);
        assertEquals(result, repository.getClan(clan.getId()));
    }

    @Test
    void getClan() {
        Clan result = repository.createClan(clan);
        assertEquals(result, repository.getClan(result.getId()));
    }
}