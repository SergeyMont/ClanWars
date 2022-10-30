package testtask.service;

import testtask.model.Clan;
import testtask.repository.ClanRepository;
import testtask.repository.ClanRepositoryImpl;

public class ClanServiceDb implements ClanService{
    private ClanRepository repository;

    public ClanServiceDb(){
        repository = new ClanRepositoryImpl();
        repository.createTableClan();
    }
    @Override
    public Clan getClan(int clanId) {
        return repository.getClan(clanId);
    }

    @Override
    public Clan createClan(Clan clan) {
        return repository.createClan(clan);
    }

    @Override
    public void deleteClan(int clanId) {
        repository.deleteClan(clanId);
    }

    @Override
    public Clan updateClan(Clan clan) {
        return repository.changeClan(clan);
    }
}
