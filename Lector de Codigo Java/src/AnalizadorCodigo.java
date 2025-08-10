import java.io.*;
import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalizadorCodigo {

    public static void main(String[] args) {
        //Ruta de carpeta a analizar
        String rutaCarpeta = "C:\\Users\\juanm\\OneDrive\\Documentos\\NetBeansProjects\\AlgoritmoHuffman";

        //Manejo d errores
        try {
            Files.walk(Paths.get(rutaCarpeta))
            .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java")) //Obtener archivos y mirar cual es .Java
                    .forEach(AnalizadorCodigo::analizadorCodigo); //Llama metodo para analizar codigo

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void analizadorCodigo(Path archivo) {
        System.out.println("\n Archivo: " + archivo.getFileName());

        int lineas = 0;
        Pattern patronClase = Pattern.compile("\\bclass\\s+(\\w+)"); //Patron para identificar a nivel de codigo que es una clase
        Pattern patronMetodo = Pattern.compile("\\b(public|private|protected)?\\s*(static)?\\s*\\w+\\s+(\\w+)\\s*\\([^)]*\\)\\s*\\{?"); //Identificador para saber que es un metodo a nivel de codigo

        try (BufferedReader br = new BufferedReader(new FileReader(archivo.toFile()))) { //Lector de archivo en que nos encontramos
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas++;

                Matcher matcherClase = patronClase.matcher(linea);
                if (matcherClase.find()) {
                    System.out.println("Clase Encontrada: " + matcherClase.group(1));
                }

                Matcher matcherMetodo = patronMetodo.matcher(linea);
                if (matcherMetodo.find()) {
                    System.out.println("Metodo Encontrada: " + matcherMetodo.group(3));
                }
            }
            System.out.println("Total Lineas: " +  lineas);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}