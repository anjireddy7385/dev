package com.mhp.coding.challenges.dependency.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mhp.coding.challenges.dependency.inquiry.Inquiry;
import com.mhp.coding.challenges.dependency.inquiry.adapter.InquiryAdapter;

@Component
public class AdapterImpl implements InquiryAdapter {

	@Autowired
	private EmailHandler emailHandler;
	@Autowired
	private PushNotificationHandler notificationHandler;

	@Override
	public void onInquiryCreated(final Inquiry inquiry) {
		emailHandler.sendEmail(inquiry);
		notificationHandler.sendNotification(inquiry);
	}

}
