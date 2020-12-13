package jpql;

import jpabook.jpashop.domain.Book;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        // 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");

        // 엔티티 매니저는 쓰레드간에 공유X (사용하고 버려야 한다.)
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            entityManager.persist(member);

//            TypedQuery<Member> query1 = entityManager.createQuery("select m from Member m", Member.class);
//            List<Member> resultList = query1.getResultList();
//            for (Member member1 : resultList) {
//                System.out.println("member1 = " + member1);
//            }

//            Member result = query1.getSingleResult();
//            System.out.println("singleResult = " + result);

//            TypedQuery<Member> query = entityManager.createQuery("select m from Member m where m.id = 10", Member.class);
//            Member result = query.getSingleResult();
//            System.out.println("result = " + result);

            TypedQuery<Member> query = entityManager.createQuery("select m from Member m where m.username = :username", Member.class);
            query.setParameter("username", "member1");
            Member singleResult = query.getSingleResult();
            System.out.println("singleResult = " + singleResult.getUsername());

            Member result = entityManager.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();
            System.out.println("result = " + result.getUsername());

//            TypedQuery<String> query2 = entityManager.createQuery("select m.username from Member m", String.class);

//            Query query3 = entityManager.createQuery("select m.username, m.age from Member m");

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
