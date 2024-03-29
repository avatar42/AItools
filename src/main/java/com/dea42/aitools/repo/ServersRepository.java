package com.dea42.aitools.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dea42.aitools.entity.Servers;

/**
 * Title: ServersRepository <br>
 * Description: Class for the Servers Repository. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Repository
public interface ServersRepository extends CrudRepository<Servers, Integer>,
JpaSpecificationExecutor<Servers> {
}
