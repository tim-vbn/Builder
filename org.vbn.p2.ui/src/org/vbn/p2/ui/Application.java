package org.vbn.p2.ui;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.equinox.p2.autoupdate.P2Util;

public class Application implements IApplication
{
	private static Object exitLock = new Object();
	
	public Object start(IApplicationContext context) throws Exception
	{
		try{
			if(P2Util.autoUpdate())
			{
				return Application.EXIT_RESTART;
			}
			
			new MainFrame();
			
			synchronized (exitLock)
			{
				exitLock.wait();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public void stop()
	{
		
	}
	
	public static void exitApplication()
	{
		synchronized (exitLock)
		{
			exitLock.notify();
		}
	}
}