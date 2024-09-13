# Brain-Fit

<p align="center">
<img src="Brain%20Fit%20App\Screenshot.png" alt="Screenshot" class="center" height="480">
</p>

> The Questions Server is currently not working due to Version conflicts. Resolving these is a current WIP. Once the Server is functioning again, I will post a full guide on how to build/use the app.

## Concept
This app was created as a five-person group project as part of my Social Gaming lab course during university. It adds a new twist to the classical quiz app through a unique way of including team play and jogging.

Our primary task when creating this app was to ensure it makes use of a long- and short-term social context as well as the mobile context. The primary gameplay loop allows two players to compete in a quiz match. They earn points by answering questions correctly, if their opponent wasn't able to answer that same question (shot-term social context). Players are matched by physical location with their opponent being the closet other player queueing at the same time. The app is intended to be used while jogging and makes use of text-to-speech and voice input to enable hands free operation. Additionally, players will be given less possible answer options the faster they are running (mobile context).

To play, users have to create or join a team. This is important because during each match the player can choose one question, which they do not want to attempt to answer, to forward to a teammate (if no other question is selected, the last one is forwarded by default). To avoid cheating, questions are sorted into categories and the player must choose whether to answer or forward a question only knowing the category and not the exact question, which is only read after choosing to answer it. At the end of the match the player can choose which teammate they want to forward the question to and it will then appear for the teammate the next time he plays a match. This encourages getting to know the interest and areas of expertise of teammates to ensure questions are forwarded to a player best suited to answer them (long-term social context).


## Tech Stack
The app was built in Java using [Android Studio][AS]. User logins are handled over [Firebase][FB] while location data is extracted via the [Google Maps API][MAPI]. The Question Server is based on the [Play Framework][Play] and utilizes [MongoDB][MongoDB] with [Jongo][Jongo] as database.

## My Contribution
Besides conceptualizing and planning out the overall development of the app as a group effort, I was responsible for creating the Question Server. It stores all user, team and question data and handles the matchmaking of players. This task involved designing and implementing all database objects and the server/app interface in close coordination with my team as well as providing the facilities for the matchmaking algorithm (simple location-based queueing) and ensuring all data objects are properly converted, entered and retrieved from the database.


## Demo
This [demo video][Demo] provides an overview of the overall gameplay and highlights the voice input.


[Demo]: <https://youtu.be/AGXOu3xjkyk>
[AS]: <https://developer.android.com/studio>
[FB]: <https://firebase.google.com/>
[MAPI]: <https://developers.google.com/maps?hl=de>
[MongoDB]: <https://www.mongodb.com/>
[Jongo]: <https://jongo.org/>
[Play]: <https://www.playframework.com/>
