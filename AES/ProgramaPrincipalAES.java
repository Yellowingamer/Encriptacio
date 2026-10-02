public class ProgramaPrincipalAES {

    public static void main(String[] args) {

        // =====================================================================
        // PROVA 1 – Clau correcta
        // Esperem recuperar exactament el missatge original.
        // =====================================================================
        System.out.println("=".repeat(60));
        System.out.println("   PROVA 1 – Clau correcta");
        System.out.println("=".repeat(60));

        String missatge1 = "Aquest és un missatge secret.";
        String clau1 = "1234567890123456"; // 16 caràcters = 128 bits

        System.out.println("Missatge original : " + missatge1);
        System.out.println("Clau              : " + clau1);

        String xifrat1 = ClasseAES.encripta(missatge1, clau1);
        System.out.println("Missatge xifrat   : " + xifrat1);

        String recuperat1 = ClasseAES.desencripta(xifrat1, clau1);
        System.out.println("Missatge recuperat: " + recuperat1);

        System.out.println("Prova 1 " + (missatge1.equals(recuperat1) ? "CORRECTA ✓" : "FALLIDA ✗"));

        // =====================================================================
        // PROVA 2 – Clau diferent
        // AES amb ECB/PKCS5 llançarà una excepció o retornarà dades incorrectes
        // perquè els bytes desxifrats no formaran text UTF-8 vàlid.
        // =====================================================================
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("   PROVA 2 – Clau diferent (incorrecta)");
        System.out.println("=".repeat(60));

        String clauIncorrecta = "9999999999999999"; // Clau de 16 caràcters però diferent
        System.out.println("Missatge xifrat   : " + xifrat1);
        System.out.println("Clau incorrecta   : " + clauIncorrecta);

        try {
            String recuperat2 = ClasseAES.desencripta(xifrat1, clauIncorrecta);
            // Si no llança excepció, el text recuperat serà corrupte/il·legible
            System.out.println("Resultat obtingut : " + recuperat2);
            System.out.println("→ El missatge recuperat NO coincideix amb l'original.");
        } catch (RuntimeException e) {
            // BadPaddingException: l'emplenament PKCS5 no és vàlid amb la clau incorrecta
            System.out.println("→ Error capturat   : " + e.getMessage());
            System.out.println("→ Explicació: AES detecta que el padding és incorrecte");
            System.out.println("  perquè els bytes desxifrats amb la clau errònia no");
            System.out.println("  respecten l'esquema PKCS5. Això és la prova que AES");
            System.out.println("  protegeix correctament el missatge.");
        }

        // =====================================================================
        // PROVA 3 – Missatge diferent
        // Verifica que el sistema funciona amb qualsevol missatge.
        // =====================================================================
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("   PROVA 3 – Missatge diferent");
        System.out.println("=".repeat(60));

        String missatge3 = "Hola, món! Això és una altra prova amb accents àéíóú.";
        String clau3 = "1234567890123456";

        System.out.println("Missatge original : " + missatge3);

        String xifrat3 = ClasseAES.encripta(missatge3, clau3);
        System.out.println("Missatge xifrat   : " + xifrat3);

        String recuperat3 = ClasseAES.desencripta(xifrat3, clau3);
        System.out.println("Missatge recuperat: " + recuperat3);

        System.out.println("Prova 3 " + (missatge3.equals(recuperat3) ? "CORRECTA ✓" : "FALLIDA ✗"));

        // =====================================================================
        // PROVA 4 – Clau de longitud incorrecta
        // AES només accepta claus de 16, 24 o 32 bytes (128/192/256 bits).
        // Una clau d'altra mida provocarà una excepció
        // java.security.InvalidKeyException.
        // =====================================================================
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("   PROVA 4 – Clau de longitud incorrecta");
        System.out.println("=".repeat(60));

        String missatge4 = "Missatge de prova";
        String clauCurta = "claucurta"; // 9 caràcters → NO és vàlida per a AES

        System.out.println("Missatge original : " + missatge4);
        System.out.println("Clau invàlida     : \"" + clauCurta + "\" (" + clauCurta.length() + " caràcters)");

        try {
            String xifrat4 = ClasseAES.encripta(missatge4, clauCurta);
            System.out.println("Missatge xifrat   : " + xifrat4); // No hauria d'arribar aquí
        } catch (RuntimeException e) {
            System.out.println("→ Error capturat   : " + e.getMessage());
            System.out.println("→ Explicació: AES requereix claus de 16, 24 o 32 bytes.");
            System.out.println("  \"" + clauCurta + "\" té " + clauCurta.length()
                    + " bytes, que no és una mida vàlida.");
            System.out.println("  Java llança InvalidKeyException perquè SecretKeySpec");
            System.out.println("  no pot construir una clau AES amb aquesta longitud.");
        }

        // =====================================================================
        // VERIFICACIÓ FINAL: Desencripta(Encripta(m, k), k) = m
        // =====================================================================
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("   VERIFICACIÓ FINAL");
        System.out.println("=".repeat(60));

        String mFinal = "Aquest és un missatge secret.";
        String kFinal = "1234567890123456";
        boolean ok = mFinal.equals(ClasseAES.desencripta(ClasseAES.encripta(mFinal, kFinal), kFinal));
        System.out.println("Desencripta(Encripta(m, k), k) == m → " + ok);
        System.out.println("=".repeat(60));
    }
}
