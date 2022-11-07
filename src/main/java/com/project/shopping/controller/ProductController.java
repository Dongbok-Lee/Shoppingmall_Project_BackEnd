package com.project.shopping.controller;


import com.project.shopping.Error.CustomExcpetion;
import com.project.shopping.Error.ErrorCode;
import com.project.shopping.auth.PrincipalDetails;
import com.project.shopping.dto.ProductDTO;
import com.project.shopping.dto.ResponseDTO;
import com.project.shopping.dto.SearchDTO;
import com.project.shopping.dto.requestDTO.ProductRequestDTO.ProductCreateRequestDTO;
import com.project.shopping.dto.requestDTO.ProductRequestDTO.ProductSearchRequestDTO;
import com.project.shopping.dto.requestDTO.ProductRequestDTO.ProductUpdateRequestDTO;
import com.project.shopping.dto.responseDTO.ProductResponseDTO.*;
import com.project.shopping.model.Product;
import com.project.shopping.model.User;
import com.project.shopping.service.CartService;
import com.project.shopping.service.ProductService;
import com.project.shopping.service.ReviewService;
import com.project.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CartService cartService;

    private String ActiveStatus= "active";

    @PostMapping("/api/product/create")
    public ResponseEntity<?> createProduct(Authentication authentication, @RequestBody ProductCreateRequestDTO productCreateRequestDTO) {
        if(authentication == null){
            throw  new CustomExcpetion("허용되지 않은 접근입니다." , ErrorCode.UnauthorizedException);
        }

        Product registeredProduct = productService.create(productCreateRequestDTO,authentication); // 상품 생성
        ProductCreateResponseDTO productCreateResponseDTO = ProductCreateResponseDTO.builder()
                .title(registeredProduct.getTitle())
                .content(registeredProduct.getContent())
                .name(registeredProduct.getName())
                .price(registeredProduct.getPrice())
                .total(registeredProduct.getTotal())
                .imgUrl(registeredProduct.getImgUrl())
                .createDate(registeredProduct.getCreateDate())
                .modifiedDate(registeredProduct.getModifiedDate())
                .build();
        Map<String, Object> result = new HashMap<>();
        result.put("msg", "상품 등록에 성공했습니다.");
        result.put("data", productCreateResponseDTO);
        return ResponseEntity.ok().body(result);
    }


    // 이미지
//    @GetMapping("/p")
//    public void imagepr(HttpServletRequest req){
//        String webPath = "resources/images/imtes/";
//        String folderPath = req.getSession().getServletContext().getRealPath(webPath);
//
//        System.out.println(folderPath);
//
//
//    }

//    @PostMapping(value = "/product/create", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
//    public ResponseEntity<?> upload(Authentication authentication, @RequestPart MultipartFile file, @RequestPart ProductRequestDTO requestDTO, HttpServletRequest request) {
//        String originalFileName = file.getOriginalFilename();
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        String current_date = now.format(dateTimeFormatter);
//        String realPath = request.getSession().getServletContext().getRealPath("/");
//        File destination = new File(realPath);
//        if(! destination.exists()){
//            destination.mkdir();
//        }
//        String path = realPath + current_date+ originalFileName ;
//        File tempFile = null;
//        try {
//            PrincipalDetails userDtails = (PrincipalDetails) authentication.getPrincipal();
//            String email = userDtails.getUser().getEmail();
//            User user = userService.findEmailByUser(email);
//            tempFile = new File(path);
//            file.transferTo(tempFile);
//            System.out.println(path);
//            Product product = Product.builder().userId(user)
//                    .title(requestDTO.getTitle())
//                    .content(requestDTO.getContent())
//                    .name(requestDTO.getName())
//                    .price(requestDTO.getPrice())
//                    .total(requestDTO.getTotal())
//                    .imgUrl(path)
//                    .createDate(Timestamp.valueOf(LocalDateTime.now())).build();
//            Product registeredProduct = productService.create(product); // 상품 생성
//            //System.out.println(registeredProduct.getImgUrl());
//
//            ProductDTO response = ProductDTO.builder()
//                    .productId(registeredProduct.getId())
//                    .useremail(registeredProduct.getUserId().getEmail())
//                    .userId(registeredProduct.getUserId().getUserId())
//                    .userName(registeredProduct.getUserId().getUsername())
//                    .userPhoneNumber(registeredProduct.getUserId().getPhoneNumber())
//                    .title(registeredProduct.getTitle())
//                    .content(registeredProduct.getContent())
//                    .name(registeredProduct.getName())
//                    .price(registeredProduct.getPrice())
//                    .total(registeredProduct.getTotal())
//                    .imgUrl(registeredProduct.getImgUrl())
//                    .createDate(registeredProduct.getCreateDate())
//                    .build();
//            return ResponseEntity.ok().body(response);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(originalFileName);
//        }
//
//    }
//
    @DeleteMapping("/api/product/delete/{id}")
    public ResponseEntity<?>  productdelete(Authentication authentication, @PathVariable(value = "id") int ProductId){

        if(authentication == null){
            throw  new CustomExcpetion("허용되지 않은 접근입니다." , ErrorCode.UnauthorizedException);
        }

        Product product = productService.deleteProduct(authentication, ProductId);

        ProductDeleteResponseDTO productDeleteResponseDTO = ProductDeleteResponseDTO.builder()
                .productId(product.getId())
                .title(product.getTitle())
                .content(product.getContent())
                .name(product.getName())
                .price(product.getPrice())
                .total(product.getTotal())
                .imgUrl(product.getImgUrl())
                .createDate(product.getCreateDate())
                .modifiedDate(product.getModifiedDate())
                .build();
        Map<String, Object> result = new HashMap<>();
        result.put("msg", "상품삭제에 성공했습니다.");
        result.put("data", productDeleteResponseDTO);

        return ResponseEntity.ok().body(result);

    }

    @GetMapping("/api/products")
    private ResponseEntity<?> findall(){
        List<Product> products = productService.getActiveProdcutList(ActiveStatus);
        List<ProductJoinResponseDTO> productdtos = new ArrayList<>();
        for (Product product:products) {
            ProductJoinResponseDTO productProductsResponseDTO = ProductJoinResponseDTO.builder()
                    .productId(product.getId())
                    .title(product.getTitle())
                    .content(product.getContent())
                    .name(product.getName())
                    .price(product.getPrice())
                    .total(product.getTotal())
                    .imgUrl(product.getImgUrl())
                    .createDate(product.getCreateDate())
                    .modifiedDate(product.getModifiedDate())
                    .build();

            productdtos.add(productProductsResponseDTO);

        }
        Map<String , Object> result = new HashMap<>();
        result.put("msg","상품 조회에 성공했습니다.");
        result.put("data", productdtos);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/api/product/search")
    public ResponseEntity<?> searchProudct(@RequestBody ProductSearchRequestDTO productSearchRequestDTO){
        List<Product> productList = productService.getProductList(productSearchRequestDTO.getKeyword(), ActiveStatus);
        List<ProductSearchResponseDTO> response  = new ArrayList<>();

        for(Product product : productList){
            ProductSearchResponseDTO productSearchResponseDTO = ProductSearchResponseDTO.builder()
                    .productId(product.getId())
                    .title(product.getTitle())
                    .content(product.getContent())
                    .name(product.getName())
                    .price(product.getPrice())
                    .total(product.getTotal())
                    .imgUrl(product.getImgUrl())
                    .createDate(product.getCreateDate())
                    .modifiedDate(product.getModifiedDate())
                    .build();
            response.add(productSearchResponseDTO);
        }
        Map<String , Object> result = new HashMap<>();
        result.put("msg","상품검색에 성공했습니다.");
        result.put("data",response);
        return ResponseEntity.ok().body(result);

    }
    // 내가 올린 상품 검색
    @GetMapping("/api/products/user")
    public ResponseEntity<?> findresisterproductuser(Authentication authentication){
        if(authentication == null){
            throw  new CustomExcpetion("허용되지 않은 접근입니다." , ErrorCode.UnauthorizedException);
        }

        List<Product> findallproduct = productService.getEqUserAndActive(authentication, ActiveStatus); // 해당 유저가 등록한 상품들 찾기

        // dto 등록
        List<ProductJoinResponseDTO> response = new ArrayList<>();

        for(Product product: findallproduct){
            ProductJoinResponseDTO productJoinResponseDTO = ProductJoinResponseDTO.builder()
                    .productId(product.getId())
                    .title(product.getTitle())
                    .content(product.getContent())
                    .name(product.getName())
                    .price(product.getPrice())
                    .total(product.getTotal())
                    .imgUrl(product.getImgUrl())
                    .createDate(product.getCreateDate())
                    .modifiedDate(product.getModifiedDate())
                    .build();
            response.add(productJoinResponseDTO);
        }
        Map<String , Object> result = new HashMap<>();
        result.put("msg","상품 조회에 성공했습니다.");
        result.put("data",response);
        return ResponseEntity.ok().body(result);

    }

    @GetMapping("/api/product/{id}")
    public ResponseEntity<?>  productfind(@PathVariable(value = "id") int ProductId){
        //service
        Product findproduct = productService.findById(ProductId);

        // dto
        ProductJoinResponseDTO productJoinResponseDTO = ProductJoinResponseDTO.builder()
                .productId(findproduct.getId())
                .title(findproduct.getTitle())
                .content(findproduct.getContent())
                .name(findproduct.getName())
                .price(findproduct.getPrice())
                .total(findproduct.getTotal())
                .imgUrl(findproduct.getImgUrl())
                .createDate(findproduct.getCreateDate())
                .modifiedDate(findproduct.getModifiedDate())
                .build();
        Map<String, Object> result = new HashMap<>();
        result.put("msg","상품검색에 성공했습니다.");
        result.put("data", productJoinResponseDTO);

        return ResponseEntity.ok().body(result);

    }

    @PutMapping("/api/product/update/{id}")
    public ResponseEntity<?> updateProduct(Authentication authentication, @PathVariable(value = "id") int ProductId, @RequestBody ProductUpdateRequestDTO productUpdateRequestDTO){
        if(authentication == null){
            throw  new CustomExcpetion("허용되지 않은 접근입니다." , ErrorCode.UnauthorizedException);
        }

        Product product = productService.update(authentication, productUpdateRequestDTO, ProductId);

        ProductUpdateResponseDTO productUpdateResponseDTO = ProductUpdateResponseDTO.builder()
                .productId(product.getId())
                .title(product.getTitle())
                .content(product.getContent())
                .name(product.getName())
                .price(product.getPrice())
                .total(product.getTotal())
                .imgUrl(product.getImgUrl())
                .createDate(product.getModifiedDate())
                .modifiedDate(product.getModifiedDate())
                .build();

        Map<String , Object> result = new HashMap<>();
        result.put("msg","상품 수정에 성공했습니다.");
        result.put("data",productUpdateResponseDTO);

        return ResponseEntity.ok().body(result);
    }




}
