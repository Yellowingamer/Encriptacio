public class ClasseCriptografica {

    public String Encripta(String missatge, int clau) {
        String resultado = "";

        for (int i = 0; i < missatge.length(); i++) {
            char caracter = missatge.charAt(i);

            // Si es un espacio, se convierte en '#'
            if (caracter == ' ') {
                resultado += "#";
            } else {
                // Convertir el carácter a su valor numérico ASCII
                int ascii = (int) caracter;

                int asciiConClave = ascii * clau;

                String binario = Integer.toBinaryString(asciiConClave);

                resultado += binario;
            }

            // Añadir un espacio para separar los bloques (salvo en el último)
            if (i < missatge.length() - 1) {
                resultado += " ";
            }
        }

        return resultado;
    }

    public String Desencripta(String missatgeEncriptat, int clau) {
        String resultado = "";

        // Separar el mensaje encriptado por los espacios
        String[] bloques = missatgeEncriptat.split(" ");

        for (int i = 0; i < bloques.length; i++) {
            String bloc = bloques[i];

            // Si el bloque es '#', se convierte en un espacio en blanco
            if (bloc.equals("#")) {
                resultado += " ";
            } else if (!bloc.isEmpty()) {
                int decimal = Integer.parseInt(bloc, 2);

                int asciiOriginal = decimal / clau;

                // Convertir el valor ASCII resultante a carácter
                char caracterOriginal = (char) asciiOriginal;

                resultado += caracterOriginal;
            }
        }

        return resultado;
    }
}
