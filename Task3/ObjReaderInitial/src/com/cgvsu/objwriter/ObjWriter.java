package com.cgvsu.objwriter;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Locale;

public class ObjWriter {
    public static void write(Model model) throws IOException {
        Path path = Path.of("output.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))
        {
            writer.write("пупупу");
            //запись вершин
            for (Vector3f v : model.vertices){
                writer.write(String.format(Locale.ROOT, "v  %.4f %.4f %.4f", v.getX(), v.getY(), v.getZ()));
                writer.newLine();
            }
            writer.newLine();

            //запись текстурных вершин
            for (Vector2f v : model.textureVertices){
                writer.write(String.format(Locale.ROOT, "vt %.4f %.4f %.4f", v.getX(), v.getY(), v.getZ()));
                writer.newLine();
            }
            writer.newLine();



            writer.write("тест");
            writer.newLine();
        }
    }
}
