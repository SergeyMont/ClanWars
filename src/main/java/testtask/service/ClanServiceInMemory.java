package testtask.service;

import testtask.model.Clan;

import java.util.HashMap;
import java.util.Map;

public class ClanServiceInMemory implements ClanService {
    private final Map<Integer, Clan> map = new HashMap<>();
    private int counter = 0;

    @Override
    public Clan getClan(int clanId) {
        return map.get(clanId);
    }

    @Override
    public Clan createClan(Clan clan) {
        counter++;
        clan.setId(counter);
        map.put(counter, clan);
        return clan;
    }

    @Override
    public void deleteClan(int clanId) {
        map.remove(clanId);
    }

    @Override
    public Clan updateClan(Clan clan) {
        return map.put(clan.getId(), clan);
    }
}
