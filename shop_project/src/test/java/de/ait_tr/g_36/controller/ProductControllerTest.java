package de.ait_tr.g_36.controller;


import de.ait_tr.g_36.domain.dto.ProductDto;
import de.ait_tr.g_36.domain.entity.Product;
import de.ait_tr.g_36.domain.entity.Role;
import de.ait_tr.g_36.domain.entity.User;
import de.ait_tr.g_36.repository.ProductRepository;
import de.ait_tr.g_36.repository.RoleRepository;
import de.ait_tr.g_36.repository.UserRepository;
import de.ait_tr.g_36.security.sec_dto.TokenResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // задаем порядок прохождения тестов
class ProductControllerTest {

    @LocalServerPort
    private int port; // port for Tomcat

    @Autowired // отдаем под управление Spring
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private TestRestTemplate template; // send http request to Tomcat
    private HttpHeaders headers; // with headers
    private ProductDto testProduct; // product for test
    private Long testProductId;

    @Autowired
    private ProductRepository productRepository; // Добавляем ProductRepository

    // tokens for admin and user
    private String adminAccessToken;
    private String userAccessToken;

    private final String ADMIN_ROLE_TITLE = "ROLE_ADMIN"; //
    private final String USER_ROLE_TITLE = "ROLE_USER"; //

    private final String TEST_PRODUCT_TITLE = "Test product 1";
    private final BigDecimal TEST_PRODUCT_PRICE = new BigDecimal(99);

    private final String TEST_ADMIN_NAME = "Test Admin";
    private final String TEST_USER_NAME = "Test User";
    private final String TEST_PASSWORD = "Test password";

    // constants for login to server
    private final String URL_PREFIX = "http://localhost:";
    private final String AUTHORIZATION_RESOURCE = "/auth";
    private final String PRODUCTS_RESOURCE = "/products";
    private final String LOGIN_ENDPOINT = "/login";
    private final String ALL_ENDPOINT = "/all";
    private final String ID_PARAM_TITLE = "?id=";

    private final String BEARER_PREFIX = "Bearer ";
    private final String AUTH_HEADER_NAME = "Authorization";

    @BeforeEach
    public void setUp() {


        template = new TestRestTemplate(); // http - протокол
        headers = new HttpHeaders();

        // product for test
        testProduct = new ProductDto();
        testProduct.setTitle(TEST_PRODUCT_TITLE);
        testProduct.setPrice(TEST_PRODUCT_PRICE);

        BCryptPasswordEncoder encoder = null;

        Role roleAdmin;
        Role roleUser = null;

        User admin = userRepository.findByUsername(TEST_ADMIN_NAME).orElse(null);
        User user = userRepository.findByUsername(TEST_USER_NAME).orElse(null);

        // check if admin is NOT exists in database
        if(admin == null){
            encoder = new BCryptPasswordEncoder();
            roleAdmin = roleRepository.findByTitle(ADMIN_ROLE_TITLE).orElse(null);
            roleUser = roleRepository.findByTitle(USER_ROLE_TITLE).orElse(null);

            if(roleAdmin == null || roleUser == null){
                throw new RuntimeException("The database doesn't have nessesary roles");
            }

            // create new admin
            admin = new User();
            admin.setUsername(TEST_ADMIN_NAME);
            admin.setPassword(encoder.encode(TEST_PASSWORD));
            admin.setRoles(Set.of(roleAdmin, roleUser));

            // save admin
            userRepository.save(admin);
        }

        // check if user exists
        if (user == null) {
            encoder = encoder == null ? new BCryptPasswordEncoder() : encoder;
            roleUser = roleUser == null ?
                    roleRepository.findByTitle(USER_ROLE_TITLE).orElse(null) : roleUser;

            if (roleUser == null) {
                throw new RuntimeException("The database doesn't have necessary roles");
            }
            // create new user
            user = new User();
            user.setUsername(TEST_USER_NAME);
            user.setPassword(encoder.encode(TEST_PASSWORD));
            user.setRoles(Set.of(roleUser));

            // save user
            userRepository.save(user);
        }

        // start Authentication
        admin.setPassword(TEST_PASSWORD);
        admin.setRoles(null);

        user.setPassword(TEST_PASSWORD);
        user.setRoles(null);

        // http request
        // POST -> http://localhost:8080/auth/login
        String url = URL_PREFIX + port + AUTHORIZATION_RESOURCE + LOGIN_ENDPOINT;
        HttpEntity<User> request = new HttpEntity<>(admin, headers); // headers - token со всей инфой

        // call to server
        ResponseEntity<TokenResponseDto> response = template
                .exchange(url, HttpMethod.POST, request, TokenResponseDto.class);
        // check response
        assertTrue(response.hasBody(), "Authorization response body is empty");
        adminAccessToken = BEARER_PREFIX + response.getBody().getAccessToken();

        request = new HttpEntity<>(user, headers);

        response = template
                .exchange(url, HttpMethod.POST, request, TokenResponseDto.class);
        assertTrue(response.hasBody(), "Authorization response body is empty");
        userAccessToken = BEARER_PREFIX + response.getBody().getAccessToken();

    }


    // ниже этого теста к моменту запуска ничего не было. Были поставлены BreackPoint-ы и после теста Выключены(Mute)
    //@Test
    //void test(){}

    @Test
    public void positiveGettingAllProductsWithoutAuthorization(){
        // Подготавливаем URL для запроса
        String url = URL_PREFIX + port + PRODUCTS_RESOURCE + ALL_ENDPOINT;
        // request
        HttpEntity<Void> request = new HttpEntity<>(headers);
        // response    Выполняем GET-запрос без авторизации
        ResponseEntity<ProductDto[]> response = template.exchange(
                url,
                HttpMethod.GET,
                request,
                ProductDto[].class);
        // Проверяем, что статус ответа 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected status");
        assertTrue(response.hasBody(), "Response doesn't have a body.");
    }


    @Test
    public void negativeSavingProductWithoutAuthorization(){

        String url = URL_PREFIX + port + PRODUCTS_RESOURCE;
        HttpEntity<ProductDto> request = new HttpEntity<>(testProduct, headers);

        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.POST, request, ProductDto.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");
        assertFalse(response.hasBody(), "Response has unexpected body.");

    }

    @Test
    public void negativeSavingProductWithUserAuthorization() {
        // TODO домашнее задание
        String url = URL_PREFIX + port + PRODUCTS_RESOURCE;
        headers.put(AUTH_HEADER_NAME, List.of(userAccessToken));
        // Оборачиваем данные и заголовки в HttpEntity
        HttpEntity<ProductDto> entity = new HttpEntity<>(testProduct, headers);
        ResponseEntity<ProductDto> responce = template
                .exchange(url, HttpMethod.POST, entity, ProductDto.class);

        assertEquals(HttpStatus.FORBIDDEN, responce.getStatusCode(), "Response has unexpected status");

    }

   @Test
   @Order(1)
/*    public void positiveSavingProductWithAdminAuthorization() {
        String url = URL_PREFIX + port + PRODUCTS_RESOURCE;
        headers.put(AUTH_HEADER_NAME, List.of(adminAccessToken));
        HttpEntity<ProductDto> entity = new HttpEntity<>(testProduct, headers);
        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.POST, entity, ProductDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected status");
        ProductDto savedProduct = response.getBody();
        assertNotNull(savedProduct, "Response body doesn't have a saved product");
        assertEquals(testProduct.getTitle(), savedProduct.getTitle(), "Saved product has unexpected title");
        testProductId = savedProduct.getId();
    }*/
   public void positiveSavingProductWithAdminAuthorization() {
       String url = URL_PREFIX + port + PRODUCTS_RESOURCE;
       headers.put(AUTH_HEADER_NAME, List.of(adminAccessToken));
       HttpEntity<ProductDto> entity = new HttpEntity<>(testProduct, headers);

       // Изменение: Добавлены логгирование и правильная проверка статуса
       ResponseEntity<ProductDto> response = template.exchange(url, HttpMethod.POST, entity, ProductDto.class);

       System.out.println("Received response status: " + response.getStatusCode()); // Изменено: Логируем статус ответа
       assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected status");

       ProductDto savedProduct = response.getBody();
       assertNotNull(savedProduct, "Response has unexpected body");
       assertEquals(testProduct.getTitle(), savedProduct.getTitle(), "Saved product has unexpected title");

       testProductId = savedProduct.getId(); // Изменено: Сохранение ID созданного продукта
       System.out.println("testProductID from upTest: " + testProductId); // Изменено: Логирование ID продукта
   }
    @Test
    @Order(2)
    public void negativeGettingProductByIdWithoutAuthorization() {
        // Формируем URL для запроса продукта по его ID
        String url = URL_PREFIX + port + PRODUCTS_RESOURCE + ID_PARAM_TITLE
                .replace("{id}", "3");

        // Очищаем заголовки, чтобы запрос был без авторизации
        headers.clear();

        // Оборачиваем заголовки в HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Выполняем запрос и получаем ответ
        ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, entity, String.class);

        // Проверяем, что ответ имеет статус 401 Unauthorized
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");
    }

    @Test
    @Order(3)
    public void negativeGettingProductByIdWithBasicAuthorization() {
        // TODO домашнее задание

//        ResponseEntity<ProductDto> response = template
//                .withBasicAuth(TEST_USER_NAME, TEST_PASSWORD)
//                .exchange(url, HttpMethod.POST, request, ProductDto.class);
        // Формируем URL для запроса продукта по его ID
        String url = URL_PREFIX + port + PRODUCTS_RESOURCE + ID_PARAM_TITLE + "1"; // замените "1" на нужный ID для теста

        // Создаем шаблон REST с базовой авторизацией
        TestRestTemplate templateWithAuth = template.withBasicAuth(TEST_USER_NAME, TEST_PASSWORD);

        // Оборачиваем заголовки в HttpEntity (в данном случае, тело не требуется для GET-запроса)
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Выполняем GET-запрос с базовой авторизацией
        ResponseEntity<ProductDto> response = templateWithAuth.exchange(url, HttpMethod.GET, entity, ProductDto.class);

        // Проверяем, что ответ имеет статус 403 Forbidden, так как доступ должен быть запрещен
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");

        // Проверяем, что тело ответа пустое, так как доступ к ресурсу запрещен
        assertFalse(response.hasBody(), "Response should not have a body.");
    }

    @Test
    @Order(4)
    public void negativeGettingProductByIdWithIncorrectToken() {
        // TODO домашнее задание
        // Формируем URL для запроса продукта по его ID
        String url = URL_PREFIX + port + PRODUCTS_RESOURCE + ID_PARAM_TITLE + "1"; // Замените "1" на нужный ID для теста

        // Устанавливаем некорректный токен в заголовок
        headers.clear();
        String incorrectToken = "Bearer invalid_token";
        headers.put(AUTH_HEADER_NAME, List.of(incorrectToken));

        // Оборачиваем заголовки в HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Выполняем GET-запрос с неверным токеном
        ResponseEntity<ProductDto> response = template.exchange(url, HttpMethod.GET, entity, ProductDto.class);

        // Проверяем, что ответ имеет статус 401 Unauthorized или 403 Forbidden
        assertTrue(response.getStatusCode() == HttpStatus.UNAUTHORIZED || response.getStatusCode() == HttpStatus.FORBIDDEN,
                "Response has unexpected status");

        // Проверяем, что тело ответа пустое, так как доступ к ресурсу запрещен
        assertFalse(response.hasBody(), "Response should not have a body.");
    }

    @Test
    @Order(5)
    public void positiveGettingProductByIdWithCorrectToken() {
        // TODO домашнее задание
        // Формируем URL для запроса продукта по его ID
        System.out.println("testProductID: " + testProductId);
        String url = URL_PREFIX + port + PRODUCTS_RESOURCE + ID_PARAM_TITLE + testProductId; // Используем testProductId, сохраненный ранее

        // Устанавливаем корректный токен администратора в заголовок
        headers.clear();
        headers.put(AUTH_HEADER_NAME, List.of(adminAccessToken));

        // Оборачиваем заголовки в HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Выполняем GET-запрос с корректным токеном
        ResponseEntity<ProductDto> response = template.exchange(url, HttpMethod.GET, entity, ProductDto.class);

        // Проверяем, что ответ имеет статус 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected status");

        // Проверяем, что тело ответа не пустое и содержит ожидаемый продукт
        assertTrue(response.hasBody(), "Response should have a body.");
        ProductDto receivedProduct = response.getBody();
        assertNotNull(receivedProduct, "Received product should not be null.");

        // Дополнительно проверяем, что полученный продукт соответствует ожидаемому
        assertEquals(testProductId, receivedProduct.getId(), "Product ID does not match.");
        assertEquals(testProduct.getTitle(), receivedProduct.getTitle(), "Product title does not match.");
        assertEquals(testProduct.getPrice(), receivedProduct.getPrice(), "Product price does not match.");
    }




                            // TODO удаляем из БД сохранённый тестовый продукт


/*        @AfterEach
    public void cleanUp() {
            System.out.println("ProductID = " + testProductId);
            if (testProductId != null) {
                // Удаляем продукт по ID
                productRepository.deleteById(testProductId);

                // Проверяем, что продукт был удален
                boolean productExists = productRepository.existsById(testProductId);
                assertFalse(productExists, "Test product was not deleted from the database.");
                testProductId = null;
            }

        }*/
       @AfterEach
       public void cleanUp() {
           if (testProductId != null) {
               // Формируем URL для удаления продукта по его ID
               String url = URL_PREFIX + port + PRODUCTS_RESOURCE + ID_PARAM_TITLE + testProductId;

               // Устанавливаем корректный токен администратора в заголовок
               headers.clear();
               headers.put(AUTH_HEADER_NAME, List.of(adminAccessToken));

               // Оборачиваем заголовки в HttpEntity
               HttpEntity<Void> entity = new HttpEntity<>(headers);

               // Выполняем DELETE-запрос для удаления продукта
               ResponseEntity<Void> response = template.exchange(url, HttpMethod.DELETE, entity, Void.class);

               // Проверяем, что продукт был успешно удален (например, статус 200 OK)
               assertEquals(HttpStatus.OK, response.getStatusCode(), "Failed to delete the test product.");

               // Сбрасываем ID тестового продукта
               testProductId = null;
           }

       }


}