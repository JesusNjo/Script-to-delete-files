import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String folderPath = "C:\\Windows\\Temp";
        Path path = Paths.get(folderPath);

        try {
            if (Files.exists(path)) {
                try (Stream<Path> stream = Files.list(path)) {
                    List<Path> files = stream.filter(Files::isRegularFile)
                            .toList();

                    files.forEach(file->{
                        try{
                            Files.delete(file);
                            System.out.println("Archivo eliminado: " + file);
                        }catch (Exception e){
                            System.out.println("No se puede eliminar el archivo: "+file);
                        }
                    });
                    System.out.println("Los archivos han sido listados.");
                }
            } else {
                System.out.println("La carpeta no existe: " + folderPath);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
