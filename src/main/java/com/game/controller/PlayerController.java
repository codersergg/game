package com.game.controller;

import com.game.entity.Player;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class PlayerController {

    @Autowired
    private PlayerService playerService;


    @GetMapping("/players")
    public ResponseEntity<List<Player>> getAllFilteredPlayers(@RequestParam(required = false, defaultValue = "ID") PlayerOrder order,
                                                              @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                              @RequestParam(required = false, defaultValue = "3") Integer pageSize,
                                                              @RequestParam Map<String, String> params) {

        List<Player> list = playerService.findAllPlayers(PageRequest.of(pageNumber, pageSize, Sort.by(order.getFieldName())), params);
        System.out.println(list);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping("/players/count")
    public Integer getCount(@RequestParam Map<String, String> params) {
        return playerService.getCount(params);
    }


    @GetMapping("/players/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Player player = playerService.getPlayerById(id);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }



    @DeleteMapping("/players/{id}")
    public ResponseEntity<Long> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }


    @PostMapping("/players")
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        Player result = playerService.addPlayer(player);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping("/players/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody Player player) {
        Player result = playerService.updatePlayer(id, player);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}