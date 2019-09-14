package helper;

public class Constants {

    private static Constants ourInstance = null;

    public static Constants getInstance() {
        if (ourInstance == null) {
            ourInstance = new Constants();
            return ourInstance;
        }
        return ourInstance;
    }

    public String service = "https://api.nytimes.com/svc/mostpopular/v2/mostviewed/all-sections/";
    public final String apikey = "OEGN4OnQq5YbxG7nyJfHAxjS9YYrpHRA";
}
