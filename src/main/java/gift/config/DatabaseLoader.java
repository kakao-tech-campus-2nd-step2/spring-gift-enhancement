package gift.config;


import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseLoader {


    @Bean
    public CommandLineRunner initData(ProductRepository repository) {
        return args -> {
            // 데이터베이스에 초기 데이터 삽입
            repository.save(new Product(2001L , "[기프티콘] BBQ 황금올리브치킨", 20000, "https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/1802204067/B.jpg?315000000" , 1));
            repository.save(new Product(2001L , "스타벅스 기프트 카드 10000원", 10000, "https://image.istarbucks.co.kr/cardImg/20200818/007633_WEB.png" , 2));
            repository.save(new Product(2002L , "스타벅스 기프트 카드 30000원", 30000, "https://image.istarbucks.co.kr/cardImg/20200818/007633_WEB.png" , 2));
            repository.save(new Product(2003L , "스타벅스 기프트 카드 50000원", 50000, "https://image.istarbucks.co.kr/cardImg/20200818/007633_WEB.png" , 2));
            repository.save(new Product(3001L , "1025 독도 크림 80ml", 25600, "https://roundlab.co.kr/web/product/big/202207/96ab1fe05298e73355fb50b04550226b.jpg" , 3));
            repository.save(new Product(4001L , "체크남방", 19900, "https://item.elandrs.com/upload/prd/orgimg/032/2004327032_0000001.jpg?w=1020&h=&q=100" , 4));
            repository.save(new Product(5001L , "인간실격", 8100 , "https://image.yes24.com/goods/1387488/XL" , 5));
            repository.save(new Product(6001L , "윌슨 NBA  콤프 농구공", 85000 , "https://kr.wilson.com/cdn/shop/products/P_WTB7100XB_view_1_1728x.png?v=1660613945" , 6));
            repository.save(new Product(7001L , "우마루", 999999 , "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdRyxSrsBRTPnctpzHYtU3vJggE87JQeSb7A&s" , 7));
            repository.save(new Product(9001L , "삼성 드럼 세탁기 9kg", 700000 , "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT77j3eHCybMMbV-D_xMRz0WPgReRpoSnHprA&s" , 9));
            repository.save(new Product(10001L , "삼성 양문형 냉장고 846 L", 1490000 , "https://images.samsung.com/kdp/goods/2022/09/05/94432d53-fbf7-4052-a68f-a355e6957783.png?$PD_GALLERY_L_PNG$" , 10));
            repository.save(new Product(11001L , "춘식이", 9999 , "https://i.namu.wiki/i/GQMqb8jtiqpCo6_US7jmWDO30KfPB2MMvbdURVub61Rs6ALKqbG-nUATj-wNk7bXXWIDjiLHJxWYkTELUgybkA.webp" , 11));
            repository.save(new Product(11002L , "라이언", 9999 , "https://item.kakaocdn.net/do/a7fd7c0630f8aea8419a565fb2773bbc82f3bd8c9735553d03f6f982e10ebe70" , 11));
            repository.save(new Product(11003L , "어피치", 9999 , "https://item.kakaocdn.net/do/d9ede575178c59ac8f99a073aa704d99effd194bae87d73dd00522794070855d" , 11));
        };
    }
}
