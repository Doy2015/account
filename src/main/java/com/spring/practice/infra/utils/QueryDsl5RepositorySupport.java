package com.spring.practice.infra.utils;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.Function;

@Repository
public class QueryDsl5RepositorySupport {

	private final Class domainClass;
	private Querydsl querydsl;
	private EntityManager entityManager;
	private JPAQueryFactory queryFactory;

	public QueryDsl5RepositorySupport(Class<?> domainClass) {
		Assert.notNull(domainClass, "Domain class must not be null!");
		this.domainClass = domainClass;
	}

	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		Assert.notNull(entityManager, "EntityManager must not be null!");
		JpaEntityInformation entityInformation =
				JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager);
		SimpleEntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
		EntityPath path = resolver.createPath(entityInformation.getJavaType());
		this.entityManager = entityManager;
		this.querydsl = new Querydsl(entityManager, new
				PathBuilder<>(path.getType(), path.getMetadata()));
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@PostConstruct
	public void validate() {
		Assert.notNull(entityManager, "EntityManager must not be null!");
		Assert.notNull(querydsl, "Querydsl must not be null!");
		Assert.notNull(queryFactory, "QueryFactory must not be null!");
	}

	protected JPAQueryFactory getQueryFactory() {
		return queryFactory;
	}
	protected Querydsl getQuerydsl() {
		return querydsl;
	}

	protected <T> Page<T> applyPagination(Pageable pageable,
										  Function<JPAQueryFactory, JPAQuery> contentQuery, Function<JPAQueryFactory, JPAQuery> countQuery) {
		JPAQuery jpaContentQuery = contentQuery.apply(getQueryFactory());
		List<T> content = getQuerydsl().applyPagination(pageable,
				jpaContentQuery).fetch();
		JPAQuery<Long> countResult = countQuery.apply(getQueryFactory());
		return PageableExecutionUtils.getPage(content, pageable,
				countResult::fetchOne);
	}
}
