import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Cracker {
    public static void main(String [] args) throws FileNotFoundException, IOException{
        BufferedReader shadowFileReader = new BufferedReader(new FileReader("shadow"));
        BufferedReader commonFileReader = new BufferedReader(new FileReader("common-passwords.txt"));

        String[] commonpasswords = commonFileReader.lines().toArray(String[]::new);

        String line = "";
        while((line=shadowFileReader.readLine()) != null){
            String [] splitLine = line.split(":");

            if(splitLine.length < 2){
                continue;
            }

            String username = splitLine[0];
            String saltHash = splitLine[1];
            String [] splitSaltHash = saltHash.split("\\$");

            String salt = splitSaltHash[2];
            String hash = splitSaltHash[3];
            System.out.println("Salt: " + salt);
            System.out.println("Hash: " + hash);
            for(String commonPassword : commonpasswords){
                String testHash = MD5Shadow.crypt(commonPassword, salt);
                if(hash.equals(testHash)){
                    System.out.println(username + ':' + commonPassword);
                }
            }
        }
        shadowFileReader.close();
        commonFileReader.close();
    
    }
}
