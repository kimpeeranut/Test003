import org.json.JSONException;

public class Main {
	public static void main(String[] args) throws JSONException {
		DoFile file = new DoFile();
		file.splitContext(file.readFile());
		file.writeFile();
	}

}
