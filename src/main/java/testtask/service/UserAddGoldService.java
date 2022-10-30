package testtask.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@AllArgsConstructor
public class UserAddGoldService {
    private final ClanService clanService;
    final Logger log = LoggerFactory.getLogger(UserAddGoldService.class);

    public void addGoldToClan(int userId, int clanId, int gold) {
        int old = getGoldClanCount(userId, clanId);
        log.info("UserId#{} add {} gold to clanId#{}. Old value of gold {}", userId, gold, clanId, old);
        int result = clanService.getClan(clanId).getGold().change(gold);
        log.info("ClanId#{} gold is {}", clanId, result);
    }

    public int getGoldClanCount(int userId, int clanId) {
        return clanService.getClan(clanId).getGold().get();
    }
}
