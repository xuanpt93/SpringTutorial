package com.example.apidemo1.controller;

import com.example.apidemo1.models.Product;
import com.example.apidemo1.models.ResponseObject;
import com.example.apidemo1.repositories.ProductRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductRespository productRespository;

    @GetMapping("")
    List<Product>  getAllProuct(){
        return productRespository.findAll();
//        finAll đã được khởi tạo trong gói spring bôot
    }

    @GetMapping("/{id}")
//    Optional<Product> finbyId(@PathVariable Long id){
//        return productRespository.findById(id);
//    }

//    Product finbyId(@PathVariable Long id){
//        return productRespository.findById(id).orElseThrow(()-> new RuntimeException("cannot find product id = " +id));
//    }
// Chuẩn hoá đối tượng trả về khi không tìm được sản phẩm id
    //let's return an object with: data, message, status
    //get detail product
    ResponseEntity<ResponseObject> finbyId(@PathVariable Long id){
        Optional<Product> foundProduct = productRespository.findById(id);
        return foundProduct.isPresent() ?

                ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK,"Query Product successfully",foundProduct)
        ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(HttpStatus.NOT_FOUND,"Cannot find product with id = "+id,"empty ")
        );

    }


    @PostMapping("/insert")
    //insert new Product with POST
    ResponseEntity<ResponseObject> inserProduct(@RequestBody Product product){
        List<Product> foundProduct = productRespository.findByProductNameEquals(product.getProductName().trim());
        if (foundProduct.size() >0 ){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(HttpStatus.NOT_IMPLEMENTED,"Product Name already taken","")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK,"Insert success",productRespository.save(product))
        );
    }

    //update, insert = ddoong loat otherwwise
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> update(@RequestBody Product product, @PathVariable Long id){
        Product updateProduct = productRespository.findById(id)
                .map(a -> {
                    a.setProductName(product.getProductName());
                    a.setYear(product.getYear());
                    a.setPrice(product.getPrice());
                    return productRespository.save(product);
                }).orElseGet(()->{
                    product.setId(id);
                    return productRespository.save(product);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK,"Update Succsessfully",updateProduct)

        );
    }
    //delete
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> delete(@PathVariable Long id){
        boolean exits = productRespository.existsById(id);
        if(exits){
            productRespository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(HttpStatus.OK,"Delete product Successfully","")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(HttpStatus.NOT_FOUND, "Cannot find product khong tim thay","")
        );
    }



}
