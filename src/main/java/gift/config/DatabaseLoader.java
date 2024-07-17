package gift.config;


import gift.model.Category;
import gift.model.Product;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseLoader {


    @Bean
    public CommandLineRunner initData(ProductRepository repository, CategoryRepository categoryRepository) {
        return args -> {
            // 데이터베이스에 초기 데이터 삽입
            repository.save(new Product(2001L , "[기프티콘] BBQ 황금올리브치킨", 20000, "https://cdn.011st.com/11dims/resize/600x600/quality/75/11src/product/1802204067/B.jpg?315000000" , 1));
            repository.save(new Product(2001L , "스타벅스 기프트 카드 10000원", 10000, "https://image.istarbucks.co.kr/cardImg/20200818/007633_WEB.png" , 2));
            repository.save(new Product(2002L , "스타벅스 기프트 카드 30000원", 30000, "https://image.istarbucks.co.kr/cardImg/20200818/007633_WEB.png" , 2));
            repository.save(new Product(2003L , "스타벅스 기프트 카드 50000원", 50000, "https://image.istarbucks.co.kr/cardImg/20200818/007633_WEB.png" , 2));
            repository.save(new Product(3001L , "1025 독도 크림 80ml", 25600, "https://roundlab.co.kr/web/product/big/202207/96ab1fe05298e73355fb50b04550226b.jpg" , 3));
            repository.save(new Product(4001L , "체크남방", 19900, "https://item.elandrs.com/upload/prd/orgimg/032/2004327032_0000001.jpg?w=1020&h=&q=100" , 4));
            repository.save(new Product(6001L , "인간실격", 8100 , "https://image.yes24.com/goods/1387488/XL" , 6));
            repository.save(new Product(7001L , "윌슨 NBA  콤프 농구공", 85000 , "https://kr.wilson.com/cdn/shop/products/P_WTB7100XB_view_1_1728x.png?v=1660613945" , 7));
            repository.save(new Product(8001L , "우마루", 999999 , "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdRyxSrsBRTPnctpzHYtU3vJggE87JQeSb7A&s" , 8));
            repository.save(new Product(10001L , "삼성 드럼 세탁기 9kg", 700000 , "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT77j3eHCybMMbV-D_xMRz0WPgReRpoSnHprA&s" , 10));
            repository.save(new Product(10002L , "삼성 양문형 냉장고 846 L", 1490000 , "https://images.samsung.com/kdp/goods/2022/09/05/94432d53-fbf7-4052-a68f-a355e6957783.png?$PD_GALLERY_L_PNG$" , 10));
            repository.save(new Product(11001L , "춘식이", 9999 , "https://i.namu.wiki/i/GQMqb8jtiqpCo6_US7jmWDO30KfPB2MMvbdURVub61Rs6ALKqbG-nUATj-wNk7bXXWIDjiLHJxWYkTELUgybkA.webp" , 11));
            repository.save(new Product(11002L , "라이언", 9999 , "https://item.kakaocdn.net/do/a7fd7c0630f8aea8419a565fb2773bbc82f3bd8c9735553d03f6f982e10ebe70" , 11));
            repository.save(new Product(11003L , "어피치", 9999 , "https://item.kakaocdn.net/do/d9ede575178c59ac8f99a073aa704d99effd194bae87d73dd00522794070855d" , 11));


            categoryRepository.save(new Category(1, "교환권", "#6c95d1", "https://png.pngtree.com/png-vector/20230330/ourmid/pngtree-red-ticket-icon-vector-illustration-png-image_6674739.png", ""));
            categoryRepository.save(new Category(2, "상품권", "#6c95d1", "https://imagescdn.gettyimagesbank.com/500/201809/a11186062.jpg", ""));
            categoryRepository.save(new Category(3, "뷰티", "#6c95d1", "https://img.imbc.com/adams/Corner/20175/131391670413239055.jpg", ""));
            categoryRepository.save(new Category(4, "패션", "#6c95d1", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQKm65ftNFrjmtS-ahj9sPyZ7W_ONShws9GfQ&s", ""));
            categoryRepository.save(new Category(5, "식품", "#6c95d1", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR1Xh0h-1FVvS5d0Wxvj38Hu7kBs4KSpUyyPw&s", ""));
            categoryRepository.save(new Category(6, "리빙/도서", "#6c95d1", "https://i.pinimg.com/236x/38/ca/ec/38caeccc9a859f3eb845c9dc91fdcd93.jpg", ""));
            categoryRepository.save(new Category(7, "레저/스포츠", "#6c95d1", "https://aiartshop.com/cdn/shop/files/a-cat-is-surfing-on-big-wave-ai-painting-316.webp?v=1711235460", ""));
            categoryRepository.save(new Category(8, "아티스트/캐릭터", "#6c95d1", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS7CwAhw_DpRjfwSpfzUFKdP-G80zGatPK7Jg&s", ""));
            categoryRepository.save(new Category(9, "유아동/반려", "#6c95d1", "https://img1.newsis.com/2023/07/12/NISI20230712_0001313626_web.jpg", ""));
            categoryRepository.save(new Category(10, "디지털/가전", "#6c95d1", "https://img.animalplanet.co.kr/news/2020/12/18/700/m264759705v49k3nxgn9.jpg", ""));
            categoryRepository.save(new Category(11, "카카오프렌즈", "#6c95d1", "https://blog.kakaocdn.net/dn/cadWYP/btrhVsFqMqq/Q3HWGGl726G9Fs0vN93Dj0/img.jpg", ""));
            categoryRepository.save(new Category(12, "트렌드 선물", "#6c95d1", "https://cdn.hankyung.com/photo/202405/01.36897265.1.jpg", ""));
            categoryRepository.save(new Category(13, "백화점", "#6c95d1", "https://i.namu.wiki/i/uRAh-N8MXDIeQzBevIneGDBTIpZ4JLuqY2POyA-GmqdHcNUr_qjTmhwKcint-NK04dq2d03viq8CRT5cXtkKGA.webp", ""));

        };
    }
}
