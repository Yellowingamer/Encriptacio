public class ClasseCriptografica {

    // Método para encriptar el mensaje con lógica multiplicativa
    public String Encripta(String missatge, int clau) {
        String resultado = "";

        for (int i = 0; i < missatge.length(); i++) {
            char caracter = missatge.charAt(i);

            // PASO 1: Si es un espacio, se convierte en '#'
            if (caracter == ' ') {
                resultado += "#";
            } else {
                // PASO 2a: Convertir el carácter a su valor numérico ASCII
                int ascii = (int) caracter;

                // PASO 2b: Multiplicar el valor ASCII por la clave
                int asciiConClave = ascii * clau;

                // PASO 2c: Convertir el número resultante a formato binario
                String binario = Integer.toBinaryString(asciiConClave);

                // Añadir el bloque binario al resultado
                resultado += binario;
            }

            // PASO 3: Añadir un espacio para separar los bloques (salvo en el último)
            if (i < missatge.length() - 1) {
                resultado += " ";
            }
        }

        return resultado;
    }

    // Método para desencriptar el mensaje (proceso inverso con división)
    public String Desencripta(String missatgeEncriptat, int clau) {
        String resultado = "";

        // PASO 1: Separar el mensaje encriptado por los espacios
        String[] bloques = missatgeEncriptat.split(" ");

        for (int i = 0; i < bloques.length; i++) {
            String bloc = bloques[i];

            // PASO 2: Si el bloque es '#', se convierte en un espacio en blanco
            if (bloc.equals("#")) {
                resultado += " ";
            } else if (!bloc.isEmpty()) {
                // PASO 3a: Transformar el bloque binario a número decimal
                int decimal = Integer.parseInt(bloc, 2);

                // PASO 3b: Dividir entre la clave (operación inversa a la multiplicación)
                int asciiOriginal = decimal / clau;

                // PASO 3c: Convertir el valor ASCII resultante a carácter
                char caracterOriginal = (char) asciiOriginal;

                // Añadir la letra recuperada al resultado final
                resultado += caracterOriginal;
            }
        }

        return resultado;
    }
}
