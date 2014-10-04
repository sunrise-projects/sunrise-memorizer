package com.sunriseprojects.memorizer.core.services;

import com.sunriseprojects.memorizer.core.framework.AbstractRule;


public abstract class QAPersistenceAwareRule extends AbstractRule {

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
