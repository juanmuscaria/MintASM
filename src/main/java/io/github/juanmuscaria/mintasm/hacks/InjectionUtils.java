package io.github.juanmuscaria.mintasm.hacks;

import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.InputStream;
import java.lang.reflect.Method;

//https://github.com/evernife/EventHelper/blob/1.7.10/src/main/java/com/gamerforea/eventhelper/util/InjectionUtils.java
//Não use isso para injetar classes com classes ocultas (blablabla.class$classOculta), ele não consegue identificar essas classes.
public final class InjectionUtils
{
    private static final Method defineClass;

    // Need Inj subclass
    public static Class<?> injectClass(String pluginName, Class<?> clazz)
    {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        if (plugin == null)
            return null;

        try (InputStream in = clazz.getClassLoader().getResourceAsStream(clazz.getName().replace('.', '/') + ".class"))
        {
            byte[] bytes = ByteStreams.toByteArray(in);
            return (Class<?>) defineClass.invoke(plugin.getClass().getClassLoader(), null, bytes, 0, bytes.length);
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
            return null;
        }
    }

    static
    {
        try
        {
            defineClass = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
            defineClass.setAccessible(true);
        }
        catch (Throwable throwable)
        {
            throw new RuntimeException("Failed hooking ClassLoader.defineClass(String, byte[], int, int) method!", throwable);
        }
    }
}