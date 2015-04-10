package com.manyi.fyb.callcenter.automaton;

import com.manyi.fyb.callcenter.utils.HttpClientHelper;


public class DistributionQuartzTask extends AutomatonTask{

	@Override
	public void start() {
		// TODO Auto-generated method stub
		if(manually){
			return;
		}
		String himsShortUrl = "/distribute/check.rest";
		HttpClientHelper.sendRestInterShort(himsShortUrl);
	}
}
