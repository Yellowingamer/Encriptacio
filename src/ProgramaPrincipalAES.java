import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ProgramaPrincipalAES {

    private static ClasseAES aes = new ClasseAES();

    public static void main(String[] args) {
        executarSistema();
    }

    public static void executarSistema() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        mostrarCabecera();

        while (!salir) {
            mostrarMenu();
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    procesarEncriptacion(scanner);
                    break;
                case "2":
                    procesarDesencriptacion(scanner);
                    break;
                case "3":
                    salir = true;
                    System.out.println("\nSaliendo del programa. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("\n[ERROR] Opción no válida. Elige 1, 2 o 3.");
                    break;
            }
        }

        scanner.close();
    }

    private static void mostrarCabecera() {
        System.out.println("=================================================");
        System.out.println("          SISTEMA CRIPTOGRÁFICO AES");
        System.out.println("=================================================");
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL (AES) ---");
        System.out.println("1. Encriptar mensaje");
        System.out.println("2. Desencriptar mensaje");
        System.out.println("3. Salir");
        System.out.print("Elige una opción (1-3): ");
    }

    private static void procesarEncriptacion(Scanner scanner) {
        System.out.println("\n[ ENCRIPTACIÓN AES ]");
        System.out.print("Introduce el mensaje a encriptar: ");
        String mensaje = scanner.nextLine();

        String clave = leerClave(scanner);

        try {
            String resultado = aes.Encripta(mensaje, clave);
            System.out.println("\n>>> RESULTADO ENCRIPTADO (Base64):");
            System.out.println(resultado);
        } catch (Exception e) {
            System.out.println("\n[ERROR] No se pudo encriptar el mensaje: " + e.getMessage());
        }
    }

    private static void procesarDesencriptacion(Scanner scanner) {
        System.out.println("\n[ DESENCRIPTACIÓN AES ]");
        System.out.print("Introduce el mensaje encriptado (Base64): ");
        String mensajeEncriptado = scanner.nextLine().trim();

        if (mensajeEncriptado.isEmpty()) {
            System.out.println("\n[ERROR] El mensaje encriptado no puede estar vacío.");
            return;
        }

        String clave = leerClave(scanner);

        try {
            String resultado = aes.Desencripta(mensajeEncriptado, clave);
            System.out.println("\nMENSAJE RECUPERADO:");
            System.out.println(resultado);
        } catch (Exception e) {
            System.out.println("\n[ERROR] No se pudo desencriptar el mensaje.");
            System.out.println("Causa: " + e.getMessage());
        }
    }

    private static String leerClave(Scanner scanner) {
        while (true) {
            System.out.print("Introduce la clave (16, 24 o 32 caracteres): ");
            String clave = scanner.nextLine();
            int bytesLen = clave.getBytes(StandardCharsets.UTF_8).length;

            // AES requiere claves de 16, 24 o 32 bytes (128, 192 o 256 bits )
            if (bytesLen == 16 || bytesLen == 24 || bytesLen == 32) {
                return clave;
            }

            System.out.println("[ERROR] La clave AES debe tener 16, 24 o 32 bytes (128, 192 o 256 bits).");
            System.out.println("        Longitud actual: " + bytesLen + " bytes.");
            System.out.println("        Ejemplo de clave válida de 16 caracteres: 1234567890123456\n");
        }
    }
}
