package net.minecraftforge.common;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.Level;

import net.minecraftforge.fml.common.FMLCommonHandler;

public class ReflectionAPI {
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
	//检查是否资源的md5.
	/*
	public static boolean checkResourceMd5(ClassLoader loader, File file) {
        try {
            final Class<?> clazz = Class.forName("net.minecraft.launchwrapper.LaunchClassLoader", false, loader);
            final Method callMethod = clazz.getMethod("checkResourceMd5", new Class[]{File.class});
            boolean bRet = (Boolean)callMethod.invoke(null, (Object)(file));
            return bRet;
		} catch (Exception e) {
			errorLog(String.format("check md5 failed.message:%s.", e.toString()));
		} 
        return false;
	}
	*/
	//获取解密后的mod字节码。
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
