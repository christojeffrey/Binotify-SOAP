package binotify.security;

import com.google.protobuf.Api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.stream.IntStream;

public class ApiKey {
    private static String password = "DimasGanteng";
    public static String secret_string = "Banget";
    public static String aes_key = "WOW";
    private String secret;
    public String key;
    public Date created_date;
    public Date valid_until;
    public String[] privileges;

    public ApiKey(){

    }

    public ApiKey(Date valid_until, String[] privileges){
        String[] infos = new String[3+ privileges.length];
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        AESEncryptionDecryption aesEncryptionDecryption = new AESEncryptionDecryption();
        String rawKey;
        String encKey;

        infos[0] = ApiKey.secret_string;
        infos[1] = formatter.format(today);
        infos[2] = formatter.format(valid_until);

        int i = 3;
        for (String s: privileges) {
            infos[i] = s;
            i++;
        }

        rawKey = this.concatCharSeparated(infos,'.');
        encKey = aesEncryptionDecryption.encrypt(rawKey, ApiKey.aes_key);

        this.secret = ApiKey.secret_string;
        this.key = encKey;
        this.privileges = privileges;
        this.valid_until = valid_until;
        this.created_date = today;
    }

    public ApiKey(String from_key){
        AESEncryptionDecryption aesEncryptionDecryption = new AESEncryptionDecryption();
        String dec;
        String[] infos;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String[] privileges;

        dec = aesEncryptionDecryption.decrypt(from_key, ApiKey.aes_key);
        infos = dec.split("\\.");
        try {
            this.key = from_key;
            this.secret = infos[0];
            this.created_date = formatter.parse(infos[1]);
            this.valid_until = formatter.parse(infos[2]);

            privileges = new String[infos.length-3];
            IntStream.range(3, infos.length).forEach(i -> {
                String element = infos[i];
                privileges[i-3] = element;
            });

            this.privileges = privileges;
        } catch (Exception e){
            throw new RuntimeException();
        }

    }

    public static Boolean checkPassword(String password){
        return password.equals(ApiKey.password);
    }

    public Boolean isSecretCorrect(){
        return Objects.equals(this.secret, ApiKey.secret_string);
    }

    public String concatCharSeparated(String[] strings, char c){
        StringBuilder result = new StringBuilder();
        IntStream.range(0, strings.length).forEach(i -> {
            String element = strings[i];
            result.append(element);
            if (i != strings.length-1) {
                result.append(c);
            }
        });
        return result.toString();
    }

    public void isValid(String method) throws Exception {
        if(!this.secret.equals(ApiKey.secret_string)){
            throw new Exception("Api Key not valid");
        }
        if(this.valid_until.before(new Date())){
            throw new Exception("Api Key Expired");
        }
        if (!Arrays.toString(this.privileges).contains(method)) {
            throw new Exception("Not Authorized");
        }

    }

    //  setter
    public void setKey(String key) {
        this.key = key;
    }
    public void setPrivileges(String[] privileges) {
        this.privileges = privileges;
    }
    public void setValid_until(Date valid_until) {
        this.valid_until = valid_until;
    }

    //  getter
    public Date getValid_until() {
        return valid_until;
    }
    public String getKey() {
        return key;
    }
    public String[] getPrivileges() {
        return privileges;
    }

    public static void main(String[] args) throws ParseException {
        String sDate1="28/11/2022";
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        String[] ss = {"newSubscription", "checkSubscription"};
        ApiKey test = new ApiKey(date1,ss);

        System.out.println(test.getKey());
        System.out.println(Arrays.toString(test.getPrivileges()));
        ApiKey test2 = new ApiKey(test.getKey());

        System.out.println(test2.getKey());
        System.out.println(Arrays.toString(test2.getPrivileges()));

    }
}
