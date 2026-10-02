import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * ClasseAES - Implementació del xifratge AES (Advanced Encryption Standard).
 *
 * AES és un algorisme de xifratge simètric estàndard: la mateixa clau s'usa
 * per encriptar i desencriptar. Opera sobre blocs de 128 bits i accepta claus
 * de 128, 192 o 256 bits (16, 24 o 32 bytes respectivament).
 *
 * Flux d'encriptació:
 *   String missatge  →  bytes (UTF-8)  →  AES  →  bytes xifrats  →  Base64  →  String xifrat
 *
 * Flux de desencriptació:
 *   String xifrat  →  Base64  →  bytes xifrats  →  AES  →  bytes (UTF-8)  →  String original
 */
public class ClasseAES {

    /**
     * Algoritme usat. "AES/ECB/PKCS5Padding" vol dir:
     *   - AES    : algorisme de xifratge
     *   - ECB    : mode Electronic Code Book (cada bloc es xifra de manera independent)
     *   - PKCS5  : esquema d'emplenament per completar l'últim bloc si cal
     */
    private static final String ALGORITME = "AES/ECB/PKCS5Padding";

    // -------------------------------------------------------------------------
    // MÈTODE: encripta
    // -------------------------------------------------------------------------

    /**
     * Encripta un missatge en text pla usant AES.
     *
     * @param missatge  El text que es vol xifrar.
     * @param clau      Clau de 16 caràcters (128 bits) per a AES.
     * @return          El missatge xifrat i codificat en Base64.
     * @throws RuntimeException si la clau no és vàlida o hi ha algun error de xifratge.
     */
    public static String encripta(String missatge, String clau) {
        try {
            // PAS 1 – Preparar la clau per a AES
            // getBytes() converteix el String de la clau en un array de bytes en UTF-8.
            // SecretKeySpec empaqueta aquests bytes indicant que s'usaran per a AES.
            byte[] clauBytes = clau.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(clauBytes, "AES");

            // PAS 2 – Crear i configurar el motor de xifratge
            // Cipher.getInstance() retorna una instància del motor criptogràfic.
            // cipher.init() l'inicialitza en mode ENCRYPT_MODE amb la nostra clau.
            Cipher cipher = Cipher.getInstance(ALGORITME);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // PAS 3 – Convertir el missatge a bytes i xifrar-lo
            // El missatge s'ha de passar com a bytes perquè AES opera sobre bytes.
            // doFinal() aplica l'algorisme i retorna els bytes xifrats.
            byte[] missatgeBytes = missatge.getBytes(StandardCharsets.UTF_8);
            byte[] bytesXifrats  = cipher.doFinal(missatgeBytes);

            // PAS 4 – Convertir el resultat a String usant Base64
            // Els bytes xifrats poden contenir valors arbitraris (0-255) que no
            // són caràcters imprimibles. Base64 els codifica en text ASCII segur.
            return Base64.getEncoder().encodeToString(bytesXifrats);

        } catch (Exception e) {
            throw new RuntimeException("Error durant l'encriptació: " + e.getMessage(), e);
        }
    }

    // -------------------------------------------------------------------------
    // MÈTODE: desencripta
    // -------------------------------------------------------------------------

    /**
     * Desencripta un missatge xifrat amb AES i codificat en Base64.
     *
     * @param missatgeXifrat  El text xifrat en Base64 (resultat d'encripta()).
     * @param clau            La mateixa clau de 16 caràcters usada per encriptar.
     * @return                El missatge original en text pla.
     * @throws RuntimeException si la clau és incorrecta, el missatge és invàlid
     *                          o hi ha algun error de desxifratge.
     */
    public static String desencripta(String missatgeXifrat, String clau) {
        try {
            // PAS 1 – Preparar la clau (idèntic a encripta)
            byte[] clauBytes = clau.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(clauBytes, "AES");

            // PAS 2 – Crear i configurar el motor de desxifratge
            // Ara s'usa DECRYPT_MODE per indicar que volem l'operació inversa.
            Cipher cipher = Cipher.getInstance(ALGORITME);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // PAS 3 – Decodificar el Base64 per obtenir els bytes xifrats originals
            byte[] bytesXifrats = Base64.getDecoder().decode(missatgeXifrat);

            // PAS 4 – Desxifrar i convertir a String
            // doFinal() aplica AES en mode invers i retorna els bytes originals del missatge.
            byte[] bytesOriginals = cipher.doFinal(bytesXifrats);
            return new String(bytesOriginals, StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException("Error durant la desencriptació: " + e.getMessage(), e);
        }
    }
}
