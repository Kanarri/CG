package com.cgvsu.objwriter;

import com.cgvsu.model.Model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ObjWriter {
    public static void write(Model model) throws IOException {
        Path path = Path.of("output.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(
                path,
                StandardOpenOption.CREATE,          // создать, если нет
                StandardOpenOption.TRUNCATE_EXISTING // перезаписать, если есть
        )) {
            writer.write("тест");
            writer.newLine();
        }
    }
}
