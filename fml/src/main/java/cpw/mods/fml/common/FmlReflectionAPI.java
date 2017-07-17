package cpw.mods.fml.common;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.Level;

//fm使用到的反射api。这个版本fm和forge不在一个工程中，不能复用forge工程中对应reflection api。
public class FmlReflectionAPI {
	//检查是否有权限使用mod。
	public static boolean checkPermission(ClassLoader loader, File modeFile) {
        try {
            final Class<?> clazz = Class.forName("net.minecraft.launchwrapper.LaunchClassLoader", false, loader);
            final Method callMethod = clazz.getMethod("checkPermission", new Class[]{String.class});
            boolean bRet = (Boolean)callMethod.invoke(null, (Object)(modeFile.getName()));
            return bRet;
		} catch (Exception e) {
			errorLog(String.format("check permission failed.message:%s.", e.toString()));
			return true;
		} 
        return false;
	}
	public static byte[] decrytClass(ClassLoader loader, byte[] classBytes) throws 
			SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try{
			final Class<?> clazz = Class.forName("net.minecraft.launchwrapper.AESHelper", false, loader);
			final Method callMethod = clazz.getMethod("Decrypt", new Class[]{byte[].class});
			byte[] decrytBytes = (byte[])callMethod.invoke(null, (Object)classBytes);
		} catch (NoSuchMethodException e) {
			return classBytes;
		} 
        return decrytBytes;
	}
	
	public static void errorLog(String s) {
		FMLCommonHandler.instance().getFMLLogger().log(Level.ERROR, s);
	}

}
