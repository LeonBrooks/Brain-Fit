package com.groupSeventeen.Util;

        import com.groupSeventeen.Match;
        import com.groupSeventeen.Question;
        import com.groupSeventeen.Team;
        import com.groupSeventeen.User;
        import com.groupSeventeen.Usersession;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.Arrays;
        import java.util.HashSet;
        import java.util.Set;
        import java.util.concurrent.ExecutionException;
        import java.util.ArrayList;
        import java.util.List;

public class Gate {

    public static int getIdFormServer(String email){
        HttpGetter get = new HttpGetter();
        get.execute("getID", email);

        try {
            String getResult = get.get();

            return Integer.parseInt(getResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /** get the User with id == uid from the server
     *
     * @param uid the id of the user to be retrieved
     * @return the retrieved user
     *         null if retrieval failed
     */
     public static User getUserFromServer(int uid){

         HttpGetter get = new HttpGetter();
         get.execute("getUser", Integer.toString(uid));

        try {
            String getResult = get.get();

            if (!getResult.equals("{ }")){
                JSONObject json = new JSONObject(getResult);

                int id = json.getInt("id");
                String username = json.getString("username");
                String email = json.getString("email");
                int team = json.getInt("team");
                int points = json.getInt("points");
                int gamesWon = json.getInt("gamesWon");
                int gamesLose = json.getInt("gamesLose");
                int questionsSentOn = json.getInt("questionSentOn");
                int questionsRight = json.getInt("questionsRight");
                int questionsWrong = json.getInt("questionsWrong");

                JSONArray questions = json.getJSONArray("inbox");
                HashSet<Integer> inbox = new HashSet<>();
                for (int i = 0; i < questions.length(); i++) {
                    inbox.add(questions.getInt(i));
                }

                return new User(id, username, email, inbox, team, points, gamesWon, gamesLose, questionsSentOn, questionsRight, questionsWrong);
            } else {
                return null;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**creates a unique user Object on the server
     *
     * @param username the username field of the user to be created
     * @return the newly created user with it's id given by the server and default creation values
     *         null creation failed
     */
    public static User createUserOnServer(String username, String email){
        HttpGetter get = new HttpGetter();
        get.execute("createUser", username, email);

        try {
            String getResult = get.get();

            if (!getResult.equals("{ }")){
                JSONObject json = new JSONObject(getResult);

                int id = json.getInt("id");
                String uName = json.getString("username");
                String mail = json.getString("email");
                int team = json.getInt("team");
                int points = json.getInt("points");
                int gamesWon = json.getInt("gamesWon");
                int gamesLose = json.getInt("gamesLose");
                int questionsSentOn = json.getInt("questionSentOn");
                int questionsRight = json.getInt("questionsRight");
                int questionsWrong = json.getInt("questionsWrong");

                JSONArray questions = json.getJSONArray("inbox");
                HashSet<Integer> inbox = new HashSet<>();
                for (int i = 0; i < questions.length(); i++) {
                    inbox.add(questions.getInt(i));
                }

                return new User(id, uName, mail, inbox, team, points, gamesWon, gamesLose, questionsSentOn, questionsRight, questionsWrong);
            } else {
                return null;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**updated the serverside version of this user (serveridUser.id == user.id)
     * !!! does not include inbox !!!
     * uses getInboxFromServer() and sendInboxToServer() to update inboxes
     *
     * @param user the user to be updated
     */
    public static void sendUserToServer(User user){
        new HttpPoster().execute("updateUser",
                Integer.toString(user.getId()),
                user.getUsername(),
                user.getEmail(),
                Integer.toString(user.getTeam()),
                Integer.toString(user.getPoints()),
                Integer.toString(user.getGamesWon()),
                Integer.toString(user.getGamesLose()),
                Integer.toString(user.getQuestionsSentOn()),
                Integer.toString(user.getQuestionsRight()),
                Integer.toString(user.getQuestionsWrong()));
    }

    /**gets numbersofquestions questions from the inbox of the serverside user with id == userid
     * the retrieved questions are deleted from the serverside inbox
     * a call to update the local user after this method has been used is highly recommended
     *
     * @param userid the id of the user with the inbox of which the questions should be taken from
     * @param numbersofquestions the number of questions to be retrieved
     * @return Set of questions that were retrieved
     *         null if retrieval failed
     */
    public static Set<Question> getInboxFromServer(int userid, int numbersofquestions){
        HttpGetter get = new HttpGetter();
        get.execute("getInbox", Integer.toString(userid),Integer.toString(numbersofquestions));

        try {
            String getResult = get.get();

            if (!getResult.equals("{ }")){
                JSONObject json = new JSONObject(getResult);

                JSONArray questions = json.getJSONArray("inbox");
                HashSet<Question> res = new HashSet<>();
                for (int i = 0; i < questions.length(); i++) {
                    res.add(getQuestionFromServer(questions.getInt(i)));
                }

                return res;
            } else {
                return null;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**adds the question with questionId to the serverside inbox of user with userid
     *
     * @param userid the id of the user to with the inbox to which the question should be added
     * @param questionId the id of the question that should be added
     */
    public static void sendInboxToServer(int userid, int questionId) {
        new HttpPoster().execute("addQuestionToInbox", Integer.toString(userid), Integer.toString(questionId));
    }


    /**retrieves the question with id id from the server
     *
     * @param id the id of the question to be retrieved
     * @return the question to be retrieved
     *         null if retrieval failed
     */
    public static Question getQuestionFromServer(int id){
        HttpGetter get = new HttpGetter();
        get.execute("getQuestion", Integer.toString(id));

        try {
            String getResult = get.get();

            if (!getResult.equals("{ }")){
                JSONObject json = new JSONObject(getResult);

                int qid = json.getInt("id");
                String category = json.getString("category");
                String text = json.getString("text");

                JSONArray answers = json.getJSONArray("answers");
                String[] answerArray = new String[answers.length()];
                for (int i = 0; i < answers.length(); i++) {
                    answerArray[i] = answers.getString(i);
                }

                int correctAnswer = json.getInt("correctAnswer");

                return new Question(qid,category,text,answerArray,correctAnswer);
            } else {
                return null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**creates a question on the server and returns it including the id given to it
     *
     * @param category the category field of the question
     * @param text the text field of the question
     * @param answers the answers field of the question
     * @param correctAnswer the correctAnswer field of the question
     * @return the newly created question
     *         null if creation failed
     */
    public static Question createQuestionOnServer(String category, String text, String[] answers, int correctAnswer){
        HttpGetter get = new HttpGetter();
        String input = "";
        for (int i = 0; i < answers.length; i++) {
            input = input + answers[i] + "\u0001";
        }
        input = input.substring(0,input.length()-1);
        get.execute("createQuestion", category, text.replaceAll("\\?", "\u0001"), input, Integer.toString(correctAnswer));

        try {
            String getResult = get.get();

            if (!getResult.equals("{ }")){
                JSONObject json = new JSONObject(getResult);

                int qid = json.getInt("id");
                String categoryR = json.getString("category");
                String textR = json.getString("text");

                JSONArray answersR = json.getJSONArray("answers");
                String[] answerArray = new String[answersR.length()];
                for (int i = 0; i < answersR.length(); i++) {
                    answerArray[i] = answersR.getString(i);
                }

                int correctAnswerR = json.getInt("correctAnswer");

                return new Question(qid,categoryR,textR,answerArray,correctAnswerR);
            } else {
                return null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**gets numbersofquestions randomly selected questions from the question database
     *
     * @param numbersofquestions the number of questions to be retrieved
     * @return the questions that were retrieved
     *         null if retrieval failed
     */
    public static Set<Question> getRandomQuestionsFromServer(int numbersofquestions){
        HttpGetter get = new HttpGetter();
        get.execute("getRandomQuestions", Integer.toString(numbersofquestions));

        try {
            String getResult = get.get();

            if (!getResult.equals("{ }")){
                JSONObject json = new JSONObject(getResult);

                JSONArray questions = json.getJSONArray("questions");
                HashSet<Question> res = new HashSet<>();
                for (int i = 0; i < questions.length(); i++) {
                    res.add(getQuestionFromServer(questions.getInt(i)));
                }

                return res;
            } else {
                return null;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**returns the serverside version of the team with id teamid
     *
     * @param teamid the id of the team to be retrieved
     * @return the team that was retrieved
     *         null if retrieval failed
     */
    public static Team getTeamFromServer(int teamid){
        HttpGetter get = new HttpGetter();
        get.execute("getTeam", Integer.toString(teamid));

        try {
            String getResult = get.get();

            if (!getResult.equals("{ }")){
                JSONObject json = new JSONObject(getResult);

                int id = json.getInt("id");
                String teamname = json.getString("teamname");
                int points = json.getInt("points");

                JSONArray users = json.getJSONArray("users");
                ArrayList<User> userList = new ArrayList<>();
                for (int i = 0; i < users.length(); i++) {
                    userList.add(getUserFromServer(users.getInt(i)));
                }

                return new Team(id, teamname, userList, points);
            } else {
                return null;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**returns the serverside version of a random existing team
     *
     * @return the team that was retrieved
     *         null if retrieval failed
     */
    public static Team getRandomTeamFromServer(){
        HttpGetter get = new HttpGetter();
        get.execute("getRandomTeam");

        try {
            String getResult = get.get();

            if (!getResult.equals("{ }")){
                JSONObject json = new JSONObject(getResult);

                int id = json.getInt("id");
                String teamname = json.getString("teamname");
                int points = json.getInt("points");

                JSONArray users = json.getJSONArray("users");
                ArrayList<User> userList = new ArrayList<>();
                for (int i = 0; i < users.length(); i++) {
                    userList.add(getUserFromServer(users.getInt(i)));
                }

                return new Team(id, teamname, userList, points);
            } else {
                return null;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    /** retrieves a List of Teams sorted in descending order by Team.teamPoints from the server
     *
     * @param size the number of teams that should be returned in the List
     *
     * @return returns a List of size size if there are enough teams on the server
     *         returns a List with all the teams on the server if that number is < size
     *         returns null if there are no teams on the server
     * */
    public static List<Team> getLeaderboardFromServer(int size){
        HttpGetter get = new HttpGetter();
        get.execute("getLeaderboard", Integer.toString(size));

        try {
            String getResult = get.get();

            if (!getResult.equals("{ }")){
                JSONObject json = new JSONObject(getResult);

                JSONArray leaderboard = json.getJSONArray("leaderboard");
                List<Team> teams = new ArrayList<>();
                for (int i = 0; i < leaderboard.length(); i++) {
                    teams.add(getTeamFromServer(leaderboard.getInt(i)));
                }

                return teams;
            } else {
                return null;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**updates the serverside version of the team team (serversideTeam.id == team.id)
     *
     * @param team the team that should be updated
     */
    public static void sendTeamToServer(Team team){
         int[] ids = new int[team.getUsers().size()];
         for (int i = 0; i < team.getUsers().size(); i++) {
             ids[i] = team.getUsers().get(i).getId();
         }
         String s = Arrays.toString(ids).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "");
         if (s.isEmpty()){
             s = "\u0002";
         }

         new HttpPoster().execute("updateTeam", Integer.toString(team.getId()),team.getTeamname(), s, Integer.toString(team.getTeamPoints()));
    }

    /**creates a team with teamname teamname and default initialisation values on the server
     *
     * @param teamname the name of the team to be created
     * @return the newly created team including the id given by the server
     */
    public static Team createTeamOnServer(String teamname){
        HttpGetter get = new HttpGetter();
        get.execute("createTeam", teamname);

        try {
            String getResult = get.get();

            if (!getResult.equals("{ }")){
                JSONObject json = new JSONObject(getResult);

                int id = json.getInt("id");
                String tname = json.getString("teamname");
                int points = json.getInt("points");

                JSONArray users = json.getJSONArray("users");
                ArrayList<User> userList = new ArrayList<>();
                for (int i = 0; i < users.length(); i++) {
                    userList.add(getUserFromServer(users.getInt(i)));
                }

                return new Team(id, tname, userList, points);
            } else {
                return null;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    /** Multiuse Wonderfunction. Handles the matchmaking, match updating, match abortion and play again functionality.
     *
     * @param match the match from which will be determined what to do
     * @return the following cases are ordered from highest to lowest priority
     * if matched == false: Matches the player to another an returns the match with 0 initialisation and empty lists. If no match can be found returns the input match.
     *
     * if updateQuestionsOnServer == true: Updates the questions on the server and returns a match with both players, matched = true and all other values on default initialisation values.
     *
     * if the question list is empty or both play again values are set to true: checks if the other player updated the questions on the server.
     *                                                                          If that is the case, returns a match with both players, matched = true and all other values on default initialisation values.
     *                                                                          If the questions haven't been updated returns the input match.
     *
     * if either player == null: Tries to stop the match and returns null if successful returns null if unsuccessful returns the input match.
     *
     * else: Updates the serverside match values of sentOn, index, answers and playAgain for this users (Usersession.getInstance().getUser())
     *
     * note: If null is returned the match isn't in progress on the server or there a there was a problem with the server/client communication.
     *       Therefor if over multiple spaced out calls of this function with the same match, null is always returned it should be assumed that the match has been canceled by the other player.
     */
    public static Match sendMatchToServer(Match match){
        if (!match.isMatched()){
            Match m;
            m = matchMe();
            if (m != null){
                return m;
            }

            return  match;
        }

        if (match.isUpdateQuestionsOnServer()){
            List<Integer> quest = new ArrayList<>();
            for (int i = 0; i < match.getQuestions().size(); i++) {
                quest.add(match.getQuestions().get(i).getId());
            }
            Match m = resetMatch(quest);

            return m;
        }

        if (match.getQuestions().isEmpty() || match.isPlayAgain1() && match.isPlayAgain2()){
            return  fetchMatch(match);
        }

        if (match.getPlayer1() == null || match.getPlayer2() == null){
            String res = abortMatch();
            if (res == null){
                return match;
            } else if (res.compareTo("OK") == 0 || res.compareTo("Bad Request") == 0){
                return null;
            } else {
                return match;
            }
        }

        int myid = Usersession.getInstance().getUser().getId();
        if (match.getPlayer1().getId() == myid){
            return  updateMatch(match.getSentOnPlayer1(), match.getIndexPlayer1(), match.getAnswersPlayer1(), match.isPlayAgain1());
        } else {
            return  updateMatch(match.getSentOnPlayer2(), match.getIndexPlayer2(), match.getAnswersPlayer2(), match.isPlayAgain2());
        }
    }

    private static Match matchMe(){
        HttpGetter get = new HttpGetter();
        int myid = Usersession.getInstance().getUser().getId();
        if (Usersession.getInstance().isLocationHandlerSet()){
            double lat = Usersession.getInstance().getLatitude();
            double lon = Usersession.getInstance().getLongitude();
            get.execute("matchMe", Integer.toString(myid), Double.toString(lat), Double.toString(lon));
        } else {
            get.execute("matchMe", Integer.toString(myid), Double.toString(0), Double.toString(0));
        }


        try {
            String[] s = get.get().split(",");
            int playerNum = Integer.parseInt(s[0]);
            int res = Integer.parseInt(s[1]);

            if (res == -1){
                return null;
            } else {
                if (playerNum == 1){
                    return new Match(getUserFromServer(myid), getUserFromServer(res), 0, 0, 0, 0, new ArrayList<Question>(), new ArrayList<Integer>(), new ArrayList<Integer>(), true, false, false);
                }

                if (playerNum == 2){
                    return new Match(getUserFromServer(res), getUserFromServer(myid), 0, 0, 0, 0, new ArrayList<Question>(), new ArrayList<Integer>(), new ArrayList<Integer>(), true, false, false);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return  null;
    }

    private static Match parseMatch(String response){
        try {
            if (!response.equals("{ }")){
                JSONObject json = new JSONObject(response);

                User player1 = getUserFromServer(json.getInt("player1"));
                User player2 = getUserFromServer(json.getInt("player2"));
                int sentOnPlayer1 = json.getInt("sentOnPlayer1");
                int sentOnPlayer2 = json.getInt("sentOnPlayer2");
                int indexPlayer1 = json.getInt("indexPlayer1");
                int indexPlayer2 = json.getInt("indexPlayer2");

                JSONArray questions = json.getJSONArray("questions");
                List<Question> quest = new ArrayList<>();
                for (int i = 0; i < questions.length(); i++) {
                    quest.add(getQuestionFromServer(questions.getInt(i)));
                }

                JSONArray answersPlayer1 = json.getJSONArray("answersPlayer1");
                List<Integer> ans1 = new ArrayList<>();
                for (int i = 0; i < answersPlayer1.length(); i++) {
                    ans1.add(answersPlayer1.getInt(i));
                }

                JSONArray answersPlayer2 = json.getJSONArray("answersPlayer2");
                List<Integer> ans2 = new ArrayList<>();
                for (int i = 0; i < answersPlayer2.length(); i++) {
                    ans2.add(answersPlayer2.getInt(i));
                }

                boolean playAgain1 = json.getBoolean("playAgain1");
                boolean playAgain2 = json.getBoolean("playAgain2");

                return new Match(player1, player2, sentOnPlayer1, sentOnPlayer2, indexPlayer1, indexPlayer2, quest, ans1, ans2, true, playAgain1, playAgain2);
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Match resetMatch(List<Integer> questions){
        HttpGetter get = new HttpGetter();
        int myid = Usersession.getInstance().getUser().getId();
        get.execute("resetMatch", Integer.toString(myid), Arrays.toString(questions.toArray()).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", ""));

        try {
            return parseMatch(get.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return  null;
    }

    private static Match fetchMatch(Match m){
        HttpGetter get = new HttpGetter();
        int myid = Usersession.getInstance().getUser().getId();
        get.execute("fetchMatch", Integer.toString(myid));

        try {
            String res = get.get();
            if (res.compareTo("waiting") == 0){
                return m;
            }
            return parseMatch(res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return  null;
    }

    private static String abortMatch(){
        try {
           return new HttpPoster().execute("abortMatch", Integer.toString(Usersession.getInstance().getUser().getId())).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Match updateMatch(int sentOn, int index, List<Integer> answers, boolean playAgain){
        HttpGetter get = new HttpGetter();
        int myid = Usersession.getInstance().getUser().getId();
        String ans = Arrays.toString(answers.toArray()).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "");
        if (ans.isEmpty()){
            ans = "\u0002";
        }

        get.execute("updateMatch", Integer.toString(myid), Integer.toString(sentOn), Integer.toString(index), ans, Boolean.toString(playAgain));

        try {
            return parseMatch(get.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    /*private static Match simulateMatchServer(Match match) {
        if (match.getPlayer2() == null) {
            return new Match(match.getPlayer1(), new User(2, "Test"), match.getSentOnPlayer1(), match.getSentOnPlayer2(),
                    match.getIndexPlayer1(), match.getIndexPlayer2(), match.getQuestions(), match.getAnswersPlayer1(),
                    match.getAnswersPlayer2(), true, match.isPlayAgain1(), match.isPlayAgain2());
        }

        if (match.getIndexPlayer1() > match.getIndexPlayer2()) {
            match.getAnswersPlayer2().set(match.getIndexPlayer2(), 0);
            return new Match(match.getPlayer1(), match.getPlayer2(), match.getSentOnPlayer1(), match.getSentOnPlayer2(),
                    match.getIndexPlayer1(), match.getIndexPlayer2() + 1, match.getQuestions(), match.getAnswersPlayer1(),
                    match.getAnswersPlayer2(), match.isMatched(), match.isPlayAgain1(), match.isPlayAgain2());
        }

        if (match.isPlayAgain1() && !match.isPlayAgain2()) {
            return new Match(match.getPlayer1(), match.getPlayer2(), match.getSentOnPlayer1(), match.getSentOnPlayer2(),
                    match.getIndexPlayer1(), match.getIndexPlayer2(), match.getQuestions(), match.getAnswersPlayer1(),
                    match.getAnswersPlayer2(), match.isMatched(), match.isPlayAgain1(), true);
        }

        if (match.isUpdateQuestionsOnServer()) {
            return new Match(match.getPlayer1(), match.getPlayer2(), 0, 0,
                    0, 0, match.getQuestions(), match.getAnswersPlayer1(),
                    match.getAnswersPlayer2(), match.isMatched(), false, false);
        }

        return match;
    }

    private static Set<Question> simulateQuestionServer(int id, int number) {
        Set<Question> questions = new HashSet<>();
        for (int i = 0; i < number; i++) {
            questions.add(new Question(1, "Testkategorie", "Testfrage" + i + ", User " + id, new String[] {"Antwort0", "Antwort1",
                    "Antwort2", "Antwort3", "Antwort4", "Antwort5"}, 1));
        }
        return questions;
    }*/
}
