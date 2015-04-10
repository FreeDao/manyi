package com.manyi.fyb.callcenter.automaton;

import com.manyi.fyb.callcenter.utils.HttpClientHelper;

public class PendingRecoveryQuartzTask extends AutomatonTask {

	@Override
	public void start() {
		// TODO Auto-generated method stub
		if(manually){
			return;
		}
		String himsShortUrl = "/distribute/pendingRecovery.rest";
		HttpClientHelper.sendRestInterShort(himsShortUrl);
	}
}
