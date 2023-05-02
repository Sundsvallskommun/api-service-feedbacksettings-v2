package se.sundsvall.contactsettings.integration.db.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEquals;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCode;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanToString;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static com.google.code.beanmatchers.BeanMatchers.registerValueGenerator;
import static java.time.OffsetDateTime.now;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DelegateEntityTest {

	@BeforeAll
	static void setup() {
		registerValueGenerator(() -> now().plusDays(nextInt()), OffsetDateTime.class);
	}

	@Test
	void testBean() {
		assertThat(DelegateEntity.class, allOf(
			hasValidBeanConstructor(),
			hasValidGettersAndSetters(),
			hasValidBeanHashCode(),
			hasValidBeanEquals(),
			hasValidBeanToString()));
	}

	@Test
	void hasValidBuilderMethods() {

		final var agent = ContactSettingEntity.create();
		final var created = now();
		final var filter = "filter";
		final var id = randomUUID().toString();
		final var modified = now();
		final var principal = ContactSettingEntity.create();

		final var entity = DelegateEntity.create()
			.withAgent(agent)
			.withCreated(created)
			.withFilter(filter)
			.withId(id)
			.withModified(modified)
			.withPrincipal(principal);

		assertThat(entity).hasNoNullFieldsOrProperties();
		assertThat(entity.getAgent()).isEqualTo(agent);
		assertThat(entity.getCreated()).isEqualTo(created);
		assertThat(entity.getFilter()).isEqualTo(filter);
		assertThat(entity.getId()).isEqualTo(id);
		assertThat(entity.getModified()).isEqualTo(modified);
		assertThat(entity.getPrincipal()).isEqualTo(principal);
	}

	@Test
	void hasNoDirtOnCreatedBean() {
		assertThat(new DelegateEntity()).hasAllNullFieldsOrProperties();
		assertThat(DelegateEntity.create()).hasAllNullFieldsOrProperties();
	}
}
