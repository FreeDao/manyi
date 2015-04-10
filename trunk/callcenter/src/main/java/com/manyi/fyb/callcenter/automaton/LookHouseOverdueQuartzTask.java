package com.manyi.fyb.callcenter.automaton;

import com.manyi.fyb.callcenter.utils.HttpClientHelper;

public class LookHouseOverdueQuartzTask extends AutomatonTask{

	@Override
	public void start() {
		// TODO Auto-generated method stub
		if(manually){
			return;
		}
		String himsShortUrl = "/rollpoling/overdue.rest";
		HttpClientHelper.sendRestInterShort(himsShortUrl);
	}
}
