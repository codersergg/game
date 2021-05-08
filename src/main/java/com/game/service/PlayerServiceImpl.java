package com.game.service;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.exceptions.BadRequestException;
import com.game.exceptions.NotFoundException;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Override
    public Player getPlayerById(Long id) {
        Player result = null;
        if (existPlayerById(id))
            result = playerRepository.findAllById(id).stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        return result;
    }

    @Override
    public void deletePlayer(Long id) {

        if (existPlayerById(id))
            playerRepository.deleteById(id);

    }

    @Override
    public Player addPlayer(Player player) {
        if (!isAllParamsFound(player) || !isValidName(player) || !isValidTitle(player) ||
                !isValidExperience(player) || !isValidBirthday(player)) {
            throw new BadRequestException("not found all params");
        } else {
            player.setLevel((int) ((Math.sqrt(2500 + (200 * player.getExperience())) - 50)) / 100);
            player.setUntilNextLevel(50 * (player.getLevel() + 1) * (player.getLevel() + 2) - player.getExperience());
            playerRepository.save(player);
            return player;
        }
    }


    @Override
    public Player updatePlayer(Long id, Player player) {
        Player result;

        if (existPlayerById(id)) {
            result = getPlayerById(id);
        } else throw new BadRequestException("invalid data");
        if (player.getName() != null)
            result.setName(player.getName());
        if (player.getTitle() != null)
            result.setTitle(player.getTitle());
        if (player.getRace() != null)
            result.setRace(player.getRace());
        if (player.getProfession() != null)
            result.setProfession(player.getProfession());
        if (player.getExperience() != null) {
            if (isValidExperience(player)) {
                result.setExperience(player.getExperience());
                result.setLevel((int) ((Math.sqrt(2500 + (200 * player.getExperience())) - 50)) / 100);
                result.setUntilNextLevel(50 * (result.getLevel() + 1) * (result.getLevel() + 2) - result.getExperience());
            } else throw new BadRequestException("invalid experience");
        }
        if (player.getBirthday() != null)
            if (isValidBirthday(player)) {
                result.setBirthday(player.getBirthday());
            } else
                throw new BadRequestException("invalid birthday");
        if (player.getBanned() != null)
            result.setBanned(player.getBanned());
        playerRepository.save(result);

        return result;
    }

    @Override
    public List<Player> findAllPlayers(Pageable pageable, Map<String, String> params) {

        List<Player> result = null;

        if (params.isEmpty()) {
            Page<Player> pagedResult = playerRepository.findAll(pageable);
            result = pagedResult.getContent();
        } else {

            String name = params.getOrDefault("name", null);
            String title = params.getOrDefault("title", null);
            Race race = params.containsKey("race") ? Race.valueOf(params.get("race")) : null;
            Profession profession = params.containsKey("profession") ? Profession.valueOf(params.get("profession")) : null;
            Date after = params.containsKey("after") ? new Date(Long.parseLong(params.get("after"))) : null;
            Date before = params.containsKey("before") ? new Date(Long.parseLong(params.get("before"))) : null;
            Boolean banned = params.containsKey("banned") ? Boolean.valueOf(params.get("banned")) : null;
            Integer minExperience = params.containsKey("minExperience") ? Integer.valueOf(params.get("minExperience")) : null;
            Integer maxExperience = params.containsKey("maxExperience") ? Integer.valueOf(params.get("maxExperience")) : null;
            Integer minLevel = params.containsKey("minLevel") ? Integer.valueOf(params.get("minLevel")) : null;
            Integer maxLevel = params.containsKey("maxLevel") ? Integer.valueOf(params.get("maxLevel")) : null;
            Integer untilNextLevel = params.containsKey("untilNextLevel") ? Integer.valueOf(params.get("untilNextLevel")) : null;


            Page<Player> pagedResult = playerRepository.findAllByParam(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel, untilNextLevel, pageable);
            result = pagedResult.getContent();
        }


        return result;
    }


    @Override
    public Integer getCount(Map<String, String> params) {
        String name = params.getOrDefault("name", null);
        String title = params.getOrDefault("title", null);
        Race race = params.containsKey("race") ? Race.valueOf(params.get("race")) : null;
        Profession profession = params.containsKey("profession") ? Profession.valueOf(params.get("profession")) : null;
        Date after = params.containsKey("after") ? new Date(Long.parseLong(params.get("after"))) : null;
        Date before = params.containsKey("before") ? new Date(Long.parseLong(params.get("before"))) : null;
        Boolean banned = params.containsKey("banned") ? Boolean.valueOf(params.get("banned")) : null;
        Integer minExperience = params.containsKey("minExperience") ? Integer.valueOf(params.get("minExperience")) : null;
        Integer maxExperience = params.containsKey("maxExperience") ? Integer.valueOf(params.get("maxExperience")) : null;
        Integer minLevel = params.containsKey("minLevel") ? Integer.valueOf(params.get("minLevel")) : null;
        Integer maxLevel = params.containsKey("maxLevel") ? Integer.valueOf(params.get("maxLevel")) : null;
        Integer untilNextLevel = params.containsKey("untilNextLevel") ? Integer.valueOf(params.get("untilNextLevel")) : null;

        return playerRepository.countByParams(name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel, untilNextLevel).size();
    }


    @Override
    public boolean existPlayerById(Long id) {
        if (!isValidId(id)) {
            //400
            throw new BadRequestException("invalid number");
        } else if (!playerRepository.existsById(id)) {
            //404
            throw new NotFoundException("invalid id");

        }
        return true;
    }

    public boolean isValidId(Long id) {
        boolean isNumber = id.toString().matches("[0-9]+");
        boolean isPositiveNumber = id > 0;
        boolean isIntegerNumber = id.toString().matches("[0-9]+[.]?[0-9]*");
        return isNumber && isPositiveNumber && isIntegerNumber;
    }

    public boolean isValidName(Player player) {
        return !player.getName().isEmpty() && player.getName().length() <= 12;
    }

    public boolean isValidTitle(Player player) {
        return !player.getTitle().isEmpty() && player.getTitle().length() <= 30;
    }

    public boolean isValidExperience(Player player) {
        return player.getExperience() >= 0 && player.getExperience() <= 10000000;
    }

    public boolean isValidBirthday(Player player) {
        return player.getBirthday().getTime() >= 0 && isValidDateRange(player.getBirthday());
    }

    public boolean isAllParamsFound(Player player) {
        return player.getName() != null && player.getTitle() != null && player.getProfession() != null &&
                player.getRace() != null && player.getExperience() != null && player.getBirthday() != null;
    }

    public boolean isValidDateRange(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = formatter.parse("2000-01-01");
            Date endDate = formatter.parse("3000-12-31");
            if (date.after(startDate) && date.before(endDate))
                return true;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}