package com.project.shopapp.services.user;

import com.project.shopapp.dtos.UserRatingDTO;
import com.project.shopapp.models.UserRating;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.repositories.UserRatingRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRatingService implements IUserRatingService{
    private final UserRatingRepository userRatingRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public UserRating createUserRating(UserRatingDTO userRatingDTO) throws Exception {
        UserRating userRating = UserRating.builder()
                .user(userRepository.findById(userRatingDTO.getUser())
                        .orElseThrow(() -> new Exception("User not found")))
                .product(productRepository.findById(userRatingDTO.getProduct())
                        .orElseThrow(() -> new Exception("Product not found")))
                .rate(userRatingDTO.getRate())
                .build();
        return userRatingRepository.save(userRating);
    }
}
