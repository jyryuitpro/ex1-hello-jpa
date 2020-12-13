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
            Team team = new Team();
            team.setName("teamA");
            entityManager.persist(team);

            Member member = new Member();
            member.setUsername("관리자");
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.setTeam(team);
            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();

//            String query = "select concat('a', 'b') from Member m";
//            String query = "select locate('de','abcdefg') from Member m";
//            String query = "select size(t.members) from Team t";
            String query = "select function('group_concat', m.username) from Member m";

//            List<Integer> result = entityManager.createQuery(query, Integer.class).getResultList();
//            for (Integer s : result) {
//                System.out.println("s = " + s);
//            }

            List<String> result = entityManager.createQuery(query, String.class).getResultList();
            for (String s : result) {
                System.out.println("s = " + s);
            }

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
