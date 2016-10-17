package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import play.db.jpa.JPA;

public class ModelList<T> {

    private static class JoinDescription {
        public String name;
        public JoinType type;
        private JoinDescription(String name, JoinType type) {
            this.name = name;
            this.type = type;
        }
    }

    public final CriteriaBuilder cb;
    public final CriteriaQuery<T> query;
    public final Root<T> root;
    public List<Predicate> where = new ArrayList<Predicate>();

    private List<JoinDescription> joins = new ArrayList<JoinDescription>();

    public int pageSize = 20;
    private final Class<T> modelClass;

    public ModelList(Class<T> modelClass) {
        this.modelClass = modelClass;
        cb = JPA.em().getCriteriaBuilder();
        query = cb.createQuery(modelClass);
        root = query.from(modelClass);
    }

    public static <T> ModelList<T> getModelListFor(Class<T> modelClass) {
        return new ModelList(modelClass);
    }

    public List<T> getPagedResults(int page, String orderBy, String order) {
        query.select(root).where(getRestrictions());
        buildOrderBy(orderBy, order);
        TypedQuery<T> tq = JPA.em().createQuery(query);
        tq.setFirstResult(Math.max(0, page - 1) * pageSize);
        tq.setMaxResults(pageSize);
        return tq.getResultList();
    }

    public List<T> getResults(String orderBy, String order) {
        query.select(root).where(getRestrictions());
        buildOrderBy(orderBy, order);
        TypedQuery<T> tq = JPA.em().createQuery(query);
        return tq.getResultList();
    }

    private void buildOrderBy(String orderBy, String order) {
        if (StringUtils.isNotEmpty(orderBy)) {
            Path<Object> path;
            if (orderBy.contains(".")) {
                // order by nested property
                List<String> parts = new ArrayList<String>(Arrays.asList(orderBy.split("\\.")));
                // get first-level property
                path = root.get(parts.remove(0));
                // and drill into...
                for (String s : parts) {
                    path = path.get(s);
                }
            } else {
                path = root.get(orderBy);
            }
            query.orderBy("DESC".equals(order) ? cb.desc(path) : cb.asc(path));
        }
    }

    public Long getCount() {
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> r = cq.from(modelClass);
        for (JoinDescription j : joins) {
            doAddJoin(r, j.name, j.type);
        }
        cq.select(cb.count(r)).where(getRestrictions());
        return JPA.em().createQuery(cq).getSingleResult();
    }

    public Predicate[] getRestrictions() {
        return where.toArray(new Predicate[where.size()]);
    }

    public <X> Join<T, X> addJoin(String attributeName) {
        return addJoin(attributeName, JoinType.INNER);
    }

    public <X> Join<T, X> addJoin(String attributeName, JoinType type) {
        joins.add(new JoinDescription(attributeName, type));
        return (Join<T, X>) doAddJoin(root, attributeName, type);
    }

    private static Join<?, ?> doAddJoin(Root<?> r, String attributeName, JoinType type) {
        if (attributeName.contains(".")) {
            List<String> parts = new ArrayList<String>(Arrays.asList(attributeName.split("\\.")));
            Join<?, ?> join = r.join(parts.remove(0), type);
            for (String s : parts) {
                join = join.join(s, type);
            }
            return join;
        } else {
            return r.join(attributeName, type);
        }
    }
}
