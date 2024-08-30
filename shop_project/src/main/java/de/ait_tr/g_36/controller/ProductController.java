package de.ait_tr.g_36.controller;

import de.ait_tr.g_36.domain.dto.ProductDto;
import de.ait_tr.g_36.exception_nandling.Response;
import de.ait_tr.g_36.exception_nandling.exceptions.FirstTestException;
import de.ait_tr.g_36.service.interfaces.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/products")
@Tag(name = "Product controller", description = "Controller for various operations with Products") // add for Swagger

public class ProductController {

    private final ProductService service; // прижевляем service

    // DI
    public ProductController(ProductService service) {
        this.service = service;
    }

    // CRUD - Create(POST), Read (GET), Update (PUT), Delete (DELETE)

    // Три уровня доступа к приложению:
    // 1. Получить все продукты могут все пользователи, в том числе анонимные
    // 2. Получить продукт по идентификатору могут только аутентифицированные пользователи с любой ролью
    // 3. Сохранить продукт в базу данных может только администратор

    @PostMapping// Create: POST -> localhost:8080/products
    public ProductDto save(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "Instance of a Product")
                    ProductDto product
    ) {
        return service.save(product);
    }

    // Read: GET -> localhost:8080/products?id=3
    @Operation(
            summary = "Get product by id",
            description = "Getting one product that exists in the database by its id"
    )
    @GetMapping
    public ProductDto getById(
            @RequestParam
            @Parameter(description = "Product unique identifier")
            Long id
    ) {
        return service.getById(id);
    }

/*    @GetMapping()
    public List<ProductDto> get(@RequestParam(required = false)Long id){
    //обращаемся к сервису, просим все продукты или по id
        if(id == null){
            return service.getAllActiveProducts();
        }else{
            ProductDto product = service.getById(id);
            return product == null ? null : List.of(product);
        }
    }*/
    @GetMapping("/all")
    public List<ProductDto> getAll() {
    // обращаемся к сервису и запрашиваем все продукты
    return service.getAllActiveProducts();
}

    // Update: PUT -> localhost:8080/products
    @PutMapping()
    public ProductDto update(@RequestBody ProductDto product) {
        return service.update(product);
    }
    @DeleteMapping()
    public void delete(@RequestParam(required = false)Long id,
                       @RequestParam(required = false) String title){
        if(id != null){
            service.deleteById(id);
        }else if(title != null){
            service.deleteByTitle(title);
        }
    }
    @PutMapping("/restore")
    public void restore(@RequestParam Long id){
       service.restoreById(id);
    }
    @GetMapping("/quantity")
    public long getProductQuantity() {
        return service.getAllActiveProductsQuantity();
    }
    @GetMapping("/total-price")
    public BigDecimal getTotalPrice() {
        return service.getAllActiveProductsTotalPrice();
    }
    @GetMapping("average-price")
    public BigDecimal getAveragePrice() {
        return service.getAllActiveProductsAveragePrice();
    }
    // User запросил продукт с wrong id

    // ПЛЮС - точечно настраиваем обработчик ошибок именно для данного контроллера,
    // если нам требуется разная логика обработки исключений в разных контроллерах
    // МИНУС - если нам не требуется разная логика для разных контроллеров,
    // придётся создавать такие обработчики в каждом контроллере отдельно
    @ExceptionHandler(FirstTestException.class)
    public Response handException(FirstTestException e){
        return new Response(e.getMessage());
    }
}
