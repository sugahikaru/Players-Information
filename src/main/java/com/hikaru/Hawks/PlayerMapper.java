package com.hikaru.Hawks;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PlayerMapper {
    @Select("SELECT * FROM players")
    List<Player> findAll();

    @Select("SELECT * FROM players WHERE team LIKE CONCAT(#{team}, '%')")
    List<Player>findByTeamWith(String team);

    @Select("SELECT * FROM players WHERE id = #{id}")
    Optional<Player> findById(int id);

    @Insert("INSERT INTO players (name, number, team) VALUES (#{name}, #{number}, #{team})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertPlayer(Player player);

    @Update("UPDATE players SET name=#{name}, number=#{number}, team=#{team} WHERE id=#{id}")
    void updatePlayer(Player player);

    @Delete("DELETE FROM players WHERE id=#{id}")
    void deletePlayer (Integer id);

}
