package de.ait_tr.g_36.controller;

import de.ait_tr.g_36.domain.dto.ProductDto;
import de.ait_tr.g_36.domain.entity.Product;
import de.ait_tr.g_36.domain.entity.Role;
import de.ait_tr.g_36.domain.entity.User;
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

    // tokens for admin and user
    private String adminAccessToken;
    private String userAccessToken;

    private final String ADMIN_ROLE_TITLE = "ROLE_ADMIN"; //
    private final String USER_ROLE_TITLE = "ROLE_USER"; //

    private final String TEST_PRODUCT_TITLE = "Test product";
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

    private final String BEARER_PREFIX = "Bearer";
    private final String AUTH_HEADER_NAME = "Authorization";

    @BeforeEach
    public void setUp() {

        template = new TestRestTemplate(); // http
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
    // Видео с 2:

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

        ResponseEntity<ProductDto> response = template.exchange(url, HttpMethod.POST, request, ProductDto.class);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status");
        assertFalse(response.hasBody(), "Response has unexpected body.");

    }

    @Test
    public void negativeSavingProductWithUserAuthorization() {
        // TODO домашнее задание
        headers.put(AUTH_HEADER_NAME, List.of(userAccessToken));
    }

/*    @Test
    @Order(1)
    public void positiveSavingProductWithAdminAuthorization() {
        // Подготавливаем URL для запроса
        String url = "/api/products";

        // Создаем объект ProductDto, который будем сохранять
        ProductDto newProduct = new ProductDto();
        newProduct.setTitle("Test Product");
        newProduct.setDescription("This is a test product.");
        newProduct.setPrice(BigDecimal.valueOf(79.00));

        // Подготавливаем заголовки с токеном администратора
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTH_HEADER_NAME, adminAccessToken);

        // Оборачиваем данные и заголовки в HttpEntity
        HttpEntity<ProductDto> request = new HttpEntity<>(newProduct, headers);

        // Выполняем POST-запрос для сохранения продукта
        ResponseEntity<ProductDto> response = template.postForEntity(url, request, ProductDto.class);

        // Проверяем, что статус ответа 201 Created
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Проверяем, что продукт успешно сохранен и возвращен в ответе
        ProductDto savedProduct = response.getBody();
        Assertions.assertNotNull(savedProduct);
        Assertions.assertEquals(newProduct.getName(), savedProduct.getName());
        Assertions.assertEquals(newProduct.getDescription(), savedProduct.getDescription());
        Assertions.assertEquals(newProduct.getPrice(), savedProduct.getPrice());

    }*/

    @Test
    @Order(2)
    public void negativeGettingProductByIdWithoutAuthorization() {
        // Подготавливаем URL для запроса, добавляя ID продукта
        String url = "/api/products/?id=1"; // Предполагается, что продукт с ID 1 существует

        // Выполняем GET-запрос без авторизации
        ResponseEntity<ProductDto> response = template.exchange(
                url,
                HttpMethod.GET,
                null,
                ProductDto.class
        );

        // Проверяем, что статус ответа 401 Unauthorized
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        // Проверяем, что тело ответа пустое
        Assertions.assertNull(response.getBody(), "Тело ответа должно быть пустым при отсутствии авторизации");

    }

    @Test
    @Order(3)
    public void negativeGettingProductByIdWithBasicAuthorization() {
        // TODO домашнее задание

//        ResponseEntity<ProductDto> response = template
//                .withBasicAuth(TEST_USER_NAME, TEST_PASSWORD)
//                .exchange(url, HttpMethod.POST, request, ProductDto.class);

    }

    @Test
    @Order(4)
    public void negativeGettingProductByIdWithIncorrectToken() {
        // TODO домашнее задание
    }

    @Test
    @Order(5)
    public void positiveGettingProductByIdWithCorrectToken() {
        // TODO домашнее задание
        // TODO удаляем из БД сохранённый тестовый продукт
    }

}