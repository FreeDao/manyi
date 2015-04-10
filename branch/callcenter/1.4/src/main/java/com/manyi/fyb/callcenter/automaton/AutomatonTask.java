package com.manyi.fyb.callcenter.automaton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AutomatonTask {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected boolean manually;
	
	public boolean isManually() {
		return manually;
	}
	public void setManually(boolean manually) {
		this.manually = manually;
	}
	public abstract void start();
}
