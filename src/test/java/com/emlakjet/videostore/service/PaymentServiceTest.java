package com.emlakjet.videostore.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.emlakjet.videostore.domain.Bill;
import com.emlakjet.videostore.domain.Content;
import com.emlakjet.videostore.domain.ContentType;
import com.emlakjet.videostore.domain.Subscription;
import com.emlakjet.videostore.domain.SubscriptionType;
import com.emlakjet.videostore.domain.User;
import com.emlakjet.videostore.repository.BillRepo;
import com.emlakjet.videostore.repository.entities.BillEntity;
import com.emlakjet.videostore.repository.entities.ContentEntity;
import com.emlakjet.videostore.repository.entities.ContentTypeEntity;
import com.emlakjet.videostore.repository.entities.SubscriptionEntity;
import com.emlakjet.videostore.repository.entities.SubscriptionTypeEntity;
import com.emlakjet.videostore.repository.entities.UserEntity;
import com.emlakjet.videostore.repository.entities.mapper.BillEntityMapper;

public class PaymentServiceTest {

	private PaymentService paymentService;
	private BillRepo billRepo;
	private BillEntityMapper entityMapper;
	private ContentService contentService;
	private UserService userService;

	@BeforeEach
	public void setup() {
		entityMapper = Mappers.getMapper(BillEntityMapper.class);
		billRepo = Mockito.mock(BillRepo.class);
		contentService = Mockito.mock(ContentService.class);
		userService = Mockito.mock(UserService.class);
		paymentService = new PaymentService(billRepo, contentService, userService, entityMapper);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetBillsSuccess() {
		LocalDateTime now = LocalDateTime.now();
		//@formatter:off
		User user = User.builder()
				.id(15L)
				.firstName("joe")
				.lastName("doe")
				.email("joe@example.com")
				.currentSubscription(Subscription.builder()
						.endDate(now.plusDays(20))
						.startDate(now.minusDays(10))
						.remainingAmount(BigDecimal.TEN)
						.subscriptionType(SubscriptionType.GOLD)
						.build())
				.build();
		UserEntity userEntity = UserEntity.builder()
				.id(15L)
				.firstName("joe")
				.lastName("doe")
				.email("joe@example.com")
				.currentSubscription(SubscriptionEntity.builder()
						.endDate(now.plusDays(20))
						.startDate(now.minusDays(10))
						.remainingAmount(BigDecimal.TEN)
						.subscriptionType(SubscriptionTypeEntity.GOLD)
						.build())
				.build();
		ContentEntity content1 = ContentEntity.builder()
				.name("The Adam Project")
				.type(ContentTypeEntity.SCIENCE_FICTION)
				.build();
		ContentEntity content2 = ContentEntity.builder()
				.name("Yoksullar")
				.type(ContentTypeEntity.COMEDY_SHOW)
				.build();
		BillEntity bill1 = BillEntity.builder()
				.amount(BigDecimal.ONE)
				.id(1L)
				.content(content1)
				.user(userEntity)
				.createdAt(now.minusDays(2L))
				.build();
		BillEntity bill2 = BillEntity.builder()
				.amount(BigDecimal.ONE)
				.id(2L)
				.content(content2)
				.user(userEntity)
				.createdAt(now.minusDays(1L))
				.build();
		//@formatter:on

		Page<BillEntity> page = Mockito.mock(Page.class);
		when(billRepo.findAllByUserId(eq(userEntity.getId()), any(PageRequest.class))).thenReturn(page);
		when(page.getContent()).thenReturn(List.of(bill1, bill2));

		List<Bill> bills = paymentService.getBills(user, 0, 2);
		Assertions.assertNotNull(bills);
		Assertions.assertTrue(bills.size() == 2);
	}

	@Test
	public void testSaveBillSuccess() {
		long billId = 15L;
		LocalDateTime now = LocalDateTime.now();
		//@formatter:off
		User user = User.builder()
				.id(15L)
				.firstName("joe")
				.lastName("doe")
				.email("joe@example.com")
				.currentSubscription(Subscription.builder()
						.endDate(now.plusDays(20))
						.startDate(now.minusDays(10))
						.remainingAmount(BigDecimal.TEN)
						.subscriptionType(SubscriptionType.GOLD)
						.build())
				.build();
		Content content = Content.builder()
				.id(5L)
				.price(BigDecimal.ONE)
				.name("The Adam Project")
				.type(ContentType.SCIENCE_FICTION)
				.build();
		//@formatter:on

		when(billRepo.save(any())).then(ans -> {
			BillEntity entity = ans.getArgument(0);
			entity.setId(billId);
			entity.setBillNo("TR00" + billId);
			return entity;
		});

		Bill bill = paymentService.saveBill(user, content);
		Assertions.assertNotNull(bill);
		Assertions.assertNotNull(bill.getBillNo());
		Assertions.assertTrue(bill.getBillNo().startsWith("TR"));
	}

	@Test
	public void testPurchaseContentSuccess() {
		long contentId = 15L;
		long billId = 45L;
		LocalDateTime now = LocalDateTime.now();
		//@formatter:off
		User user = User.builder()
				.id(15L)
				.firstName("joe")
				.lastName("doe")
				.email("joe@example.com")
				.currentSubscription(Subscription.builder()
						.endDate(now.plusDays(20))
						.startDate(now.minusDays(10))
						.remainingAmount(BigDecimal.TEN)
						.subscriptionType(SubscriptionType.GOLD)
						.build())
				.build();
		Content content = Content.builder()
				.id(contentId)
				.name("The Adam Project")
				.type(ContentType.SCIENCE_FICTION)
				.price(BigDecimal.ONE)
				.build();
		//@formatter:on

		when(contentService.getContent(contentId)).thenReturn(Optional.of(content));
		when(userService.save(any())).thenReturn(user);
		when(billRepo.save(any())).then(ans -> {
			BillEntity entity = ans.getArgument(0);
			entity.setId(billId);
			entity.setBillNo("TR0003");
			return entity;
		});

		Bill bill = paymentService.purchaseContent(user, contentId);
		Assertions.assertNotNull(bill);
		Assertions.assertEquals(billId, bill.getId());
		Assertions.assertNotNull(bill.getBillNo());
		Assertions.assertTrue(bill.getBillNo().startsWith("TR"));
	}

	@Test
	public void testPurchaseSubscriptionSuccess() {
		LocalDateTime now = LocalDateTime.now();
		//@formatter:off
		User user = User.builder()
				.id(15L)
				.firstName("joe")
				.lastName("doe")
				.email("joe@example.com")
				.currentSubscription(Subscription.builder()
						.endDate(now.plusDays(20))
						.startDate(now.minusDays(10))
						.remainingAmount(BigDecimal.TEN)
						.subscriptionType(SubscriptionType.NO_SUBSCRIPTION)
						.build())
				.build();
		//@formatter:on
		when(userService.save(any())).then(ans -> ans.getArgument(0));
		Subscription purchasedSubscription = paymentService.purchaseSubscription(user, SubscriptionType.GOLD);
		Assertions.assertNotNull(purchasedSubscription);
		Assertions.assertEquals(BigDecimal.valueOf(60).setScale(2, RoundingMode.UNNECESSARY),
				purchasedSubscription.getRemainingAmount());
	}
}
