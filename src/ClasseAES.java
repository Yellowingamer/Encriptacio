import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ClasseAES {

    // Configuración: AES en modo ECB con relleno PKCS5
    private static final String ALGORITME = "AES/ECB/PKCS5Padding";

    // AES en Java solo admite claves de 16, 24 o 32 bytes (128, 192 o 256 bits)
    public static boolean esClauValida(String clau) {
        if (clau == null) {
            return false;
        }
        int len = clau.getBytes(StandardCharsets.UTF_8).length;
        return len == 16 || len == 24 || len == 32;
    }

    public static String encripta(String missatge, String clau) {
        try {
            if (missatge == null || clau == null) {
                throw new IllegalArgumentException("El mensaje y la clave no pueden ser nulos.");
            }

            // Generamos la clave para AES a partir de los bytes de la cadena
            byte[] clauBytes = clau.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(clauBytes, "AES");

            // Preparamos el Cipher en modo encriptación
            Cipher cipher = Cipher.getInstance(ALGORITME);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Ciframos los bytes del mensaje
            byte[] missatgeBytes = missatge.getBytes(StandardCharsets.UTF_8);
            byte[] bytesXifrats = cipher.doFinal(missatgeBytes);

            // Pasamos los bytes cifrados a Base64 para obtener un texto legible
            return Base64.getEncoder().encodeToString(bytesXifrats);

        } catch (java.security.InvalidKeyException e) {
            throw new RuntimeException("Clave inválida para AES: debe tener 16, 24 o 32 bytes (128, 192 o 256 bits).",
                    e);
        } catch (Exception e) {
            String msg = (e.getMessage() != null) ? e.getMessage() : e.getClass().getSimpleName();
            throw new RuntimeException("Error durante la encriptación: " + msg, e);
        }
    }

    public static String desencripta(String missatgeXifrat, String clau) {
        try {
            if (missatgeXifrat == null || clau == null) {
                throw new IllegalArgumentException("El mensaje cifrado y la clave no pueden ser nulos.");
            }

            // Preparamos la misma clave AES para desencriptar
            byte[] clauBytes = clau.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(clauBytes, "AES");

            // Preparamos el Cipher en modo desencriptación
            Cipher cipher = Cipher.getInstance(ALGORITME);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // Decodificamos el Base64 para recuperar los bytes cifrados
            byte[] bytesXifrats = Base64.getDecoder().decode(missatgeXifrat.trim());

            // Desciframos y convertimos de nuevo a texto
            byte[] bytesOriginals = cipher.doFinal(bytesXifrats);
            return new String(bytesOriginals, StandardCharsets.UTF_8);

        } catch (javax.crypto.BadPaddingException e) {
            // Salta cuando la clave es incorrecta y no coincide el relleno PKCS5
            throw new RuntimeException("Clave incorrecta o mensaje corrupto.", e);
        } catch (java.security.InvalidKeyException e) {
            throw new RuntimeException("Clave inválida para AES: debe tener 16, 24 o 32 bytes (128, 192 o 256 bits).",
                    e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("El mensaje cifrado no tiene un formato Base64 válido.", e);
        } catch (Exception e) {
            String msg = (e.getMessage() != null) ? e.getMessage() : e.getClass().getSimpleName();
            throw new RuntimeException("Error durante la desencriptación: " + msg, e);
        }
    }

    public static String Encripta(String missatge, String clau) {
        return encripta(missatge, clau);
    }

    public static String Desencripta(String missatgeXifrat, String clau) {
        return desencripta(missatgeXifrat, clau);
    }
}
