import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Timer timer = new Timer();
        long delay = 0;
        long period = 86400000L;

        timer.scheduleAtFixedRate(new DeleteFilesTask("C:\\Windows\\Temp"), delay, period);
        timer.scheduleAtFixedRate(new DeleteFilesTask(System.getenv("TEMP")), delay, period);
    }
}

class DeleteFilesTask extends TimerTask {
    private final String folderPath;

    public DeleteFilesTask(String folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    public void run() {
        Path path = Paths.get(folderPath);

        try {
            if (Files.exists(path)) {
                try (Stream<Path> stream = Files.walk(path)) {
                    List<Path> files = stream.sorted((p1, p2) -> p2.compareTo(p1))
                            .filter(p -> Files.isRegularFile(p) || Files.isDirectory(p))
                            .toList();

                    files.forEach(file -> {
                        try {
                            if (Files.isDirectory(file)) {
                                Files.delete(file);
                                System.out.println("Carpeta eliminada: " + file);
                            } else {
                                Files.delete(file);
                                System.out.println("Archivo eliminado: " + file);
                            }
                        } catch (IOException e) {
                            System.out.println("No se puede eliminar: " + file);
                        }
                    });

                    System.out.println("Los archivos y carpetas han sido eliminados.");
                }
            } else {
                System.out.println("La carpeta no existe: " + folderPath);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
