package com.configServer.repository;

import com.configServer.model.ZuulRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZuulRouteRepository extends JpaRepository<ZuulRoute, Integer> {


}
