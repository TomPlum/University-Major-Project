package university.twitter;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

public class MongoConnection {
    private final String uri = "mongodb+srv://TomPlum:i7ljjmXIi19PK1CU@twitter-yu9se.mongodb.net/";
    private String database;
    private String collection;
    private MongoClient mongoClient;

    public MongoConnection(String database, String collection) {
        setDatabase(database);
        setCollection(collection);
        initConn();
    }

    private void initConn() {
        try {
            mongoClient = new MongoClient(new MongoClientURI(uri));
        } catch (MongoSocketReadException e) {
            System.out.println("MongoSocketReadException! Perhaps your IP needs whitelisting.");
        }
        System.out.println("MongoDBAtlas Cluster Connection Made @ " + uri);
    }

    public MongoCollection<Document> getMongoCollection() {
        try {
            MongoDatabase db = mongoClient.getDatabase(getDatabase());
            return db.getCollection(getCollection());
        } catch (MongoSocketReadException e) {
            System.out.println("MongoSocketReadException! Perhaps your IP needs whitelisting.");
            return null;
        }
    }

    public void insertDocument(Document object) {
        getMongoCollection().insertOne(object);
    }

    public void removeDocumentsByKey(String key, String val) {
        DeleteResult delete = getMongoCollection().deleteMany(eq(key, val));
        System.out.println("Successfully Deleted " + delete.getDeletedCount() + " Tweets.");
        System.out.println("Where " + key + " matches '" + val + "'.");
    }

    public void disconnect() {
        mongoClient.close();
        System.out.println("Successfully Disconnected From " + uri);
    }

    public String getDatabase() {
        return database;
    }

    public String getCollection() {
        return collection;
    }

    public void printDatabaseStats() {
        MongoDatabase db = mongoClient.getDatabase(getDatabase());
        System.out.println("Database: " + db.getName());
        db.listCollections();
    }

    private void setDatabase(String database) {
        this.database = database;
    }

    private void setCollection(String collection) {
        this.collection = collection;
    }
}
