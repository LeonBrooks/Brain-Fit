package com.groupSeventeen;

import com.groupSeventeen.Util.Gate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {
    private final int id;
    private String teamname;
    private List<User> users;
    private int teamPoints;

    public Team(int id, String teamname){
        this.id = id;
        this.teamname = teamname;
        users = new ArrayList<>();
    }

    public Team(int id, String teamname, List<User> users, int teamPoints) {
        this.id = id;
        this.teamname = teamname;
        this.users = users;
        this.teamPoints = teamPoints;
    }

    /** updates User and Team on Server. A call to update user and team after this  method is recommended
     *
     * @param u the user that is added to this team
     * */
    public void addUser(User u){
        u.setTeam(id);
        Gate.sendUserToServer(u);
        users.add(u);
        Gate.sendTeamToServer(this);
    }

    public void removePlayer(int id){
        users.remove(id);
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public int getId() {
        return id;
    }

    public String getTeamname() {
        return teamname;
    }

    public List<User> getUsers() {
        return users;
    }

    public int getTeamPoints() {
        return teamPoints;
    }

    public void addTeamPoints(int points) {
        teamPoints += points;
    }
}
