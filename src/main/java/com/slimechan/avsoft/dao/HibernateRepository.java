package com.slimechan.avsoft.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.criterion.Example;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.StreamSupport;

public abstract class HibernateRepository<T, ID extends Serializable> extends AbstractRepository<T, ID> {

    private static SessionFactory sessionFactory = buildSessionFactory();

    protected HibernateRepository(Class<ID> idClass, Class<T> entityClass) {
        super(idClass, entityClass);
    }

    public static <A extends HibernateRepository> A getInstance() {
        return null;
    }

    protected static SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);

            throw new ExceptionInInitializerError("Initial SessionFactory failed" + e);
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

    @Override
    public Optional<T> find(ID id) {
        return Optional.of(
                CompletableFuture.supplyAsync(() -> {
                    Session session = getSessionFactory().openSession();
                    session.beginTransaction();
                    T model = session.get(getEntityClass(), id);
                    session.getTransaction().commit();
                    session.close();

                    return model;
                }).join()
        );
    }

    @Override
    public List<T> findByExample(T example) {
        return
                CompletableFuture.supplyAsync(() -> {
                    Session session = getSessionFactory().openSession();
                    session.beginTransaction();
                    Example criteriaExample = Example.create(example).excludeZeroes();
                    Criteria criteria = session.createCriteria(getEntityClass()).add(criteriaExample);

                    List<T> result = criteria.list();

                    session.close();

                    return result;
                }).join();
    }

    @Override
    public List<T> findAll() {
        return
                CompletableFuture.supplyAsync(() -> {
                    Session session = getSessionFactory().openSession();
                    session.beginTransaction();

                    CriteriaBuilder cb = session.getCriteriaBuilder();
                    CriteriaQuery<T> cq = cb.createQuery(getEntityClass());
                    Root<T> rootEntry = cq.from(getEntityClass());
                    CriteriaQuery<T> all = cq.select(rootEntry);

                    TypedQuery<T> allQuery = session.createQuery(all);

                    List<T> result = allQuery.getResultList();

                    session.close();

                    return result;
                }).join();
    }

    @Override
    public T save(T entity) {
        return CompletableFuture.supplyAsync(() -> {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(entity);
            session.getTransaction().commit();
            session.close();

            return entity;
        }).join();
    }

    @Override
    public Iterable<T> save(Iterable<T> entities) {
        return CompletableFuture.supplyAsync(() -> {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            entities.spliterator().forEachRemaining(session::saveOrUpdate);
            session.getTransaction().commit();
            session.close();

            return entities;
        }).join();
    }

    @Override
    public T delete(ID id) {
        return CompletableFuture.supplyAsync(() -> {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
/*

                    String idField = Arrays.stream(getEntityClass().getDeclaredFields()).parallel()
                            .filter(field -> field.isAnnotationPresent(Id.class))
                            .map(field -> field.getName())
                            .findFirst().orElse("id");

                    String entityName = Arrays.stream(getEntityClass().getAnnotations()).parallel()
                            .filter(annotation -> annotation.equals(Entity.class))
                            .map(annotation -> ((Entity)annotation).name())
                            .findFirst().orElse(getEntityClass().getSimpleName());

                    Query q = session.createQuery("delete from " + entityName + " where " + idField + " = :id ");
                    q.setParameter("id", id);
                    q.executeUpdate();
*/
            T model = session.get(getEntityClass(), id);
            session.getTransaction().commit();
            session.close();

            return model;
        }).join();
    }

    @Override
    public T delete(T entity) {
        return CompletableFuture.supplyAsync(() -> {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            session.close();

            return entity;
        }).join();
    }

    @Override
    public Iterable<T> delete(Iterable<T> entities) {
        return CompletableFuture.supplyAsync(() -> {
            Session session = getSessionFactory().openSession();
            session.beginTransaction();
            StreamSupport.stream(entities.spliterator(), true)
                    .forEach(session::delete);
            session.getTransaction().commit();
            session.close();

            return entities;
        }).join();
    }

}
