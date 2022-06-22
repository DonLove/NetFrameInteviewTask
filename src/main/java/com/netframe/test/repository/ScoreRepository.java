
package com.netframe.test.repository;

import com.netframe.test.model.Score;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author liubov
 */
public interface ScoreRepository extends CrudRepository<Score, Long> {
    
}
