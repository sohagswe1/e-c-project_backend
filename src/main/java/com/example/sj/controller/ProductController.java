package com.example.sj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.sj.dto.ProductDTO;
import com.example.sj.entity.Category;
import com.example.sj.entity.Product;
import com.example.sj.entity.Seller;
import com.example.sj.entity.User;
import com.example.sj.repository.CategoryRepository;
import com.example.sj.repository.SellerRepository;
import com.example.sj.repository.UserRepository;
import com.example.sj.service.FileStorageService;
import com.example.sj.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SellerRepository sellerRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    /**
     * Helper method to create error response
     */
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        return response;
    }
    
    /**
     * Convert Product Entity to ProductDTO
     */
    private ProductDTO convertToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .image(product.getImage())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .build();
    }
    
    /**
     * Create new product
     */
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            System.out.println("📤 Creating product: " + productDTO.getName());
            System.out.println("   categoryId: " + productDTO.getCategoryId());
            
            // Get current user from authentication
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = authentication.getName();
            
            // Find user and seller
            User user = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            Seller seller = sellerRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Seller profile not found"));
            
            // Get category with better error message
            Category category = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException(
                        "Category not found with ID: " + productDTO.getCategoryId() + 
                        ". Available categories: Please check database or create category first."
                    ));
            
            System.out.println("✓ Category found: " + category.getName());
            
            // Service এ DTO convert হয়ে Entity হবে এবং save হবে
            Product savedProduct = productService.saveFromDTO(productDTO, seller, category);
            
            System.out.println("✓ Product created: " + savedProduct.getId());
            
            // Return DTO response
            return new ResponseEntity<>(convertToDTO(savedProduct), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("✗ Error creating product: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(createErrorResponse(e.getMessage()));
        }
    }
    
    /**
     * Get product by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        try {
            Optional<Product> product = productService.findById(id);
            if (product.isPresent()) {
                return ResponseEntity.ok(convertToDTO(product.get()));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(createErrorResponse("Product not found"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createErrorResponse(e.getMessage()));
        }
    }
    
    /**
     * Get all products
     */
    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productService.findAll();
            List<ProductDTO> productDTOs = products.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(productDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createErrorResponse(e.getMessage()));
        }
    }
    
    /**
     * Get products by current seller
     */
    @GetMapping("/seller")
    public ResponseEntity<?> getSellerProducts() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserEmail = authentication.getName();
            
            User user = userRepository.findByEmail(currentUserEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            Seller seller = sellerRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Seller profile not found"));
            
            // Get products for this seller from repository
            // Note: Add this method to ProductRepository
            List<ProductDTO> productDTOs = productService.findBySeller(seller).stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            
            return ResponseEntity.ok(productDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createErrorResponse(e.getMessage()));
        }
    }
    
    /**
     * Update product
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody ProductDTO productDTO) {
        try {
            Optional<Product> existingProduct = productService.findById(id);
            if (!existingProduct.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Product not found"));
            }
            
            // Service এ DTO convert হয়ে Entity update হবে
            Product updatedProduct = productService.updateFromDTO(id, productDTO);
            return ResponseEntity.ok(convertToDTO(updatedProduct));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createErrorResponse(e.getMessage()));
        }
    }
    
    /**
     * Delete product
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        try {
            Optional<Product> existingProduct = productService.findById(id);
            if (!existingProduct.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(createErrorResponse("Product not found"));
            }
            productService.delete(id);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Product deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createErrorResponse(e.getMessage()));
        }
    }
    
    /**
     * Upload product image
     * 
     * Frontend এ file input এর মাধ্যমে ছবি পাঠাবে
     * ছবি সেভ হবে: http://localhost:8080/upload/filename.jpg এ
     */
    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(createErrorResponse("Please select a file to upload"));
            }
            
            // FileStorageService দিয়ে ফাইল সেভ করা
            String fileName = fileStorageService.storeFile(file);
            
            // Response তে filename দিচ্ছি যাতে frontend database এ সেভ করতে পারে
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("fileName", fileName);
            response.put("imageUrl", "http://localhost:8080/upload/" + fileName);
            response.put("message", "Image uploaded successfully");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(createErrorResponse("Upload failed: " + e.getMessage()));
        }
    }
}
