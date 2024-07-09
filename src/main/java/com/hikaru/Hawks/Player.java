package com.hikaru.Hawks;

import java.util.Objects;

public class Player {

    private  Integer id;
    private String name;

    private int number;

    private  String team;

    public Player(Integer id, String name, int number, String team) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.team = team;
    }

    public Player(String name, int number, String team) {
        this.name = name;
        this.number = number;
        this.team = team;
    }

    public Integer getId() {
        return id;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTeam(String team) {
        this.team = team;
    }


    @Override
    public boolean equals(Object o){
        if(this == o)return true;
        if(o == null || getClass() != o.getClass())return false;
        Player player = (Player) o;
        return id == player.id && Objects.equals(name,player.name) && Objects.equals(number,player.number) && Objects.equals(team,player.team);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id,name,number,team);
    }

    @Override
    public String toString() {
        return "{\"id\":" + id + ",\"name\":\"" + name + "\"number\":" + number + "\"team\":" + team +"\"}";
    }
}
