package com.eauto100.pub.service.framework.context;


public class EopContext {
	private static ThreadLocal<EopContext> EopContextHolder = new ThreadLocal<EopContext>();
	
	public static void setContext(EopContext context) {
		EopContextHolder.set(context);
	}

	public static void remove() {
		EopContextHolder.remove();
	}

	public static EopContext getContext() {
		EopContext context = EopContextHolder.get();
		return context;
	}

 

 

	 

	 
}
