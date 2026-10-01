import java.util.Scanner;

public class ProgramaPrincipal {

    // Objeto de la clase criptográfica para llamar a sus métodos
    private static ClasseCriptografica cripto = new ClasseCriptografica();

    // 1. Punto de entrada del programa
    public static void main(String[] args) {
        executarSistema();
    }

    // 2. Control del flujo del programa y opciones del menú
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

    // 3. Muestra la cabecera al iniciar
    private static void mostrarCabecera() {
        System.out.println("=================================================");
        System.out.println("   SISTEMA CRIPTOGRÁFICO BSA (Multiplicativo)");
        System.out.println("=================================================");
    }

    // 4. Muestra las opciones disponibles
    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Encriptar mensaje");
        System.out.println("2. Desencriptar mensaje");
        System.out.println("3. Salir");
        System.out.print("Elige una opción (1-3): ");
    }

    // 5. Pide los datos de la opción 1 y llama a Encripta de ClasseCriptografica
    private static void procesarEncriptacion(Scanner scanner) {
        System.out.println("\n[ ENCRIPTACIÓN ]");
        System.out.print("Introduce el mensaje a encriptar: ");
        String mensaje = scanner.nextLine();

        System.out.print("Introduce la clave (número entero): ");
        int clave = leerEntero(scanner);

        String resultado = cripto.Encripta(mensaje, clave);
        System.out.println("\n>>> RESULTADO ENCRIPTADO:");
        System.out.println(resultado);
    }

    // 6. Pide los datos de la opción 2 y llama a Desencripta de ClasseCriptografica
    private static void procesarDesencriptacion(Scanner scanner) {
        System.out.println("\n[ DESENCRIPTACIÓN ]");
        System.out.print("Introduce el mensaje encriptado (bloques binarios y/o '#'): ");
        String mensajeEncriptado = scanner.nextLine();

        System.out.print("Introduce la clave (número entero): ");
        int clave = leerEntero(scanner);

        String resultado = cripto.Desencripta(mensajeEncriptado, clave);
        System.out.println("\n>>> MENSAJE ORIGINAL RECUPERADO:");
        System.out.println(resultado);
    }

    // 7. Función auxiliar para leer enteros y evitar errores de teclado
    private static int leerEntero(Scanner scanner) {
        while (true) {
            try {
                String entrada = scanner.nextLine().trim();
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.print("Error: Introduce un número entero válido: ");
            }
        }
    }
}
