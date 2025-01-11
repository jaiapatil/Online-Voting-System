# Online-Voting-System

This is an Online Voting System built with Java and MySQL. The system allows registered users to verify their identity, cast votes for political parties, and view the election results. It includes a simple user interface built with Java Swing and connects to a MySQL database for storing voter details and vote counts.

Features :-

1.Voter Verification: Users can verify their identity using their name, voter ID, and password. A user can only verify once.
2.Vote Casting: After verification, voters can cast their votes for one of the available political parties.
3.Winner Announcement: After voting concludes, the system displays the winner based on the vote count or indicates a tie.

Technologies Used :-

1.Java: For creating the graphical user interface (GUI) and handling the backend logic.
2.MySQL: For storing voter details, votes, and vote counts.
3.JDBC: For connecting Java with the MySQL database.

How to Use:-

1.Set up MySQL Database:

a)Create the voting_system database using the provided SQL schema.
b)Populate the database with sample data for voters and vote counts.

2.Run the Project:

a)Clone or download the repository.
b)Open the project in a Java IDE (e.g., IntelliJ IDEA, Eclipse).
c)Ensure the MySQL database is set up and running.
d)Run the MainPage.java class to start the application.
