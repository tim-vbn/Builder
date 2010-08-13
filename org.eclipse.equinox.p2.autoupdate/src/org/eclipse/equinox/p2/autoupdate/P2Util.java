package org.eclipse.equinox.p2.autoupdate;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.equinox.internal.p2.core.helpers.ServiceHelper;
import org.eclipse.equinox.p2.core.IProvisioningAgent;
import org.eclipse.equinox.p2.operations.ProvisioningJob;
import org.eclipse.equinox.p2.operations.ProvisioningSession;
import org.eclipse.equinox.p2.operations.UpdateOperation;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;

/*
 * To Use P2Util.autoUpdate():
 * 		-Must have a p2.inf file next to product file of product to be updated that specifies
 * 		 the update site from which to update
 * 		-Best to have products/features/plugins use .qualifier in their versioning
 * 		-Include feature.p2.autoupdate in the main product feature and the product
 * 		-Have the following plugins set to autostart in Product configurations
 * 			org.eclipse.equinox.ds
 * 			org.eclipse.equinox.p2.autoupdate
 */

public class P2Util
{
	private static final String JUSTUPDATED = "justUpdated";
	
	public static IStatus checkForUpdates(IProvisioningAgent agent, IProgressMonitor monitor) throws OperationCanceledException
	{
		ProvisioningSession session = new ProvisioningSession(agent);
		UpdateOperation operation = new UpdateOperation(session);
		SubMonitor sub = SubMonitor.convert(monitor, "Checking for application updates...", 100);
		IStatus status = operation.resolveModal(sub.newChild(1));
		
		if (status.getCode() == UpdateOperation.STATUS_NOTHING_TO_UPDATE)
		{
			return status;
		}
		
		if (status.getSeverity() == IStatus.CANCEL)
		{
			throw new OperationCanceledException();
		}
		
		if (status.getSeverity() != IStatus.ERROR)
		{
			ProvisioningJob job = operation.getProvisioningJob(sub.newChild(2));
			if(job != null)
			{
				status = job.runModal(sub.newChild(98));
				
				if (status.getSeverity() == IStatus.CANCEL)
				{
					throw new OperationCanceledException();
				}
			}
		}
		return status;
	}
	
	public static boolean autoUpdate()
	{
		final IProvisioningAgent agent = (IProvisioningAgent) ServiceHelper.getService(Activator.bundleContext,IProvisioningAgent.SERVICE_NAME);
		if (agent == null)
		{
			return false;
		}
		
		final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		if (prefStore.getBoolean(JUSTUPDATED))
		{
			prefStore.setValue(JUSTUPDATED, false);
			return false;
		}
		
		IRunnableWithProgress runnable = new IRunnableWithProgress()
		{
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException
			{
				IStatus updateStatus = P2Util.checkForUpdates(agent, monitor);
				if (updateStatus.getCode() == UpdateOperation.STATUS_NOTHING_TO_UPDATE)
				{
					prefStore.setValue(JUSTUPDATED, false);
				} else if (updateStatus.getSeverity() != IStatus.ERROR)
				{
					prefStore.setValue(JUSTUPDATED, true);
				}
			}
		};
		
		try {
			new ProgressMonitorDialog(null).run(true, true, runnable);
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		if(prefStore.getBoolean(JUSTUPDATED))
		{
			return true;
		}
		return false;
	}
}
