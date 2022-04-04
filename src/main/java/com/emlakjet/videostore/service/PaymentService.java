package com.emlakjet.videostore.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.emlakjet.videostore.domain.Bill;
import com.emlakjet.videostore.domain.Content;
import com.emlakjet.videostore.domain.Subscription;
import com.emlakjet.videostore.domain.SubscriptionType;
import com.emlakjet.videostore.domain.User;
import com.emlakjet.videostore.exception.ContentNotFoundException;
import com.emlakjet.videostore.exception.NotEnoughLimitException;
import com.emlakjet.videostore.repository.BillRepo;
import com.emlakjet.videostore.repository.entities.BillEntity;
import com.emlakjet.videostore.repository.entities.mapper.BillEntityMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class PaymentService {

	private static final long THIRTY_DAYS = 30L;

	private final BillRepo billRepo;
	private final ContentService contentService;
	private final UserService userService;
	private final BillEntityMapper entityMapper;

	public Bill purchaseContent(User user, long contentId) {
		Content content = contentService.getContent(contentId).orElseThrow(ContentNotFoundException::new);
		Subscription currentSubscription = user.getCurrentSubscription();
		BigDecimal remainingAmount = currentSubscription.getRemainingAmount();
		BigDecimal contentPrice = content.getPrice();
		if (remainingAmount.compareTo(contentPrice) >= 0) {
			BigDecimal newRemainingAmount = remainingAmount.subtract(contentPrice);
			user.getCurrentSubscription().setRemainingAmount(newRemainingAmount);
			userService.save(user);

			return saveBill(user, content);
		} else {
			log.info("there is not enough limit in user subscription. remainin amount:{} content price:{}",
					remainingAmount, contentPrice);
			throw new NotEnoughLimitException();
		}
	}

	protected Bill saveBill(User user, Content content) {
		//@formatter:off
		Bill bill = Bill.builder()
				.amount(content.getPrice())
				.content(content)
				.user(user)
				.createdAt(LocalDateTime.now())
				.build();
		//@formatter:on

		BillEntity billEntity = entityMapper.domainToEntity(bill);
		BillEntity savedBillEntity = billRepo.save(billEntity);
		Bill savedBill = entityMapper.entityToDomain(savedBillEntity);
		log.debug("bill:{} is created", savedBill.getBillNo());
		return savedBill;
	}

	public Subscription purchaseSubscription(User user, SubscriptionType subscriptionType) {
		if (subscriptionType == SubscriptionType.NO_SUBSCRIPTION) {
			throw new IllegalArgumentException("invalid subscription type to purchase");
		}
		Subscription currentSubscription = user.getCurrentSubscription();
		currentSubscription.setSubscriptionType(subscriptionType);
		LocalDateTime now = LocalDateTime.now();
		currentSubscription.setStartDate(now);
		currentSubscription.setEndDate(now.plusDays(THIRTY_DAYS));
		BigDecimal remainingAmount = currentSubscription.getRemainingAmount();
		BigDecimal newRemainingAmount = remainingAmount.add(subscriptionType.getPackagePrice());
		currentSubscription.setRemainingAmount(newRemainingAmount);

		User savedUser = userService.save(user);
		log.debug("subscription is saved successfully");
		return savedUser.getCurrentSubscription();
	}

	public List<Bill> getBills(User user, int page, int size) {
		PageRequest sortByCreationDate = PageRequest.of(page, size, Sort.by("createdAt"));
		Page<BillEntity> billEntities = billRepo.findAllByEmail(user.getEmail(), sortByCreationDate);
		List<Bill> bills = entityMapper.entityToDomain(billEntities.getContent());
		log.debug("fetched bills:{}", bills.size());
		return bills;
//		return billEntities.stream().map(entityMapper::entityToDomain).collect(Collectors.toList());
	}
}
