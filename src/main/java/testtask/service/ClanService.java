package testtask.service;

import testtask.model.Clan;

public interface ClanService {
    Clan getClan(int clanId);

    Clan createClan(Clan clan);

    void deleteClan(int clanId);

    Clan updateClan(Clan clan);
}
