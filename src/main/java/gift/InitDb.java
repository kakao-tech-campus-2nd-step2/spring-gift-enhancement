package gift;

import gift.domain.Category;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitDb {

    private final Init init;

    public InitDb(Init init) {
        this.init = init;
    }

    @PostConstruct
    public void init(){
        init.init1();
    }

    @Component
    @Transactional
    static class Init{
        private final EntityManager em;

        public Init(EntityManager em) {
            this.em = em;
        }

        public void init1(){
            Category category1 = new Category("고기", "#0001");
            Category category2 = new Category("생선", "#0002");
            Category category3 = new Category("음료", "#0003");

            em.persist(category1);
            em.persist(category2);
            em.persist(category3);
        }
    }

}
