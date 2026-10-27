package com.cgvsu.objwriter;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

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

            //запись нормалей
            for (Vector3f v : model.normals){
                writer.write(String.format(Locale.ROOT, "vn %.4f %.4f %.4f", v.getX(), v.getY(), v.getZ()));
                writer.newLine();
            }
            writer.newLine();

            //запись полигонов
            for (Polygon f : model.polygons){
                ArrayList<Integer> v = f.getVertexIndices();
                ArrayList<Integer> vt = f.getTextureVertexIndices();
                ArrayList<Integer> vn = f.getNormalIndices();

                // f 1 2 3 // v
                if (vn.isEmpty() && vt.isEmpty() && !v.isEmpty()){
                    String joined = v.stream().map(String::valueOf).collect(Collectors.joining(" "));
                    writer.write(String.format("f  %s", joined));
                }
                // f 1/1 2/2 3/3 // v/vt
                if (vn.isEmpty() && !vt.isEmpty() && !v.isEmpty()){
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < v.size(); i++){
                        sb.append(v.get(i) + "/" + vt.get(i) + " ");
                    }
                    sb.delete(sb.length()-1, sb.length());
                    writer.write(String.format("f  %s", sb));
                }
                //    f 1//1 2//2 3//3         v//vn
                if (!vn.isEmpty() && vt.isEmpty() && !v.isEmpty()){
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < v.size(); i++){
                        sb.append(v.get(i) + "//" + vn.get(i) + " ");
                    }
                    sb.delete(sb.length()-1, sb.length());
                    writer.write(String.format("f  %s", sb));
                }
                // f  1/1/1 2/2/2 3/3/3    v/vt/vn
                if (!vn.isEmpty() && !vt.isEmpty() && !v.isEmpty()){
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < v.size(); i++){
                        sb.append(v.get(i) + "/" + vt.get(i) + "/" + vn.get(i) + " ");
                    }
                    sb.delete(sb.length()-1, sb.length());
                    writer.write(String.format("f  %s", sb));
                }
                writer.newLine();

            }
        }
    }
}
