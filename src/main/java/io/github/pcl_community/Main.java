package io.github.pcl_community;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class Main {
    private static final Path TEMP_PATH = Paths.get(".gptmp");

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Usage: java -jar glfw-patcher.jar path");
            System.exit(1);
        }

        String glfwPath = args[0];
        System.out.println("glfwPath is: " + glfwPath);

        try (JarFile jarFile = new JarFile(glfwPath);
             JarOutputStream jos = new JarOutputStream(Files.newOutputStream(TEMP_PATH))
        ) {
            try {
                Enumeration<JarEntry> entries = jarFile.entries();

                while(entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().endsWith(".class")) {
                        String className = entry.getName().replace(".class", "");
                        if (className.equals("org/lwjgl/glfw/GLFW")) {
                            byte[] bytes = readStream(jarFile.getInputStream(entry));
                            ClassReader reader = new ClassReader(bytes);
                            ClassWriter writer = new ClassWriter(reader, 2);
                            GLFWClassVisitor visitor = new GLFWClassVisitor(writer);
                            reader.accept(visitor, 8);
                            jos.putNextEntry(new ZipEntry(entry.getName()));
                            jos.write(writer.toByteArray());
                            jos.closeEntry();
                            continue;
                        }
                    }
                    copy(jarFile, entry, jos);
                }
            } catch (Throwable var34) {
                throw new RuntimeException(var34);
            }
        }

        Path path = Paths.get(glfwPath);
        Files.delete(path);
        Files.copy(TEMP_PATH, path);
        Files.delete(TEMP_PATH);
    }

    private static byte[] readStream(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[16384];

        int nRead;
        while((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();
    }

    private static void copy(JarFile jarFile, JarEntry entry, JarOutputStream jos) throws IOException {
        jos.putNextEntry(new ZipEntry(entry.getName()));
        byte[] buffer = new byte[1024];
        InputStream is = jarFile.getInputStream(entry);
        int len;
        while ((len = is.read(buffer)) > 0) {
            jos.write(buffer, 0, len);
        }
        jos.closeEntry();
    }
}
