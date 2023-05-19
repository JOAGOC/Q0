import java.util.List;
import java.security.AlgorithmConstraints;
import java.util.ArrayList;

public class AnalizadorLéxico {
    private static String codeLine = "  0.23\n  #Resistencia1_N#\n134.123\n##\n";
    private String document = "0.23\n#Resistencia1_N#";
    private List<String> errores = new ArrayList<String>(); // almacenar errores
    private List<String[]> tabla = new ArrayList<String[]>();

    public AnalizadorLéxico(String document) {
        this.document = document;
    }

    public static int esIdentificador(int index, String codeLine) throws Exception{
        // Columnas de transicion
        final int gato = 0;
        final int letra = 1;
        final int numero =2;
        final int guionBajo = 3;
        final int fin = 4;// Estado final

        // Estados
        final int q1 = 1;
        final int q2 = 2;
        final int q3 = 3;
        final int q4 = 4;

        final int error = -1;
        final int estadoInicial = 0;
        final int estados[][] = {
                // #        letra    numero    _        otro
                {  q1,      fin,     fin,      fin,     fin},
                {  error,   q2,      error,    error,   error},
                {  q4,      q2,      q2,       q3,      error},
                {  error,   q2,      q2,       error,   error},
                {  error,   error,   error,    error,   fin}
        };

        int estado = estadoInicial;
        int caracter = estadoInicial;
        char c = codeLine.charAt(index);
        do {
            int cCode = (int) c;
            if (cCode == (int)'#'){
                caracter = gato;
            } else if ((cCode >= 65 && cCode <= 90) || (cCode >= 97 && cCode <= 122)) //65-90 mayúsculas 97-122 minúsculas
                caracter = letra;
            else if (cCode >= 48 && cCode <= 57) // Se corrige esta condición
                caracter = numero;
            else if ((int) '_' ==  cCode)
                caracter = guionBajo;
            else
                caracter = fin;
            if ((estado = estados[estado][caracter]) == error)
            throw new Exception(String.format(
                "Error: El carácter '%c' en la columna %d no es un caracter válido para un identificador.",
                c,index)); // Se cambia '%d' por '%c' y se agrega variable c
            if(++index >= codeLine.length()) // Se agrega esta condición para evitar el error StringIndexOutOfBoundsException
                return index;
            c = codeLine.charAt(index);
        } while (caracter != fin);
        return --index;
    }

    public static int esNumero(int index, String codeLine) throws Exception {
        // Columnas de transicion
        final int numero = 0;
        final int punto = 1;
        final int fin = 2;// Estado final

        // Estados
        final int q1 = 1;
        final int q2 = 2;
        final int q3 = 3;

        final int error = -1;
        final int estadoInicial = 0;
        final int estados[][] = {
                // {numero, punto, otro}
                { q1, q2, fin },
                { q1, q2, fin },
                { q3, error, error },
                { q3, error, fin }
        };
        int estado = estadoInicial;
        int caracter = estadoInicial;
        char c = codeLine.charAt(index);
        do {
            int cCode = (int) c;
            if (cCode >= 48 &&  cCode <= 57) // Se corrige esta condición
                caracter = numero;
            else if ((int) '.' ==  cCode)
                caracter = punto;
            else
                caracter = fin;
            if ((estado = estados[estado][caracter]) == error)
                throw new Exception(String.format(
                        "Error: El carácter '%c' en la columna %d no pertenece al conjunto de caracteres permitidos en un número.",
                        c,index)); // Se cambia '%d' por '%c' y se agrega variable c
            if(++index >= codeLine.length()) // Se agrega esta condición para evitar el error StringIndexOutOfBoundsException
                return index;
            c = codeLine.charAt(index);
        } while (caracter != fin);
        return --index;
    }

    public static void main(String[] args) {
        AnalizadorLéxico aLexico = new AnalizadorLéxico(AnalizadorLéxico.codeLine); // Se corrige el nombre de la instancia
        aLexico.analizar();
        System.out.println("Tabla de tokens:\n");
        aLexico.tabla.forEach((e)->{
            for (String string : e) {
                System.out.print(string+",");
            }
            System.out.println("");
        });
        aLexico.errores.forEach((e)->System.out.println(e));
        // try {
        //     int index = 0;
        //     String[] fila = { codeLine.substring(index, esNumero(0)), "Número" };
        //     aLexico.tabla.add(fila);
        // } catch (Exception e) {
        //     aLexico.errores.add(e.getMessage());
        // }

        // for (String error : aLexico.errores) {
        //     System.out.println(error);
        // }
        // for (String[] arreglo : aLexico.tabla) {
        //     System.out.println(arreglo[0] + " " + arreglo[1]);
        // }
    }

    private void analizar() {
        String[] lineas = document.split("\n");
        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i];
            for (int j = 0; j < lineas[i].length(); j++) {
                int indiceActual = j;
                switch (lineas[i].charAt(j)){
                    case ' ':
                    case '\t':break;
                    default:
                    try {
                        if ((j = esNumero(indiceActual,linea))!=indiceActual)
                            tabla.add(new String[]{linea.substring(indiceActual, j), "Número"});
                        else if ((j = esIdentificador(indiceActual,linea))!=indiceActual)
                            tabla.add(new String[]{linea.substring(indiceActual, j),"Identificador"});
                    } catch (Exception e) {
                        errores.add("Línea "+(i+1)+ "; " +e.getMessage());
                        e.printStackTrace();
                    } //Hay que validar identificadores no válidos
                }
            }
        }       
    }
}