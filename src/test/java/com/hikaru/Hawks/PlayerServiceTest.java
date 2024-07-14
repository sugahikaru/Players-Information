package com.hikaru.Hawks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @InjectMocks
    private PlayerService playerService;

    @Mock
    private PlayerMapper playerMapper;
    @Test
    public void 存在する選手情報のIDを指定した時に正常に選手情報を取得できていること(){
        Player player = new Player(1,"柳田悠岐",9,"ソフトバンク");
        doReturn(Optional.of(player)).when(playerMapper).findById(1);
        Player actual = playerService.findPlayer(1);
        assertThat(actual).isEqualTo(player);
    }

    @Test
    public void 全ての選手情報が取得できる事(){
        List<Player> players = List.of(
                new Player(1,"柳田悠岐",9,"ソフトバンク"),
                new Player(2,"イチロー",55,"マリナーズ"),
                new Player(3,"大谷翔平",17,"ドジャース")
        );
        doReturn(players).when(playerMapper).findAll();
        List<Player> actual = playerService.findByTeam(null);
        assertThat(actual).isEqualTo(players);
        verify(playerMapper).findAll();
    }

    @Test
    public void 存在しないユーザーのIDを指定したときに例外を返すこと() {
        doReturn(Optional.empty()).when(playerMapper).findById(0);
        assertThrows(lnforNotfoundException.class, () -> playerService.findPlayer(0));
    }

    @Test
    public void 新しい選手情報が登録できる事(){
        Player newPlayer = new Player("山田哲人",1,"ヤクルト");
        doNothing().when(playerMapper).insertPlayer(newPlayer);
        playerService.insert(newPlayer.getName(),newPlayer.getNumber(),newPlayer.getTeam());
        verify(playerMapper).insertPlayer(newPlayer);
    }

    @Test
    public void 選手情報が更新できる事(){
        Player existingPlayer = new Player(1,"柳田悠岐",9,"ソフトバンク");
        PlayerRequest updateRequest = new PlayerRequest("ダルビッシュ有",11,"パドレス");
        Player expectedPlayer = new Player(1,"ダルビッシュ有",11,"パドレス");

        doReturn(Optional.of(existingPlayer)).when(playerMapper).findById(1);
        doNothing().when(playerMapper).updatePlayer(any(Player.class));
        Player updatedPlayer = playerService.update(1,updateRequest);
        assertThat(updatedPlayer).isEqualTo(expectedPlayer);
        verify(playerMapper).updatePlayer(updatedPlayer);
    }

    @Test
    public void 存在しないIDの選手情報を更新しようとしたときに例外がスローされること(){
        PlayerRequest updateRequest = new PlayerRequest("ダルビッシュ有",11,"パドレス");
        doReturn(Optional.empty()).when(playerMapper).findById(0);
        assertThatThrownBy(()->playerService.update(0,updateRequest))
                .isInstanceOf(lnforNotfoundException.class)
                .hasMessage("選手情報が見つかりません");
    }

    @Test
    public void 選手情報が削除できる事(){
        Player existingPlayer = new Player(1,"柳田悠岐",9,"ソフトバンク");
        doReturn(Optional.of(existingPlayer)).when(playerMapper).findById(1);
        doNothing().when(playerMapper).deletePlayer(1);
        playerService.delete(1);
        verify(playerMapper).deletePlayer(1);
    }

    @Test
    public void 存在しないIDの選手情報を削除しようとした時に例外をスローされる事(){
        doReturn(Optional.empty()).when(playerMapper).findById(0);
        assertThatThrownBy(()->playerService.delete(0))
                .isInstanceOf(lnforNotfoundException.class)
                .hasMessage("選手情報が見つかりません");
    }
}
