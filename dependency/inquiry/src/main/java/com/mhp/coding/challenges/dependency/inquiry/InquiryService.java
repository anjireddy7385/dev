package com.mhp.coding.challenges.dependency.inquiry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mhp.coding.challenges.dependency.inquiry.adapter.InquiryAdapter;

@Component
public class InquiryService {

	private static final Logger LOG = LoggerFactory.getLogger(InquiryService.class);

	@Autowired
	private InquiryAdapter adapter;

	public void create(final Inquiry inquiry) {
		LOG.info("User sent inquiry: {}", inquiry);
		adapter.onInquiryCreated(inquiry);
	}

}
