package com.hikaru.Hawks;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class PlayerController {

    public PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/players")
    public List<Player> findPlayerTeam(@RequestParam(value = "team", required = false) String team) {
        return playerService.findByTeam(team);
    }

    @GetMapping("/players/{id}")
    public Player findPlayer(@PathVariable("id") int id) {
        return playerService.findPlayer(id);
    }


    @PostMapping("/players")
    public ResponseEntity<PlayerResponse> insert(@RequestBody @Validated PlayerRequest playerRequest, UriComponentsBuilder uriBuilder) {
        Player player = playerService.insert(playerRequest.getName(), playerRequest.getNumber(), playerRequest.getTeam());
        URI location = uriBuilder.path("/players/{id}").buildAndExpand(player.getId()).toUri();
        PlayerResponse body = new PlayerResponse("選手情報が登録されました");
        return ResponseEntity.created(location).body(body);
    }

    @PatchMapping("/players/{id}")
    public ResponseEntity<PlayerResponse> update(@PathVariable("id") Integer id, @RequestBody @Validated PlayerRequest playerRequest) {
        Player updatedPlayer = playerService.update(id, playerRequest);
        PlayerResponse body = new PlayerResponse("選手情報が更新されました");
        return ResponseEntity.ok(body);
    }

    @DeleteMapping("/players/{id}")
    public ResponseEntity<PlayerResponse> delete(@PathVariable("id") Integer id){
        playerService.delete(id);
        PlayerResponse body = new PlayerResponse("選手情報が削除されました");
        return ResponseEntity.ok(body);
    }
}
