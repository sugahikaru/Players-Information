package com.hikaru.Hawks;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    public PlayerMapper playerMapper;

    public PlayerService(PlayerMapper playerMapper) {
        this.playerMapper = playerMapper;
    }

    public List<Player>findByTeam(String team){
        if(team!=null&&!team.isEmpty()){
            return playerMapper.findByTeamWith(team);
        }else {
            return playerMapper.findAll();
        }
    }

    public Player findPlayer(int id) {
        Optional<Player> user = this.playerMapper.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new lnforNotfoundException("user not found");
        }
    }

    public Player insert(String name, int number,String team) {
        Player player = new Player( name, number,team);
        playerMapper.insertPlayer(player);
        return player;
    }

    public Player update(Integer id,PlayerRequest playerRequest) {
        Player player =playerMapper.findById(id)
                .orElseThrow(()-> new lnforNotfoundException("選手情報が見つかりません"));

        player.setName(playerRequest.getName());
        player.setNumber(playerRequest.getNumber());
        player.setTeam(playerRequest.getTeam());

        playerMapper.updatePlayer(player);
        return player;
    }

    public void delete(Integer id){
        playerMapper.findById(id)
                .orElseThrow(()->new lnforNotfoundException("選手情報が見つかりません"));
        playerMapper.deletePlayer(id);
    }
}