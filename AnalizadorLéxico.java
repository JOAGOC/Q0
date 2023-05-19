import java.util.List;
import java.util.ArrayList;

public class AnalizadorLéxico {
    private static String documento = "g12a";
    private List<String> errores = new ArrayList<String>(); // almacenar errores
    private ArrayList<String[]> tabla = new ArrayList<String[]>();

    public AnalizadorLéxico() {
    }

    public static int esNumero(int index) throws Exception {
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
        char c = documento.charAt(index);
        do {
            if ((int) c >= 48 && (int) c <= 57) // Se corrige esta condición
                caracter = numero;
            else if ((int) '.' == (int) c)
                caracter = punto;
            else
                caracter = fin;
            if ((estado = estados[estado][caracter]) == -1)
                throw new Exception(String.format(
                        "Error: El carácter '%c' no pertenece al conjunto de caracteres permitidos en un número.",
                        c)); // Se cambia '%d' por '%c' y se agrega variable c
            index++;
            if(index >= documento.length()) // Se agrega esta condición para evitar el error StringIndexOutOfBoundsException
                return index;
            c = documento.charAt(index);
        } while (caracter != fin);
        return --index;
    }

    public static void main(String[] args) {
        AnalizadorLéxico aLexico = new AnalizadorLéxico(); // Se corrige el nombre de la instancia
        try {
            int index = 0;
            String[] fila = { documento.substring(index, esNumero(0)), "Número" };
            aLexico.tabla.add(fila);
        } catch (Exception e) {
            aLexico.errores.add(e.getMessage());
        }

        for (String error : aLexico.errores) {
            System.out.println(error);
        }
        for (String[] arreglo : aLexico.tabla) {
            System.out.println(arreglo[0] + " " + arreglo[1]);
        }
    }
}