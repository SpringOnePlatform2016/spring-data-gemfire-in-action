package example.app.config;

import java.util.Properties;

import com.gemstone.gemfire.cache.GemFireCache;
import com.gemstone.gemfire.cache.RegionAttributes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.IndexFactoryBean;
import org.springframework.data.gemfire.IndexType;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.RegionAttributesFactoryBean;

import example.app.RepositoryExampleApplication;
import example.app.model.Contact;

/**
 * Spring @{@link Configuration} class used to configure and bootstrap Apache Geode as an embedded cache
 * used as the application's system of record (SOR).
 *
 * @author John Blum
 * @since 1.0.0
 */
@Configuration
@SuppressWarnings("unused")
public class GemFireConfiguration {

	protected static final String DEFAULT_GEMFIRE_LOG_LEVEL = "config";

	protected String applicationName() {
		return RepositoryExampleApplication.class.getSimpleName();
	}

	protected String logLevel() {
		return System.getProperty("gemfire.log-level", DEFAULT_GEMFIRE_LOG_LEVEL);
	}

	@Bean
	public Properties gemfireProperties() {
		Properties gemfireProperties = new Properties();

		gemfireProperties.setProperty("name", applicationName());
		gemfireProperties.setProperty("mcast-port", "0");
		gemfireProperties.setProperty("log-level", logLevel());

		return gemfireProperties;
	}

	@Bean
	public CacheFactoryBean gemfireCache() {
		CacheFactoryBean gemfireCache = new CacheFactoryBean();

		gemfireCache.setClose(true);
		gemfireCache.setProperties(gemfireProperties());

		return gemfireCache;
	}

	@Bean(name = "Contacts")
	public PartitionedRegionFactoryBean<Long, Contact> contactsRegion(GemFireCache gemfireCache,
			RegionAttributes<Long, Contact> contactsRegionAttributes) {

		PartitionedRegionFactoryBean<Long, Contact> contactsRegion = new PartitionedRegionFactoryBean<>();

		contactsRegion.setAttributes(contactsRegionAttributes);
		contactsRegion.setCache(gemfireCache);
		contactsRegion.setClose(false);
		contactsRegion.setPersistent(false);

		return contactsRegion;
	}

	@Bean
	@SuppressWarnings("unchecked")
	public RegionAttributesFactoryBean contactsRegionAttributes() {
		RegionAttributesFactoryBean contactsRegionAttributes = new RegionAttributesFactoryBean();

		contactsRegionAttributes.setKeyConstraint(Long.class);
		contactsRegionAttributes.setValueConstraint(Contact.class);

		return contactsRegionAttributes;
	}

	@Bean
	@DependsOn("Contacts")
	public IndexFactoryBean emailIndex(GemFireCache gemfireCache) {
		IndexFactoryBean lastNameIndex = new IndexFactoryBean();

		lastNameIndex.setCache(gemfireCache);
		lastNameIndex.setExpression("email");
		lastNameIndex.setFrom("/Contacts");
		lastNameIndex.setName("emailIdx");
		lastNameIndex.setType(IndexType.HASH);

		return lastNameIndex;
	}

	@Bean
	@DependsOn("Contacts")
	public IndexFactoryBean lastNameIndex(GemFireCache gemfireCache) {
		IndexFactoryBean lastNameIndex = new IndexFactoryBean();

		lastNameIndex.setCache(gemfireCache);
		lastNameIndex.setExpression("person.lastName");
		lastNameIndex.setFrom("/Contacts");
		lastNameIndex.setName("personLastNameIdx");
		lastNameIndex.setType(IndexType.HASH);

		return lastNameIndex;
	}
}
