package com.hikaru.Hawks;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public class PlayerRequest {
    @NotBlank(message = "名前を入力してくだい")
    @Size(max = 100,message = "名前は１００字以内で入力してください")
    private String name;

    @NotNull(message = "背番号を入力してください")
    @Positive(message = "背番号は整数を入力してください")
    private int number;

     @NotBlank(message = "チームを入力してください")
     @Size(max = 100,message = "チームは１００字以内で入力してください")
    private String team;

    public PlayerRequest(String name, int number, String team) {
        this.name = name;
        this.number = number;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getTeam() {
        return team;
    }
}
