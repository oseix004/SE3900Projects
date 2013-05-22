
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class serves as the back end of the Website Manager's GUI. It will be
 * able to connect to the database and compile the information that will be
 * displayed to the website manager.
 * @author James Kriegh
 */
public class WebsiteInformationBuilder {
    // Connection Variables
    private ServerSocket socketConnection;
    private Socket clientConnection;
    private Socket databaseConnection;
    private Scanner clientInput;
    private Formatter clientOutput;
    private ObjectInputStream databaseResult;
    private ObjectOutputStream databaseQuery;
    private String databaseServerName;
    private boolean connected;
    
    // Query Variables
    private ArrayList query;
    private ArrayList results;
    
    // Information Variables
    private int numOfAdministrators;
    private int numOfPlayers;
    private int numOfDevelopers;
    private int numOfProfiles;
    private String playerProfileInformation;
    private String developerProfileInformation;
    private String administratorProfileInformation;
    private String hotlistInformation;
    private String databaseHistoryInformation;
    private String loginHistoryInformation;
    private String gameInformation;
    
    public static void main(String[] args) {
        WebsiteInformationBuilder wib = new WebsiteInformationBuilder("146.57.164.38");
    }
    
    /**
     * This constructor was created for testing purposes, so that I didn't have
     * to send in the connection information because tests would be done on the
     * WAMP Server on my machine (i.e. static connection). For full functional
     * tests, use the other constructor and provide connection information to
     * the current database server.
     */
    public WebsiteInformationBuilder(String dbsn) {
        while(true) {
            System.out.println("Initializing connection variables");
            databaseServerName = dbsn;
            query = new ArrayList();

            System.out.println("Initializing connections");
            initializeConnections();

            System.out.println("Executing information building");
            execute();
        }
    }
    
    private void execute() {
        int inActiveLoopCount = 0;
        try {
            clientConnection = socketConnection.accept();
            System.out.println("Client connected. Openning I/O Connections");
            clientInput = new Scanner(clientConnection.getInputStream());
            clientOutput = new Formatter(clientConnection.getOutputStream());
            connected = true;
            while(connected) {
                System.out.println("Connected to client. Listening for input");
                if(clientInput.hasNextLine()) {
                    processMessage(clientInput.nextLine());
                    inActiveLoopCount = 0;
                }
                inActiveLoopCount++;
                if(inActiveLoopCount > 10) {
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(WebsiteInformationBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            System.out.println("Client not connected. Shutting down connections");
            query.add("close");
            databaseQuery.writeObject(query);
            databaseQuery.flush();
            databaseQuery.reset();
            databaseQuery.close();
            clientOutput.close();
            clientInput.close();
            clientConnection.close();
            socketConnection.close();
            databaseResult.close();
            databaseConnection.close();
        } catch(IOException ex) {
            Logger.getLogger(WebsiteInformationBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initializeConnections() {
        try {
            socketConnection = new ServerSocket(3309);
            databaseConnection = new Socket(databaseServerName, 3308);
            databaseResult = new ObjectInputStream(databaseConnection.getInputStream());
//            databaseResult = new Scanner(databaseConnection.getInputStream());
            databaseQuery = new ObjectOutputStream(databaseConnection.getOutputStream());
        } catch (IOException ex) {
                Logger.getLogger(WebsiteInformationBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void processMessage(String message) {
        String[] messageParts = message.split(" ");
        System.out.println("Processing message: " + messageParts[0]);
        switch(messageParts[0]) {
            case "initialize"                                                  :
                System.out.println("This was an initialize command. Building initial database query");
                clientOutput.format("%s\n", initializeInformationVariables());
                clientOutput.flush();
                System.out.println("Initial results sent to client");
                break;
            case "hotlist"                                                     :
                try {
                    System.out.println("This was an update command for a game's hotlist stat");
                    query.add("execute");
                    query.add("Update games Set Hotlist = " + messageParts[1]
                            + " Where GameID = " + messageParts[2]);
                    databaseQuery.writeObject(query);
                    databaseQuery.flush();
                    databaseQuery.reset();
                    query.remove(0);
                    query.remove(0);
                    System.out.println("Change message for hotlist stat sent to the database");
                    
                    System.out.println("Getting the updated hotlist stat for the client");
                    query.add("query");
                    query.add("Select Hotlist from games where GameID = " + messageParts[2]);
                    databaseQuery.writeObject(query);
                    databaseQuery.flush();
                    results = (ArrayList) databaseResult.readObject();
                    databaseQuery.reset();
                    System.out.println("Response received from the database. Sending it to the client");
                    clientOutput.format("%s\n", ((ArrayList) ((ArrayList) results).get(0)).get(0).toString());
                    clientOutput.flush();
                    query.remove(0);
                    query.remove(0);
                } catch(IOException ex) {
                    Logger lgr = Logger.getLogger(WebsiteInformationBuilder.class.getName());
                    lgr.log(Level.SEVERE, ex.getMessage(), ex);
                } catch(ClassNotFoundException ex) {
                    Logger lgr = Logger.getLogger(WebsiteInformationBuilder.class.getName());
                    lgr.log(Level.SEVERE, ex.getMessage(), ex);
                }
            case "close"                                                       :
                System.out.println("This was a close command");
                connected = false;
                break;
        }
        System.out.println("Message parsed\n\n");
    }
    
    private String initializeInformationVariables() {
        System.out.println("Initializing information");
//        boolean waitingForResponse = true;
        try {
            System.out.println("Finding out how many profiles are in the database");
            query.add("query");
            query.add("Select Count(*) From login");
            databaseQuery.writeObject(query);
            System.out.println("Message built: " + query);
            System.out.println("Sending to database");
            databaseQuery.flush();
            results = (ArrayList) databaseResult.readObject();
            databaseQuery.reset();
            
//            while(waitingForResponse) {
//                System.out.println("Listening for response from database");
//                if(clientInput.hasNextLine()) {
//                    System.out.println("Response Received");
//                    results = databaseResult.nextLine();
//                    System.out.println(results);
//                    waitingForResponse = false;
//                }
//            }
            
            System.out.println("Message sent and response recieved.");
            System.out.println("The message was: " + results);
            System.out.println("Result was an:                        " + results.getClass() );
            System.out.println("The first element of Result was an:   " + results.get(0).getClass());
            System.out.println("The specific result was an:           " + ((ArrayList) results.get(0)).get(0).getClass() );
            System.out.println("The value of the specific result was: " + (((ArrayList) results.get(0)).get(0)).toString());
            numOfProfiles = Integer.parseInt(((ArrayList) ((ArrayList) results).get(0)).get(0).toString());
//            numOfProfiles = Integer.parseInt(results);
            query.remove(0);
            query.remove(0);
            
            
            System.out.println("Finding out how many adminstrators are in the database");
            query.add("query");
            query.add("Select Count(ProfileType) From login "
                    + "Where ProfileType = 'administrator'");
            databaseQuery.writeObject(query);
            System.out.println("Message built: " + query);
            databaseQuery.flush();
            results = (ArrayList) databaseResult.readObject();
            databaseQuery.reset();
//            waitingForResponse = true;
//            while(waitingForResponse) {
//                System.out.println("Listening for response from database");
//                if(clientInput.hasNextLine()) {
//                    results = databaseResult.nextLine();
//                    waitingForResponse = false;
//                }
//            }
            
            numOfAdministrators = Integer.parseInt(((ArrayList) results.get(0)).get(0).toString());
//            numOfAdministrators = Integer.parseInt(results);
            query.remove(0);
            query.remove(0);
            
            System.out.println("Finding out how many players are in the database");
            query.add("query");
            query.add("Select Count(ProfileType) From login "
                    + "Where ProfileType = 'player'");
            databaseQuery.writeObject(query);
            System.out.println("Message built: " + query);
            databaseQuery.flush();
            results = (ArrayList) databaseResult.readObject();
            databaseQuery.reset();
//            waitingForResponse = true;
//            while(waitingForResponse) {
//                System.out.println("Listening for response from database");
//                if(clientInput.hasNextLine()) {
//                    results = databaseResult.nextLine();
//                    waitingForResponse = false;
//                }
//            }
            numOfPlayers = Integer.parseInt(((ArrayList) results.get(0)).get(0).toString());
//            numOfPlayers = Integer.parseInt(results);
            query.remove(0);
            query.remove(0);
            
            System.out.println("Finding out how many developers are in the database");
            query.add("query");
            query.add("Select Count(ProfileType) From login "
                    + "Where ProfileType = 'developer'");
            databaseQuery.writeObject(query);
            System.out.println("Message built: " + query);
            databaseQuery.flush();
            results = (ArrayList) databaseResult.readObject();
            databaseQuery.reset();
//            waitingForResponse = true;
//            while(waitingForResponse) {
//                System.out.println("Listening for response from database");
//                if(clientInput.hasNextLine()) {
//                    results = databaseResult.nextLine();
//                    waitingForResponse = false;
//                }
//            }
            numOfDevelopers = Integer.parseInt(((ArrayList) results.get(0)).get(0).toString());
//            numOfDevelopers = Integer.parseInt(results);
            query.remove(0);
            query.remove(0);
            
            
            System.out.println("Finding information for Player Profile Table");
            playerProfileInformation = "";
            query.add("query");
            query.add("Select T.username, P.FirstName, P.LastName From (Select Username From login Where ProfileType = 'player')T " + "Inner Join " + "profile P" /*"(Select FirstName, LastName, username From profile where FirstName is not null && LastName is not null) P"*/ +" On T.username = P.username" + " Order By T.username");
            databaseQuery.writeObject(query); // preparing the information to send to the database.
            System.out.println("Message built: " + query);
            System.out.println("Sending to database");
            databaseQuery.flush(); // sends information to the database.
            results = (ArrayList) databaseResult.readObject();// reading the response
            databaseQuery.reset(); //basically am just unsubscribing from the response.
            for(int x = 0; x< results.size(); x++){
                for(int y = 0; y < ((ArrayList)results.get(x)).size(); y++){
                playerProfileInformation += ((ArrayList)results.get(x)).get(y) + ":"; 
                System.out.println("Current Progress of Columns " + playerProfileInformation);
            }
                playerProfileInformation += ";";
                System.out.println("Current Progress of Rows: " + playerProfileInformation);
            }
            query.remove(0);
            query.remove(0);
            
            
                      System.out.println("Finding information for developer Profile Table");
            developerProfileInformation = "";
            query.add("query");
            query.add("Select T.username, P.FirstName, P.LastName From (Select Username From login Where ProfileType = 'developer')T " + "Inner Join " + "profile P" /*"(Select FirstName, LastName, username From profile where FirstName is not null && LastName is not null) P"*/ +" On T.username = P.username" + " Order By T.username");
            databaseQuery.writeObject(query); // preparing the information to send to the database.
            System.out.println("Message built: " + query);
            System.out.println("Sending to database");
            databaseQuery.flush(); // sends information to the database.
            results = (ArrayList) databaseResult.readObject();// reading the response
            databaseQuery.reset(); //basically am just unsubscribing from the response.
            for(int x = 0; x< results.size(); x++){
                for(int y = 0; y < ((ArrayList)results.get(x)).size(); y++){
                playerProfileInformation += ((ArrayList)results.get(x)).get(y) + ":"; 
                System.out.println("Current Progress of Columns " + developerProfileInformation);
            }
                developerProfileInformation += ";";
                System.out.println("Current Progress of Rows: " + developerProfileInformation);
            }
            query.remove(0);
            query.remove(0);
            
            
                      System.out.println("Finding information for Administrator Profile Table");
             administratorProfileInformation = "";
            query.add("query");
            query.add("Select T.username, P.FirstName, P.LastName From (Select Username From login Where ProfileType = 'administrator')T " + "Inner Join " + "profile P" /*"(Select FirstName, LastName, username From profile where FirstName is not null && LastName is not null) P"*/ +" On T.username = P.username" + " Order By T.username");
            databaseQuery.writeObject(query); // preparing the information to send to the database.
            System.out.println("Message built: " + query);
            System.out.println("Sending to database");
            databaseQuery.flush(); // sends information to the database.
            results = (ArrayList) databaseResult.readObject();// reading the response
            databaseQuery.reset(); //basically am just unsubscribing from the response.
            for(int x = 0; x< results.size(); x++){
                for(int y = 0; y < ((ArrayList)results.get(x)).size(); y++){
                administratorProfileInformation += ((ArrayList)results.get(x)).get(y) + ":"; 
                System.out.println("Current Progress of Columns " + administratorProfileInformation);
            }
                administratorProfileInformation += ";";
                System.out.println("Current Progress of Rows: " + administratorProfileInformation);
            }
            query.remove(0);
            query.remove(0);
            
            
                      System.out.println("Finding information for Hotlist Profile Table");
          hotlistInformation= "";
            query.add("query");
            query.add("Select GameID, DateAdded, TotalPlays, Hotlist From games");
            databaseQuery.writeObject(query); // preparing the information to send to the database.
            System.out.println("Message built: " + query);
            System.out.println("Sending to database");
            databaseQuery.flush(); // sends information to the database.
            results = (ArrayList) databaseResult.readObject();// reading the response
            databaseQuery.reset(); //basically am just unsubscribing from the response.
            for(int x = 0; x< results.size(); x++){
                for(int y = 0; y < ((ArrayList)results.get(x)).size(); y++){
                hotlistInformation += ((ArrayList)results.get(x)).get(y) + ":"; 
            }
                hotlistInformation += ";";
            }
            query.remove(0);
            query.remove(0);
            
         System.out.println("Finding information for database Profile Table");
            databaseHistoryInformation= ""; //databaseHistoryInformation
            query.add("query");
            query.add("Select T.Username, T.LoggedIn,T.LoggedOut, P.FowlConduct From (Select Username, LoggedIn, LoggedOut, from (loggedin) T) Inner Join (profile) P On T. Username = P.Username Order By T.Username");
            databaseQuery.writeObject(query); // preparing the information to send to the database.
            System.out.println("Message built: " + query);
            System.out.println("Sending to database");
            databaseQuery.flush(); // sends information to the database.
            results = (ArrayList) databaseResult.readObject();// reading the response
            databaseQuery.reset(); //basically am just unsubscribing from the response.
            for(int x = 0; x< results.size(); x++){
                for(int y = 0; y < ((ArrayList)results.get(x)).size(); y++){
                databaseHistoryInformation += ((ArrayList)results.get(x)).get(y) + ":"; 
            }
                databaseHistoryInformation += ";";
            }
            query.remove(0);
            query.remove(0);
            
            System.out.println("Finding information for login Profile Table");
            loginHistoryInformation= ""; //databaseHistoryInformation
            query.add("query");
            query.add("Select * From loggedin");
            databaseQuery.writeObject(query); // preparing the information to send to the database.
            System.out.println("Message built: " + query);
            System.out.println("Sending to database");
            databaseQuery.flush(); // sends information to the database.
            results = (ArrayList) databaseResult.readObject();// reading the response
            databaseQuery.reset(); //basically am just unsubscribing from the response.
            for(int x = 0; x< results.size(); x++){
                for(int y = 0; y < ((ArrayList)results.get(x)).size(); y++){
                loginHistoryInformation += ((ArrayList)results.get(x)).get(y) + ":"; 
            }
                loginHistoryInformation += ";";
            }
            query.remove(0);
            query.remove(0);
            
           System.out.println("Finding information for game Profile Table"); 
            gameInformation= ""; //databaseHistoryInformation
            query.add("query");
            query.add("Select GameID, GameName, Genre, Developer, ShortDescription, LongDescription, DateAdded, Rating, TotalPlays From games");
            databaseQuery.writeObject(query); // preparing the information to send to the database.
                    System.out.println("Message built: " + query);
            System.out.println("Sending to database");
            databaseQuery.flush(); // sends information to the database.
            results = (ArrayList) databaseResult.readObject();// reading the response
            databaseQuery.reset(); //basically am just unsubscribing from the response.
            for(int x = 0; x< results.size(); x++){
                for(int y = 0; y < ((ArrayList)results.get(x)).size(); y++){
                gameInformation += ((ArrayList)results.get(x)).get(y) + ":"; 
            }
                gameInformation += ";";
            }
            query.remove(0);
            query.remove(0);
            
            
            
        } catch (ClassNotFoundException ex) {
            Logger lgr = Logger.getLogger(WebsiteInformationBuilder.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (IOException ex) {
            Logger lgr = Logger.getLogger(WebsiteInformationBuilder.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        
        System.out.println("Information built\n");
        
        return getPlayerRatio() + " " + getDeveloperRatio() + " " + 
                + getAdministratorRatio() + " " + playerProfileInformation 
                + " " + hotlistInformation+ " "+administratorProfileInformation + " "+ developerProfileInformation +" "+ databaseHistoryInformation + " "+ loginHistoryInformation +" "+ gameInformation;
    }
    
    public int getPlayerRatio() {
        return (int) (((numOfPlayers * 1.0) / numOfProfiles) * 100);
    }
    
    public int getDeveloperRatio() {
        return (int) (((numOfDevelopers * 1.0) / numOfProfiles) * 100);
    }
    
    public int getAdministratorRatio() {
        return (int) (((numOfAdministrators * 1.0) / numOfProfiles) * 100);
    }
    
    
    
    @Override
    public String toString() {
        String toStringOutput = "";
        toStringOutput += "\nNumber of Profiles: " + numOfProfiles;
        toStringOutput += "\nNumber of Administrators: " + numOfAdministrators;
        toStringOutput += "\nNumber of Players: " + numOfPlayers;
        toStringOutput += "\nNumber of Developers: " + numOfDevelopers;
        toStringOutput += "\nRatio of Players in the database: "
                + getPlayerRatio();
        toStringOutput += "\nRatio of Developers in the database: "
                + getDeveloperRatio();
        toStringOutput += "\nRatio of Administrators in the database: "
                + getAdministratorRatio();
        return toStringOutput;
    }
}
