import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Test {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HurtowniaAGDRTV");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		
		entityManager.close();
		entityManagerFactory.close();
	}

}
