package mx.edu.itlp.proyectomovil.validaciones;

import org.kobjects.base64.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class cifrarContrasena {
    public static String encrypt(String input, String key){
        byte[] crypted = null;
        try{
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        }catch(Exception e){
            System.out.println(e.toString());
        }
        return new String(Base64.encode(crypted));
    }
}
