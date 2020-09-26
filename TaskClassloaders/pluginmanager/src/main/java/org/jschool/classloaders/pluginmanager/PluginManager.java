package org.jschool.classloaders.pluginmanager;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class PluginManager {
    private final String pluginRootDirectory;
    private final Map<String, URLClassLoader> classLoaderMap;

    public PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
        this.classLoaderMap = new HashMap<>();
    }

    /**
     * Возврщает объект приведенный к интерфейсу Plugin, загруженный из pluginRootDirectory\pluginName
     * Для каждого pluginName создаётся один экземпляр URLClassLoader'а при первом обращении к pluginName,
     * который хранится в Map (ключ - pluginName, значение - URLClassLoader). Таким образом, один класслоадер
     * будет загружать все классы одного плагина, и следовательно даже если в разных плагинах (например А и Б)
     * совпадут имена классов, то экземпляр загрузчика плагина Б, не "будет знать" о загруженных классах плагина А,
     * и загрузит необхудимый класс по указанному путь.
     *
     * TODO Для реализации усложненной версии необходим создать свой класс - наследник от URLClassLoader
     * который не будет "отправлять" запрашиваемый класс если он его не загружал в родительский ClassLoader,
     * а будет загружать сам.
     *
     * @param pluginName        String  наименование плагина
     * @param pluginClassName   String  имя класса плагина
     * @return  Возвращает объект указанного класса приведенный к интерфейсу Plugin
     * @throws Exception    Данная реализация не обрабатывает исключения
     */
    public Plugin load(String pluginName, String pluginClassName) throws Exception {
        URLClassLoader pluginLoader;
        if (classLoaderMap.containsKey(pluginName)) {
            pluginLoader = classLoaderMap.get(pluginName);
        }
        else {
            pluginLoader = getPluginLoader(pluginName);
            classLoaderMap.put(pluginName, pluginLoader);
        }
        Class<?> pluginClazz = pluginLoader.loadClass(pluginClassName);
        return (Plugin) pluginClazz.newInstance();
    }


    public URLClassLoader getPluginLoader(String pluginName) throws MalformedURLException {
        URL[] currentPluginPath = new URL[0];
        currentPluginPath[0] = new URL(pluginRootDirectory + "\\" + pluginName);
        return URLClassLoader.newInstance(currentPluginPath);
    }
}