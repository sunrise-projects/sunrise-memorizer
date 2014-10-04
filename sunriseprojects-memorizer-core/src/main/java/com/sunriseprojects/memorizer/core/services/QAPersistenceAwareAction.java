package com.sunriseprojects.memorizer.core.services;

import com.sunriseprojects.memorizer.core.framework.AbstractAction;


public abstract class QAPersistenceAwareAction extends AbstractAction {

	private QAPersistenceInterface persistenceService;

	/**
	 * @param persistenceService The persistenceService to set.
	 */
	public void setPersistenceService(QAPersistenceInterface persistenceService) {
		this.persistenceService = persistenceService;
	}

	/**
	 * @return Returns the persistenceService.
	 */
	public QAPersistenceInterface getPersistenceService() {
		return persistenceService;
	}
}
