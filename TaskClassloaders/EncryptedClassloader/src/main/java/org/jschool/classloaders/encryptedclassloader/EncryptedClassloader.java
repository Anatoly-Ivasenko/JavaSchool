package org.jschool.classloaders.encryptedclassloader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystemException;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;

public class EncryptedClassloader extends ClassLoader {
    private final String key;
    private final File dir;

    public EncryptedClassloader(String key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    /**
     * Переопределяет метод findClass и возвращает класс с указанным именем загруженный из зашифрованного файла,
     * который находится в директории dir, и дешифрованный ключом key.
     *
     * Поиск и загрузка массива байт осуществляется методом findAndLoad, дешифровка - методом decrypt.
     *
     * @param className String имя класса
     * @return     Class
     * @throws ClassNotFoundException генерируется в случае отсутствия директории, файла, или невозможности
     * прочитать файл
     */
    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            byte[] byteCode = decrypt(findAndLoad(className));
            return defineClass(className, byteCode, 0, byteCode.length);
        } catch (IOException o) {
            throw new ClassNotFoundException(o.getMessage());
        }
    }

    /**
     * Ищет файл-зашированный класс по указанному пути, в случае наличия возвращает массив байтов из этого файла
     *
     * Для поиска нужного файла используется реализация интерфейса FilenameFilter и метод File.listFiles
     *
     * @param className String  имя класса
     * @return  byte[] массив байт, содержащийся в зашифрованном файле-классе.
     * @throws IOException  Исключения генерируются в случае отсутствия директории, файла,
     * или невозможности прочитать файл
     */
    private byte[] findAndLoad (String className) throws IOException {
        if (!dir.isDirectory()) throw new NotDirectoryException("Not directory");
        FilenameFilter filter = new FilenameFilterImpl(dir, className);
        if (dir.listFiles(filter).length == 1) {
            File classFile = dir.listFiles(filter)[0];
            if (!classFile.canRead()) {
                throw new FileSystemException("Cannot read file");
            }
            FileInputStream encryptFileStream = new FileInputStream(classFile);
            byte[] encrypt = new byte[(int) classFile.length()];
            encryptFileStream.read(encrypt);
            encryptFileStream.close();
            return encrypt;
        }
        else throw new NoSuchFileException("No such classfile");
    }

    /**
     * Возвращает дешифрованный массив байтов для указанного исходного (шифрованного) массива байтов,
     * дешифровка производится ключом key.
     * Алгоритм дешифровки: строка key преобразуется в массив байт методом String.getBytes(Charset charset),
     * затем побайтно складываем исходный массив и ключ, результат сложения записываем в дешифрованный массива
     * без старших разрядов, кодга ключ заканчивается начинаем перебирать его сначала.
     *
     * @param encrypt   byte[] зашифрованный массив байтов
     * @return          byte[] дешифрованный массив байтов
     */
    private byte[] decrypt(byte[] encrypt) {
        byte[] cryptKey = key.getBytes(StandardCharsets.UTF_16);
        byte[] decrypt = new byte[encrypt.length];
        int keyCounter = 0;
        for (int i = 0;  i < encrypt.length; i++) {
            decrypt[i] = (byte) (encrypt[i] - cryptKey[keyCounter]);
            keyCounter++;
            if (keyCounter >= cryptKey.length) {
                keyCounter = 0;
            }
        }
        return decrypt;
    }
}
