package com.hikaru.Hawks;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlayerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DataSet(value = "datasets/players.yml")
    @Transactional
    void ユーザーが全件取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/players"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("""
                        [
                           {
                             "id": 1,
                             "name": "柳田悠岐",
                             "number": 9,
                             "team":"ソフトバンク"
                           },
                           {
                             "id": 2,
                             "name": "イチロー",
                             "number": 55,
                             "team":"マリナーズ"
                           },
                           {
                             "id": 3,
                             "name": "大谷翔平",
                             "number": 17,
                             "team":"ドジャース"
                           }
                        ]
                        """
                ));
    }

    @Test
    @DataSet(value = "datasets/players.yml")
    @Transactional
    void 存在する選手のIDを指定して取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/players/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                             "id": 1,
                             "name": "柳田悠岐",
                             "number": 9,
                             "team":"ソフトバンク"
                         }
                         """
                ));
    }

    @Test
    @DataSet(value = "datasets/players.yml")
    @Transactional
    void 存在しない選手のIDを指定したときに404を返されること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/players/4"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                             "message": "user not found"
                         }
                         """
                ));
    }

    @Test
    @DataSet(value = "datasets/players.yml")
    @Transactional
    void 新規選手を登録できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                             "name": "ダルビッシュ有",
                             "number": 11,
                             "team":"パドレス"
                         }
                         """))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.message").value("選手情報が登録されました"));
    }

    @Test
    @DataSet(value = "datasets/players.yml")
    @Transactional
    void 不正のリクエストボディを送り新規登録をしようとした際に例外の400のエラーが返ってくること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                             "name": "ダルビッシュ有",
                             "number": -11,
                             "team":"パドレス"
                         }
                         """))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.number").value("背番号は整数を入力してください"));
    }

    @Test
    @DataSet(value = "datasets/players.yml")
    @Transactional
    void 指定したIDの選手情報を更新できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/players/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                     "name": "ダルビッシュ有",
                                     "number": 11,
                                     "team":"パドレス"
                                 }
                                 """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("選手情報が更新されました"));

        mockMvc.perform(MockMvcRequestBuilders.get("/players/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("ダルビッシュ有"))
                .andExpect(jsonPath("$.number").value(11))
                .andExpect(jsonPath("$.team").value("パドレス"));
    }

    @Test
    @DataSet(value = "datasets/players.yml")
    @Transactional
    void 存在しないIDの選手情報を更新しようとすると例外の404のエラーが返ってくること() throws Exception {
        var player = new Player("山田哲人", 1, "o");
        var objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.patch("/players/5")
                        .content(objectMapper.writeValueAsString(player))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("選手情報が見つかりません"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/players/5"));
    }

    @Test
    @DataSet(value = "datasets/players.yml")
    @Transactional
    void 指定したIDの選手を削除すること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/players/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                               {
                                 "message": "選手情報が削除されました"
                               }
                            """
                ));
    }

    @Test
    @DataSet(value = "datasets/players.yml")
    @Transactional
    void 選手を削除する時に指定したIDが存在しない場合に例外の404のエラーが返ってくること () throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/players/5"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("404"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("選手情報が見つかりません"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Not Found"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.path").value("/players/5"));
    }
}

