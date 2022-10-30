package testtask.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testtask.model.Clan;
import testtask.model.Gold;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserAddGoldServiceTest {
    private UserAddGoldService service;
    private Clan clan;
    //@Mock
    ClanService clanService = new ClanServiceInMemory();

    @BeforeEach
    public void setup() {
        clan = new Clan();
        clan.setId(1);
        clan.setName("some");
        clan.setGold(new Gold());
        //MockitoAnnotations.initMocks(this);
        //Mockito.when(clanService.getClan(Mockito.anyInt())).thenReturn(clan);
        clanService.createClan(clan);
        service = new UserAddGoldService(clanService);
    }

    @Test
    void addGoldToClan() {

        service.addGoldToClan(1, 1, 1);
        assertEquals(service.getGoldClanCount(1, 1), 1);
    }

    @Test
    void stressThreadAddGold() {
        int amount = 100;
        Runnable runnable = () -> {
            service.addGoldToClan(1, 1, 1);
        };
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            Thread tr = new Thread(runnable);
            threads.add(tr);
        }
        for (Thread tr : threads) {
            tr.start();
            try {
                tr.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        assertEquals(service.getGoldClanCount(1, 1), 100);
    }
}