package inflearn.interview;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TestRepository<T, D> {

    @PersistenceContext
    private final EntityManager em;

    public TestRepository(EntityManager em) {
        this.em = em;
    }

    public T findById(Class<T> clazz, D d){
        return em.find(clazz, d);
    }

    public List<T> findAll(Class<T> clazz){
        return em.createQuery("select u from "+clazz.getSimpleName()+" u", clazz).getResultList();
    }

    @Transactional
    public T save(T entity){
        // if(ObjectUtils.isEmpty(entity.getId())){
        //     em.persist(entity);
        // }else{
        //     em.merge(entity);
        // }
        if (em.contains(entity)) { // DB 에서 해당 entity 객체가 있는지 영속상태를 확인해준다.
            em.merge(entity);
        } else {
            em.persist(entity);
        }
        return entity;
    }

    @Transactional
    public void delete(T entity){ // 삭제할때도 오브젝트를 넣어서 찾는다 !!
        em.remove(entity);
    }
}
