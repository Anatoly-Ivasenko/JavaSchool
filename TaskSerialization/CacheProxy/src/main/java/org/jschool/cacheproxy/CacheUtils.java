package org.jschool.cacheproxy;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

public class CacheUtils {

    public static void save(Object result, CacheAnnotationResolver cacheSettings, File cacheFile) throws Exception {

        try {
            if (!cacheFile.createNewFile()) throw new FileAlreadyExistsException("Файл уже существует");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Нет доступа к файлу");
        }

        try (FileOutputStream cacheFileStream = new FileOutputStream(cacheFile);
             ObjectOutputStream cacheWrite = new ObjectOutputStream(cacheFileStream))
        {
            cacheWrite.writeObject(result);
        }
        catch (NotSerializableException e) {
            throw new NotSerializableException("Результат работы метода " + cacheSettings.getMethodName() +
                    " не сериализуем. Кэширование возможно только в память.");
        }
    }

    public static Object getCache(File cacheFile) throws Exception {
        try {
            try (FileInputStream cacheFileStream = new FileInputStream(cacheFile);
                 ObjectInputStream cacheRead = new ObjectInputStream(cacheFileStream))
            {
                return cacheRead.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new ClassNotFoundException("Не найден класс сериализованного объекта");
            } catch (InvalidClassException e) {
                e.printStackTrace();
                throw new InvalidClassException("Класс сериализванного объекта найден, но подвергся изменениям");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileNotFoundException("Не обнаружен файл кэша");
        } catch (InvalidClassException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Не возможно прочитать файл кэша");
        }

    }

    public static Object trimResult(Object result, CacheAnnotationResolver cacheSettings) {

        if (cacheSettings.listCapacity() == 0 || !cacheSettings.resultIsAssignableToList()) {
            return result;
        }

        List<?> listResult = (List<?>) result;
        return listResult.subList(0, cacheSettings.listCapacity() - 1);
    }
}
