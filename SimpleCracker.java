import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SimpleCracker{

    /*   convert a byte array into a String that contains the hexadecimal
    representation of the byte array:*/

    public static String toHex(byte[] bytes){
        BigInteger bi = new BigInteger(1, bytes);
        return String.format("%0" + (bytes.length << 1) + "X", bi);
    }
    /* Compute the MD5 Hash for the input string */
    public static String md5Hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] DigestMessage = md.digest(input.getBytes());
        return toHex(DigestMessage);
    }

    public static void main(String [] args) throws IOException, NoSuchAlgorithmException {
        BufferedReader shadowFileReader = new BufferedReader(new FileReader("shadow-simple"));
        BufferedReader commonFileReader = new BufferedReader(new FileReader("common-passwords.txt"));

        String[] commonpasswords = commonFileReader.lines().toArray(String[]::new);

        String shadowline;
        while((shadowline=shadowFileReader.readLine()) != null){
            String [] splittingParts = shadowline.split(":");
            if (splittingParts.length != 3 ) continue;

            String username =splittingParts[0];
            String salt =splittingParts[1];
            String Hashes=splittingParts[2];

            /* Checking each common password */
            for (String password: commonpasswords){
                String saltPassword = salt + password;
                String hash = md5Hash(saltPassword);

                if (hash.equals(Hashes)){
                    System.out.println(username + ":" + password);
                }
            }

        }

        shadowFileReader.close();
        commonFileReader.close();

    }
}