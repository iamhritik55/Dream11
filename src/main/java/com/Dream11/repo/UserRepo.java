package com.Dream11.repo;
import java.util.List;
import com.Dream11.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends MongoRepository<User,Integer> {
//    @Modifying
//    public void updateChosenPlayerIdList(@Param("userId") int userId, @Param("receivedListOfPlayerIds") List<Integer> receivedListOfPlayerIds);
}
