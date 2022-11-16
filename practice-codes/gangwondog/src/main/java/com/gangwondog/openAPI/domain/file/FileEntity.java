package com.gangwondog.openAPI.domain.file;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@Table(name = "tb_file")
@Entity
public class FileEntity {

  @Id
  @Column(name = "id", columnDefinition = "INT UNSIGNED")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "use_at")
  private String useAt;

  @Column(name = "create_date")
  private Instant createDate;

}
