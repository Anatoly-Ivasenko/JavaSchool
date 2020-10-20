package org.jschool.concurrentcacheproxy;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Реализует статические методы записи/считывания кэша, хранимого в ФС,
 * а также ограничения длины для результатов имплементирующих List.
 */

public class CacheUtils {

    /**
     * Обеспечивает сериализацию объекта result, являющегося результатом работы кэшируемого метода с настройками кэширования
     * из cacheSettings, в файл cacheFile
     * @param result Объект для сериализации в ФС
     * @param cacheSettings объект предоставляющий настройки кэширования
     * @param cacheFile файл для кэшировани
     * @throws Exception Бросает ряд исключений, связанных с некорректной работой с ФС и сериализацией
     */
    public static void save(Object result, CacheSettings cacheSettings, File cacheFile) throws Exception {
        synchronized (cacheFile) {
            try {
                if (!cacheFile.createNewFile()) throw new FileAlreadyExistsException("Файл уже существует");
            } catch (IOException e) {
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
    }

    /**
     * Обеспечивает сериализацию и сжатие объекта result, являющегося результатом работы кэшируемого метода с настройками кэширования
     * из cacheSettings, в файл cacheFile
     * @param result Объект для сериализации в ФС
     * @param cacheSettings объект предоставляющий настройки кэширования
     * @param cacheFile файл для кэшировани
     * @throws Exception Бросает ряд исключений, связанных с некорректной работой с ФС и сериализацией
     */
    public static void saveToZip(Object result, CacheSettings cacheSettings, File cacheFile) throws Exception {
        synchronized (cacheFile) {
            try {
                if (!cacheFile.createNewFile()) throw new FileAlreadyExistsException("Файл уже существует");
            } catch (IOException e) {
                throw new IOException("Нет доступа к файлу");
            }

            try (FileOutputStream cacheFileStream = new FileOutputStream(cacheFile);
                 GZIPOutputStream cacheZipFileStream = new GZIPOutputStream(cacheFileStream);
                 ObjectOutputStream cacheWrite = new ObjectOutputStream(cacheZipFileStream))
            {
                cacheWrite.writeObject(result);
            }
            catch (NotSerializableException e) {
                throw new NotSerializableException("Результат работы метода " + cacheSettings.getMethodName() +
                        " не сериализуем. Кэширование возможно только в память.");
            }
        }
    }

    /**
     * Обеспечивает десериализацию объекта, хранимогов в ФС в файле cacheFile
     * @param cacheFile файл для считывания
     * @return  возвращает десериализованный объект
     * @throws Exception  Бросает ряд исключений, связанных с некорректной работой с ФС и сериализацией
     */
    public static Object getCache(File cacheFile) throws Exception {
        try {
            try (FileInputStream cacheFileStream = new FileInputStream(cacheFile);
                 ObjectInputStream cacheRead = new ObjectInputStream(cacheFileStream))
            {
                return cacheRead.readObject();
            } catch (ClassNotFoundException e) {
                throw new ClassNotFoundException("Не найден класс сериализованного объекта");
            } catch (InvalidClassException e) {
                throw new InvalidClassException("Класс сериализванного объекта найден, но подвергся изменениям");
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Не обнаружен файл кэша");
        } catch (InvalidClassException e) {
            throw e;
        } catch (IOException e) {
            throw new IOException("Не возможно прочитать файл кэша");
        }
    }

    /**
     * Обеспечивает десериализацию и распаковку объекта, хранимогов в ФС в файле cacheFile
     * @param cacheFile файл для считывания
     * @return  возвращает десериализованный и распакованный объект
     * @throws Exception  Бросает ряд исключений, связанных с некорректной работой с ФС и сериализацией
     */
    public static Object getCacheFromZip(File cacheFile) throws Exception {
        try {
            try (FileInputStream cacheFileStream = new FileInputStream(cacheFile);
                 GZIPInputStream cacheZipFileStream = new GZIPInputStream(cacheFileStream);
                 ObjectInputStream cacheRead = new ObjectInputStream(cacheZipFileStream))
            {
                return cacheRead.readObject();
            } catch (ClassNotFoundException e) {
                throw new ClassNotFoundException("Не найден класс сериализованного объекта");
            } catch (InvalidClassException e) {
                throw new InvalidClassException("Класс сериализванного объекта найден, но подвергся изменениям");
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Не обнаружен файл кэша");
        } catch (InvalidClassException e) {
            throw e;
        } catch (IOException e) {
            throw new IOException("Не возможно прочитать файл кэша");
        }
    }

    /**
     * Обеспечивание проверку имплементирует ли класс указанного объекта result интерфейс List, установлены ли ограничения
     * длины списков для хранения в кэше в настройка кэширования cacheSettings, если оба условаия выполняются, то
     * объект result приводится к List и возвращатеся список длины установленной в настройках cacheSettings.
     * В противном случае возвращается объект result без изменений
     * @param result объект
     * @param cacheSettings настройки кэширования
     * @return объект
     */
    public static Object trimResult(Object result, CacheSettings cacheSettings) {

        if (cacheSettings.listCapacity() == 0 || !cacheSettings.resultIsAssignableToList()) {
            return result;
        }

        List<?> listResult = (List<?>) result;
        return listResult.subList(0, cacheSettings.listCapacity() - 1);
    }
}
