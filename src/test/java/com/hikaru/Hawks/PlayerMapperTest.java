package com.hikaru.Hawks;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlayerMapperTest {

    @Autowired
    PlayerMapper playerMapper;

    @Test
    @DataSet(value = "datasets/players.yml")
    @Transactional
    void 選手情報が全件取得できていること() {
        List<Player> players = playerMapper.findAll();
        assertThat(players)
                .hasSize(3)
                .contains(
                        new Player(1, "柳田悠岐", 9, "ソフトバンク"),
                        new Player(2, "イチロー", 55, "マリナーズ"),
                        new Player(3, "大谷翔平", 17, "ドジャース")
                );
    }

    @Test
    @DataSet(value = "datasets/players.yml")
    @Transactional
    void 指定したIDの選手情報が取得できていること() {
        Optional<Player> actual = playerMapper.findById(1);
        assertThat(actual).hasValue(new Player(1,"柳田悠岐",9,"ソフトバンク"));
    }

    @Test
    @DataSet(value = "datasets/players.yml")
    @Transactional
    void 存在しないIDを指定した場合に空が返ること() {
        Optional<Player> actual = playerMapper.findById(0);
        assertThat(actual).isEmpty();
    }

    @Test
    @DataSet(value = "datasets/players.yml")
    @Transactional
    void 選手情報が登録できること() {
        Player player = new Player("ダルビッシュ有",11,"パドレス");
        playerMapper.insertPlayer(player);
        Optional<Player> players = playerMapper.findById(player.getId());
        assertThat(players).isPresent();
    }

    @Test
    @DataSet(value = "datasets/players.yml")
    @Transactional
    void 選手情報が更新できること() {
        Player playerUpdate = new Player(1,"ダルビッシュ有",11,"パドレス");
        playerMapper.updatePlayer(playerUpdate);
        Optional<Player>updatedPlayer = playerMapper.findById(1);
        assertThat(updatedPlayer).isPresent();
        assertThat(updatedPlayer.get()).isEqualTo(playerUpdate);
    }

    @Test
    @DataSet(value = "datasets/players.yml")
    @Transactional
    void 選手情報が削除できること() {
        Integer playerDelete = 1;
        playerMapper.deletePlayer(playerDelete);

        Optional<Player> deletedPlayer = playerMapper.findById(playerDelete);
        assertThat(deletedPlayer).isEmpty();
    }
}
