package com.pickupluck.ecogging.domain.file.repository;

import com.pickupluck.ecogging.domain.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {


}
