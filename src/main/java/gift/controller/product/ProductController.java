package gift.controller.product;

import gift.domain.product.Product;
import gift.domain.product.ProductRequestDTO;
import gift.service.product.ProductService;
import gift.util.ImageStorageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        Page<Product> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {
        Product product = new Product(productRequestDTO.getName(), productRequestDTO.getPrice(),
                productRequestDTO.getDescription(), productRequestDTO.getImageUrl(), null);
        productService.addProduct(product, productRequestDTO.getCategoryId());
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                 @RequestBody @Valid ProductRequestDTO productRequestDTO) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        product.update(productRequestDTO.getName(), productRequestDTO.getPrice(),
                productRequestDTO.getDescription(), productRequestDTO.getImageUrl(), null);

        productService.updateProduct(product, productRequestDTO.getCategoryId());

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/imageUpload")
    public ResponseEntity<String> uploadImage(@RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        String imagePath = ImageStorageUtil.saveImage(imageFile);
        String imageUrl = ImageStorageUtil.encodeImagePathToBase64(imagePath);
        return ResponseEntity.ok(imageUrl);
    }

    @PostMapping("/{id}/imageUpdate")
    public ResponseEntity<Product> updateProductImage(@PathVariable Long id,
                                                      @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        Product product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
            ImageStorageUtil.deleteImage(ImageStorageUtil.decodeBase64ImagePath(product.getImageUrl()));
        }

        String imagePath = ImageStorageUtil.saveImage(imageFile);
        String imageUrl = ImageStorageUtil.encodeImagePathToBase64(imagePath);
        product.updateImage(imageUrl);
        productService.updateProduct(product, product.getCategory().getId());
        return ResponseEntity.ok(product);
    }

    @GetMapping(value = "/{base64EncodedPath}/imageView", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImageByEncodedPath(@PathVariable String base64EncodedPath) throws IOException {
        String imagePath = ImageStorageUtil.decodeBase64ImagePath(base64EncodedPath);
        byte[] imageBytes = java.nio.file.Files.readAllBytes(new File(imagePath).toPath());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
}
