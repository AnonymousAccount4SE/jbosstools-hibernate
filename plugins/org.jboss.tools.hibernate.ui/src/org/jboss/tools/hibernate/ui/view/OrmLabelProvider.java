/*******************************************************************************
 * Copyright (c) 2007-2009 Red Hat, Inc.
 * Distributed under license by Red Hat, Inc. All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributor:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.jboss.tools.hibernate.ui.view;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.hibernate.console.ConsoleConfiguration;
import org.hibernate.console.KnownConfigurations;
import org.hibernate.console.execution.ExecutionContext;
import org.hibernate.eclipse.console.HibernateConsolePlugin;
import org.jboss.tools.hibernate.runtime.spi.IColumn;
import org.jboss.tools.hibernate.runtime.spi.IConfiguration;
import org.jboss.tools.hibernate.runtime.spi.IPersistentClass;
import org.jboss.tools.hibernate.runtime.spi.IProperty;
import org.jboss.tools.hibernate.runtime.spi.IService;

/**
 *
 */
public class OrmLabelProvider extends LabelProvider implements IColorProvider, IFontProvider {

	private Map<ImageDescriptor, Image> imageCache = new HashMap<ImageDescriptor, Image>(25);
	private IService service = null;
	
	protected String consoleConfigName;

	public OrmLabelProvider() {
	}

	public OrmLabelProvider(String consoleConfigName) {
		super();
		setConsoleConfigName(consoleConfigName);
	}

	public void setConsoleConfigName(String consoleConfigName) {
		if (this.consoleConfigName == consoleConfigName) {
			return;
		}
		this.consoleConfigName = consoleConfigName;
		final ConsoleConfiguration consoleConfig = getConsoleConfig();
		// need to build session factory for hibernate 4.3
		// TODO verify if/how this can be done differently
		if (consoleConfig != null && "4.3".equals(consoleConfig.getHibernateExtension().getHibernateVersion())) {
			getConsoleConfig().execute(new ExecutionContext.Command() {			
				@Override
				public Object execute() {
					return getConfig().buildSessionFactory();
				}
			});
		}
	}

	protected IConfiguration getConfig() {
		final ConsoleConfiguration consoleConfig = getConsoleConfig();
		if (consoleConfig != null) {
			if (!consoleConfig.hasConfiguration()) {
				try {
    				consoleConfig.build();
    				consoleConfig.buildMappings();
				} catch (Exception he) {
					HibernateConsolePlugin.getDefault().logErrorMessage("HibernateException: ", he); //$NON-NLS-1$
				}
			}
			return consoleConfig.getConfiguration();
		}
		return null;
	}

	protected ConsoleConfiguration getConsoleConfig() {
		final KnownConfigurations knownConfigurations = KnownConfigurations.getInstance();
		ConsoleConfiguration consoleConfig = knownConfigurations.find(consoleConfigName);
		return consoleConfig;
	}

	@Override
	public Image getImage(Object element) {
		ImageDescriptor descriptor = OrmImageMap.getImageDescriptor(element, getConsoleConfig());
		if (descriptor == null) {
			return null;
		}
		Image image = imageCache.get(descriptor);
		if (image == null) {
			image = descriptor.createImage();
			imageCache.put(descriptor, image);
		}
		return image;
	}

	@Override
	public String getText(Object obj) {
		if (obj instanceof IColumn) {
			updateColumnSqlType((IColumn)obj);
		}
		return OrmLabelMap.getLabel(obj, getConsoleConfig());
	}

	public void dispose() {
		for (Iterator<Image> i = imageCache.values().iterator(); i.hasNext();) {
			i.next().dispose();
		}
		imageCache.clear();
	}

	public Color getForeground(Object element) {
		if (element instanceof IPersistentClass && ((IPersistentClass)element).isInstanceOfRootClass()) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GREEN);
		} else if (element instanceof IProperty) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE);
		}
		return null;
	}

	public Color getBackground(Object element) {
		return null;
	}

	public Font getFont(Object element) {
		//return JFaceResources.getFontRegistry().getBold(JFaceResources.getTextFont().getFontData()[0].getName());
		return null;
	}
	
	/**
	 * For correct label creation should update column sql type.
	 * @param column
	 * @return
	 */
	public boolean updateColumnSqlType(final IColumn column) {
		String sqlType = column.getSqlType();
		if (sqlType != null) {
			return false;
		}
		final IConfiguration config = getConfig();
		if (config != null) {
			final ConsoleConfiguration consoleConfig = getConsoleConfig();
			try {
				sqlType = (String)consoleConfig.execute(new ExecutionContext.Command() {
					public Object execute() {
						return column.getSqlType(config);
					}
				} );
			} catch (Exception e) {
				// do not ignore it - print in Error Log
				HibernateConsolePlugin.getDefault().logErrorMessage("Exception: ", e); //$NON-NLS-1$
			}
		}
		if (sqlType != null) {
			column.setSqlType(sqlType);
			return true; 
		}
		return false;
	}
	
	private IService getService() {
		if (service == null) {
			service = getConsoleConfig().getHibernateExtension().getHibernateService();
		}
		return service;
	}

}