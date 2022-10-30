package testtask.repository;

import testtask.model.Clan;

public interface ClanRepository {
    void createTableClan();

    Clan createClan(Clan clan);

    void deleteClan(int id);

    Clan changeClan(Clan clan);

    Clan getClan(int id);
}
