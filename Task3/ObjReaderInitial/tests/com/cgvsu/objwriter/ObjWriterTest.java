package com.cgvsu.objwriter;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ObjWriterTest {

    private static final Path OUTPUT = Path.of("output.txt");

    private static Model emptyModel() {
        Model model = new Model();
        model.vertices = new ArrayList<>();
        model.textureVertices = new ArrayList<>();
        model.normals = new ArrayList<>();
        model.polygons = new ArrayList<>();
        return model;
    }

    private static Polygon polygon(List<Integer> v, List<Integer> vt, List<Integer> vn) {
        Polygon f = new Polygon();
        f.setVertexIndices(new ArrayList<>(v));
        if (vt != null) f.setTextureVertexIndices(new ArrayList<>(vt));
        if (vn != null) f.setNormalIndices(new ArrayList<>(vn));
        return f;
    }

    private static List<String> readOutput() throws IOException {
        return Files.readAllLines(OUTPUT);
    }

    @Test
    public void testWriteEmptyModel() throws IOException {
        ObjWriter.write(emptyModel());
        List<String> lines = readOutput();
        Assertions.assertTrue(lines.stream().allMatch(String::isEmpty),"Ожидались только пустые строки, а получили: " + lines);
    }

    @Test
    public void testWriteVertices() throws IOException {
        Model model = emptyModel();
        model.vertices.add(new Vector3f(1.0f, 2.0f, 3.0f));
        model.vertices.add(new Vector3f(-1.5f, 0.0f, 4.25f));
        ObjWriter.write(model);
        List<String> lines = readOutput();
        Assertions.assertEquals("v  1.0000 2.0000 3.0000", lines.get(0));
        Assertions.assertEquals("v  -1.5000 0.0000 4.2500", lines.get(1));
    }

    @Test
    public void testWriteTextureVertices() throws IOException {
        Model model = emptyModel();
        model.textureVertices.add(new Vector2f(0.5f, 0.25f));
        ObjWriter.write(model);
        List<String> lines = readOutput();
        Assertions.assertEquals("vt 0.5000 0.2500 0.0000", lines.get(1));
    }

    @Test
    public void testWriteNormals() throws IOException {
        Model model = emptyModel();
        model.normals.add(new Vector3f(0.0f, 0.0f, 1.0f));
        ObjWriter.write(model);
        List<String> lines = readOutput();
        Assertions.assertEquals("vn 0.0000 0.0000 1.0000", lines.get(2));
    }

    @Test
    public void testWritePolygonOnlyVertices() throws IOException {
        Model model = emptyModel();
        model.vertices.addAll(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(0, 1, 0)));
        model.polygons.add(polygon(Arrays.asList(0, 1, 2), null, null));
        ObjWriter.write(model);
        List<String> lines = readOutput();
        Assertions.assertEquals("f  1 2 3", lines.get(6));
    }

    @Test
    public void testWritePolygonWithTexture() throws IOException {
        Model model = emptyModel();
        model.vertices.addAll(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(0, 1, 0)));
        model.textureVertices.addAll(Arrays.asList(
                new Vector2f(0, 0),
                new Vector2f(1, 0),
                new Vector2f(0, 1)));
        model.polygons.add(polygon(Arrays.asList(0, 1, 2), Arrays.asList(0, 1, 2), null));
        ObjWriter.write(model);
        List<String> lines = readOutput();
        Assertions.assertEquals("f  1/1 2/2 3/3", lines.get(9));
    }

    @Test
    public void testWritePolygonWithNormals() throws IOException {
        Model model = emptyModel();
        model.vertices.addAll(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(0, 1, 0)));
        model.normals.addAll(Arrays.asList(
                new Vector3f(0, 0, 1),
                new Vector3f(0, 0, 1),
                new Vector3f(0, 0, 1)));
        model.polygons.add(polygon(Arrays.asList(0, 1, 2), null, Arrays.asList(0, 1, 2)));
        ObjWriter.write(model);
        List<String> lines = readOutput();
        Assertions.assertEquals("f  1//1 2//2 3//3", lines.get(9));
    }

    @Test
    public void testWritePolygonWithTextureAndNormals() throws IOException {
        Model model = emptyModel();
        model.vertices.addAll(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(0, 1, 0)));
        model.textureVertices.addAll(Arrays.asList(
                new Vector2f(0, 0),
                new Vector2f(1, 0),
                new Vector2f(0, 1)));
        model.normals.addAll(Arrays.asList(
                new Vector3f(0, 0, 1),
                new Vector3f(0, 0, 1),
                new Vector3f(0, 0, 1)));
        model.polygons.add(polygon(Arrays.asList(0, 1, 2), Arrays.asList(0, 1, 2), Arrays.asList(0, 1, 2)));
        ObjWriter.write(model);
        List<String> lines = readOutput();
        Assertions.assertEquals("f  1/1/1 2/2/2 3/3/3", lines.get(12));
    }

    @Test
    public void testWriteOverwritesExistingFile() throws IOException {
        //первая запись
        Model first = emptyModel();
        first.vertices.add(new Vector3f(1, 1, 1));
        ObjWriter.write(first);
        //старый файл должен быть перезаписан
        Model second = emptyModel();
        second.vertices.add(new Vector3f(2, 2, 2));
        ObjWriter.write(second);
        List<String> lines = readOutput();
        Assertions.assertEquals("v  2.0000 2.0000 2.0000", lines.get(0));
        Assertions.assertEquals(4, lines.size(), "Файл должен быть перезаписан а не дополнен");
    }

    @Test
    public void testWritePolygonWithMismatchedTextureSize() throws IOException {
        Model model = emptyModel();
        model.vertices.addAll(Arrays.asList(
                new Vector3f(0, 0, 0),
                new Vector3f(1, 0, 0),
                new Vector3f(0, 1, 0)));
        model.textureVertices.addAll(Arrays.asList(
                new Vector2f(0, 0),
                new Vector2f(1, 0),
                new Vector2f(0, 1)));
        Polygon f = polygon(
                Arrays.asList(0, 1, 2),
                Arrays.asList(0, 1, 2),
                null);
        f.getTextureVertexIndices().remove(2);
        f.getTextureVertexIndices().remove(1);

        model.polygons.add(f);

        try {
            ObjWriter.write(model);
            Assertions.fail("Ожидалось ObjWriterException из-за несовпадения размеров v и vt");
        } catch (ObjWriterException exception) {
            Assertions.assertTrue(exception.getMessage().contains("TextureVertexes"),"Сообщение должно указывать на проблему с текстурными вершинами, а получили: " + exception.getMessage());
        }
    }
}