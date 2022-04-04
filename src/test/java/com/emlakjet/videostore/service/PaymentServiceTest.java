package com.emlakjet.videostore.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;

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
		Content content1 = Content.builder()
				.name("The Adam Project")
				.type(ContentType.SCIENCE_FICTION)
				.build();
		Content content2 = Content.builder()
				.name("Yoksullar")
				.type(ContentType.COMEDY_SHOW)
				.build();
		Bill bill1 = Bill.builder()
				.amount(BigDecimal.ONE)
				.billNo("TR0001")
				.content(content1)
				.user(user)
				.createdAt(now.minusDays(2L))
				.build();
		Bill bill2 = Bill.builder()
				.amount(BigDecimal.ONE)
				.billNo("TR0002")
				.content(content2)
				.user(user)
				.createdAt(now.minusDays(1L))
				.build();
		//@formatter:on

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
		when(billRepo.findAllByEmail(userEntity.getEmail(), any())).thenReturn(page);
		when(page.getContent()).thenReturn(List.of(bill1, bill2));

		List<Bill> bills = paymentService.getBills(user, 0, 2);
		Assertions.assertNotNull(bills);
		Assertions.assertTrue(bills.size() == 2);
	}

	@Test
	public void testSaveBillSuccess() {
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
				.name("The Adam Project")
				.type(ContentType.SCIENCE_FICTION)
				.build();
		//@formatter:on

		Bill bill = paymentService.saveBill(user, content);
		Assertions.assertNotNull(bill);
	}

	@Test
	public void testPurchaseContentSuccess() {

	}

	@Test
	public void testPurchaseSubscriptionSuccess() {

	}
}
