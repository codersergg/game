package com.game.service;


import com.game.entity.Player;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;

public interface PlayerService {

    boolean existPlayerById(Long id);
    Player getPlayerById(Long id);
    void deletePlayer(Long id);
    Player addPlayer(Player player);
    Player updatePlayer(Long id, Player player);
    Integer getCount(Map<String, String> params);

    List<Player> findAllPlayers(Pageable pageable, Map<String, String> params);

}