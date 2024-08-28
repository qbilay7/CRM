/*package com.golfsprint.GolfSprint.Shop;

import com.golfsprint.GolfSprint.Company.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "golfsprint/api/shop")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping(path = "/getAll/{companyEmail}")
    public List<Product> getProducts(@PathVariable("companyEmail") String companyEmail){
        return productService.getProductsByCompany(companyEmail);
    }
    @GetMapping(path = "/getAllById/{companyId}")
    public List<Product> getProductsById(@PathVariable("companyId") Long companyId){
        return productService.getProductsByCompanyId(companyId);
    }
    @PostMapping(path = "/add/{companyEmail}")
    public ResponseEntity<String> addProduct(@RequestBody Product product, @PathVariable("companyEmail") String companyEmail){
       return productService.addProduct(product, companyEmail);
    }
    @DeleteMapping(path = "/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Long productId){
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Successfully deleted.");
    }
    @PutMapping(path = "/update/{companyEmail}/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable("companyEmail") String companyEmail,@PathVariable("productId") Long productId, @RequestBody Product product){
       return productService.updateProduct(companyEmail,productId, product.getProductName(), product.getDescription(), product.getPrice(), product.getCurrency(), product.getImage(), product.getCategory());
    }
    @GetMapping(path = "/get/{productId}")
    public Product getProduct(@PathVariable("productId") Long productId){
        return productService.getProduct(productId);
    }
    //TODO: Buy function for users
}
*/